package com.classes;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Every class is extending Users class.This class has the attributes and methods that every kind of user must have
 * Any user has a username, password, firstname, surname, age and is able to Login and Logout from the website
 */
public class Users
{
    // Basic characteristics of each user
    private String username, password, firstname, surname;
    private boolean loggedOn;
    private int age;

    static Enumeration<String> attributes; //Enumeration list that contains user's session attribute names

    // 'UsersCount' variable counts the number of users
    private static int UsersCount = 0;

    public Users(String username, String password, String firstname, String surname, int age)
    {
        this.username  = username;
        this.password  = password;
        this.firstname = firstname;
        this.surname   = surname;
        this.age       = age;
        this.loggedOn  = false;
        UsersCount++;
    }

    /**
     * Provides an error HTML page with a title "Something went wrong!". A user can provide an error message to  specify
     * what went wrong during any process of registration or login.
     * @param response A response object to provide an HTML page.
     * @param reason An error message specifying what went wrong.
     * @throws IOException
     */
    public static void Fail(HttpServletResponse response, String reason, String redirect_to) throws IOException
    {
        if (reason.isBlank())
            reason = "An unknown error occurred. Please try again.";

        PrintWriter writer = response.getWriter();

        // show an html web page back to the client
        writer.println("<!DOCTYPE html>");

        writer.println("<html>");
        writer.println("<head>");
        writer.println("<title>Error during register</title>");
        writer.println("<meta http-equiv = \"refresh\" content = \"5; url = "+ redirect_to +" \" />"); // redirect page after 7 seconds
        writer.println("<link rel=\"stylesheet\" href=\"CSS/styles.css\">"); // use styles from the styles.css file
        writer.print("</head>");
        writer.println("<body>");
        writer.println("<form>");
        writer.println("<div class=\"imgcontainer\">");
        writer.println("<img src=\"img/logo1.png\" alt=\"logo_image\" class=\"avatar\">");
        writer.println("</div>");
        writer.println("<article>");
        writer.println("<h1> Something went wrong! </h1>");
        writer.println("<h3> " + reason + "</h3>");
        writer.println("</article>");
        writer.println("</form>");
        writer.println("</body>");

        writer.println("</html>");
    }

    /**
     * Registers a Patient. Preceding the injection, all fields are carefully processed and tested for duplicates in the database.
     * If patient's register is successful, he is being redirected to 'patient_main_environment.jsp' page and his data are being
     * stored into the database.
     *
     * @param response A Servlet response required to provide error information.
     * @param dataSource A Datasource to inject SQL statements into.
     * @throws IOException if anything goes wrong with the HttpServletResponse.
     */
    public void Register(HttpServletRequest request, HttpServletResponse response, DataSource dataSource, String register_page) throws IOException
    {
        //Checks for all the fields. We use Users.Fail() to provide a plain-text HTML page to print any errors.

        if (this.getUsername().isBlank())
        {
            Fail(response, "Invalid Username! A username cannot be blank.",register_page);
            return;
        }

        else if (this.getPassword().length() < 4)
        {
            Fail(response, "Provide a password with at least 4 characters.",register_page);
            return;
        }

        else if (!this.getFirstname().matches("[A-Z][a-z]+"))
        {
            Fail(response, "Invalid Firstname! All first/last names must start with one capital letter with a succeeding lowercase letter. No other characters, other than letters, are allowed.",register_page);
            return;
        }

        else if (!this.getSurname().matches("[A-Z][a-z]+"))
        {
            Fail(response, "Invalid Lastname! All first/last names must start with one capital letter with a succeeding lowercase letter. No other characters, other than letters, are allowed.",register_page);
            return;
        }

        else if (this.getAge() > 119 || this.getAge() <= 0)
        {
            Fail(response, "Invalid Age! A registered age cannot be greater than 119 years or a non-positive number.",register_page);
            return;
        }

        else if (this instanceof Patient && !((Patient)this).getAMKA().matches("[0-9]{11}") ||
                this instanceof Doctor  && !((Doctor)this).getAMKA().matches("[0-9]{11}"))
        {

            Fail(response, "Invalid AMKA! A social security number must have exactly 11 digits.",register_page);
            return;
        }

        else if (this instanceof Doctor && !((Doctor)this).getSpeciality().equals("Pathologist") &&
                !((Doctor)this).getSpeciality().equals("Ophthalmologist") && !((Doctor)this).getSpeciality().equals("Orthopedist"))
        {
            Fail(response, "Doctors can have only one of the following specialities: Pathologist, Ophthalmologist and Orthopedist",register_page);
            return;
        }

        //if we get to this point it means none of the fields are incorrect. we can execute sql statements safely.
        //checking for duplicates in the database

        try
        {
            //Check if there are any duplicates in the database what comes to AMKA and username.
            //preparing an sql statement
            Connection connection = dataSource.getConnection();
            PreparedStatement statement;
            String selectquery, insertquery;
            ResultSet rs;
            Integer age = this.getAge();
            String username = this.getUsername(), password = this.getPassword(), name = this.getFirstname(), surname = this.getSurname();
            String user;
            String salt;

            if(this instanceof Patient)
            {
                selectquery = "SELECT * FROM patient WHERE patientAMKA=? OR username=?";
                statement = connection.prepareStatement(selectquery);
                statement.setString(1, ((Patient)this).getAMKA());
                statement.setString(2, username);

                rs = statement.executeQuery();

                if (rs.next())
                {
                    Fail(response, "This username/AMKA is already taken!", register_page);
                    rs.close();
                    connection.close();
                    return;
                }

                statement = connection.prepareStatement("SELECT * FROM doctor WHERE doctorAMKA=?");
                statement.setString(1, ((Patient)this).getAMKA());

                rs = statement.executeQuery();

                if (rs.next())
                {
                    Fail(response, "This AMKA is already taken by a Doctor!", register_page);
                    rs.close();
                    connection.close();
                    return;
                }

                salt = createSalt(response);

                insertquery = "INSERT INTO patient (username,hashedpassword,name,surname,age,patientAMKA,salt) VALUES (?,?,?,?,?,?,?)";
                statement = connection.prepareStatement(insertquery);
                statement.setString(1, username);
                statement.setString(2, hashPassword(password, salt));
                statement.setString(3, name);
                statement.setString(4, surname);
                statement.setString(5, age.toString()); //age, as a parameter is an Integer (not an int), so we convert it instantly to string.
                statement.setString(6, ((Patient)this).getAMKA());
                statement.setString(7, salt);

                statement.execute();
                user="Patient";
            }
            else if(this instanceof Doctor)
            {
                selectquery = "SELECT * FROM doctor WHERE doctorAMKA=? OR username=?";
                statement = connection.prepareStatement(selectquery);
                statement.setString(1, ((Doctor)this).getAMKA());
                statement.setString(2, username);

                rs = statement.executeQuery();

                if (rs.next())
                {
                    Fail(response, "This username/AMKA is already taken!", register_page);
                    rs.close();
                    connection.close();
                    return;
                }

                statement = connection.prepareStatement("SELECT * FROM patient WHERE patientAMKA=?");
                statement.setString(1, ((Doctor)this).getAMKA());

                rs = statement.executeQuery();

                if (rs.next())
                {
                    Fail(response, "This AMKA is already taken by a Patient!", register_page);
                    rs.close();
                    connection.close();
                    return;
                }

                salt = createSalt(response);

                insertquery = "INSERT INTO doctor (username,hashedpassword,name,surname,age,doctorAMKA,specialty,ADMIN_username,salt) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                statement = connection.prepareStatement(insertquery);
                statement.setString(1, username);
                statement.setString(2, hashPassword(password, salt));
                statement.setString(3, name);
                statement.setString(4, surname);
                statement.setString(5, age.toString());       //age, as a parameter is an Integer (not an int), so we convert it instantly to string.
                statement.setString(6, ((Doctor)this).getAMKA());
                statement.setString(7, ((Doctor)this).getSpeciality());
                statement.setString(8, request.getSession().getAttribute("adminusername").toString());   //when an admin tries to add a doctor, we have to store which admin made that doctor in database
                statement.setString(9, salt);                                                               //We can get admin's username from the session attribute "username"

                statement.execute();
                user="Doctor";
            }
            else
            {
                selectquery = "SELECT * FROM admin WHERE username=?";
                statement = connection.prepareStatement(selectquery);
                statement.setString(1, this.getUsername());

                rs = statement.executeQuery();

                if (rs.next())
                {
                    Fail(response, "This username is already taken!", register_page);
                    rs.close();
                    connection.close();
                    return;
                }

                salt = createSalt(response);

                insertquery = "INSERT INTO admin (username,hashedpassword,name,surname,age,salt) VALUES (?, ?, ?, ?, ?, ?)";
                statement = connection.prepareStatement(insertquery);

                statement.setString(1, username);
                statement.setString(2, hashPassword(password, salt));
                statement.setString(3, name);
                statement.setString(4, surname);
                statement.setString(5, age.toString()); //age, as a parameter is an Integer (not an int), so we convert it instantly to string.
                statement.setString(6, salt);

                statement.execute();
                user="Administrator";
            }

            request.setAttribute("user", user);
            request.setAttribute("redirect",register_page);
            RequestDispatcher rd = request.getRequestDispatcher("register-success.jsp");
            rd.forward(request, response);

            rs.close();
            connection.close();
        }
        catch (Exception exception)
        {
            //if anything goes wrong it'll be printed on the user's screen.
            Fail(response, "Cannot insert data. Exception message: \n" + exception.getMessage(), register_page);
            exception.printStackTrace();
        }

    }

    /**
     * Logs in a user, specified from the login page with all specified credentials.
     *
     * Makes a connection with the database, it is
     * searching the patient's attributes from it,
     * it initializes the patient object with these attributes
     * and redirects him to the corresponding page.
     *
     * If the user is not found, it redirects him to the 'fail.html' page.
     *
     * @param type The type of user to be logged in. (Patient/Doctor/Admin)
     * @param request An HTTPServletRequest to acquire the username and password.
     * @param response An HTTPServletResponse to redirect the user accordingly.
     * @param datasource The datasource required to search the username and password.
     * @return The user, if found in the database. If the user is not found, returns null.
     */
    public static void Login(String type, HttpServletRequest request, HttpServletResponse response, DataSource datasource) throws IOException
    {
        HttpSession user_session = request.getSession();

        String name = request.getParameter("username");
        String pass = request.getParameter("password");
        String table;

        try
        {
            //establishing a connection to the database.
            Connection con = datasource.getConnection();

            //specifying the type of user to log on.
            table = type.toLowerCase();

            //preparing a general statement.
            PreparedStatement stmnt = con.prepareStatement("SELECT hashedpassword,salt FROM `"+ table +"` WHERE username=?");
            stmnt.setString(1, name);

            ResultSet rs = stmnt.executeQuery();

            //if this username exists and the password is correct
            if(rs.next() && hashPassword(pass, rs.getString("salt")).equals(rs.getString("hashedpassword")))
            {
                stmnt = con.prepareStatement("SELECT * FROM `"+ table +"` WHERE username=?");
                stmnt.setString(1,name);

                rs = stmnt.executeQuery();
                rs.next();

                String previous_attribute;

                while(attributes != null && attributes.hasMoreElements())
                {
                    previous_attribute = (String) attributes.nextElement();
                    user_session.removeAttribute(previous_attribute);
                }

                switch (type)
                {
                    case "Patient":
                        user_session.setAttribute("patientusername" , rs.getString("username"));
                        user_session.setAttribute("name", rs.getString("name"));
                        user_session.setAttribute("surname", rs.getString("surname"));
                        user_session.setAttribute("age", rs.getString("age"));
                        user_session.setAttribute("patientAMKA", rs.getString("patientAMKA"));

                        response.sendRedirect("patient_main_environment.jsp");
                        break;

                    case "Doctor":
                        user_session.setAttribute("doctorusername" , rs.getString("username"));
                        user_session.setAttribute("name", rs.getString("name"));
                        user_session.setAttribute("surname", rs.getString("surname"));
                        user_session.setAttribute("age", rs.getString("age"));
                        user_session.setAttribute("specialty", rs.getString("specialty"));
                        user_session.setAttribute("doctorAMKA", rs.getString("doctorAMKA"));

                        response.sendRedirect("doctor_main_environment.jsp");
                        break;

                    case "Admin":
                        user_session.setAttribute("adminusername" , rs.getString("username"));
                        user_session.setAttribute("name", rs.getString("name"));
                        user_session.setAttribute("surname", rs.getString("surname"));
                        user_session.setAttribute("age", rs.getString("age"));

                        response.sendRedirect("admin_main_environment.jsp");
                        break;
                }

                attributes = user_session.getAttributeNames(); //store attributes in list
            }
            else //wrong credentials
            {
                response.sendRedirect("fail.html");
            }

            rs.close();
            con.close();
        }

        catch(Exception e)
        {
            System.out.println("An exception occured during database connection: "+e.toString());
        }
    }

    /**
     * Terminates access from the user logged on.
     */
    public static void Logout(HttpServletResponse response, HttpServletRequest request) throws IOException
    {
        HttpSession session = request.getSession();

        String attribute;

        while (attributes.hasMoreElements())
        {
            attribute = (String) attributes.nextElement();
            session.removeAttribute(attribute);
        }

        session.invalidate();
        response.sendRedirect("login.jsp");
    }
    
    private static String hashPassword(String password, String salt)
    {
        // Hash the password.
        final String toHash = salt + password + salt;
        MessageDigest messageDigest = null;
        try
        {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ex)
        {
            return "00000000000000000000000000000000";
        }
        messageDigest.update(toHash.getBytes(), 0, toHash.length());
        String hashed = new BigInteger(1, messageDigest.digest()).toString(16);
        if (hashed.length() < 32)
        {
            hashed = "0" + hashed;
        }
        return hashed.toUpperCase();
    }
    
    private static String createSalt(HttpServletResponse response)
    {
        PrintWriter writer = null;

        try
        {
            writer = response.getWriter();
            SecureRandom random = new SecureRandom();

            byte bytes[]= new byte[20];
            random.nextBytes(bytes);

            for(int i = 0; i< bytes.length; i++)
            {
                if(bytes[i] < 0)
                    bytes[i] = (byte) -bytes[i];
            }

            return new String(bytes, "UTF-8");

        }
        catch(UnsupportedEncodingException e)
        {
            writer.println("UnsupportedEncodingException: "+e.toString());
            return "";
        }
        catch (IOException e)
        {
            System.out.println("IOException: "+e.toString());
            return "";
        }
    }
    
    /**
     * This method returns the characteristics of each User
     * @return firstname,username,surname and age
     */
    @Override
    public String toString()
    {
        return this.firstname + " \"" + this.username + "\" " + this.surname + ", " + this.age + " years old";
    }

    //getters
    public String getUsername() {
        return username;
    }

    public String getPassword(){
        return password;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getSurname() {
        return surname;
    }

    public int getAge() {
        return age;
    }

    public boolean isLoggedOn() {
        return this.loggedOn;
    }

    public static int getUsersCount() {
        return UsersCount;
    }

    // setters
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setAge(int age) {
        if (age > 0 && age < 119)
            this.age = age;
    }
}

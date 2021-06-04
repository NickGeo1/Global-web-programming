package com.classes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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

    public void Fail(HttpServletResponse response, String reason) throws IOException
    {
        PrintWriter writer = response.getWriter();
        writer.println("<h1> Something went wrong! </h1>");
        writer.println("<h3> " + reason + "</h3>");
    }

    /**
     *
     *
     */
    public static Users Login(String type, HttpServletRequest request, HttpServletResponse response, DataSource datasource)
    {
        String name = request.getParameter("username");
        String pass = request.getParameter("password");

        String table;

        Users user;

        try
        {
            Connection con = datasource.getConnection();

            /*if(user instanceof Patient)
                table = "patient";
            else if(user instanceof Doctor)
                table = "doctor";
            else
                table = "admin";*/

            if(type.equals("Patient"))
                table = "patient";
            else if(type.equals("Doctor"))
                table = "doctor";
            else
                table = "admin";

            PreparedStatement stmnt = con.prepareStatement("SELECT hashedpassword FROM `"+ table +"` WHERE username=?");

            //stmnt.setString(1, table);

            stmnt.setString(1, name);

            ResultSet rs = stmnt.executeQuery();

            if(rs.next() && rs.getString("hashedpassword").equals(pass)) //correct details
            {
                stmnt = con.prepareStatement("SELECT * FROM `"+ table +"` WHERE username=?");

                //stmnt.setString(1, table);
                stmnt.setString(1,name);

                rs = stmnt.executeQuery();
                rs.next();

                if(type.equals("Patient"))/*(user instanceof Patient)*/
                {
                    user = new Patient(rs.getString("username"), rs.getString("hashedpassword"),
                            rs.getString("name"), rs.getString("surname"),
                            rs.getInt("age"),rs.getString("patientAMKA") );
                }
                else if(type.equals("Doctor"))/*(user instanceof Doctor)*/
                {
                    user = new Doctor(rs.getString("username"), rs.getString("hashedpassword"),
                            rs.getString("name"), rs.getString("surname"),
                            rs.getInt("age"),rs.getString("specialty"),rs.getString("doctorAMKA"));
                }
                else
                {
                    user = new Admin(rs.getString("username"), rs.getString("hashedpassword"),
                            rs.getString("name"), rs.getString("surname"),
                            rs.getInt("age"));
                }

                user.loggedOn = true;

                if(type.equals("Patient"))/*(user instanceof Patient)*/
                {
                    response.sendRedirect("patient_main_environment.jsp");
                }

                else if(type.equals("Doctor"))/*(user instanceof Doctor)*/
                {
                    //page for doctor
                }

                else
                {
                    //page for admin
                }

                rs.close();
                con.close();

                return user;

            }else if(rs.next() && !rs.getString("hashedpassword").equals(pass)) //correct username wrong pass
            {

                response.sendRedirect("fail.jsp");

            }else //wrong username
            {
                response.sendRedirect("fail.jsp");
            }

            rs.close();
            con.close();

            return null;

        }catch(Exception e)
        {
            System.out.println("An exception occured during database connection: "+e.toString());
            return null;
        }
    }

    /**
     * Terminates access from the user logged on.
     */
    public void Logout()
    {
        System.out.println("Successfully logged out user " + this.username + ".");
        loggedOn = false;
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

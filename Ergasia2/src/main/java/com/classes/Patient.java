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
 * This is the model of a patient.A patient is able to register to the website,search for an available appointment and
 * see his scheduled or past appointments. Except of User's attributes, each patient has also his unique AMKA.
 */
public class Patient extends Users
{
    private final String AMKA; // This is the unique AMKA of each patient

    public Patient(String username, String password, String firstname, String lastname, int age, String AMKA)
    {
        super(username, password, firstname, lastname, age);
        this.AMKA = AMKA;
    }

    /**
     * Registers a Patient. Preceding the injection, all fields are carefully processed and tested for duplicates in the database.
     * @param response A Servlet response required to provide error information.
     * @param dataSource A Datasource to inject SQL statements into.
     * @throws IOException if anything goes wrong with the HttpServletResponse.
     */
    public void Register(HttpServletResponse response, DataSource dataSource) throws IOException
    {
        //Checks for all the fields. We use Users.Fail() to provide a plain-text HTML page to print any errors.

        if (this.getUsername().isBlank())
        {
            this.Fail(response, "Invalid Username! A username cannot be blank.");
            return;
        }

        else if (this.getPassword().length() < 4)
        {
            this.Fail(response, "Provide a password with at least 4 characters.");
            return;
        }

        else if (!this.getFirstname().matches("[A-Z][a-z]+"))
        {
            this.Fail(response, "Invalid Firstname! All first/last names must start with one capital letter with a succeeding lowercase letter. No other characters, other than letters, are allowed.");
            return;
        }

        else if (!this.getSurname().matches("[A-Z][a-z]+"))
        {
            this.Fail(response, "Invalid Lastname! All first/last names must start with one capital letter with a succeeding lowercase letter. No other characters, other than letters, are allowed.");
            return;
        }

        else if (this.getAge() > 119 || this.getAge() < 0)
        {
            this.Fail(response, "Invalid Age! A registered age cannot be greater than 119 years or a negative number.");
            return;
        }

        else if (!this.getAMKA().matches("[0-9]{11}"))
        {
            this.Fail(response, "Invalid AMKA! A social security number must have exactly 11 digits.");
            return;
        }

        //if we get to this point it means none of the fields are incorrect. we can execute sql statements safely.
        //checking for duplicates in the database

        try
        {
            //Check if there are any duplicates in the database what comes to AMKA and username.
            //preparing an sql statement
            Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM patient WHERE patientAMKA=? OR username=?");

            //setting the parameters
            statement.setString(1, this.getAMKA());
            statement.setString(2, this.getUsername());

            //executing statement
            ResultSet rs = statement.executeQuery();

            //if the statement yields any data, it means there is at least one duplicate. We don't continue.
            if (rs.next())
            {
                this.Fail(response, "This username/AMKA is already taken!");
                rs.close();
                connection.close();
                return;
            }

            //checking again, this time for Doctor AMKA.
            statement = connection.prepareStatement("SELECT * FROM doctor WHERE doctorAMKA=?");
            statement.setString(1, this.getAMKA());
            rs = statement.executeQuery();

            if (rs.next())
            {
                this.Fail(response, "This AMKA is already taken by a doctor!");
                rs.close();
                connection.close();
                return;
            }

            //getting to this point means that none of the above yield errors. We can safely inject database.
            Integer a = this.getAge();

            statement = connection.prepareStatement("INSERT INTO patient (patientAMKA,username,hashedpassword,name,surname,age) VALUES (?,?,?,?,?,?);");
            statement.setString(1, this.getAMKA());
            statement.setString(2, this.getUsername());
            statement.setString(3, this.getPassword());
            statement.setString(4, this.getFirstname());
            statement.setString(5, this.getSurname());
            statement.setString(6, a.toString());
            statement.execute();

            rs.close();
            connection.close();
        }
        catch (Exception exception)
        {
            //if anything goes wrong it'll be printed on the user's screen.
            this.Fail(response, "Cannot insert data. Exception message: \n" + exception.getMessage());
            exception.printStackTrace();
        }

    }

    /**
     * Search available doctor appointments by doctor attribute
     *
     * @param searchby The attribute of a doctor we want to search an appointment for.
     * If 'searchby' value is not set, the method searches all available appointments
     *
     * @param value The actual value of 'searchby' attribute we are looking for
     *
     */
    public void searchAvailableAppointments(String searchby, String value)
    {
        if (!isLoggedOn())
        {
            System.out.println("You must be logged on to search for available appointments.");
            return;
        }

        if(searchby.equals(""))
            System.out.println("Searching every speciality/doctor appointment...");
        else
            System.out.println("Searching appointment by "+ searchby +", where "+searchby+" is "+value);
    }

    /**
     * Show past doctor appointments by doctor attribute
     *
     *
     *
     */
    public void showAppointmentHistory(String showby, String value, HttpServletRequest request, HttpServletResponse response, DataSource datasource) throws IOException
    {
        if (!isLoggedOn())
        {
            System.out.println("You must be logged on to show appointment history.");
            return;
        }

        System.out.println(showby + value);
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");

        PrintWriter showhtml = response.getWriter();
        showhtml.println("<html>");
        showhtml.print("<head><title>Appointment History</title></head>");
        showhtml.println("<body>");

        try
        {
            Connection con = datasource.getConnection();

            PreparedStatement showHistory = con.prepareStatement("SELECT * FROM appointment WHERE PATIENT_patientAMKA=?");
            showHistory.setString(1, this.AMKA);

            ResultSet rs = showHistory.executeQuery();

            if(rs.next())
            {
                showhtml.println("<table border=\"1\">");
                showhtml.println("<tr>");
                showhtml.println("<th>Date</th>");
                showhtml.println("<th>Start time</th>");
                showhtml.println("<th>End time</th>");
                showhtml.println("<th>Patient AMKA</th>");
                showhtml.println("<th>Doctor AMKA</th>");
                showhtml.println("</tr>");

                String date;
                String startSlotTime;
                String endSlotTime;
                String PATIENT_patientAMKA;
                String DOCTOR_doctorAMKA;
                String htmlRow;

                do
                {
                    date = rs.getString("date");
                    startSlotTime = rs.getString("startSlotTime");
                    endSlotTime = rs.getString("endSlotTime");
                    PATIENT_patientAMKA = rs.getString("PATIENT_patientAMKA");
                    DOCTOR_doctorAMKA = rs.getString("DOCTOR_doctorAMKA");

                    htmlRow = createTableRow(date, startSlotTime, endSlotTime, PATIENT_patientAMKA, DOCTOR_doctorAMKA);

                    showhtml.println(htmlRow);

                }while(rs.next());

                showhtml.println("</table>");

                showhtml.println("<br><br><br>"

                                +"<form action=\"patient\" method=\"post\" id=\"form\">"

                                    +"Choose a category to search appointments by:"

                                    +"<select name=\"showby\" id=\"showby\">"
                                    +"<option value=\"Showall\">Showall</option>\""
                                    +"<option value=\"Doctor AMKA\">Doctor AMKA</option>"
                                    +"<option value=\"Date\">Date</option>"
                                    +"</select>"

                                    +"<br><br>"

                                    +"<label for=\"value\">Insert the doctor's AMKA/appointment date:</label><br>"

                                    +"<input type=\"text\" id=\"value\" name=\"value\"><input type=\"submit\" value=\"Search\">"

                                    +"<input type=\"hidden\" name=\"patient_action\" value=\"1\">"

                                +"</form>");
            }
            else
            {
                showhtml.println("<h1>Appointment history is empty</h1>");
            }

            rs.close();

            con.close();

        } catch(Exception e)
        {
            showhtml.println(e.toString());
        }

        showhtml.println("</body>");
        showhtml.println("</html>");

    }

    /**
     * Show scheduled doctor appointments by doctor attribute
     *
     * @param showby The attribute of a doctor we want to show scheduled appointments for.
     * If 'showby' value is not set, the method shows all scheduled appointments.
     *
     * @param value The actual value of 'showby' attribute we are looking for
     *
     */
    public void showScheduledAppointments(String showby, String value)
    {
        if (!isLoggedOn())
        {
            System.out.println("You must be logged on to show all scheduled appointments.");
            return;
        }

        if(showby.equals(""))
            System.out.println("Show all scheduled appointments");
        else
            System.out.println("Show scheduled appointments by "+showby+", where "+showby+" is "+value);
    }

    private static String createTableRow(String date, String startSlotTime, String endSlotTime, String PATIENT_patientAMKA, String DOCTOR_doctorAMKA)
    {
        StringBuilder tablerow = new StringBuilder();

        tablerow.append("<tr>");
        tablerow.append("<td>" + date + "</td>");
        tablerow.append("<td>" + startSlotTime + "</td>");
        tablerow.append("<td>" + endSlotTime + "</td>");
        tablerow.append("<td>" + PATIENT_patientAMKA + "</td>");
        tablerow.append("<td>" + DOCTOR_doctorAMKA + "</td>");
        tablerow.append("</tr>");

        return tablerow.toString();
    }

        /**
         * @return The characteristics of each Patient (firstname,username,surname, age and his AMKA)
         */
    @Override
    public String toString()
    {
        return super.toString() + ", AMKA: "+AMKA;
    }

    // Getter for the attribute AMKA
    public String getAMKA() { return this.AMKA; }

    /**
     * This function shows if a patient is available for an appointment or not
     * @return True if patient is available. False if patient is not available
     */
    public boolean isAvailable(){

        return true;

    }
}

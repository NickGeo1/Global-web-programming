package com.classes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


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
            Fail(response, "Invalid Username! A username cannot be blank.");
            return;
        }

        else if (this.getPassword().length() < 4)
        {
            Fail(response, "Provide a password with at least 4 characters.");
            return;
        }

        else if (!this.getFirstname().matches("[A-Z][a-z]+"))
        {
            Fail(response, "Invalid Firstname! All first/last names must start with one capital letter with a succeeding lowercase letter. No other characters, other than letters, are allowed.");
            return;
        }

        else if (!this.getSurname().matches("[A-Z][a-z]+"))
        {
            Fail(response, "Invalid Lastname! All first/last names must start with one capital letter with a succeeding lowercase letter. No other characters, other than letters, are allowed.");
            return;
        }

        else if (this.getAge() > 119 || this.getAge() < 0)
        {
            Fail(response, "Invalid Age! A registered age cannot be greater than 119 years or a negative number.");
            return;
        }

        else if (!this.getAMKA().matches("[0-9]{11}"))
        {
            Fail(response, "Invalid AMKA! A social security number must have exactly 11 digits.");
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
                Fail(response, "This username/AMKA is already taken!");
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
                Fail(response, "This AMKA is already taken by a doctor!");
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
            Fail(response, "Cannot insert data. Exception message: \n" + exception.getMessage());
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

        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");

        PrintWriter showhtml = response.getWriter();

        // print the html responce page from server
        showhtml.println("<!DOCTYPE html>");
        showhtml.println("<html>");

        showhtml.print("<head>");
        showhtml.println("<title>View appointment history</title>");
        showhtml.println("<link rel=\"stylesheet\" href=\"CSS/styles.css\">");
        showhtml.print("</head>");

        showhtml.println("<body>");

        showhtml.println("<form action=\"patient\" method=\"post\" id=\"form\">");
        showhtml.println("<div class=\"imgcontainer\">");
        showhtml.println("<img src=\"img/logo1.png\" alt=\"logo_image\" class=\"avatar\">");
        showhtml.println("</div>");


        String category;

        try
        {
            Connection con = datasource.getConnection();
            PreparedStatement showHistory = null;

            switch (showby)
            {
                case "Doctor AMKA":

                    if(!value.matches("[0-9]{11}"))
                        throw new ParseException("Invalid AMKA",0);

                    showHistory = con.prepareStatement("SELECT * FROM appointment WHERE DOCTOR_doctorAMKA = ? AND PATIENT_patientAMKA = ?");
                    showHistory.setString(1, value);
                    showHistory.setString(2, this.getAMKA());
                    break;

                case "Date":

                    value = changeDateFormat("dd-MM-yyyy", "yyyy-MM-dd", value);

                    showHistory = con.prepareStatement("SELECT * FROM appointment WHERE date = ? AND PATIENT_patientAMKA = ?");
                    showHistory.setString(1, value);
                    showHistory.setString(2, this.getAMKA());

                    break;

                default:
                    showHistory = con.prepareStatement("SELECT * FROM appointment WHERE PATIENT_patientAMKA = ?");
                    showHistory.setString(1, this.getAMKA());
                    break;

            }

            ResultSet rs = showHistory.executeQuery();

            if(rs.next())
            {

                showhtml.println("<table border=\"1\" align=\"center\">");
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

                    date = changeDateFormat("yyyy-MM-dd", "dd-MM-yyyy", date);
                    startSlotTime = rs.getString("startSlotTime");
                    endSlotTime = rs.getString("endSlotTime");
                    PATIENT_patientAMKA = rs.getString("PATIENT_patientAMKA");
                    DOCTOR_doctorAMKA = rs.getString("DOCTOR_doctorAMKA");

                    htmlRow = createTableRow(date, startSlotTime, endSlotTime, PATIENT_patientAMKA, DOCTOR_doctorAMKA);

                    showhtml.println(htmlRow);

                }while(rs.next());

                showhtml.println("</table>");

                showhtml.println("<br><br><br>"

                                    +"<div class=\"container\">"
                                    +"<label><b style=\"color:#012A6C\">Choose a category to search appointments by:</b></label>"
                                    +"</div>"

                                    +"<select name=\"showby\" id=\"showby\" onclick=\"checkoption();\">"
                                        +"<option value=\"Show all\">Show all</option>\""
                                        +"<option value=\"Doctor AMKA\">Doctor AMKA</option>"
                                        +"<option value=\"Date\">Date</option>"
                                    +"</select>"

                                    +"<br><br>"

                                    +"<label for=\"value\">Insert the doctor's AMKA/appointment date:</label><br>"

                                    +"<input type=\"text\" id=\"value\" name=\"value\" disabled=\"true\">"

                                    + "<button type=\"submit\">Search</button>"

                                    +"<input type=\"hidden\" name=\"patient_action\" value=\"1\">"

                                    +"<script>"
                                        +"function checkoption()"
                                        +"{"
                                            +"var s = document.getElementById(\"showby\");"
                                            +"var o = s.options[s.selectedIndex].value;"

                                            +"if(o == \"Show all\")"
                                            +"{"
                                            +    "document.getElementById(\"value\").disabled = true;"
                                            +    "document.getElementById(\"value\").value = \"\";"
                                            +"}"
                                            +"else"
                                            +"{"
                                            +    "document.getElementById(\"value\").disabled = false;"
                                            +"}"

                                        +"}"
                                    +"</script>"

                                +"</form>");
            }
            else if(!rs.next() && showby.equals("Show all"))
            {
                showhtml.println("<h1>Appointment history is empty</h1>");
            }
            else
            {
                if(showby.equals("Date"))
                    value = changeDateFormat("yyyy-MM-dd", "dd-MM-yyyy", value);

                showhtml.println("<h1>No results found for "+showby + " " + value+ "<h1>");
            }

            rs.close();
            con.close();

        }
        catch (ParseException e)
        {
            showhtml.println("<h1>Invalid " + showby + " format</h1>");
        }
        catch(Exception e)
        {
            showhtml.println(e.toString());
        }

        showhtml.println("<div class=\"navbar\">");
        showhtml.println("<p>Do you need help? Check the <a href=\"instructions.jsp\" class=\"active\" style=\"font-size: 16px;\">instuctions</a></p>");
        showhtml.println("</div>");


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

    private static String changeDateFormat(String oldformat, String newformat, String date) throws ParseException
    {
        SimpleDateFormat df = new SimpleDateFormat(oldformat);
        Date d = df.parse(date);
        df.applyPattern(newformat);
        date = df.format(d);

        return date;
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

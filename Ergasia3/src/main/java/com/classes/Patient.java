package com.classes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.Calendar;
import java.util.Date;


/**
 * This is the model of a patient.A patient is able to register to the website,search for an available appointment and
 * see his scheduled or past appointments. Except of User's attributes, each patient has also his unique AMKA.
 */
public class Patient extends Users
{
    //Objects we are going to need during database access
    private static Connection connection;
    private static PreparedStatement statement;
    private static ResultSet rs;
    private static StringBuilder html;

    private final String AMKA; // This is the unique AMKA of each patient

    public Patient(String username, String password, String firstname, String lastname, int age, String AMKA)
    {
        super(username, password, firstname, lastname, age);
        this.AMKA = AMKA;
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
    public void Register(HttpServletResponse response, DataSource dataSource) throws IOException
    {
        //Checks for all the fields. We use Users.Fail() to provide a plain-text HTML page to print any errors.

        if (this.getUsername().isBlank())
        {
            Fail(response, "Invalid Username! A username cannot be blank.","index.html");
            return;
        }

        else if (this.getPassword().length() < 4)
        {
            Fail(response, "Provide a password with at least 4 characters.","index.html");
            return;
        }

        else if (!this.getFirstname().matches("[A-Z][a-z]+"))
        {
            Fail(response, "Invalid Firstname! All first/last names must start with one capital letter with a succeeding lowercase letter. No other characters, other than letters, are allowed.","index.html");
            return;
        }

        else if (!this.getSurname().matches("[A-Z][a-z]+"))
        {
            Fail(response, "Invalid Lastname! All first/last names must start with one capital letter with a succeeding lowercase letter. No other characters, other than letters, are allowed.","index.html");
            return;
        }

        else if (this.getAge() > 119 || this.getAge() <= 0)
        {
            Fail(response, "Invalid Age! A registered age cannot be greater than 119 years or a non-positive number.","index.html");
            return;
        }

        else if (!this.getAMKA().matches("[0-9]{11}"))
        {
            Fail(response, "Invalid AMKA! A social security number must have exactly 11 digits.","index.html");
            return;
        }

        //if we get to this point it means none of the fields are incorrect. we can execute sql statements safely.
        //checking for duplicates in the database

        try
        {
            //Check if there are any duplicates in the database what comes to AMKA and username.
            //preparing an sql statement
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT * FROM patient WHERE patientAMKA=? OR username=?");

            //setting the parameters
            statement.setString(1, this.getAMKA());
            statement.setString(2, this.getUsername());

            //executing statement
            rs = statement.executeQuery();

            //if the statement yields any data, it means there is at least one duplicate. We don't continue.
            if (rs.next())
            {
                Fail(response, "This username/AMKA is already taken!","index.html");
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
                Fail(response, "This AMKA is already taken by a doctor!","index.html");
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

            response.sendRedirect("register-success.html");

            rs.close();
            connection.close();

        }
        catch (Exception exception)
        {
            //if anything goes wrong it'll be printed on the user's screen.
            Fail(response, "Cannot insert data. Exception message: \n" + exception.getMessage(),"index.html");
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
     *  Show past doctor appointments by doctor attribute
     *
     *  @param showby The doctor attribute to search history appointments by
     *  @param value The actual value of the above attribute
     *  @param response A Servlet response required to provide html content to client.
     *  @param datasource A Datasource to inject SQL statements into.
     *  @throws IOException if anything goes wrong with the HttpServletResponse.
     *
     *  Makes a html table which contains, on each row, the details about the patient's history appointments.
     *  Each row contains the following information: the date, the start time and the ending time of the appointment and
     *  the patient and doctor's AMKA.If the patient has not any history appointments a 'Appointment history is empty' message appears.
     *
     */
    public void showAppointmentHistory(String showby, String value, HttpServletResponse response, DataSource datasource) throws IOException
    {
        if (!isLoggedOn()) //in case patient is not any more logged on, print a message
        {
            System.out.println("You must be logged on to show appointment history.");
            return;
        }

        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        PrintWriter showhtml = response.getWriter();

        // print the html response page from server

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
        showhtml.println("<br>");

        //The user has the option(from an option box) to choose by which doctor attribute(doctor AMKA or appointment date)
        //his appointments are going to be shown(there is also a 'Show All' option to show appointments without a restriction).
        //The desirable value of an attribute is being submitted in a textbox before the results are shown.

        try
        {
            connection = datasource.getConnection();    //connection object for database connection
            statement = null;       //A prepared statement object, on which we are going to store our sql statement

            String query =  "SELECT date,startSlotTime,endSlotTime,PATIENT_patientAMKA,DOCTOR_doctorAMKA,specialty,name,surname " +
                            "FROM appointment JOIN doctor ON DOCTOR_doctorAMKA = doctorAMKA " +
                            "WHERE PATIENT_patientAMKA = ? AND (date < cast(now() as date) OR date = cast(now() as date) AND endSlotTime < cast(now() as time))";

            switch (showby)     //depending on the showby value we execute the corresponding sql statement
            {
                case "Doctor AMKA":

                    if(!value.matches("[0-9]{11}"))
                        throw new ParseException("Invalid AMKA",0); //in case of invalid AMKA format, we throw a parse exception

                    query+= " AND DOCTOR_doctorAMKA = ?";
                    break;

                case "Date":
                    //in case we want to search by date, we have to change its format because the format is different in the database
                    value = changeDateFormat("dd-MM-yyyy", "yyyy-MM-dd", value);

                    query += " AND date = ?";
                    break;

                case "Specialty":

                    query += " AND specialty = ?";
                    break;
            }

            statement = connection.prepareStatement(query);
            statement.setString(1, this.getAMKA());

            if(!showby.equals("Show all"))
                statement.setString(2, value);

            //after executing the correct statement we check the results.

            rs = statement.executeQuery();

            if(rs.next()) //in case there is at least one record, make the table headers
            {

                showhtml.println("<table>");
                showhtml.println("<tr>");
                showhtml.println("<th>Date</th>");
                showhtml.println("<th>Start time</th>");
                showhtml.println("<th>End time</th>");
                showhtml.println("<th>Patient AMKA</th>");
                showhtml.println("<th>Doctor AMKA</th>");
                showhtml.println("<th>Doctor specialty</th>");
                showhtml.println("<th>Doctor name</th>");
                showhtml.println("<th>Doctor surname</th>");
                showhtml.println("</tr>");

                String date;
                String startSlotTime;
                String endSlotTime;
                String PATIENT_patientAMKA;
                String DOCTOR_doctorAMKA;
                String Doctor_specialty;
                String Doctor_name;
                String Doctor_surname;
                String htmlRow;

                do  //add the result's rows on the table
                {
                    date = rs.getString("date");

                    //change the date to the correct format before storing it into the variable
                    date = changeDateFormat("yyyy-MM-dd", "dd-MM-yyyy", date);
                    startSlotTime = rs.getString("startSlotTime");
                    endSlotTime = rs.getString("endSlotTime");
                    PATIENT_patientAMKA = rs.getString("PATIENT_patientAMKA");
                    DOCTOR_doctorAMKA = rs.getString("DOCTOR_doctorAMKA");
                    Doctor_specialty = rs.getString("specialty");
                    Doctor_name = rs.getString("name");
                    Doctor_surname = rs.getString("surname");

                    htmlRow = createTableRow(0,false, date, startSlotTime, endSlotTime, PATIENT_patientAMKA, DOCTOR_doctorAMKA, Doctor_specialty, Doctor_name, Doctor_surname);

                    showhtml.println(htmlRow);

                }while(rs.next());

                //html code for the option box and input text.If option 'Show all' is selected, the input text is being disabled(Javascript)

                showhtml.println("</table>");

                showhtml.println("<br><br>"

                                    + "<div class=\"container\">"
                                    + "<label><b style=\"color:#012A6C\">Choose a category to search appointments by:  </b></label>"
                                    + "<select name=\"showby\" id=\"showby\" onclick=\"checkoption();\">"
                                        + "<option selected value=\"Show all\">Show all</option>\""
                                        + "<option value=\"Doctor AMKA\">Doctor AMKA</option>"
                                        + "<option value=\"Date\">Date</option>"
                                        + "<option value=\"Specialty\">Specialty</option>"
                                    + "</select>"
                                    + "</div>"

                                    + "<div class=\"container\">"
                                    + "<label for=\"value\"><b style=\"color:#012A6C\">Insert the doctor's AMKA/appointment date/speciality:  </b></label>"
                                    + "<input type=\"text\" id=\"value\" name=\"value\" disabled=\"true\">"
                                    + "<button type=\"submit\">Search</button>"
                                    + "<input type=\"hidden\" name=\"patient_action\" value=\"1\">"
                                    + "</div>"

                                    + "<script>"
                                        + "function checkoption()"
                                        + "{"
                                            + "var s = document.getElementById(\"showby\");"
                                            + "var o = s.options[s.selectedIndex].value;"

                                            + "if(o == \"Show all\")"
                                            + "{"
                                            +    "document.getElementById(\"value\").disabled = true;"
                                            +    "document.getElementById(\"value\").value = \"\";"
                                            + "}"
                                            + "else"
                                            + "{"
                                            +    "document.getElementById(\"value\").disabled = false;"
                                            + "}"

                                        + "}"
                                    + "</script>"

                                    +"</form>"

                                +"<br><br>"
                                +"<div class=\"navbar\">"
                                +"<p>Do you want to go back? Click <a href=\"patient_main_environment.jsp\">here</a></p>"
                                +"</div>");

            }
            else if(!rs.next() && showby.equals("Show all")) //if there is not any record on the results and the option
            {                                                // is 'Show all', that means history is empty

                showhtml.println("<h1>Appointment history is empty</h1>");
                showhtml.println("</form><br><br>" +
                        "<div class=\"navbar\">" +
                        "<p>Do you want to go back? Click <a href=\"javascript:history.back()\">here</a></p>" +
                        "</div>");
            }
            else  //In this case, there is not any record on the results but the option wasn't 'Show all'.
            {     //That means there is not any results JUST for the restrictions we had set

                if(showby.equals("Date"))
                    value = changeDateFormat("yyyy-MM-dd", "dd-MM-yyyy", value);

                showhtml.println("<h1>No results found for "+showby + " " + value+ "</h1>");
                showhtml.println("</form><br><br>" +
                        "<div class=\"navbar\">" +
                        "<p>Do you want to go back? Click <a href=\"javascript:history.back()\">here</a></p>" +
                        "</div>");
            }

            rs.close();
            connection.close(); //close ResultSet and Connection

        }
        catch (ParseException e) //parse exception occurs if we try to search a date or an AMKA with invalid format typed
        {
            showhtml.println("<h1>Invalid " + showby + " format</h1>");
            showhtml.println("</form><br><br>" +
                    "<div class=\"navbar\">" +
                    "<p>Do you want to go back? Click <a href=\"javascript:history.back()\">here</a></p>" +
                    "</div>");
        }
        catch(Exception e)
        {
            showhtml.println(e.toString());
        }
        finally
        {
            showhtml.println("</body>");
            showhtml.println("</html>");
        }

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
    public void showScheduledAppointments(String showby, String value, HttpServletRequest request, HttpServletResponse response, DataSource datasource) throws IOException
    {
        if (!isLoggedOn())
        {
            System.out.println("You must be logged on to show all scheduled appointments.");
            return;
        }

        try
        {
            connection = datasource.getConnection();    //connection object for database connection

            String query =  "SELECT date,startSlotTime,endSlotTime,PATIENT_patientAMKA,DOCTOR_doctorAMKA,specialty,name,surname " +
                    "FROM appointment JOIN doctor ON DOCTOR_doctorAMKA = doctorAMKA " +
                    "WHERE PATIENT_patientAMKA = ? AND (date > cast(now() as date) OR date = cast(now() as date) AND startSlotTime > cast(now() as time))";

            switch (showby)     //depending on the showby value we execute the corresponding sql statement
            {
                case "Doctor AMKA":

                    if(!value.matches("[0-9]{11}"))
                        throw new ParseException("Invalid AMKA",0); //in case of invalid AMKA format, we throw a parse exception

                    query+= " AND DOCTOR_doctorAMKA = ?";
                    break;

                case "Date":
                    //in case we want to search by date, we have to change its format because the format is different in the database
                    value = changeDateFormat("dd-MM-yyyy", "yyyy-MM-dd", value);

                    query += " AND date = ?";
                    break;

                case "Specialty":

                    query += " AND specialty = ?";
                    break;
            }

            statement = connection.prepareStatement(query);  //A prepared statement object, on which we are going to store our sql statement
            statement.setString(1, this.getAMKA());

            if(!showby.equals("Show all"))
                statement.setString(2, value);

            //after executing the correct statement we check the results.

            rs = statement.executeQuery();

            if(rs.next()) //in case there is at least one record, make the table headers
            {
                 html = new StringBuilder(
                                "<table>"
                                +"<tr>"
                                +"<th>Date</th>"
                                +"<th>Start time</th>"
                                +"<th>End time</th>"
                                +"<th>Patient AMKA</th>"
                                +"<th>Doctor AMKA</th>"
                                +"<th>Doctor specialty</th>"
                                +"<th>Doctor name</th>"
                                +"<th>Doctor surname</th>"
                                +"</tr>");

                String date;
                String startSlotTime;
                String endSlotTime;
                String PATIENT_patientAMKA;
                String DOCTOR_doctorAMKA;
                String Doctor_specialty;
                String Doctor_name;
                String Doctor_surname;
                String htmlRow;

                int row = 0;

                do  //add the result's rows on the table
                {
                    row++;
                    date = rs.getString("date");

                    //change the date to the correct format before storing it into the variable
                    date = changeDateFormat("yyyy-MM-dd", "dd-MM-yyyy", date);
                    startSlotTime = rs.getString("startSlotTime");
                    endSlotTime = rs.getString("endSlotTime");
                    PATIENT_patientAMKA = rs.getString("PATIENT_patientAMKA");
                    DOCTOR_doctorAMKA = rs.getString("DOCTOR_doctorAMKA");
                    Doctor_specialty = rs.getString("specialty");
                    Doctor_name = rs.getString("name");
                    Doctor_surname = rs.getString("surname");

                    htmlRow = createTableRow(row,true, date, startSlotTime, endSlotTime, PATIENT_patientAMKA, DOCTOR_doctorAMKA, Doctor_specialty, Doctor_name, Doctor_surname);
                    html.append(htmlRow);

                }while(rs.next());

                response.sendRedirect("ScheduledAppointments.jsp");
            }
            else if(!rs.next() && showby.equals("Show all")) //if there is not any record on the results and the option
            {                                                // is 'Show all', that means history is empty

                Fail(response,"You have not any scheduled appointments yet","patient_main_environment.jsp");
            }
            else  //In this case, there is not any record on the results but the option wasn't 'Show all'.
            {     //That means there is not any results JUST for the restrictions we had set

                if(showby.equals("Date"))
                    value = changeDateFormat("yyyy-MM-dd", "dd-MM-yyyy", value);

                Fail(response,"No results found for "+showby + " " + value,"ScheduledAppointments.jsp");
            }

            rs.close();
            connection.close(); //close ResultSet and Connection

        }
        catch (ParseException e) //parse exception occurs if we try to search a date or an AMKA with invalid format typed
        {
            Fail(response,"Invalid " + showby + " format","ScheduledAppointments.jsp");
        }
        catch(Exception e)
        {
            PrintWriter showhtml = response.getWriter();
            showhtml.println(e.toString());
        }
    }

    /**
     *
     * @param date
     */
    public void cancelScheduledAppointment(String date, HttpServletResponse response) throws ParseException, IOException
    {
        Date now = new Date(); //today's date

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date appointment_date = df.parse(date); //appointment date

        Calendar cal =  Calendar.getInstance();
        cal.setTime(now);
        cal.add(Calendar.DAY_OF_MONTH, 3);
        Date nowplus3 = cal.getTime(); //appointment date + 3 days

        if(nowplus3.after(appointment_date))
            Fail(response, "You cannot cancel an appointment that is scheduled in less than 3 days from now", "ScheduledAppointments.jsp");
        //else

    }

    /**
     * Makes an html row in string format with the attributes that passed as parameters
     *
     * @param date appointment's date
     * @param startSlotTime appointment's startSlotTime
     * @param endSlotTime appointment's endSlotTime
     * @param PATIENT_patientAMKA appointment's PATIENT_patientAMKA
     * @param DOCTOR_doctorAMKA appointment's DOCTOR_doctorAMKA
     * @return string format of html row
     */
    private static String createTableRow(int row, boolean show_btn, String date, String startSlotTime, String endSlotTime, String PATIENT_patientAMKA, String DOCTOR_doctorAMKA, String Doctor_specialty, String Doctor_name, String Doctor_surname)
    {
        StringBuilder tablerow = new StringBuilder();


        tablerow.append("<tr>");
        tablerow.append("<td>" + date + "</td>");
        tablerow.append("<td>" + startSlotTime + "</td>");
        tablerow.append("<td>" + endSlotTime + "</td>");
        tablerow.append("<td>" + PATIENT_patientAMKA + "</td>");
        tablerow.append("<td>" + DOCTOR_doctorAMKA + "</td>");
        tablerow.append("<td>" + Doctor_specialty + "</td>");
        tablerow.append("<td>" + Doctor_name + "</td>");
        tablerow.append("<td>" + Doctor_surname + "</td>");
        if(show_btn)
            tablerow.append("<td><input type=\"submit\" name=\""+row+"\" id=\""+row+"\" value=\"Cancel\"</td>");
        tablerow.append("</tr>");

        return tablerow.toString();
    }

    /**
     * Takes a date in string format as a parameter and converts it from it's old format to the new one
     *
     * @param oldformat the old format of the date
     * @param newformat the new format of the date
     * @param date the date in string format
     * @return the given date at it's new format(as string)
     * @throws ParseException
     */
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

    public static String getHTML()
    {
        return html.toString();
    }
}

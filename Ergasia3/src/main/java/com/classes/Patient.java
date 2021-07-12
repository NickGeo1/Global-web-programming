package com.classes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;


/**
 * This is the model of a patient.A patient is able to register to the website,search for an available appointment and
 * see his scheduled or past appointments. Except of User's attributes, each patient has also his unique AMKA.
 */
public class Patient extends Users
{
    private final String AMKA; // This is the unique AMKA of each patient

    //variables for database management
    private static Connection connection;
    private static PreparedStatement statement;
    private static ResultSet rs;

    //Patient constructor
    public Patient(String username, String password, String firstname, String lastname, int age, String AMKA)
    {
        super(username, password, firstname, lastname, age);
        this.AMKA = AMKA;
    }

    /**
     *
     * Searching for available appointments between a given time interval, specified(or not) by a doctor attribute and the attribute value
     *
     * Makes a html table which contains, on each row, the details about the doctor who is available on a specific date and time,
     * followed by an interactive "Book" button.
     * Each row contains the following information: the date, the start time and the ending time of the appointment,
     * the doctor's AMKA and the doctor's firstname, lastname and specialty.In case a booking is successful, a success
     * message appears. In any other case(ex. no results found) a corresponding message appears, with the help of Fail function.
     *
     * @param start_date The start date of the desired search interval
     * @param end_date The end date of the desired search interval
     * @param searchby   The doctor attribute to search available appointments by
     * @param value      The actual value of the above attribute
     * @param response   A Servlet response object required to redirect user to another page.
     * @param datasource A Datasource to inject SQL statements into.
     * @throws IOException
     */
    public static void searchAvailableAppointments(String start_date, String end_date, String searchby, String value, HttpServletResponse response, DataSource datasource) throws IOException {

        StringBuilder html = new StringBuilder(""); //a string builder object to store the html content we want to show on "AvailableDoctorAppointments.jsp"

        try
        {
            connection = datasource.getConnection(); //connection object for database connection

            //This is our default query, in case we want to search all the available appointments
            //Query selects all the required data from appointment and doctor tables, from records that
            //have patientAMKA = 0(available) and date between the date interval the patient gave
            String query = "SELECT date,startSlotTime,endSlotTime,DOCTOR_doctorAMKA,specialty,name,surname " +
                    "FROM appointment JOIN doctor ON DOCTOR_doctorAMKA = doctorAMKA " +
                    "WHERE PATIENT_patientAMKA = 0 AND date BETWEEN ? AND ?";

            switch (searchby)  //depending on the searchby value we add the corresponding constraint to our query
            {
                case "Doctor AMKA": //in case we want to search by Doctor AMKA

                    //if AMKA has invalid format, set the corresponding message on HTML object, close connection and return
                    if (!value.matches("[0-9]{11}"))
                    {
                        html.append("Invalid AMKA format");
                        setHTML(html);
                        connection.close();
                        return;
                    }

                    //add to query the corresponding doctorAMKA constraint
                    query += " AND DOCTOR_doctorAMKA = ?";
                    statement = connection.prepareStatement(query);
                    statement.setString(1, start_date);
                    statement.setString(2, end_date);
                    statement.setString(3, value);
                    break;

                case "Full name": //in case we want to search by Full name

                    //in this case, value contains the first name and the last name splitted by space
                    //we store the firstname and the last name seperately on names array
                    String[] names = value.split(" ");

                    //add to query the corresponding name and surname constraint
                    query += " AND (name = ? OR surname=?)";
                    statement = connection.prepareStatement(query);
                    statement.setString(1, start_date);
                    statement.setString(2, end_date);
                    statement.setString(3, names[0]);
                    statement.setString(4, names[1]);
                    break;

                case "Specialty": //in case we want to search by Specialty

                    //add to query the corresponding specialty constraint
                    query += " AND specialty = ?";
                    statement = connection.prepareStatement(query);
                    statement.setString(1, start_date);
                    statement.setString(2, end_date);
                    statement.setString(3, value);
                    break;

                default: //default case is when we want to search all the available appointments, so we dont change the query
                    statement = connection.prepareStatement(query);
                    statement.setString(1, start_date);
                    statement.setString(2, end_date);
            }

            rs = statement.executeQuery(); //execute query

            if (rs.next()) //in case there is at least one record, append on html variable the table headers
            {
                html.append(
                            "<table>"
                            + "<tr>"
                            + "<th>Date</th>"
                            + "<th>Start time</th>"
                            + "<th>End time</th>"
                            + "<th>Doctor AMKA</th>"
                            + "<th>Doctor name</th>"
                            + "<th>Doctor surname</th>"
                            + "<th>Doctor specialty</th>"
                            + "</tr>");

                String date;
                String startSlotTime;
                String endSlotTime;
                String DOCTOR_doctorAMKA;
                String Doctor_specialty;
                String Doctor_name;
                String Doctor_surname;
                String htmlRow;

                do  //add the result's rows on html variable
                {
                    date = rs.getString("date");

                    //change the date to the correct format before storing it into the variable(database holds date in yyyy-MM-dd format)
                    date = changeDateFormat("yyyy-MM-dd", "dd-MM-yyyy", date);
                    startSlotTime = rs.getString("startSlotTime");
                    endSlotTime = rs.getString("endSlotTime");
                    DOCTOR_doctorAMKA = rs.getString("DOCTOR_doctorAMKA");
                    Doctor_specialty = rs.getString("specialty");
                    Doctor_name = rs.getString("name");
                    Doctor_surname = rs.getString("surname");

                    //Get table row for available appointment
                    htmlRow = createTableRow(2, date, startSlotTime, endSlotTime, DOCTOR_doctorAMKA, Doctor_specialty, Doctor_name, Doctor_surname);
                    //append on html variable the above row
                    html.append(htmlRow);

                } while (rs.next());

                html.append("</table>");
            }
            else if (!rs.next() && searchby.equals("Show all")) //if there is not any results on "Show all category"
            {
                //change from database date format to dd-MM-yyyy format in order to show the fail message to user
                start_date = changeDateFormat("yyyy-MM-dd", "dd-MM-yyyy", start_date);
                end_date = changeDateFormat("yyyy-MM-dd", "dd-MM-yyyy", end_date);

                //append the message on html variable
                html.append("There is not any appointment available on interval " + start_date + " through " + end_date);
            }
            else  //In this case, there is not any record on the results but the option wasn't 'Show all'.
            {     //That means there is not any results JUST for the restrictions we had set

                //change from database date format to dd-MM-yyyy format in order to show the fail message to user
                start_date = changeDateFormat("yyyy-MM-dd", "dd-MM-yyyy", start_date);
                end_date = changeDateFormat("yyyy-MM-dd", "dd-MM-yyyy", end_date);

                //append the message on html variable
                html.append("No results found for interval " + start_date + " through " + end_date + " and " + searchby + " " + value);
            }

            rs.close();
            connection.close(); //close ResultSet and Connection

        }
        catch (ArrayIndexOutOfBoundsException e) //In this Exception case, user didnt enter the doctor's first name and last name correct
        {
            html.append("Invalid firstname/lastname format");
        }
        catch (Exception e) //in other Exception cases, print message on console
        {
            System.out.println(e.toString());
        }
        finally //in any case, set the HTML value equal to html value. HTML is being shown in "AvailableDoctorAppointments.jsp"
        {
            setHTML(html);
            response.sendRedirect("AvailableDoctorAppointments.jsp"); //redirect the user back
        }
    }

    /**
     *
     * Books the selected appointment after "Book" button is being pressed.
     * In database level, this method just set's the desired appointment's
     * Patient AMKA from value 0 to value equal to the patient's AMKA
     * Appointment is specified by the parameters
     *
     * @param date Date of the appointment patient wants to book
     * @param start Starting hour of the appointment patient wants to book
     * @param end Ending hour of the appointment patient wants to book
     * @param dAMKA DoctorAMKA that specifies the doctor of the appointment patient wants to book
     * @param response Servlet response object to redirect the user back in "AvailableDoctorAppointments.jsp" or to print a message on page
     * @param request Servlet request object to get the patient's session attributes
     * @param datasource Datasource object required to access database
     * @throws IOException
     */
    public static void bookAppointment(String date, String start, String end, String dAMKA, HttpServletResponse response, HttpServletRequest request, DataSource datasource) throws IOException
    {

        HttpSession session = request.getSession(); //get session object

        try
        {
            connection = datasource.getConnection();

            //Update the corresponding appointment AMKA and set it equal to patient's AMKA(appointment booked).
            //We can specify the appointment, by the given parameters
            statement = connection.prepareStatement("UPDATE appointment SET PATIENT_patientAMKA = ? WHERE date = ? AND startSlotTime=? AND endSlotTime=? AND DOCTOR_doctorAMKA=?");
            statement.setString(1, session.getAttribute("patientAMKA").toString());
            statement.setString(2, changeDateFormat("dd-MM-yyyy", "yyyy-MM-dd", date));
            statement.setString(3, start);
            statement.setString(4, end);
            statement.setString(5, dAMKA);
            statement.execute();
            connection.close();

            StringBuilder html = new StringBuilder("Thank you for using our web application to book your appointment " + session.getAttribute("name") +
                    "!\nYour appointment has been booked on " + date + " at " + start + " until " + end + "(Doctor's AMKA: " + dAMKA + ")");

            setHTML(html); //Set HTML value equal to the message above(HTML content is being shown in "AvailableDoctorAppointments.jsp")

            response.sendRedirect("AvailableDoctorAppointments.jsp"); //redirect patient back
        }
        catch (Exception e) //in case something goes wrong print message to page
        {
            PrintWriter showhtml = response.getWriter();
            showhtml.println(e.toString());
        }
    }

    /**
     * Show past doctor and patient appointments by doctor attribute
     * <p>
     * Makes a html table which contains, on each row, the details about the patient's history appointments.
     * Each row contains the following information: the date, the start time and the ending time of the appointment,
     * the doctor's AMKA and the doctor's firstname, lastname and specialty.
     * If the patient has not any history appointments a 'Appointment history is empty' message appears(Fail function).
     * In any other case a corresponding message appears, with the help of Fail function.
     *
     * @param showby     The doctor attribute to search history appointments by
     * @param value      The actual value of the above attribute
     * @param response   A Servlet response object required to redirect user to another page.
     * @param request    A Servlet request object required to get the session attributes
     * @param datasource A Datasource to inject SQL statements into.
     * @throws IOException if anything goes wrong with the HttpServletResponse.
     *
     */
    public static void showAppointmentHistory(String showby, String value, HttpServletResponse response, HttpServletRequest request, DataSource datasource) throws IOException
    {
        HttpSession session = request.getSession();

        PrintWriter showhtml = response.getWriter();

        //The user has the option(from an option box) to choose by which doctor attribute(doctor AMKA, appointment date or doctor specialty)
        //his appointments are going to be shown(there is also a 'Show All' option to show appointments without a restriction).
        //The desirable value of an attribute is being submitted in a textbox before the results are shown.

        try
        {
            connection = datasource.getConnection();    //connection object for database connection
            statement = null;       //A prepared statement object, on which we are going to store our sql statement

            //Query that selects all the required information of the history appointments
            String query = "SELECT date,startSlotTime,endSlotTime,DOCTOR_doctorAMKA,specialty,name,surname " +
                    "FROM appointment JOIN doctor ON DOCTOR_doctorAMKA = doctorAMKA " +
                    "WHERE PATIENT_patientAMKA = ? AND (date < cast(now() as date) OR date = cast(now() as date) AND endSlotTime < cast(now() as time))";

            switch (showby)    //depending on the showby value we add one more constraint on the query.
            {
                case "Doctor AMKA":

                    if (!value.matches("[0-9]{11}"))
                        throw new ParseException("Invalid AMKA", 0); //in case of invalid AMKA format, we throw a parse exception

                    query += " AND DOCTOR_doctorAMKA = ?"; //Add doctor AMKA constraint
                    break;

                case "Date":
                    //in case we want to search by date, we have to change its format because the format is different in the database
                    value = changeDateFormat("dd-MM-yyyy", "yyyy-MM-dd", value);

                    query += " AND date = ?"; //Add date constraint
                    break;

                case "Specialty":

                    query += " AND specialty = ?"; //Add doctor specialty constraint
                    break;
            }

            //set the first query parameter equal to the patient's AMKA
            statement = connection.prepareStatement(query);
            statement.setString(1, session.getAttribute("patientAMKA").toString());

            //if we dont want to show all the history appointments, we have to add the doctor attribute value parameter to query
            if (!showby.equals("Show all"))
                statement.setString(2, value);

            //after executing the correct statement we check the results.
            rs = statement.executeQuery();

            if (rs.next()) //in case there is at least one record on result set
            {
                StringBuilder html = new StringBuilder(""); //a string builder object to store the html content we want to show on "appointmenthistory.jsp"

                //append on html variable the table headers
                html.append("<table>");
                html.append("<tr>");
                html.append("<th>Date</th>");
                html.append("<th>Start time</th>");
                html.append("<th>End time</th>");
                html.append("<th>Doctor AMKA</th>");
                html.append("<th>Doctor name</th>");
                html.append("<th>Doctor surname</th>");
                html.append("<th>Doctor specialty</th>");
                html.append("</tr>");

                String date;
                String startSlotTime;
                String endSlotTime;
                String DOCTOR_doctorAMKA;
                String Doctor_specialty;
                String Doctor_name;
                String Doctor_surname;
                String htmlRow;

                do  //append the result's rows on the html variable
                {
                    date = rs.getString("date");

                    //change the date to the correct format before storing it into the variable
                    date = changeDateFormat("yyyy-MM-dd", "dd-MM-yyyy", date);
                    startSlotTime = rs.getString("startSlotTime");
                    endSlotTime = rs.getString("endSlotTime");
                    DOCTOR_doctorAMKA = rs.getString("DOCTOR_doctorAMKA");
                    Doctor_specialty = rs.getString("specialty");
                    Doctor_name = rs.getString("name");
                    Doctor_surname = rs.getString("surname");

                    //Get table row for history appointment
                    htmlRow = createTableRow(0, date, startSlotTime, endSlotTime, DOCTOR_doctorAMKA, Doctor_specialty, Doctor_name, Doctor_surname);
                    //append to html variable the above row
                    html.append(htmlRow);

                } while (rs.next());

                html.append("</table>");
                //set HTML value equal to html value.(HTML content appears in "appointmenthistory.jsp")
                setHTML(html);
                response.sendRedirect("appointmenthistory.jsp"); //redirect patient back
            }
            else if (!rs.next() && showby.equals("Show all")) //if there is not any record on the results and the option
            {                                                // is 'Show all', that means history is empty
                Fail(response, "Appointment history is empty", "appointmenthistory.jsp");
            }
            else  //In this case, there is not any record on the results but the option wasn't 'Show all'.
            {     //That means there is not any results JUST for the restrictions we had set

                if (showby.equals("Date")) //if showby value was "Date", change date format to inform the user
                    value = changeDateFormat("yyyy-MM-dd", "dd-MM-yyyy", value);

                Fail(response, "No results found for " + showby + " " + value, "appointmenthistory.jsp");
            }

            rs.close();
            connection.close(); //close ResultSet and Connection

        }
        catch (ParseException e) //parse exception occurs if we try to search a date or an AMKA with invalid format typed
        {
            Fail(response, "Invalid " + showby + " format", "appointmenthistory.jsp");
        }
        catch (Exception e) //In any other exception case, print message to page
        {
            showhtml.println(e.toString());
        }

    }

    /**
     *
     * Show scheduled doctor appointments by doctor attribute.
     * Makes a html table which contains, on each row, the details about the appointment that has been scheduled
     * with a specified doctor.Each row is being followed by a "Cancel" button, to give patient the opportunity to
     * cancel the appointment.
     *
     * Each row contains the following information: the date, the start time and the ending time of the appointment,
     * the doctor's AMKA and the doctor's firstname, lastname and specialty.
     * In any other case(ex. no results found) a corresponding message appears, with the help of Fail function.
     *
     * @param showby The attribute of a doctor we want to show scheduled appointments for.
     * @param value  The actual value of 'showby' attribute we are looking for
     * @param response Servlet response object required to redirect to other pages and show messages on page
     * @param request Servlet request object required to get session attributes
     * @param datasource Datasource object required to access database
     *
     */
    public static void showScheduledAppointments(String showby, String value, HttpServletResponse response, HttpServletRequest request, DataSource datasource) throws IOException
    {

        HttpSession session = request.getSession(); //get session

        try
        {
            connection = datasource.getConnection();    //connection object for database connection

            //Query that selects all the required information of the scheduled appointments
            String query = "SELECT date,startSlotTime,endSlotTime,DOCTOR_doctorAMKA,specialty,name,surname " +
                    "FROM appointment JOIN doctor ON DOCTOR_doctorAMKA = doctorAMKA " +
                    "WHERE PATIENT_patientAMKA = ? AND (date > cast(now() as date) OR date = cast(now() as date) AND startSlotTime > cast(now() as time))";

            switch (showby)     //depending on the showby value we add one more constraint on the query.
            {
                case "Doctor AMKA": //showby = Doctor AMKA

                    if (!value.matches("[0-9]{11}"))
                        throw new ParseException("Invalid AMKA", 0); //in case of invalid AMKA format, we throw a parse exception

                    query += " AND DOCTOR_doctorAMKA = ?"; //add Doctor AMKA constraint
                    break;

                case "Date": //showby = Date
                    //in case we want to search by date, we have to change its format because the format is different in the database
                    value = changeDateFormat("dd-MM-yyyy", "yyyy-MM-dd", value);

                    query += " AND date = ?"; //add date constraint
                    break;

                case "Specialty": //showby = Specialty

                    query += " AND specialty = ?"; //add doctor specialty constraint
                    break;
            }

            //set the first query parameter equal to the patient's AMKA
            statement = connection.prepareStatement(query);
            statement.setString(1, session.getAttribute("patientAMKA").toString());

            //if we dont want to show all the history appointments, we have to add the doctor attribute value parameter to query
            if (!showby.equals("Show all"))
                statement.setString(2, value);

            //after executing the correct statement we check the results.
            rs = statement.executeQuery();

            if (rs.next()) //in case there is at least one record, make the table headers
            {
                StringBuilder html = new StringBuilder(""); //a string builder object to store the html content we want to show on "ScheduledAppointments.jsp"

                //append table headers on html variable
                html.append(
                            "<table>"
                            + "<tr>"
                            + "<th>Date</th>"
                            + "<th>Start time</th>"
                            + "<th>End time</th>"
                            + "<th>Doctor AMKA</th>"
                            + "<th>Doctor name</th>"
                            + "<th>Doctor surname</th>"
                            + "<th>Doctor specialty</th>"
                            + "</tr>");

                String date;
                String startSlotTime;
                String endSlotTime;
                String DOCTOR_doctorAMKA;
                String Doctor_specialty;
                String Doctor_name;
                String Doctor_surname;
                String htmlRow;

                do  //append the result's rows on the html variable
                {
                    date = rs.getString("date");

                    //change the date to the correct format before storing it into the variable
                    date = changeDateFormat("yyyy-MM-dd", "dd-MM-yyyy", date);
                    startSlotTime = rs.getString("startSlotTime");
                    endSlotTime = rs.getString("endSlotTime");
                    DOCTOR_doctorAMKA = rs.getString("DOCTOR_doctorAMKA");
                    Doctor_specialty = rs.getString("specialty");
                    Doctor_name = rs.getString("name");
                    Doctor_surname = rs.getString("surname");

                    //Get the scheduled appointment row
                    htmlRow = createTableRow(1, date, startSlotTime, endSlotTime, DOCTOR_doctorAMKA, Doctor_specialty, Doctor_name, Doctor_surname);

                    //Append the result on html variable
                    html.append(htmlRow);

                } while (rs.next());

                html.append("</table>");
                setHTML(html); //Set HTML equal to html. HTML content is being shown at "ScheduledAppointments.jsp"
                response.sendRedirect("ScheduledAppointments.jsp"); //redirect back
            }
            else if (!rs.next() && showby.equals("Show all")) //if there is not any record on the results and the option
            {                                                // is 'Show all', that means there is not any scheduled appointment
                Fail(response, "You have not any scheduled appointments yet", "patient_main_environment.jsp");
            }
            else  //In this case, there is not any record on the results but the option wasn't 'Show all'.
            {     //That means there is not any results JUST for the restrictions we had set

                //convert date to dd-MM-yyyy format in order to inform the user
                if (showby.equals("Date"))
                    value = changeDateFormat("yyyy-MM-dd", "dd-MM-yyyy", value);

                Fail(response, "No results found for " + showby + " " + value, "ScheduledAppointments.jsp");
            }

            rs.close();
            connection.close(); //close ResultSet and Connection

        }
        catch (ParseException e) //parse exception occurs if we try to search a date or an AMKA with invalid format typed
        {
            Fail(response, "Invalid " + showby + " format", "ScheduledAppointments.jsp");
        }
        catch (Exception e) //Show message on page in any other case
        {
            PrintWriter showhtml = response.getWriter();
            showhtml.println(e.toString());
        }
    }

    // Getter for the attribute AMKA
    public String getAMKA() {
        return this.AMKA;
    }

}

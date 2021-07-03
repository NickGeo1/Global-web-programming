package com.classes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    private static StringBuilder html = new StringBuilder("");

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


    /**
     *
     *
     */
    public static void searchAvailableAppointments(String start_date, String end_date, String searchby, String value, HttpServletResponse response, DataSource datasource) throws IOException
    {
//        if (!isLoggedOn())
//        {
//            System.out.println("You must be logged on to search for available appointments.");
//            return;
//        }

        try
        {
            connection = datasource.getConnection(); //connection object for database connection

            String query = "SELECT date,startSlotTime,endSlotTime,DOCTOR_doctorAMKA,specialty,name,surname " +
                    "FROM appointment JOIN doctor ON DOCTOR_doctorAMKA = doctorAMKA " +
                    "WHERE PATIENT_patientAMKA = 0 AND date BETWEEN ? AND ?";

            switch (searchby)     //depending on the showby value we execute the corresponding sql statement
            {
                case "Doctor AMKA":

                    if (!value.matches("[0-9]{11}"))
                    {
                        html.append("Invalid AMKA format");
                        connection.close();
                        return;
                    }

                    query += " AND DOCTOR_doctorAMKA = ?";
                    statement = connection.prepareStatement(query);
                    statement.setString(1, start_date);
                    statement.setString(2, end_date);
                    statement.setString(3, value);
                    break;

                case "Full name":

                    String[] names = value.split(" ");

                    query += " AND (name = ? OR surname=?)";
                    statement = connection.prepareStatement(query);
                    statement.setString(1, start_date);
                    statement.setString(2, end_date);
                    statement.setString(3, names[0]);
                    statement.setString(4, names[1]);
                    break;

                case "Specialty":

                    query += " AND specialty = ?";
                    statement = connection.prepareStatement(query);
                    statement.setString(1, start_date);
                    statement.setString(2, end_date);
                    statement.setString(3, value);
                    break;

                default:
                    statement = connection.prepareStatement(query);
                    statement.setString(1, start_date);
                    statement.setString(2, end_date);
            }

            rs = statement.executeQuery();

            if (rs.next()) //in case there is at least one record, make the table headers
            {
                html = new StringBuilder(
                        "<table>"
                                + "<tr>"
                                + "<th>Date</th>"
                                + "<th>Start time</th>"
                                + "<th>End time</th>"
                                + "<th>Doctor AMKA</th>"
                                + "<th>Doctor specialty</th>"
                                + "<th>Doctor name</th>"
                                + "<th>Doctor surname</th>"
                                + "</tr>");

                String date;
                String startSlotTime;
                String endSlotTime;
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
                    DOCTOR_doctorAMKA = rs.getString("DOCTOR_doctorAMKA");
                    Doctor_specialty = rs.getString("specialty");
                    Doctor_name = rs.getString("name");
                    Doctor_surname = rs.getString("surname");

                    htmlRow = createTableRow(2, date, startSlotTime, endSlotTime, "", DOCTOR_doctorAMKA, Doctor_specialty, Doctor_name, Doctor_surname);
                    html.append(htmlRow);

                } while (rs.next());

                html.append("</table>");
            }
            else if (!rs.next() && searchby.equals("Show all"))
            {
                start_date = changeDateFormat("yyyy-MM-dd", "dd-MM-yyyy", start_date);
                end_date = changeDateFormat("yyyy-MM-dd", "dd-MM-yyyy", end_date);

                html.append("There is not any appointment available on interval "+start_date+" through "+end_date);
            }
            else  //In this case, there is not any record on the results but the option wasn't 'Show all'.
            {     //That means there is not any results JUST for the restrictions we had set

                start_date = changeDateFormat("yyyy-MM-dd", "dd-MM-yyyy", start_date);
                end_date = changeDateFormat("yyyy-MM-dd", "dd-MM-yyyy", end_date);

                html.append("No results found for interval "+start_date+" through "+end_date+" and "+searchby + " " + value);
            }

            rs.close();
            connection.close(); //close ResultSet and Connection

        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            html.append("Invalid firstname/lastname format");
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
        finally
        {
            response.sendRedirect("AvailableDoctorAppointments.jsp");
        }
    }


    public static void bookAppointment(String date, String start, String end, String dAMKA, HttpServletResponse response, HttpServletRequest request, DataSource datasource) throws IOException
    {
        HttpSession session = request.getSession();

        try
        {
            connection = datasource.getConnection();

            statement = connection.prepareStatement("UPDATE appointment SET PATIENT_patientAMKA = ? WHERE date = ? AND startSlotTime=? AND endSlotTime=? AND DOCTOR_doctorAMKA=?");
            statement.setString(1, session.getAttribute("patientAMKA").toString());
            statement.setString(2, changeDateFormat("dd-MM-yyyy","yyyy-MM-dd",date));
            statement.setString(3, start);
            statement.setString(4, end);
            statement.setString(5, dAMKA);
            statement.execute();
            connection.close();
            html.append("Thank you for using our web application to book your appointment "+session.getAttribute("name") +
                    "!\nYour appointment has been booked on "+date+" at "+ start +" until "+end +"(Doctor's AMKA: "+dAMKA +")");

            searchAvailableAppointments("","","","",response,datasource);
        }
        catch(Exception e)
        {
            PrintWriter showhtml = response.getWriter();
            showhtml.println(e.toString());
        }
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
    public static void showAppointmentHistory(String showby, String value, HttpServletResponse response, HttpServletRequest request, DataSource datasource) throws IOException
    {
        HttpSession session  = request.getSession();

        PrintWriter showhtml = response.getWriter();

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
            statement.setString(1, session.getAttribute("patientAMKA").toString());

            if(!showby.equals("Show all"))
                statement.setString(2, value);

            //after executing the correct statement we check the results.

            rs = statement.executeQuery();

            if(rs.next()) //in case there is at least one record, make the table headers
            {

                html.append("<table>");
                html.append("<tr>");
                html.append("<th>Date</th>");
                html.append("<th>Start time</th>");
                html.append("<th>End time</th>");
                html.append("<th>Patient AMKA</th>");
                html.append("<th>Doctor AMKA</th>");
                html.append("<th>Doctor specialty</th>");
                html.append("<th>Doctor name</th>");
                html.append("<th>Doctor surname</th>");
                html.append("</tr>");

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

                    htmlRow = createTableRow(0, date, startSlotTime, endSlotTime, PATIENT_patientAMKA, DOCTOR_doctorAMKA, Doctor_specialty, Doctor_name, Doctor_surname);

                    html.append(htmlRow);

                }while(rs.next());

                html.append("</table>");
                response.sendRedirect("appointmenthistory.jsp");
            }
            else if(!rs.next() && showby.equals("Show all")) //if there is not any record on the results and the option
            {                                                // is 'Show all', that means history is empty
                Fail(response,"Appointment history is empty","appointmenthistory.jsp");
            }
            else  //In this case, there is not any record on the results but the option wasn't 'Show all'.
            {     //That means there is not any results JUST for the restrictions we had set

                if(showby.equals("Date"))
                    value = changeDateFormat("yyyy-MM-dd", "dd-MM-yyyy", value);

                Fail(response,"No results found for "+showby + " " + value,"appointmenthistory.jsp");
            }

            rs.close();
            connection.close(); //close ResultSet and Connection

        }
        catch (ParseException e) //parse exception occurs if we try to search a date or an AMKA with invalid format typed
        {
            Fail(response,"Invalid " + showby + " format","appointmenthistory.jsp");
        }
        catch(Exception e)
        {
            showhtml.println(e.toString());
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
    public static void showScheduledAppointments(String showby, String value, HttpServletResponse response, HttpServletRequest request , DataSource datasource) throws IOException
    {
        HttpSession session = request.getSession();

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
            statement.setString(1, session.getAttribute("patientAMKA").toString());

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

                    htmlRow = createTableRow(1, date, startSlotTime, endSlotTime, PATIENT_patientAMKA, DOCTOR_doctorAMKA, Doctor_specialty, Doctor_name, Doctor_surname);
                    html.append(htmlRow);

                }while(rs.next());

                html.append("</table>");
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
    public static void cancelScheduledAppointment(String date, String pAMKA, String dAMKA, HttpServletRequest request, HttpServletResponse response, DataSource datasource) throws IOException
    {
        Date now = new Date(); //today's date
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date appointment_date = null;

        try
        {
            appointment_date = df.parse(date); //appointment date

            Calendar cal =  Calendar.getInstance();
            cal.setTime(now);
            cal.add(Calendar.DAY_OF_MONTH, 3);
            Date nowplus3 = cal.getTime(); //appointment date + 3 days

            if(nowplus3.after(appointment_date))
            {
                Fail(response, "You cannot cancel an appointment that is scheduled in less than 3 days from now", "ScheduledAppointments.jsp");
                return;
            }

            connection = datasource.getConnection();
            statement = connection.prepareStatement("UPDATE appointment SET PATIENT_patientAMKA=0 WHERE date = ? AND PATIENT_patientAMKA = ? AND DOCTOR_doctorAMKA = ?");
            date = changeDateFormat("dd-MM-yyyy","yyyy-MM-dd",date);
            statement.setString(1, date);
            statement.setString(2, pAMKA);
            statement.setString(3, dAMKA);
            statement.execute();
            connection.close();

            showScheduledAppointments("Show all","", response, request, datasource);
        }
        catch(ParseException e)
        {
            PrintWriter exc = response.getWriter();
            exc.println("Parse exception during date parsing: "+e.toString());
        }
        catch(Exception e)
        {
            PrintWriter showhtml = response.getWriter();
            showhtml.println(e.toString());
        }
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
    private static String createTableRow(int table_case, String date, String startSlotTime, String endSlotTime, String PATIENT_patientAMKA, String DOCTOR_doctorAMKA, String Doctor_specialty, String Doctor_name, String Doctor_surname)
    {
        StringBuilder tablerow = new StringBuilder();

        tablerow.append("<tr>");
        tablerow.append("<td>" + date + "</td>");
        tablerow.append("<td>" + startSlotTime + "</td>");
        tablerow.append("<td>" + endSlotTime + "</td>");
        if(table_case != 2)
            tablerow.append("<td>" + PATIENT_patientAMKA + "</td>");
        tablerow.append("<td>" + DOCTOR_doctorAMKA + "</td>");
        tablerow.append("<td>" + Doctor_specialty + "</td>");
        tablerow.append("<td>" + Doctor_name + "</td>");
        tablerow.append("<td>" + Doctor_surname + "</td>");
        if(table_case == 1)
            tablerow.append("<td><button style=\"width:60px;\" type=\"button\" onclick=\"setvalue(7); cancelappointment('"+date+"','"+PATIENT_patientAMKA+"','"+DOCTOR_doctorAMKA+"');\">Cancel</button></td>");
        else if(table_case == 2)
            tablerow.append("<td><button type=\"button\" onclick=\"setvalue(8); bookappointment('"+date+"','"+startSlotTime+"','"+endSlotTime+"','"+DOCTOR_doctorAMKA+"');\">Book</button></td>");
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

    public static void clearHTML()
    {
        html.setLength(0);
    }
}

package com.servlets;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.sql.DataSource;

@WebServlet(name = "PatientServlet", value = "/patientlogin") //servlet annotation
public class PatientServlet extends HttpServlet
{
    private static DataSource datasource = null; //datasource object

    private static int PATIENT_SERVLET_ACTION; //Variable that describes what action the servlet should perform for the patient

    //init method runs at every start of a servlet action.
    //init searches the patient's attributes from the database
    //and initializes a patient object with these attributes

    public void init()
    {
        try
        {
            InitialContext ctx = new InitialContext();
            datasource = (DataSource)ctx.lookup("java:comp/env/jdbc/LiveDataSource");

        } catch(Exception e)
        {
            System.out.println("A Datasource exception has occured: "+e.toString());
        }

    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        //Get the action value from the hidden html input tag(see patient_main_environment.jsp)

        PATIENT_SERVLET_ACTION = Integer.parseInt(request.getParameter("patient_action"));

        //Depending on the action value, execute the corresponding method

        switch(PATIENT_SERVLET_ACTION)
        {
            case 1:
                break;

            case 2:
                break;

            case 3:
                break;

            case 4:
                break;
        }

        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");

        String pat = request.getParameter("fn");

        PrintWriter showhtml = response.getWriter();
        showhtml.println("<html>");
        showhtml.print("<head><title>Appointment History</title></head>");
        showhtml.println("<body>");

        try
        {
            Connection con = datasource.getConnection();
            Statement stmt = con.createStatement();

            String amka = null;

            PreparedStatement showHistory = con.prepareStatement("SELECT patientAMKA FROM patient WHERE userid=?");
            showHistory.setString(1,pat);

            ResultSet rs = showHistory.executeQuery();
            rs.next();

            amka = rs.getString("patientAMKA");

            rs.close();

            showHistory = con.prepareStatement("SELECT * FROM appointment WHERE PATIENT_patientAMKA=?");
            showHistory.setString(1,amka);

            rs = showHistory.executeQuery();

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

    private String createTableRow(String date, String startSlotTime, String endSlotTime, String PATIENT_patientAMKA, String DOCTOR_doctorAMKA)
    {
        String tablerow = "<tr>";
        tablerow  += "<td>" + date + "</td>";
        tablerow  += "<td>" + startSlotTime + "</td>";
        tablerow  += "<td>" + endSlotTime + "</td>";
        tablerow  += "<td>" + PATIENT_patientAMKA + "</td>";
        tablerow  += "<td>" + DOCTOR_doctorAMKA + "</td>";
        tablerow +="</tr>";

        return tablerow;
    }
}
package com.servlets;

import com.classes.Doctor;
import com.classes.Patient;
import com.classes.Users;

import javax.naming.InitialContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@WebServlet(name = "DoctorServlet", value = "/doctor")
public class DoctorServlet extends HttpServlet
{
    private static String DOCTOR_SERVLET_ACTION;
    private static DataSource datasource;

    public void init()
    {
        try
        {
            InitialContext ctx = new InitialContext();
            datasource = (DataSource)ctx.lookup("java:comp/env/jdbc/LiveDataSource");
        }
        catch(Exception e)
        {
            System.out.println("A Datasource exception has occurred: " + e.toString());
        }

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        DOCTOR_SERVLET_ACTION = request.getParameter("doctor_action");
        String requestedURL = request.getHeader("referer");

        switch (DOCTOR_SERVLET_ACTION)
        {
            //login
            case "login":
                Users.Login("Doctor", request, response, datasource);
                break;

            //logout
            case "logout":
                Users.Logout(response, request);
                break;

            //set availability
            case "set availability":

                //if the doctor is on his main page and clicks the button to set an appointment, we redirect him to the set availability page.
                if (requestedURL.endsWith("doctor_main_environment.jsp"))
                    response.sendRedirect("doctor_set_availability.jsp");

                //otherwise, it means he's on the set availability page
                else
                {
                    //at first we get the date the doctor has set.
                    LocalDateTime date = LocalDateTime.parse(request.getParameter("date_of_appointment"));

                    //then we call the set availability function to insert data into the database.
                    if (Doctor.set_availability(datasource, date, request.getSession().getAttribute("doctorAMKA").toString()))
                    {
                        //if it's successful, then we redirect he doctor to the success page.
                        request.setAttribute("action", "set availability for " + date.toString());
                        request.setAttribute("redirect", "doctor_set_availability.jsp");
                        RequestDispatcher RD = request.getRequestDispatcher("success.jsp");
                        RD.forward(request, response);
                    }
                    else
                    {
                        //otherwise, we redirect the doctor to the fail page.
                        Users.Fail(response, "An unexpected error occurred.", "doctor_set_availability.jsp");
                    }
                }

                break;

            case "view appointments":
                if (requestedURL.endsWith("doctor_main_environment.jsp"))
                    response.sendRedirect("doctor_view_appointments.jsp");
                else
                {
                    String date;

                    if(request.getParameter("showby").equals("Week"))
                        date = request.getParameter("week");
                    else
                        date = request.getParameter("month");

                    Doctor.viewAppointments(request.getParameter("showby"),date,response,request,datasource);
                }

                break;

            case "cancel":
                String date = request.getParameter("datevalue");
                String pAMKA = request.getParameter("patientAMKA");
                String dAMKA = (String) request.getSession().getAttribute("doctorAMKA");
                Patient.cancelScheduledAppointment(date,pAMKA,dAMKA,request,response,datasource);
                break;
        }
    }
}
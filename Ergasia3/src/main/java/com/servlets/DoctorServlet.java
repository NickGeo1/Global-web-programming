package com.servlets;

import com.classes.Doctor;
import com.classes.Users;

import javax.naming.InitialContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
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

            case "set availability":
                if (requestedURL.endsWith("doctor_main_environment.jsp"))
                    response.sendRedirect("doctor_set_availability.jsp");
                else
                {
                    LocalDate date = LocalDate.parse(request.getParameter("date_of_appointment"));
                }

                break;

            case "view appointments":
                if (requestedURL.endsWith("doctor_main_environment.jsp"))
                    response.sendRedirect("doctor_view_appointments.jsp");
                else
                    response.sendRedirect("doctor_main_environment.jsp");
                break;
        }
    }
}
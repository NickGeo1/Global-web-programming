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
        }
    }
}
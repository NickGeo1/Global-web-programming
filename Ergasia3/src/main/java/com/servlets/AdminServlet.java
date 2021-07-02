package com.servlets;

import javax.naming.InitialContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Enumeration;

import com.classes.Admin;
import com.classes.Users;

@WebServlet(name = "AdminServlet", value = "/admin")
public class AdminServlet extends HttpServlet
{
    private static String ADMIN_SERVLET_ACTION;
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
        ADMIN_SERVLET_ACTION = request.getParameter("admin_action");
        String requestedURL = request.getHeader("referer");

        //based on what an admin wants to do, we have a switch-case depending on his actions.
        switch (ADMIN_SERVLET_ACTION)
        {
                //login
            case "login":
                Users.Login("Admin", request, response, datasource);
                break;

            case "add_admin":
                if (requestedURL.endsWith("admin_main_environment.jsp"))
                    response.sendRedirect("add_new_admin.jsp");

                else if (requestedURL.endsWith("add_new_admin.jsp"))
                {
                    String username  = request.getParameter("username");
                    String password  = request.getParameter("password");
                    String firstname = request.getParameter("firstname");
                    String surname   = request.getParameter("surname");
                    Integer age      = Integer.parseInt(request.getParameter("age"));

                    Admin.add_admin(response, datasource, username, password, firstname, surname, age);
                }

                break;

            case "add_patient":
                if (requestedURL.endsWith("admin_main_environment.jsp"))
                    response.sendRedirect("add_new_patient.jsp");

                else if (requestedURL.endsWith("add_new_patient.jsp"))
                {
                    String username   = request.getParameter("username");
                    String password   = request.getParameter("password");
                    String firstname  = request.getParameter("firstname");
                    String surname    = request.getParameter("surname");
                    Integer age       = Integer.parseInt(request.getParameter("age"));
                    String AMKA       = request.getParameter("AMKA");

                    Admin.add_patient(response, datasource, username, password, firstname, surname, age, AMKA);
                }

                break;

            case "add_doctor":
                if (requestedURL.endsWith("admin_main_environment.jsp"))
                    response.sendRedirect("add_new_doctor.jsp");

                else if (requestedURL.endsWith("add_new_doctor.jsp"))
                {
                    String username   = request.getParameter("username");
                    String password   = request.getParameter("password");
                    String firstname  = request.getParameter("firstname");
                    String surname    = request.getParameter("surname");
                    Integer age       = Integer.parseInt(request.getParameter("age"));
                    String speciality = request.getParameter("speciality");
                    String AMKA       = request.getParameter("AMKA");

                    Admin.add_doctor(request, response, datasource, username, password, firstname, surname, age, speciality, AMKA);
                }

                break;

            case "delete_admin":
                if (requestedURL.endsWith("admin_main_environment.jsp"))
                {
                    response.sendRedirect("delete_admin.jsp");
                }

                else if (requestedURL.endsWith("delete_admin.jsp"))
                {

                }
                break;

            case "delete_patient":
                if (requestedURL.endsWith("admin_main_environment.jsp"))
                {
                    response.sendRedirect("delete_patient.jsp");
                }

                else if (requestedURL.endsWith("delete_patient.jsp"))
                {

                }
                break;

            case "delete_doctor":
                if (requestedURL.endsWith("admin_main_environment.jsp"))
                {
                    response.sendRedirect("delete_doctor.jsp");
                }

                else if (requestedURL.endsWith("delete_doctor.jsp"))
                {

                }
                break;

            case "logout":
                Users.Logout(response, request);
                break;
        }
    }
}
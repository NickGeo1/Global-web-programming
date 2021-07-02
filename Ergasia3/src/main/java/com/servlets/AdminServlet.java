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
    private static Admin admin;
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

        switch (ADMIN_SERVLET_ACTION)
        {
                //login
            case "login":
                admin = (Admin) Users.Login("Admin", request, response, datasource);
                break;

                //delete an admin
            case "delete_admin":
                if (requestedURL.endsWith("admin_main_environment.jsp"))
                {
                    response.sendRedirect("delete_admin.jsp");
                }

                else if (requestedURL.endsWith("delete_admin.jsp"))
                {

                }
                break;

                //delete patient
            case "delete_patient":
                if (requestedURL.endsWith("admin_main_environment.jsp"))
                {
                    response.sendRedirect("delete_patient.jsp");
                }

                else if (requestedURL.endsWith("delete_patient.jsp"))
                {

                }
                break;

                //delete a doctor
            case "delete_doctor":
                if (requestedURL.endsWith("admin_main_environment.jsp"))
                {
                    response.sendRedirect("delete_doctor.jsp");
                }

                else if (requestedURL.endsWith("delete_doctor.jsp"))
                {

                }
                break;

                //logout
            case "logout":
                admin.Logout(response);
                admin = null;
                break;
        }
    }

    public static Admin getAdmin()
    {
        return admin;
    }
}
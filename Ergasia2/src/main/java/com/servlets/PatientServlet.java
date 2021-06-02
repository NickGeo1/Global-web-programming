package com.servlets;

import java.io.*;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.sql.DataSource;

@WebServlet(name = "PatientServlet", value = "/patientlogin")
public class PatientServlet extends HttpServlet
{
    private DataSource datasource = null;

    public void init() throws ServletException {
        try {

            InitialContext ctx = new InitialContext();
            datasource = (DataSource)ctx.lookup("java:comp/env/jdbc/LiveDataSource");

        } catch(Exception e) {
            throw new ServletException(e.toString());
        }

    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
       String pat =  request.getParameter("patient");
       PrintWriter showhtml = response.getWriter();
       showhtml.println(pat);
    }
}
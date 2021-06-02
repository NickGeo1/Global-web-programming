package com.servlets;

import java.io.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "PatientServlet", value = "/patientlogin")
public class PatientServlet extends HttpServlet
{
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
       String pat =  request.getParameter("patient");
       PrintWriter showhtml = response.getWriter();
       showhtml.println(pat);
    }
}
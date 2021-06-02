package com.servlets;

import com.classes.Patient;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "PatientRegisterServlet", value = "/patientregister")
public class PatientRegisterServlet extends HttpServlet
{
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        //getting the parameters as they are from the form.
        Patient patient = new Patient(request.getParameter("username"),
                                      request.getParameter("password"),
                                      request.getParameter("fn"),
                                      request.getParameter("ln"),
                                      Integer.parseInt(request.getParameter("age")),
                                      request.getParameter("AMKA")
                                     );

        System.out.println(patient.toString());
    }
}
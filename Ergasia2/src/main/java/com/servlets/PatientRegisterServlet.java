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
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String firstname = request.getParameter("fn");
        String lastname = request.getParameter("ln");
        int age = Integer.parseInt(request.getParameter("age"));
        String AMKA = request.getParameter("AMKA");

        Patient patient = new Patient(username, password, firstname, lastname, age, AMKA);
        System.out.println(patient.toString());
    }
}
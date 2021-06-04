package com.servlets;

import com.classes.Doctor;
import com.classes.Patient;
import com.classes.Users;

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

@WebServlet(name = "PatientServlet", value = "/patient") //servlet annotation
public class PatientServlet extends HttpServlet
{
    private static DataSource datasource = null; //datasource object

    private static int PATIENT_SERVLET_ACTION; //Variable that describes what action the servlet should perform for the patient

    private static Patient patient; //Patient variable that is being initialized at login and uninitialized at logout

    //init method runs at the very start of a servlet.
    //Here, init gets the datasource which corresponds
    //to our database

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
            case 1:         //appointment history
                patient.showAppointmentHistory("","", request, response, datasource);
                break;

            case 2:         //Book an appointment
                break;

            case 3:         //Scheduled appointments
                break;

            case 4:         //logout
                break;

            case 5:         //register
                //getting the parameters as they are from the form.
                patient = new Patient(  request.getParameter("username"),
                                        request.getParameter("password"),
                                        request.getParameter("fn"),
                                        request.getParameter("ln"),
                                        Integer.parseInt(request.getParameter("age")),
                                        request.getParameter("AMKA")
                );

                //registering the patient as they are.
                patient.Register(response);
                break;

            //login

            //login case makes a connection with the database, it is
            //searching the patient's attributes from it
            //and initializes the patient object with these attributes

            case 6:
                patient = (Patient) Users.Login("Patient", request, response, datasource);
                break;
        }
    }

}
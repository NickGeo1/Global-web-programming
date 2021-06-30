package com.servlets;

import com.classes.Patient;
import com.classes.Users;

import java.io.*;
import javax.naming.InitialContext;
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
                patient.showAppointmentHistory(request.getParameter("showby"), request.getParameter("value"), response, datasource);
                break;

            case 2:         //Book an appointment
                break;

            case 3:         //Scheduled appointments
                patient.showScheduledAppointments(request.getParameter("showby"), request.getParameter("value"), request, response, datasource);
                break;

            case 4:         //logout
                patient.Logout(response);
                patient = null;
                break;

            case 5:         //register
                //getting the age as it is from the form and try to cast it to integer
                int age = 0;
                try
                {
                    age = Integer.parseInt(request.getParameter("age"));

                }catch (NumberFormatException e)
                {
                    Users.Fail(response,"Invalid Age! A registered age must be a number.","register.html");
                    return;
                }

                //getting the rest parameters as they are from the form.
                patient = new Patient(  request.getParameter("username"),
                                        request.getParameter("password"),
                                        request.getParameter("fn"),
                                        request.getParameter("ln"),
                                        age,
                                        request.getParameter("AMKA")
                );

                //registering the patient as he is.
                patient.Register(response, datasource);
                patient = null;
                break;

            //login

            case 6:
                patient = (Patient) Users.Login("Patient", request, response, datasource);
                break;
        }
    }

    public static Patient getPatient() //returns the logged on patient object
    {
        return patient;
    }
}
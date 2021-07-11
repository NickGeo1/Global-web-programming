package com.servlets;

import com.classes.Patient;
import com.classes.Users;

import java.io.*;
import java.text.ParseException;
import javax.naming.InitialContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.sql.DataSource;

@WebServlet(name = "PatientServlet", value = "/patient") //servlet annotation
public class PatientServlet extends HttpServlet
{
    private static DataSource datasource = null; //datasource object

    private static int PATIENT_SERVLET_ACTION; //Variable that describes what action the servlet should perform for the patient

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
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        //Get the action value from the hidden html input tag(see patient_main_environment.jsp)
        PATIENT_SERVLET_ACTION = Integer.parseInt(request.getParameter("patient_action"));

        String requestedURL = request.getHeader("referer");
        //Depending on the action value, execute the corresponding method

        switch(PATIENT_SERVLET_ACTION)
        {
            case 1:         //appointment history
                if (requestedURL.endsWith("patient_main_environment.jsp"))
                    response.sendRedirect("appointmenthistory.jsp");
                else
                    Patient.showAppointmentHistory(request.getParameter("showby"), request.getParameter("value"), response, request, datasource);
                break;

            case 2:         //Search available appointment
                String value_param = "";

                //dates are being passed in yyyy-MM-dd format from the form
                if (requestedURL.endsWith("patient_main_environment.jsp"))
                {
                    response.sendRedirect("AvailableDoctorAppointments.jsp");
                    return;
                }
                else if (request.getParameter("searchby").equals("Full name"))
                    value_param = request.getParameter("value") + " " + request.getParameter("value2");
                else
                    value_param = request.getParameter("value");

                Patient.searchAvailableAppointments(request.getParameter("start"), request.getParameter("end"), request.getParameter("searchby"), value_param, response, datasource);
                break;

            case 3:         //Scheduled appointments
                if(requestedURL.endsWith("patient_main_environment.jsp"))
                    response.sendRedirect("ScheduledAppointments.jsp");
                else
                    Patient.showScheduledAppointments(request.getParameter("showby"), request.getParameter("value"), response, request, datasource);
                break;

            case 4:         //logout
                Patient.Logout(response, request);
                break;

            case 5:         //register
                //getting the age as it is from the form and try to cast it to integer
                int age = 0;
                try
                {
                    age = Integer.parseInt(request.getParameter("age"));

                }
                catch (NumberFormatException e)
                {
                    Users.Fail(response,"Invalid Age! A registered age must be a number.","index.jsp");
                    return;
                }

                //getting the rest parameters as they are from the form.
                Patient patient = new Patient(  request.getParameter("username"),
                                        request.getParameter("password"),
                                        request.getParameter("fn"),
                                        request.getParameter("ln"),
                                        age,
                                        request.getParameter("AMKA")
                );

                //registering the patient as he is.
                patient.Register(request, response, datasource,"register.jsp");
                break;

            //login

            case 6:
                if (!request.getParameter("category").equals("Patient"))
                {
                    Users.Fail(response, "Wrong Users Category!", "login.jsp");
                    return;
                }

                Users.Login("Patient",request,response,datasource);
                break;

            //cancel appointment
            case 7:
                String date = request.getParameter("datevalue");
                String pAMKA = (String) request.getSession().getAttribute("patientAMKA");
                String dAMKA = request.getParameter("doctorAMKA");
                String start = request.getParameter("start");
                Patient.cancelScheduledAppointment(date,pAMKA,dAMKA,start,request,response,datasource);
                break;

            //book appointment
            case 8:
                String date2 = request.getParameter("datevalue");
                String start2 = request.getParameter("startvalue");
                String end = request.getParameter("endvalue");
                String dAMKA2 = request.getParameter("dAMKA");
                Patient.bookAppointment(date2,start2,end,dAMKA2,response,request,datasource);
        }
    }
}
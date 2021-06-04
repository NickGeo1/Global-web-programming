package com.servlets;

import com.classes.Patient;

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
                patient = new Patient(request.getParameter("username"),
                        request.getParameter("password"),
                        request.getParameter("fn"),
                        request.getParameter("ln"),
                        Integer.parseInt(request.getParameter("age")),
                        request.getParameter("AMKA")
                );

                System.out.println(patient.toString());

                patient = null;

                break;

            //login

            //login case makes a connection with the database, it is
            //searching the patient's attributes from it
            //and initializes a patient object with these attributes

            case 6:

                String name = request.getParameter("username");
                String pass = request.getParameter("password");

                try
                {
                    Connection con = datasource.getConnection();

                    PreparedStatement stmnt = con.prepareStatement("SELECT hashedpassword FROM patient WHERE username=?");
                    stmnt.setString(1,name);

                    ResultSet rs = stmnt.executeQuery();

                    if(rs.next() && rs.getString("hashedpassword").equals(pass)) //correct details
                    {
                        stmnt = con.prepareStatement("SELECT * FROM patient WHERE username=?");
                        stmnt.setString(1,name);

                        rs = stmnt.executeQuery();
                        rs.next();

                        patient = new Patient(rs.getString("username"), rs.getString("hashedpassword"),
                                rs.getString("name"), rs.getString("surname"),
                                rs.getInt("age"),rs.getString("patientAMKA") );

                        patient.loggedOn = true;

                        response.sendRedirect("patient_main_environment.jsp");

                    }else if(rs.next() && !rs.getString("hashedpassword").equals(pass)) //correct username wrong pass
                    {

                        response.sendRedirect("fail.jsp");

                    }else
                    {
                        response.sendRedirect("fail.jsp");
                    }

                    rs.close();
                    con.close();

                }catch(Exception e)
                {
                    System.out.println("An exception occured during database connection");
                }

                break;

        }
    }

}
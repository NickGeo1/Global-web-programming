package com.classes;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * This is the model of an Admin. Admins are the only ones who can add,modify and delete doctors from the
 * application
 */
public class Admin extends Users
{
    private static Connection connection;
    private static PreparedStatement statement;
    private static ResultSet rs;

    // Constructor method
    public Admin(String username, String password, String firstname, String surname, int age)
    {

        super(username, password, firstname, surname, age);

    }

    public void DeleteDoctor(HttpServletResponse response, DataSource datasource, String AMKA) throws IOException
    {
        //an admin must be logged on to perform delete
        if (!isLoggedOn())
        {
            Users.Fail(response, "An admin must be logged on, in order to delete a doctor. Please login!", "login.html");
            return;
        }

        //executing sql at this point.
        try
        {
            //getting the connection and preparing the sql statement.
            connection = datasource.getConnection();
            statement  = connection.prepareStatement("DELETE FROM doctor WHERE doctorAMKA=?");
            statement.setString(1, AMKA);

            rs = statement.executeQuery();
            if (!rs.rowDeleted())
            {
                Users.Fail(response, "There is no such AMKA.", "admin_main_environment.jsp");
                rs.close();
                connection.close();
                return;
            }

            connection.close();
            rs.close();
            response.sendRedirect("register-success.html");
        }
        catch (Exception e)
        {
            Users.Fail(response, "An error has occurred. MESSAGE: " + e.getMessage(), "admin_main_environment.jsp");
        }

    }

    /**
     * This function is used to add/create a new doctor.
     * @return an object of type Doctor
     */
    public Doctor add_doctor(String username, String password, String firstname, String surname, int age, String speciality, String AMKA)
    {
        if (!isLoggedOn())
        {
            System.out.println("You must be logged on to add a new Doctor.");
            return null;
        }

        System.out.println("New doctor added!");
        return new Doctor (username, password, firstname, surname, age, speciality, AMKA);
    }

    /**
     * This function is used to modify a doctor that already exists.
     * @param username is the username of the doctor that we want to modify
     */
    public void change_doctor(String username){

        if (!isLoggedOn())
        {
            System.out.println("You must be logged on to modify a doctor.");
            return;
        }

        System.out.println("Doctor's characteristics have been changed!");
    }

    /**
     * This method returns the characteristics of each Admin
     * @return firstname,username,surname and age of Admin object
     */
    @Override
    public String toString(){
        return super.toString();
    }
}

package com.classes;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.xml.crypto.Data;
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
            Users.Fail(response, "An error has occurred. MESSAGE: " + e.getMessage(), "add_new_doctor.jsp");
        }

    }

    public void add_patient(HttpServletResponse response, DataSource datasource, String username, String password, String firstname, String surname, Integer age, String AMKA) throws IOException
    {
        //an admin must be logged on to perform delete
        if (!isLoggedOn())
        {
            Users.Fail(response, "An admin must be logged on, in order to add a patient. Please login!", "login.html");
            return;
        }

        //executing sql at this point.
        try
        {
            //getting the connection and preparing the sql statement.
            //search for duplicates in the doctor.
            connection = datasource.getConnection();
            statement  = connection.prepareStatement("SELECT * FROM doctor WHERE doctorAMKA=? OR username=?");
            statement.setString(1, AMKA);
            statement.setString(2, username);

            //if there are duplicates in the doctor table, abort.
            rs = statement.executeQuery();
            if (rs.next())
            {
                Users.Fail(response, "Duplicate Found in Doctors. Check again for AMKA/Username", "add_new_doctor.jsp");
                rs.close();
                connection.close();
                return;
            }

            //search for AMKA duplicates in patient
            statement  = connection.prepareStatement("SELECT * FROM patient WHERE patientAMKA=?");
            statement.setString(1, AMKA);

            //if there are any duplicates in the patient table, abort.
            rs = statement.executeQuery();
            if (rs.next())
            {
                Users.Fail(response, "Found an AMKA duplicate in Patient.", "add_new_doctor.jsp");
                rs.close();
                connection.close();
                return;
            }

            statement = connection.prepareStatement("INSERT INTO patient VALUES (?, ?, ?, ?, ?, NULL, ?)");
            statement.setString(1, AMKA);
            statement.setString(2, username);
            statement.setString(3, password);
            statement.setString(4, firstname);
            statement.setString(5, surname);
            statement.setString(6, age.toString());    //age, as a parameter is an Integer (not an int), so we convert it instantly to string.

            statement.execute();
            connection.close();
            rs.close();
            response.sendRedirect("new-patient-success.html");
        }
        catch (Exception e)
        {
            Users.Fail(response, "An error has occurred. MESSAGE: " + e.getMessage() + ", " + e.toString(), "admin_main_environment.jsp");
            e.printStackTrace();
        }
    }

    public void add_doctor(HttpServletResponse response, DataSource datasource, String username, String password, String firstname, String surname, Integer age, String speciality, String AMKA) throws IOException
    {
        //an admin must be logged on to perform delete
        if (!isLoggedOn())
        {
            Users.Fail(response, "An admin must be logged on, in order to add a doctor. Please login!", "login.html");
            return;
        }

        //executing sql at this point.
        try
        {
            //getting the connection and preparing the sql statement.
            //search for duplicates in the doctor.
            connection = datasource.getConnection();
            statement  = connection.prepareStatement("SELECT * FROM doctor WHERE doctorAMKA=? OR username=?");
            statement.setString(1, AMKA);
            statement.setString(2, username);

            //if there are duplicates in the doctor table, abort.
            rs = statement.executeQuery();
            if (rs.next())
            {
                Users.Fail(response, "Duplicate Found in Doctors. Check again for AMKA/Username", "add_new_doctor.jsp");
                rs.close();
                connection.close();
                return;
            }

            //search for AMKA duplicates in patient
            statement  = connection.prepareStatement("SELECT * FROM patient WHERE patientAMKA=?");
            statement.setString(1, AMKA);

            //if there are any duplicates in the patient table, abort.
            rs = statement.executeQuery();
            if (rs.next())
            {
                Users.Fail(response, "Found an AMKA duplicate in Patient.", "add_new_doctor.jsp");
                rs.close();
                connection.close();
                return;
            }

            statement = connection.prepareStatement("INSERT INTO doctor VALUES (?, ?, ?, ?, ?, ?, ?, NULL, ?)");
            statement.setString(1, AMKA);
            statement.setString(2, username);
            statement.setString(3, password);
            statement.setString(4, firstname);
            statement.setString(5, surname);
            statement.setString(6, speciality);
            statement.setString(7, this.getUsername());
            statement.setString(8, age.toString());    //age, as a parameter is an Integer (not an int), so we convert it instantly to string.

            statement.execute();
            connection.close();
            rs.close();
            response.sendRedirect("new-doctor-success.html");
        }
        catch (Exception e)
        {
            Users.Fail(response, "An error has occurred. MESSAGE: " + e.getMessage() + ", " + e.toString(), "admin_main_environment.jsp");
            e.printStackTrace();
        }
    }

    public void add_admin(HttpServletResponse response, DataSource datasource, String username, String password, String firstname, String surname, Integer age) throws IOException
    {
        //an admin must be logged on to perform delete
        if (!isLoggedOn())
        {
            Users.Fail(response, "An admin must be logged on, in order to add an admin. Please login!", "login.html");
            return;
        }

        //executing sql at this point.
        try
        {
            //getting the connection and preparing the sql statement.
            //search for duplicates in the doctor.
            connection = datasource.getConnection();
            statement  = connection.prepareStatement("SELECT * FROM admin WHERE username=?");
            statement.setString(1, username);

            //if there are duplicates in the doctor table, abort.
            rs = statement.executeQuery();
            if (rs.next())
            {
                Users.Fail(response, "Duplicate Found in Administrators. Check again for Username", "add_new_admin.jsp");
                rs.close();
                connection.close();
                return;
            }

            statement = connection.prepareStatement("INSERT INTO admin VALUES (?, ?, NULL, ?, ?, ?)");
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, age.toString());    //age, as a parameter is an Integer (not an int), so we convert it instantly to string.
            statement.setString(4, firstname);
            statement.setString(5, surname);

            statement.execute();
            connection.close();
            rs.close();
            response.sendRedirect("new-admin-success.html");
        }
        catch (Exception e)
        {
            Users.Fail(response, "An error has occurred. MESSAGE: " + e.getMessage() + ", " + e.toString(), "admin_main_environment.jsp");
            e.printStackTrace();
        }
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

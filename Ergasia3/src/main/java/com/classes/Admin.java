package com.classes;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
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

    public static void delete_users(HttpServletRequest request, HttpServletResponse response, DataSource datasource, String Table, String ValueOfElement, String delete_page) throws IOException
    {
        String ElementToDelete;

        switch (Table)
        {
            case "admin":
                ElementToDelete = "username";
                break;
            default:
                ElementToDelete = Table + "AMKA";
                break;
        }

        try
        {
            //getting the connection and preparing the sql statement.
            connection = datasource.getConnection();

            //the sql statement is intentionally vague, so all users can be deleted.
            //first we use a select statement to see if this AMKA/username exists.
            statement  = connection.prepareStatement("SELECT * FROM " + Table + " WHERE " + ElementToDelete + "=?");
            statement.setString(1, ValueOfElement);
            rs = statement.executeQuery();

            //if it doesn't exist, we show a fail page to the user.
            if (!rs.next())
            {
                Users.Fail(response, "There is no such " + ElementToDelete + ".", delete_page);
                rs.close();
                connection.close();
                return;
            }

            //otherwise we can execute the delete statement. If the delete statement retains a doctor or a patient to be deleted, we also delete this from the appointments table.
            rs.close();
            statement  = connection.prepareStatement((Table.equals("admin") ? "" : ("DELETE FROM appointment WHERE " + Table.toUpperCase() + "_" + Table + "AMKA=?;")));
            statement.setString(1, ValueOfElement);
            statement.execute();

            statement = connection.prepareStatement("DELETE FROM " + Table + " WHERE " + ElementToDelete + "=?;");
            statement.setString(1, ValueOfElement);
            statement.execute();

            connection.close();

            request.setAttribute("action", "deleted the " + Table + " with " + ElementToDelete + " " + ValueOfElement);
            request.setAttribute("redirect", delete_page);
            RequestDispatcher rd = request.getRequestDispatcher("success.jsp");
            rd.forward(request, response);
        }
        catch (Exception e)
        {
            Users.Fail(response, "An error has occurred. MESSAGE: " + e.getMessage(), delete_page);
        }

    }

    public static void add_patient(HttpServletRequest request, HttpServletResponse response, DataSource datasource, String username, String password, String firstname, String surname, Integer age, String AMKA) throws IOException
    {
        new Patient(username, password, firstname, surname, age, AMKA).Register(request, response,datasource,"add_new_patient.jsp");
    }

    public static void add_doctor(HttpServletRequest request, HttpServletResponse response, DataSource datasource, String username, String password, String firstname, String surname, Integer age, String speciality, String AMKA) throws IOException
    {
        new Doctor(username,password,firstname,surname,age,speciality,AMKA).Register(request,response,datasource,"add_new_doctor.jsp");
    }

    public static void add_admin(HttpServletRequest request, HttpServletResponse response, DataSource datasource, String username, String password, String firstname, String surname, Integer age) throws IOException
    {
        new Admin(username,password,firstname,surname,age).Register(request,response,datasource,"add_new_admin.jsp");
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

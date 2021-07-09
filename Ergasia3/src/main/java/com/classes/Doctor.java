package com.classes;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;

/**
 * This is the model (class) of a Doctor. Each doctor has his speciality and
 * some other abilities, which are to be able to see his appointments, to create a new appointment,
 * as well as say if he is available (or not) for a new appointment.
 */
public class Doctor extends Users
{
    private String speciality; // Speciality that a doctor has
    private static Connection connection;
    private static PreparedStatement statement;
    private final String AMKA; // This is the unique AMKA of each doctor

    // Constructor method
    public Doctor(String username, String password, String firstname, String surname, int age, String speciality, String AMKA)
    {

        super(username, password, firstname, surname, age); // Constructor of class Doctor calls superclasses' constructor
        this.speciality = speciality;
        this.AMKA = AMKA;

    }

    public static boolean set_availability(DataSource datasource, LocalDate localDate, String AMKA)
    {
        Date date = new Date().p

        try
        {
            connection = datasource.getConnection();
            statement  = connection.prepareStatement("INSERT INTO appointment VALUES(?, NULL, NULL, 0, ?)");

            statement.setDate(1, (Date) date);

        }
        catch (Exception e)
        {

        }
    }

    // Getter for the attribute speciality that a doctor has
    public String getSpeciality()
    {
        return speciality;
    }

    // Getter for the attribute AMKA
    public String getAMKA() { return this.AMKA; }

    /**
     * @return The characteristics of each Doctor (firstname,username,surname, age and his speciality)
     */
    @Override
    public String toString()
    {
        return super.toString() + ", speciality: "+getSpeciality() + ", AMKA: "+ getAMKA();
    }

}

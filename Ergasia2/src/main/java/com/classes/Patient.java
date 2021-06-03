package com.classes;

import java.awt.print.Printable;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the model of a patient.A patient is able to register to the website,search for an available appointment and
 * see his scheduled or past appointments. Except of User's attributes, each patient has also his unique AMKA.
 */
public class Patient extends Users
{
    private final String AMKA; // This is the unique AMKA of each patient

    List<Appointment> past_appointments = new ArrayList<>();
    List<Appointment> scheduled_appointments = new ArrayList<>(); // every patient has a list of some appointment objects

    public Patient(String username, String password, String firstname, String lastname, int age, String AMKA)
    {
        super(username, password, firstname, lastname, age);
        this.AMKA = AMKA;
    }

    /**
     * Registers a Patient. All required fields are registered, and all are provided by the user. In some circumstances,
     * the user has to provide numbers. Invalid inputs will be asked to be provided again.
     * @return A new Patient object, with all fields filled.
     */
    public static Patient Register()
    {
        return null;
    }

    /**
     * Search available doctor appointments by doctor attribute
     *
     * @param searchby The attribute of a doctor we want to search an appointment for.
     * If 'searchby' value is not set, the method searches all available appointments
     *
     * @param value The actual value of 'searchby' attribute we are looking for
     *
     */
    public void searchAvailableAppointments(String searchby, String value)
    {
        if (!isLoggedOn())
        {
            System.out.println("You must be logged on to search for available appointments.");
            return;
        }

        if(searchby.equals(""))
            System.out.println("Searching every speciality/doctor appointment...");
        else
            System.out.println("Searching appointment by "+ searchby +", where "+searchby+" is "+value);
    }

    /**
     * Show past doctor appointments by doctor attribute
     *
     * @param showby The attribute of a doctor we want to show past appointments for.
     * If 'showby' value is not set, the method shows all past appointments.
     *
     * @param value The actual value of 'showby' attribute we are looking for
     *
     */
    public void showAppointmentHistory(String showby, String value)
    {
        if (!isLoggedOn())
        {
            System.out.println("You must be logged on to show appointment history.");
            return;
        }
        /////

    }

    /**
     * Show scheduled doctor appointments by doctor attribute
     *
     * @param showby The attribute of a doctor we want to show scheduled appointments for.
     * If 'showby' value is not set, the method shows all scheduled appointments.
     *
     * @param value The actual value of 'showby' attribute we are looking for
     *
     */
    public void showScheduledAppointments(String showby, String value)
    {
        if (!isLoggedOn())
        {
            System.out.println("You must be logged on to show all scheduled appointments.");
            return;
        }

        if(showby.equals(""))
            System.out.println("Show all scheduled appointments");
        else
            System.out.println("Show scheduled appointments by "+showby+", where "+showby+" is "+value);
    }

    /**
     * @return The characteristics of each Patient (firstname,username,surname, age and his AMKA)
     */
    @Override
    public String toString(){
        return super.toString() + ", AMKA: "+AMKA;
    }

    // Getter for the attribute AMKA
    public String GetAMKA() { return this.AMKA; }

    /**
     * This function shows if a patient is available for an appointment or not
     * @return True if patient is available. False if patient is not available
     */
    public boolean isAvailable(){

        return true;

    }
}

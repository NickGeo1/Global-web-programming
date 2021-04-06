package com.ergasia1;

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

    public void registration()
    {
        System.out.println(getUsername() + " has been successfully registered!");
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
    public void searchAvailableappointments(String searchby, String value)
    {
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
    public void showAppointmenthistory(String showby, String value)
    {
        if(showby.equals(""))
            System.out.println("Show all appointment history");
        else
            System.out.println("Show appointment history by "+showby+", where "+showby+" is "+value);
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
    public void showScheduledappointments(String showby, String value)
    {
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

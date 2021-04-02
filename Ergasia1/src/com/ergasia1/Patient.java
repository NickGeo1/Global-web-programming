package com.ergasia1;

import java.util.ArrayList;
import java.util.List;

public class Patient extends Users
{
    private final String AMKA;
    List<Appointment> scheduled_appointments = new ArrayList<>(); //every patient has a list of some appointment objects
    List<Appointment> past_appointments = new ArrayList<>();

    public Patient(String username, String password, String firstname, String lastname, int age, String AMKA)
    {
        super(username, password, firstname, lastname, age);
        this.AMKA = AMKA;
    }

    public void registration()
    {
        System.out.println(getUsername() + " has been successfully registered!");
    }

    //we can search an appointment by doctor name or speciality

    public void searchAvailableappointment(String searchby, String value)
    {
        if(searchby.equals(""))
            System.out.println("Searching every speciality/doctor appointment...");
        else
            System.out.println("Searching appointment by "+ searchby +", where "+searchby+" is "+value);
    }

    public void showAppointmenthistory(String showby, String value)
    {
        if(showby.equals(""))
            System.out.println("Show all appointment history");
        else
            System.out.println("Show appointment history by "+showby+", where "+showby+" is "+value);
    }

    public void showScheduledappointments(String showby, String value)
    {
        if(showby.equals(""))
            System.out.println("Show all scheduled appointments");
        else
            System.out.println("Show scheduled appointments by "+showby+", where "+showby+" is "+value);
    }

    public String GetAMKA() { return this.AMKA; }
}

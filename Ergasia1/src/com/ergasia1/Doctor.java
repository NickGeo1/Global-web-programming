package com.ergasia1;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the model (class) of a Doctor. Each doctor has his speciality and
 * some other abilities, which are to be able to see his appointments, to create a new appointment,
 * as well as say if he is available (or not) for a new appointment.
 */
public class Doctor extends Users
{
    String speciality; // Speciality that a doctor has
    List<Appointment> appointments = new ArrayList<>(); //The list of doctor's appointments

    public Doctor(String username, String password, String firstname, String surname, int age,String speciality) {

        super(username, password, firstname, surname, age); // Constructor of class Doctor calls superclasse's constructor
        this.speciality = speciality;

    }

    /**
     * This function shows if a doctor is available for an appointment or not
     * @return True if doctor is available. False if doctor is not available
     */
    public boolean isAvailable(){

        return true;

    }

    /**
     * This function shows to the screen all the appointments that a doctor has.
     */
    public void show_appointments(){
        System.out.println("Doctor's appointments are being shown here");
    }

    /**
     *  Create a new apppointment between a patient and a doctor
     */
    public void create_new_appointment(){
        System.out.println("Created a new appointment");
    }

    public String getSpeciality()
    {
        return speciality;
    }

    @Override
    public String toString(){
        return super.toString() + ", speciality: "+speciality;
    }

}

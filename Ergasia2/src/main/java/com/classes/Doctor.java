package com.classes;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the model (class) of a Doctor. Each doctor has his speciality and
 * some other abilities, which are to be able to see his appointments, to create a new appointment,
 * as well as say if he is available (or not) for a new appointment.
 */
public class Doctor extends Users
{
    List<Appointment> appointments = new ArrayList<>(); //The list of doctor's appointments

    private String speciality; // Speciality that a doctor has

    // Constructor method
    public Doctor(String username, String password, String firstname, String surname, int age,String speciality) {

        super(username, password, firstname, surname, age); // Constructor of class Doctor calls superclasse's constructor
        this.speciality = speciality;

    }

    /**
     * This function shows if a doctor is available for an appointment or not
     * @return True if doctor is available. False if doctor is not available
     */
    public boolean isAvailable(){

        if (!isLoggedOn())
        {
            System.out.println("You must be logged on to search for available appointments.");
            return false;
        }

        System.out.println("Doctor is available!");
        return true;
    }

    /**
     * This function shows to the screen all the appointments that a doctor has.
     * @param option takes only values "week" and "day". By this way, the appointments
     * that a doctor has daily or weekly will be shown.
     */
    public void show_appointments(String option){

        if (!isLoggedOn())
        {
            System.out.println("You must be logged on to search for available appointments.");
            return;
        }

        switch(option) {
            case "week":
                System.out.println("Week's appointments are:");
                break;
            case "day":
                System.out.println("Today's appointments are:");
                break;
            default:
                System.out.println("No appointments found based on your search.");
        }

    }

    /**
     * This function creates a new appointment only if the patient and the
     * are available.
     * @param d is an instance of class Doctor
     * @param p is an instance of class Patient
     */
    void createAppointment (Doctor d,Patient p){

        if (!isLoggedOn())
        {
            System.out.println("You must be logged on to search for available appointments.");
            return;
        }

        if (d.isAvailable() && p.isAvailable()){
            System.out.println("Appointment created!");
        }
        else
        {
            System.out.println("Appointment was not created!");
        }

    }

    /**
     * This function is user for canceling an appointment. Appointmenrs will be canceled
     * only if they have been scheduled three days after current date.
     */
    void cancelAppointment(){

        if (!isLoggedOn())
        {
            System.out.println("You must be logged on to cancel an appointment.");
            return;
        }

        System.out.println("Appointment deleted!");

    }

    // Getter for the attribute speciality that a doctor has
    String getSpeciality()
    {
        return speciality;
    }

    /**
     * @return The characteristics of each Doctor (firstname,username,surname, age and his speciality)
     */
    @Override
    public String toString(){
        return super.toString() + ", speciality: "+getSpeciality();
    }

}

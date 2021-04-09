package com.ergasia1;
import java.util.Date;

/**
 * This class is used for managing appointments (insert,delete and modify an appointment)
 * The appointments will be saved to the database
 */
public class Appointment
{

    Date date; // The date of the appointment
    Boolean done = false;

    // Constructor method
    Appointment (Date d,Boolean done){

        this.done = true;
        this.date = d;
    }

    /**
     * This function is used for inserting a new
     * appointment to the database
     */
    void insertNewAppointment(){

        if (done)
            System.out.println("Appointment was added to the database!");

    }

    /**
     * This function is used for deleting an existing
     * appointment from the database
     */
    void deleteAppointment(){

        if (done)
            System.out.println("Appointment was deleted from the database!");

    }

    /**
     * This function is used for modifying an existing
     * appointment from the database
     */
    void modifyAppointment(){

        if (done)
            System.out.println("Appointment was successfully modified!");

    }

    /**
     * This function is used for desplaying the
     *  date and status of an appointment.
     */
    @Override
    public String toString(){
        return  "Date: "+date +", is done: "+done;
    }

}

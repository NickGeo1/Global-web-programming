package com.ergasia1;

/**
 * This class is used for managing appointments (insert,delete and modify an appointment)
 * The appointments will be saved to the database
 */
public class Appointment
{

    /**
     * This function is used for inserting a new
     * appointment to the database
     */
    void insertNewAppointment(){

        System.out.println("Appointment was added to the database!");

    }

    /**
     * This function is used for deleting an existing
     * appointment from the database
     */
    void deleteAppointment(){

        System.out.println("Appointment was deleted from the database!");

    }

    /**
     * This function is used for modifying an existing
     * appointment from the database
     */
    void modifyAppointment(){

        System.out.println("Appointment was successfully modified from the database!");

    }

}

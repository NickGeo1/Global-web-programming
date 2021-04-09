package com.ergasia1;
import java.io.*;

public class CreateUsers
{

    public static void main(String[] args) throws IOException
    {
        System.out.println("Program starts\n");

        // Create objects and show attributes/methods

        //Admin
        UserInteraction.showAttributes("Admin");

        //Patient
        UserInteraction.showAttributes("Patient");

        //Doctor
        UserInteraction.showAttributes("Doctor");

        //Appointment
        UserInteraction.showAttributes("Appointment");

        //Users login
        UserInteraction.showAttributes("User");

        //Give Patient,Doctor attributes and store Patient's to output file
        UserInteraction.giveAttributes("Patient");
        UserInteraction.giveAttributes("Doctor");

        //txt file Input
        UserInteraction.readObjectfromFile();

        System.out.println("\nTotal users created: "+Users.getUsersCount());

    }
}

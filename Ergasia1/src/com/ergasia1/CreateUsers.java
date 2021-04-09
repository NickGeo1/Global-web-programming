package com.ergasia1;
import java.io.*;
import java.util.Scanner;
import java.util.regex.Matcher;

public class CreateUsers
{

    public static void main(String[] args) throws IOException
    {
        System.out.println("Program starts\n");

        // Create objects and show attributes/methods

        //Patient
        UserInteraction.showAttributes("Patient");

        //Doctor
        UserInteraction.showAttributes("Doctor");

        //Admin
        UserInteraction.showAttributes("Admin");

        //Appointment
        UserInteraction.showAttributes("Appointment");

        //Users login
        UserInteraction.showAttributes("User");

        //Give Patient,Doctor attributes and store Patient's to output file
        UserInteraction.giveAttributes("Patient");
        UserInteraction.giveAttributes("Doctor");

        //txt file Input
        System.out.println("\n");
        UserInteraction.readObjectfromFile();

        System.out.println("\nTotal users created: "+Users.getUsersCount());

    }
}

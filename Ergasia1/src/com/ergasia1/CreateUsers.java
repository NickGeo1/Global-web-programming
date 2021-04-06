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
        UserInteraction.showAttributes("Appointmrnt");

        //Users login
        UserInteraction.showAttributes("User login");

        //Give Patient,Doctor attributes and store them to output file
        UserInteraction.giveAttributes("Patient");
        UserInteraction.giveAttributes("Doctor");

        //txt file Input
        UserInteraction.readObjectfromFile();

    }
}

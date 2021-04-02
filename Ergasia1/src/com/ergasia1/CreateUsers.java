package com.ergasia1;
import java.util.Scanner;

public class CreateUsers
{
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args)
    {
        System.out.println("Program starts\n");

        // Create objects

        //Patient
        System.out.println("Creating a patient object...\nAttributes are:");
        Patient p1 = new Patient("GeorgeMC2610", "blabla", "Georgios","Seimenis",19,"986yug97");
        System.out.println(p1.toString());
        System.out.println("registration method:");
        p1.registration();
        System.out.println("searchAvailableappointment method:");
        p1.searchAvailableappointment("name","Nikolaos");
        System.out.println("showAppointmenthistory method:");
        p1.showAppointmenthistory("speciality","pulmonologist");
        System.out.println("showScheduledappointments method:");
        p1.showScheduledappointments("","");

        //Doctor
        System.out.println("\nCreating a Doctor object...\nAttributes are:");
        Doctor d1 = new Doctor("NickGeo01","iamadocotr","Nikolaos","Georgiadis",19,"Cardiologist");
        System.out.println(d1.toString());
        System.out.println("isAvailable method returns true if doctor is available.Returns false otherwise");
        System.out.println("isAvailable returns: "+d1.isAvailable());
        System.out.println("show_appointments method:");
        d1.show_appointments();
        System.out.println("create_new_appointment method:");
        d1.create_new_appointment();








    }
}

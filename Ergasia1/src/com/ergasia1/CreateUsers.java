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
        p1.searchAvailableappointments("name","Nikolaos");
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

        //Admin
        System.out.println("\nCreating an Admin object...\nAttributes are:");
        Admin a1 = new Admin("Stratosk123", "stratos444", "Efstratios","Karkanis",19);
        System.out.println(a1.toString());
        System.out.println("delete_doctor method:");
        a1.delete_doctor();
        System.out.println("add_doctor method:");
        a1.add_doctor();
        System.out.println("delete_doctor method:");
        a1.delete_doctor();

        //Appointment
        System.out.println("\nCreating an Appointment object...\nAttributes are:");
        Appointment ap1 = new Appointment();
        //...

        //Users login
        System.out.println("\n" + p1.getUsername()+" is trying to login...");
        p1.Login("123");
        System.out.println(a1.getUsername()+" is trying to login...");
        a1.Login("stratos444");
        System.out.println(a1.getUsername()+" is trying to logout...");
        a1.Logout();

        //Give Patient,Doctor attributes
        giveAttributes("Patient");
        giveAttributes("Doctor");



    }

    /**
     * Tells the user to give the corresponding constructor arguments splitted by ','(for Patient or Doctor), depending on 'user' value.
     * Probable exceptions are being handled.
     * @param user Represents which Class's(Patient or Doctor) attributes the user should give.For example, if users=Patient, the user must
     * give Patient attributes.
     */
    private static void giveAttributes(String user)
    {
        String[] atr = {};

        while(true) //Ask attributes from user, until he give them right
        {
            try
            {
                System.out.println("\nGive "+user+" attributes splitted by ',' in the following order : username, password, firstname, lastname, age, " +
                        ""+(user.equals("Patient")?"AMKA:":"speciality:"));
                String values = input.nextLine();
                atr = values.split(",");

                //if user gave more than 6 attributes, we tell him to give attributes again

                if(atr.length>6){
                    System.out.println("Fewer attributes expected");
                    continue;
                }

                if(user.equals("Patient"))
                {
                    Patient p2 = new Patient(atr[0], atr[1], atr[2], atr[3], Integer.parseInt(atr[4]), atr[5]);
                }
                else
                {
                    Doctor d2 = new Doctor(atr[0], atr[1], atr[2], atr[3], Integer.parseInt(atr[4]), atr[5]);
                }

                break; //At this point user gave correct attributes so we exit
            }
            catch(NumberFormatException e1) //Exception in case user gave wrong age format
            {
                System.out.println("Wrong age format!");
                if(atr.length==5)
                    System.out.println("More attributes expected!"); //In case user gave 5 attributes
            }
            catch (ArrayIndexOutOfBoundsException e2) //Exception in case user gave fewer than 6 attributes
            {
                System.out.println("More attributes expected!");
            }
            catch (Exception e) //Any other Exception case
            {
                System.out.println("An exception has occured!");
            }
        }
    }
}

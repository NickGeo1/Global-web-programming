package com.ergasia1;

import java.io.*;
import java.util.Scanner;
import java.util.Date;

public class UserInteraction {

    static Patient p2 = null;
    static Scanner input = new Scanner(System.in);

    /**
     * Create an object of the given type and show its attributes
     * @param type The type of the object we want to show attributes of.
     * If no specific type of object is given, the default action is to create
     * an Admin and Patient object and make them to Login/Logout(Users method)
     */
    static void showAttributes(String type)
    {
        Patient p1 = new Patient("GeorgeMC2610", "blabla", "Georgios", "Seimenis", 19, "9867");
        Doctor d1 = new Doctor("NickGeo01", "iamadocotr", "Nikolaos", "Georgiadis", 19, "Cardiologist");
        Admin a1 = new Admin("Stratosk123", "stratos444", "Efstratios", "Karkanis", 19);
        Appointment ap1 = new Appointment(new Date());

        switch (type)
        {
            case "Patient" :
                System.out.println("Creating a Patient object...\nAttributes are:");
                System.out.println(p1.toString());
                System.out.println("Register method:");
                p2 = Patient.Register();
                System.out.println("searchAvailableappointment method:");
                p1.searchAvailableAppointments("name", "Nikolaos");
                System.out.println("showAppointmenthistory method:");
                p1.showAppointmentHistory("speciality", "pulmonologist");
                System.out.println("showScheduledappointments method:");
                p1.showScheduledAppointments("", "");
                break;

            case "Doctor" :
                System.out.println("\nCreating a Doctor object...\nAttributes are:");
                System.out.println(d1.toString());
                System.out.println("isAvailable method returns true if doctor is available.Returns false otherwise");
                System.out.println("isAvailable returns: " + d1.isAvailable());
                System.out.println("show_appointments method:");
                d1.show_appointments();
                System.out.println("createAppointment method:");
                d1.createAppointment(d1,p1);
                break;

            case "Admin" :
                System.out.println("\nCreating an Admin object...\nAttributes are:");
                System.out.println(a1.toString());
                System.out.println("delete_doctor method:");
                a1.delete_doctor(d1);
                System.out.println("add_doctor method:");
                a1.add_doctor("GeorgeS12", "12345", "George", "Seimenis", 56, "Dentist");
                System.out.println("delete_doctor method:");
                a1.delete_doctor(d1);
                break;

            case "Appointment" :
                System.out.println("\nCreating an Appointment object...\nAttributes are:");
                System.out.println(ap1.toString());
                System.out.println("insertNewAppointment method:");
                ap1.insertNewAppointment();
                System.out.println("deleteAppointment method:");
                ap1.deleteAppointment();
                System.out.println("modifyAppointment method:");
                ap1.modifyAppointment();
                break;

            default :
                System.out.println("\n(Login method)Give password for user "+ p2.getUsername());
                String pass1 = input.nextLine();
                p2.Login(pass1);
                System.out.println("(Logout method)" +p2.getUsername() + " is trying to logout...");
                p2.Logout();
        }
    }

    /**
     * Tells the user to give the corresponding constructor arguments splitted by ','(for Patient or Doctor), depending on 'user' value.
     * Probable exceptions are being handled.If a Patient is being initialized, its attributes are being written in an output file
     * @param user Represents which Class's(Patient or Doctor) attributes the user should give.For example, if users=Patient, the user must
     * give Patient attributes.
     */
    static void giveAttributes(String user) throws IOException
    {
        String[] atr = {};
        Patient p2 = null;

        while(true) //Ask attributes from user, until he give them right
        {
            try
            {
                System.out.println("\nGive "+user+" attributes splitted by ',' in the following order:\nusername,password,firstname,lastname,age," +
                        ""+(user.equals("Patient")?"AMKA":"speciality"));
                String values = input.nextLine();
                atr = values.split(",");

                //If user gave more than 6 attributes or gave wrong format of any, we tell him to give attributes again

                //Firstname,surname and speciality have to be only letters(Starting with capital,except of speciality)
                //age,AMKA must be integers
                //Username cannot contain only space characters

                if(atr.length>6)
                {
                    System.out.println("Fewer attributes expected");
                    continue;

                }else if(atr[0].isBlank())
                {
                    System.out.println("Username cannot be null, or contain only space characters.");
                    continue;
                }
                else if(atr[1].isEmpty())
                {
                    System.out.println("Password cannot be null.");
                    continue;
                }
                else if(!atr[2].matches("[A-Z][a-z]*") || !atr[3].matches("[A-Z][a-z]*"))
                {
                    System.out.println("Firstname/surname have to contain only letters and they must start with a capital letter.");
                    continue;
                }

                if(user.equals("Patient"))
                {
                    Integer.parseInt(atr[5]);
                    p2 = new Patient(atr[0], atr[1], atr[2], atr[3], Integer.parseInt(atr[4]), atr[5]);
                }
                else if(!atr[5].matches("[A-Za-z]+")) //in this case user="Doctor"
                {
                    System.out.println("Wrong speciality format.");
                    continue;
                }
                else {
                    Doctor d2 = new Doctor(atr[0], atr[1], atr[2], atr[3], Integer.parseInt(atr[4]), atr[5]);
                }

                break; //At this point user gave correct attributes so we exit
            }
            catch(NumberFormatException e1) //Exception in case user gave wrong age format
            {
                System.out.println("Wrong age/AMKA format!");
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

        //txt file Output

        if(user.equals("Patient"))
        {
            BufferedWriter outputStream = null;

            try
            {
                outputStream = new BufferedWriter ( new FileWriter("C:\\Users\\nikos\\Documents\\GitHub\\Global-web-programming\\Ergasia1\\src\\com\\ergasia1\\Patient_attr",true) );
                outputStream.write("\n"+p2.getUsername() +" "+ p2.getPassword() +" "+ p2.getFirstname() +" "+ p2.getSurname() +" "+ p2.getAge() +" "+ p2.GetAMKA());
                System.out.println("Successfully stored above patient in output file!");
            }
            catch (IOException e)
            {
                System.out.println("An I/O exception has occured!");
            }
            catch (Exception e)
            {
                System.out.println("An Exception has occured!");
            }
            finally
            {
                if (outputStream != null)
                {
                    outputStream.close();
                }
            }
        }
    }

    /**
     * Reads a Patient's attributes from the output file, and makes a Patient object with them.
     * Checks for each attribute if format is correct
     * @throws IOException
     */
    static void readObjectfromFile() throws IOException
    {
        BufferedReader inputStream = null;

        try
        {
            inputStream = new BufferedReader ( new FileReader("C:\\Users\\nikos\\Documents\\GitHub\\Global-web-programming\\Ergasia1\\src\\com\\ergasia1\\Patient_attr") );
            String[] attr = inputStream.readLine().split(" ");

            if(!attr[2].matches("[A-Z][a-z]*") || !attr[3].matches("[A-Z][a-z]*"))
            {
                System.out.println("Wrong firstname/surname format in output file.Attributes failed to be read");
                inputStream.close();
                return;
            }

            Patient p3 = new Patient(attr[0], attr[1], attr[2], attr[3], Integer.parseInt(attr[4]), attr[5]);
            System.out.println("\nPatient attributes red from file:");
            System.out.println("Username: "+attr[0]);
            System.out.println("Password: "+attr[1]);
            System.out.println("Firstname: "+attr[2]);
            System.out.println("Surname: "+attr[3]);
            System.out.println("Age: "+attr[4]);
            System.out.println("AMKA: "+attr[5]);
        }
        catch(NumberFormatException e) //Exception in case output file contains wrong age format
        {
            System.out.println("Wrong age format!");
        }
        catch (IOException e)
        {
            System.out.println("An I/O exception has occured!");
        }
        catch (Exception e)
        {
            System.out.println("An Exception has occured!");
        }
        finally
        {
            if (inputStream != null)
            {
                inputStream.close();
            }
        }
    }

}

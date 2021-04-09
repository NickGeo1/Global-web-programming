package com.ergasia1;

import java.io.*;
import java.util.Scanner;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class UserInteraction {

    static Patient p1;
    static Patient p2;
    static Doctor d1;
    static Admin a1;
    static Appointment ap1;
    static Scanner input = new Scanner(System.in);

    /**
     * Create an object of the given type and show its attributes
     * @param type The type of the object we want to show attributes of.
     * If no specific type of object is given, the default action is to create
     * an Admin and Patient object and make them to Login/Logout(Users method)
     */
    static void showAttributes(String type) {

        try
        {
            switch (type)
            {
                case "Patient" :
                    System.out.println("Creating a Patient object...");
                    p1 = new Patient("GeorgeMC2610", "blabla", "Georgios", "Seimenis", 19, "9867");
                    TimeUnit.SECONDS.sleep(2);
                    System.out.println("Attributes are:");
                    System.out.println(p1.toString());
                    TimeUnit.SECONDS.sleep(2);

                    p1.Login("blabla");
                    TimeUnit.SECONDS.sleep(2);

                    System.out.println("Register method:");
                    p2 = Patient.Register();
                    TimeUnit.SECONDS.sleep(2);
                    System.out.println("searchAvailableΑppointment method:");
                    p1.searchAvailableAppointments("name", "Nikolaos");
                    TimeUnit.SECONDS.sleep(2);
                    System.out.println("showAppointmentΗistory method:");
                    p1.showAppointmentHistory("speciality", "pulmonologist");
                    TimeUnit.SECONDS.sleep(2);
                    System.out.println("showScheduledΑppointments method:");
                    p1.showScheduledAppointments("", "");
                    TimeUnit.SECONDS.sleep(2);
                    System.out.println("isAvailable method:");
                    System.out.println("Returns: "+p1.isAvailable());
                    TimeUnit.SECONDS.sleep(2);
                    p1.Logout();
                    TimeUnit.SECONDS.sleep(2);
                    break;

                case "Doctor" :
                    System.out.println("\nCreating a Doctor object...");
                    d1 = new Doctor("NickGeo01", "iamadocotr", "Nikolaos", "Georgiadis", 19, "Cardiologist");
                    TimeUnit.SECONDS.sleep(2);
                    System.out.println("Attributes are:");
                    System.out.println(d1.toString());
                    TimeUnit.SECONDS.sleep(2);

                    d1.Login("iamadocotr");
                    TimeUnit.SECONDS.sleep(2);

                    System.out.println("isAvailable method:");
                    boolean b = d1.isAvailable();
                    System.out.println("isAvailable returns: " + b);
                    TimeUnit.SECONDS.sleep(2);
                    System.out.println("show_appointments method:");
                    d1.show_appointments();
                    TimeUnit.SECONDS.sleep(2);
                    System.out.println("createAppointment method:");
                    d1.createAppointment(d1,p1);
                    TimeUnit.SECONDS.sleep(2);
                    d1.Logout();
                    TimeUnit.SECONDS.sleep(2);
                    break;

                case "Admin" :
                    System.out.println("\nCreating an Admin object...");
                    a1 = new Admin("Stratosk123", "stratos444", "Efstratios", "Karkanis", 19);
                    TimeUnit.SECONDS.sleep(2);
                    System.out.println("Attributes are:");
                    System.out.println(a1.toString());
                    TimeUnit.SECONDS.sleep(2);

                    a1.Login("stratos444");
                    TimeUnit.SECONDS.sleep(2);

                    System.out.println("add_doctor method:");
                    Doctor d2 = a1.add_doctor("GeorgeS12", "12345", "George", "Seimenis", 56, "Dentist");
                    TimeUnit.SECONDS.sleep(2);
                    System.out.println("change_doctor method:");
                    a1.change_doctor(a1.getUsername());
                    TimeUnit.SECONDS.sleep(2);
                    System.out.println("delete_doctor method:");
                    a1.delete_doctor(d2);
                    TimeUnit.SECONDS.sleep(2);
                    a1.Logout();
                    TimeUnit.SECONDS.sleep(2);
                    break;

                case "Appointment" :
                    System.out.println("\nCreating an Appointment object...");
                    ap1 = new Appointment(new Date(),true);
                    TimeUnit.SECONDS.sleep(2);
                    System.out.println("Attributes are:");
                    System.out.println(ap1.toString());
                    TimeUnit.SECONDS.sleep(2);
                    System.out.println("insertNewAppointment method:");
                    ap1.insertNewAppointment();
                    TimeUnit.SECONDS.sleep(2);
                    System.out.println("deleteAppointment method:");
                    ap1.deleteAppointment();
                    TimeUnit.SECONDS.sleep(2);
                    System.out.println("modifyAppointment method:");
                    ap1.modifyAppointment();
                    TimeUnit.SECONDS.sleep(2);
                    break;

                default :
                    System.out.println("\n(Login method)Give password for user "+ p2.getUsername()+":");
                    String pass1 = input.nextLine();
                    if(p2.Login(pass1))
                    {
                        TimeUnit.SECONDS.sleep(2);
                        System.out.println("(Logout method)" +p2.getUsername() + " is trying to logout...");
                        p2.Logout();
                    }
                    TimeUnit.SECONDS.sleep(2);
                    System.out.println("isLoggedOn method:");
                    System.out.println("For user "+p2.getUsername()+" isLoggedOn returns: "+p2.isLoggedOn());
                    TimeUnit.SECONDS.sleep(2);
            }
        }catch(InterruptedException e){}
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
                else if(atr[1].isEmpty() || atr[1].length() < 4)
                {
                    System.out.println("Password cannot be null or less than 4 characters.");
                    continue;
                }
                else if(!atr[2].matches("[A-Z][A-Za-z]*") || !atr[3].matches("[A-Z][A-Za-z]*"))
                {
                    System.out.println("Firstname/surname have to contain only letters and they must start with a capital letter.");
                    continue;
                }

                if(user.equals("Patient"))
                {
                    Integer.parseInt(atr[5]);
                    p2 = new Patient(atr[0], atr[1], atr[2], atr[3], Integer.parseInt(atr[4]), atr[5]);
                    System.out.println("Patient object created!");
                }
                else if(!atr[5].matches("[A-Za-z]+")) //in this case user="Doctor"
                {
                    System.out.println("Wrong speciality format.");
                    continue;
                }
                else
                {
                    Doctor d2 = new Doctor(atr[0], atr[1], atr[2], atr[3], Integer.parseInt(atr[4]), atr[5]);
                    System.out.println("Doctor object created!");
                }
                TimeUnit.SECONDS.sleep(2);

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
                outputStream.write("\n"+p2.getUsername() +","+ p2.getPassword() +","+ p2.getFirstname() +","+ p2.getSurname() +","+ p2.getAge() +","+ p2.GetAMKA());
                System.out.println("Successfully stored above patient in output file!");
                TimeUnit.SECONDS.sleep(2);
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
            String[] attr = inputStream.readLine().split(",");

            if(attr.length>6)
            {
                System.out.println("Fewer attributes expected.Attributes failed to be read");
                inputStream.close();
                return;
            }
            else if(!attr[2].matches("[A-Z][a-z]*") || !attr[3].matches("[A-Z][a-z]*"))
            {
                System.out.println("Wrong firstname/surname format in output file.Attributes failed to be read");
                inputStream.close();
                return;
            }

            Integer.parseInt(attr[5]);

            Patient p3 = new Patient(attr[0], attr[1], attr[2], attr[3], Integer.parseInt(attr[4]), attr[5]);
            System.out.println("\nPatient object created!");
            System.out.println("Patient attributes red from file:");
            System.out.println("Username: "+attr[0]);
            System.out.println("Password: "+attr[1]);
            System.out.println("Firstname: "+attr[2]);
            System.out.println("Surname: "+attr[3]);
            System.out.println("Age: "+attr[4]);
            System.out.println("AMKA: "+attr[5]);
            TimeUnit.SECONDS.sleep(2);
        }
        catch(NumberFormatException e) //Exception in case output file contains wrong age format
        {
            System.out.println("Wrong age/AMKA format on file.Attributes failed to be read");
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            System.out.println("More attributes expected.Attributes failed to be read");
        }
        catch(IOException e)
        {
            System.out.println("An I/O exception has occured!");
        }
        catch(Exception e)
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

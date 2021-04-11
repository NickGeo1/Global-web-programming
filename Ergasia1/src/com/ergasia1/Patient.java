package com.ergasia1;

import java.awt.print.Printable;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the model of a patient.A patient is able to register to the website,search for an available appointment and
 * see his scheduled or past appointments. Except of User's attributes, each patient has also his unique AMKA.
 */
public class Patient extends Users
{
    private final String AMKA; // This is the unique AMKA of each patient

    List<Appointment> past_appointments = new ArrayList<>();
    List<Appointment> scheduled_appointments = new ArrayList<>(); // every patient has a list of some appointment objects

    public Patient(String username, String password, String firstname, String lastname, int age, String AMKA)
    {
        super(username, password, firstname, lastname, age);
        this.AMKA = AMKA;
    }

    /**
     * Registers a Patient. All required fields are registered, and all are provided by the user. In some circumstances,
     * the user has to provide numbers. Invalid inputs will be asked to be provided again.
     * @return A new Patient object, with all fields filled.
     */
    public static Patient Register()
    {
        //starting of the registration, we provide an appropriate message.
        System.out.println("Initiated Patient Registration.");

        //we scan linearly the values
        System.out.print("Provide a username --> ");
        String username = UserInteraction.input.nextLine();
        while (username.isBlank())
        {
            System.out.print("A username cannot be blank. Provide a new one --> ");
            username = UserInteraction.input.nextLine();
        }

        System.out.print("Provide a password --> ");
        String password = UserInteraction.input.nextLine();
        while (password.isEmpty() || password.length() < 4)
        {
            System.out.print("A password cannot be empty or less than four characters. Provide a new one --> ");
            password = UserInteraction.input.nextLine();
        }

        System.out.print("Provide a first name --> ");
        String firstname = UserInteraction.input.nextLine();
        while (!firstname.matches("[A-Z][a-z]*"))
        {
            System.out.print("A first name must start with a capital latin character.The rest could be none or more lowercase characters.\nProvide a new one --> ");
            firstname = UserInteraction.input.nextLine();
        }

        System.out.print("Provide a last name --> ");
        String lastname = UserInteraction.input.nextLine();
        while (!lastname.matches("[A-Z][a-z]*"))
        {
            System.out.print("A last name must start with a capital latin character.The rest could be none or more lowercase characters\nProvide a new one --> ");
            lastname = UserInteraction.input.nextLine();
        }

        //the age must be a number, so we make sure the program does so.
        int age = 0;
        do
        {
            //we get a first String of an age.
            System.out.print("Enter your age --> ");
            String firstAge = UserInteraction.input.nextLine();

            //we try to parse it.
            try
            {
                //if the parsing was done successfully, we break out of the loop.
                age = Integer.parseInt(firstAge);
                if (age < 0)
                    continue;

                break;
            }
            catch (Exception e)
            {
                //if it doesn't, we continue and repeat the same process.
                System.out.println("Provide an integer number.");
            }
        } while (true);

        //same thing happens with the AMKA.
        String AMKA;
        do
        {
            //we force the user to provide an integer number.
            System.out.print("Enter your Patient Number (AMKA) --> ");
            String firstAMKA = UserInteraction.input.nextLine();

            //we try to parse the value the user gave to an integer
            try
            {
                //but we don't store it anywhere, because AMKA is a String value. We just have to make sure it's a number by parsing it.
                Long.parseLong(firstAMKA);
                AMKA = firstAMKA;
                break;
            }
            catch (Exception e)
            {
                System.out.println("Provide an integer number.");
            }
        } while (true);

        //and then we create the user.
        return new Patient(username, password, firstname, lastname, age, AMKA);
    }

    /**
     * Search available doctor appointments by doctor attribute
     *
     * @param searchby The attribute of a doctor we want to search an appointment for.
     * If 'searchby' value is not set, the method searches all available appointments
     *
     * @param value The actual value of 'searchby' attribute we are looking for
     *
     */
    public void searchAvailableAppointments(String searchby, String value)
    {
        if (!isLoggedOn())
        {
            System.out.println("You must be logged on to search for available appointments.");
            return;
        }

        if(searchby.equals(""))
            System.out.println("Searching every speciality/doctor appointment...");
        else
            System.out.println("Searching appointment by "+ searchby +", where "+searchby+" is "+value);
    }

    /**
     * Show past doctor appointments by doctor attribute
     *
     * @param showby The attribute of a doctor we want to show past appointments for.
     * If 'showby' value is not set, the method shows all past appointments.
     *
     * @param value The actual value of 'showby' attribute we are looking for
     *
     */
    public void showAppointmentHistory(String showby, String value)
    {
        if (!isLoggedOn())
        {
            System.out.println("You must be logged on to show appointment history.");
            return;
        }

        if(showby.equals(""))
            System.out.println("Show all appointment history");
        else
            System.out.println("Show appointment history by "+showby+", where "+showby+" is "+value);
    }

    /**
     * Show scheduled doctor appointments by doctor attribute
     *
     * @param showby The attribute of a doctor we want to show scheduled appointments for.
     * If 'showby' value is not set, the method shows all scheduled appointments.
     *
     * @param value The actual value of 'showby' attribute we are looking for
     *
     */
    public void showScheduledAppointments(String showby, String value)
    {
        if (!isLoggedOn())
        {
            System.out.println("You must be logged on to show all scheduled appointments.");
            return;
        }

        if(showby.equals(""))
            System.out.println("Show all scheduled appointments");
        else
            System.out.println("Show scheduled appointments by "+showby+", where "+showby+" is "+value);
    }

    /**
     * @return The characteristics of each Patient (firstname,username,surname, age and his AMKA)
     */
    @Override
    public String toString(){
        return super.toString() + ", AMKA: "+AMKA;
    }

    // Getter for the attribute AMKA
    public String GetAMKA() { return this.AMKA; }

    /**
     * This function shows if a patient is available for an appointment or not
     * @return True if patient is available. False if patient is not available
     */
    public boolean isAvailable(){

        return true;

    }
}

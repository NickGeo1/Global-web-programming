package com.ergasia1;

/**
 * This is the model of an Admin. Admins are the only ones who can add,modify and delete doctors from the
 * application
 */
public class Admin extends Users
{
    // Constructor method
    public Admin(String username, String password, String firstname, String surname, int age) {

        super(username, password, firstname, surname, age);

    }

    /**
     * @param doctor is a type of User. So, the function takes an object of type User and destroyes it.
     * This function is used to delete a doctor that already exists.
     */
    public void delete_doctor(Users doctor){

        if (!isLoggedOn())
        {

            System.out.println("You must be logged on to delete a doctor.");
            return;
        }

        System.out.println("Doctor " + doctor.getUsername() + " has been deleted!");
    }

    /**
     * This function is used to add/create a new doctor.
     * @return an object of type Doctor
     */
    public Doctor add_doctor(String username, String password, String firstname, String surname, int age,String speciality){

        if (!isLoggedOn())
        {
            System.out.println("You must be logged on to add a new Doctor.");
            return null;
        }

        System.out.println("New doctor added!");
        return new Doctor (username,password,firstname,surname,age,speciality);
    }

    /**
     * This function is used to modify a doctor that already exists.
     * @param username is the username of the doctor that we want to modify
     */
    public void change_doctor(String username){

        if (!isLoggedOn())
        {
            System.out.println("You must be logged on to modify a doctor.");
            return;
        }

        System.out.println("Doctor's characteristics have been changed!");
    }

    /**
     * This method returns the characteristics of each Admin
     * @return firstname,username,surname and age of Admin object
     */
    @Override
    public String toString(){
        return super.toString();
    }
}

package com.ergasia1;

/**
 * This is the model of an Admin. Admins are the only ones who can add,modify and delete doctors from the
 * application
 */
public class Admin extends Users
{

    public Admin(String username, String password, String firstname, String surname, int age) {

        super(username, password, firstname, surname, age);

    }

    /**
     * @param doctor Doctor is a type of User. So, the function takes an object of type User and destroyes it.
     * This function is used to delete a doctor that already exists.
     */
    public void delete_doctor(Users doctor){
        System.out.println("Doctor " + doctor.getUsername() + "has been deleted!");
    }

    /**
     * This function is used to add/create a new doctor.
     */
    public void add_doctor(String username, String password, String firstname, String surname, int age,String speciality){
        Doctor new_doctor = new Doctor(username,password,firstname,username,age,speciality);

        System.out.println("New doctor added!");
    }

    /**
     * This function is used to modify a doctor that already exists.
     */
    public void change_doctor(String username){
    }

    /**
     * This method returns the characteristics of each Doctor
     * @return firstname,username,surname and age
     */
    @Override
    public String toString(){
        return super.toString();
    }
}

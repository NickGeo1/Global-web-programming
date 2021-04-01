package com.ergasia1;

/**
 * This is the model of an Admin. Admins are the only one who can add,modify and delete doctors from the
 * application
 */
public class Admin extends Users
{

    public Admin(String username, String password, String firstname, String surname, int age) {

        super(username, password, firstname, surname, age);

    }

    /**
     * This function is used to delete a doctor that already exists.
     */
    private void delete_doctor(){}

    /**
     * This function is used to add/create a new doctor.
     */
    private void add_doctor(){}

    /**
     * This function is used to modify a doctor that already exists.
     */
    private void change_doctor(){}
}

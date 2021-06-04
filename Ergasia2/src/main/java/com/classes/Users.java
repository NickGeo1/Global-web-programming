package com.classes;

/**
 * Every class is extending Users class.This class has the attributes and methods that every kind of user must have
 * Any user has a username, password, firstname, surname, age and is able to Login and Logout from the website
 */
public abstract class Users 
{
    // Basic characteristics of each user
    private String username, password, firstname, surname;
    public boolean loggedOn;
    private int age;

    // 'UsersCount' variable counts the number of users
    private static int UsersCount = 0;

    public Users(String username, String password, String firstname, String surname, int age)
    {
        this.username  = username;
        this.password  = password;
        this.firstname = firstname;
        this.surname   = surname;
        this.age       = age;
        this.loggedOn  = false;
        UsersCount++;
    }

    /**
     * Grants access to the specified User, if the credentials match accordingly.
     * @param password The user-specified password.
     * @return True if the user successfully logs in. False if the credentials don't match.
     */
    public boolean Login(String password)
    {
        if (this.password.equals(password))
        {
            System.out.println("Successfully logged in! Welcome back " + this.firstname + "!");
            return loggedOn = true;
        }

        System.out.println("These credentials don't match.");
        return false;
    }

    /**
     * Terminates access from the user logged on.
     */
    public void Logout()
    {
        System.out.println("Successfully logged out user " + this.username + ".");
        loggedOn = false;
    }

    /**
     * This method returns the characteristics of each User
     * @return firstname,username,surname and age
     */
    @Override
    public String toString()
    {
        return this.firstname + " \"" + this.username + "\" " + this.surname + ", " + this.age + " years old";
    }

    //getters
    public String getUsername() {
        return username;
    }

    public String getPassword(){
        return password;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getSurname() {
        return surname;
    }

    public int getAge() {
        return age;
    }

    public boolean isLoggedOn() {
        return this.loggedOn;
    }

    public static int getUsersCount() {
        return UsersCount;
    }

    // setters
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setAge(int age) {
        if (age > 0 && age < 119)
            this.age = age;
    }
}

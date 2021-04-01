package com.ergasia1;

public abstract class Users 
{
    private String username, password, firstname, surname;
    private int age;
    private static int UsersCount;

    public Users(String username, String password, String firstname, String surname, int age)
    {
        this.username  = username;
        this.password  = password;
        this.firstname = firstname;
        this.surname   = surname;
        this.age       = age;
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
            System.out.println("Welcome back, " + this.firstname + "!");
            return true;
        }

        System.out.println("These credentials don't match. Please try again...");
        return false;
    }

    /**
     * Terminates access from the user logged on.
     */
    public void Logout()
    {
        System.out.println("Successfully logged out from user " + this.username + ".");
    }

    //getters
    public String getUsername() {
        return username;
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

    public static int getUsersCount() {
        return UsersCount;
    }

    //setters

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

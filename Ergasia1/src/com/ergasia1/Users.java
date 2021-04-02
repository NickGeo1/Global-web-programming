package com.ergasia1;

public abstract class Users 
{
    // Basic characteristics of each user
    private String username, password, firstname, name;
    private boolean loggedOn;
    private int age;

    // 'UsersCount' variable counts the number of users
    private static int UsersCount = 0;

    public Users(String username, String password, String firstname, String name, int age)
    {
        this.username  = username;
        this.password  = password;
        this.firstname = firstname;
        this.name   = name;
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
            System.out.println("Welcome back " + this.firstname + "!");
            return loggedOn = true;
        }

        System.out.println("These credentials don't match. Please try again...");
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

    @Override
    public String toString()
    {
        return this.firstname + " \"" + this.username + "\" " + this.name + ", " + this.age + " years old.";
    }

    //getters
    public String getUsername() {
        return username;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getSurname() {
        return name;
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
        this.name = surname;
    }

    public void setAge(int age) {
        if (age > 0 && age < 119)
            this.age = age;
    }
}

package com.ergasia1;

public abstract class Users 
{
    private String username, password;
    private static int UsersCount;

    public Users(String username, String password)
    {
        this.username = username;
        this.password = password;
        UsersCount++;
    }

    public void Login(String username, String password)
    {
        if (this.username.equals(username) && this.password.equals(password))
            System.out.println("Successful login!");
        else
            System.out.println("These credentials don't match. Please try again...");
    }

    public void Logout()
    {

    }


    public String GetUsername()                { return this.username; }
    public void   SetUsername(String username) { this.username = username; }

    public void SetPassword(String password)   { this.password = password; }

    public int GetUsersCount()                 { return UsersCount; }
}

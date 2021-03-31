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

    public String GetUsername()                { return this.username; }
    public void   SetUsername(String username) { this.username = username; }

    public void SetPassword(String password)   { this.password = password; }

    public int GetUsersCount()                 { return UsersCount; }
}

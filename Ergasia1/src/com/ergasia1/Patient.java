package com.ergasia1;

public class Patient extends Users
{
    private final String AMKA;

    public Patient(String username, String password, String firstname, String lastname, int age, String AMKA)
    {
        super(username, password, firstname, lastname, age);
        this.AMKA = AMKA;
    }

    public String GetAMKA() { return this.AMKA; }
}

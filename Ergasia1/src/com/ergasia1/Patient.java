package com.ergasia1;

public class Patient extends Users
{
    private final String AMKA;

    public Patient(String username, String password, String AMKA)
    {
        super(username, password);
        this.AMKA = AMKA;
    }

    public String GetAMKA() { return this.AMKA; }
}

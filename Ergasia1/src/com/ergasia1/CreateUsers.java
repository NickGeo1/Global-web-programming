package com.ergasia1;

import java.util.Scanner;

public class CreateUsers
{
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args)
    {
        System.out.println("Starting.");

        Patient p1 = new Patient("GeorgeMC2610", "blabla", "555465652");

        p1.Login("GeorgeMC2610", "blabla");
    }
}

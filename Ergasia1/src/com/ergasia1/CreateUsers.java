package com.ergasia1;
import java.util.Scanner;

public class CreateUsers
{
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args)
    {
        System.out.println("Program starts");

        // Create objects
        System.out.println();
        Patient p1 = new Patient("GeorgeMC2610", "blabla", "Georgios","Seimenis",19,"986yug97");

        System.out.println(p1.toString());
    }
}

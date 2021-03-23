package com.ergasia1;

public class Main
{
    public static void main(String[] args)
    {
        Testing test = new Testing("Giorgos", 19, 8.8d);
        System.out.println(test.getAge());
        System.out.println(test.getName());
        System.out.println(test.getGrade());
    }
}

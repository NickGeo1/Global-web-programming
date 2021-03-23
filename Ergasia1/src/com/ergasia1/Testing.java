package com.ergasia1;

public class Testing
{
    String name;
    int age;
    double grade;

    String s = "όχι, εσύ είσαι μαλάκας";

    public Testing(String name, int age, double grade)
    {
        this.name = name;
        this.age = age;
        this.grade = grade;
    }

    public String getName()
    {
        return name;
    }

    public int getAge()
    {
        return age;
    }

    public double getGrade() {return grade;}
}

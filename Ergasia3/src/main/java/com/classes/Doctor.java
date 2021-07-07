package com.classes;

/**
 * This is the model (class) of a Doctor. Each doctor has his speciality and
 * some other abilities, which are to be able to see his appointments, to create a new appointment,
 * as well as say if he is available (or not) for a new appointment.
 */
public class Doctor extends Users
{
    private String speciality; // Speciality that a doctor has

    private final String AMKA; // This is the unique AMKA of each doctor

    // Constructor method
    public Doctor(String username, String password, String firstname, String surname, int age, String speciality, String AMKA)
    {

        super(username, password, firstname, surname, age); // Constructor of class Doctor calls superclasses' constructor
        this.speciality = speciality;
        this.AMKA = AMKA;

    }


    // Getter for the attribute speciality that a doctor has
    String getSpeciality()
    {
        return speciality;
    }

    // Getter for the attribute AMKA
    public String getAMKA() { return this.AMKA; }

    /**
     * @return The characteristics of each Doctor (firstname,username,surname, age and his speciality)
     */
    @Override
    public String toString()
    {
        return super.toString() + ", speciality: "+getSpeciality() + ", AMKA: "+ getAMKA();
    }

}

package se.fredrik.databashantering.Person;

import java.util.Date;

//! Attribut till Person klassen
public class Person {
    private int personID;
    private String firstName;
    private String lastName;
    private int gender;
    private Date dob;
    private double income;

    //! Konstruktor med alla attribut (för hämtning från databasen)
    //! Med personID
    public Person(int personID, String firstName, int gender, String lastName, Date dob, double income) {
        this.personID = personID;
        this.firstName = firstName;
        this.gender = gender;
        this.lastName = lastName;
        this.dob = dob;
        this.income = income;
    }

    //! Konstruktor utan personID (för nya objekt
    //! Utan personID
    public Person(String firstName, int gender, String lastName, Date dob, double income) {
        this.firstName = firstName;
        this.gender = gender;
        this.lastName = lastName;
        this.dob = dob;
        this.income = income;
    }

    //! Getter & Setter Metoder
    public int getPersonID() {return personID;}
    public void setPersonID(int personID) {this.personID = personID;}
    public String getFirstName() {return firstName;}
    public void setFirstName(String firstName) {this.firstName = firstName;}
    public String getLastName() {return lastName;}
    public void setLastName(String lastName) {this.lastName = lastName;}
    public int getGender() {return gender;}
    public void setGender(int gender) {this.gender = gender;}
    public Date getDob() {return dob;}
    public void setDob(Date dob) {this.dob = dob;}
    public double getIncome() {return income;}
    public void setIncome(double income) {this.income = income;}

    //! Equals metod
    public boolean squals (Object o){
        //! Samma referens - De är lika
        if (this == o) return true;

        //! Olika typer
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        //! Jämför resultat baserat på personID
        return personID == person.personID;
    }
}

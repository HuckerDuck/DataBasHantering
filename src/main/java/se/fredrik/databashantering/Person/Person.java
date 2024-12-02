package se.fredrik.databashantering.Person;

import java.util.Date;

//! Attribut till Person klassen
public class Person {
    private int personID;
    private String firstName;
    private String lastName;
    private String gender;
    private Date dob;
    private double income;

    //! Konstruktor med alla attribut (för hämtning från databasen)
    //! Med personID
    public Person(int personID, String firstName, String gender, String lastName, Date dob, double income) {
        this.personID = personID;
        this.firstName = firstName;
        this.gender = gender;
        this.lastName = lastName;
        this.dob = dob;
        this.income = income;
    }

    //! Konstruktor utan personID (för nya objekt
    //! Utan personID
    public Person(String firstName, String gender, String lastName, Date dob, double income) {
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
    public String getGender() {return gender;}
    public void setGender(String gender) {this.gender = gender;}
    public java.sql.Date getDob() {return (java.sql.Date) dob;}
    public void setDob(Date dob) {this.dob = dob;}
    public double getIncome() {return income;}
    public void setIncome(double income) {this.income = income;}

    //! Equals metod

    //! 1: Om två objekt är lika enligt equals-metoder, måste de ha samma hashCode
    //! 2: Om två objekt har samma hashCode, behöver de inte vara lika enligt equals,
    //!    men sannolikheten ökar att de är lika dock

    @Override
    public boolean equals (Object o){
        //! Samma referens - De är lika
        if (this == o) return true;

        //! Olika typer
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        //! Jämför resultat baserat på personID
        return personID == person.personID;
    }


    //! hashCode-metod

    //! En hashCode är ett heltal som används som en unik representation av ett objekt.
    //! Tänk det lite som de sista siffrorna i ditt personnummer
    //! Tillsammans med ditt fördelsedatum så blir personnumret specifikt till dig och är en
    //! typ av hashCode

    //! Man kan snabbt lagra och hämta objekten i en HashMap eller HashSet
    @Override
    public int hashCode() {
        //! Använder personID som bas för hashkoden

        //! Denna returnerar en specifik och unik hashCode för varje person i vår Databas
        return Integer.hashCode(personID);
    }


}

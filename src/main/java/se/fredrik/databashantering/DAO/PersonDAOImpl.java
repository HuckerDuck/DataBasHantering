package se.fredrik.databashantering.DAO;

import se.fredrik.databashantering.Person.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersonDAOImpl implements PersonDAO {
    //! En private Kontakt för att koppla sig till servern
    private Connection conn;

    //! Konstruktorn tar ett Connection-objekt
    //! Detta används för att skapa själva kopplingen till servern
    public PersonDAOImpl(Connection conn) {
        //! Initierar anslutningen mot Databasen som kommer att användas av denna DAO
        this.conn = conn;
    }

    //! Lägg till en person
    //! Insert använder VALUES för att definiera de nya värderna som ska läggas till

    @Override
    public void insertPerson(Person person) throws SQLException {
        //! SQL-fråga för att lägga till en ny person i tabellen
        String queryInsert = "Insert into Person.person (first_name, last_name, gender, dob, income ) values (?,?,?,?,?)";

        //! Tryblock med preparedStatement
        //! Här skapar vi en koppling och sen skickar vi in våran query
        try (PreparedStatement pstmt = conn.prepareStatement(queryInsert)) {
            //! Här kommer vi att sätta alla stringen som ska skickas in utifrån det som finns i tabellen
            pstmt.setString(1, person.getFirstName()); //? Förnamn
            pstmt.setString(2, person.getLastName());  //? Efternamn
            pstmt.setString(3, person.getGender());    //? Kön

            //! DOB använder sig av java.sql.Date så därför blir denna rad med kod lite annorlunda
            if (person.getDob() != null) {                          //? Födelseår och datum
                //! Sätter datum om det finns
                pstmt.setDate(4, person.getDob());
            } else {
                //! Sätter null om datum saknas
                pstmt.setNull(4, java.sql.Types.DATE);
            }

            pstmt.setDouble(5, person.getIncome());    //? Inkomst
            pstmt.executeUpdate();                                  //?  Exekvera SQL-frågan

        } catch (SQLException e) {
            //! Skriver ut felet och kastar det vidare
            e.printStackTrace();
            throw e;
        }
    }


    //! Uppdatera personen i Databasen
    //! UPDATE definierar vad som ska uppdateras och använder en WHERE-sats för att specificera vilka rader som påverkas

    @Override
    public void updatePerson(Person person) throws SQLException {
        //! MySQL Query
        String queryUpdate = "Update Person.person SET first_name = ?, last_name = ?, gender = ?, dob = ?, income = ? WHERE person_id = ?";

        //! Tryblock med preparedStatement
        try (PreparedStatement pstmt = conn.prepareStatement(queryUpdate)){

            //! Set-metoder

            pstmt.setString(1, person.getFirstName()); //? Förnamn
            pstmt.setString(2, person.getLastName());  //? Efternamn
            pstmt.setString(3, person.getGender());    //? Kön

            if (person.getDob() != null) {                          //? Datum och födelseår
                pstmt.setDate(4, person.getDob());
            }
            else{
                pstmt.setNull(4, java.sql.Types.DATE);
            }

            pstmt.setDouble(5, person.getIncome());    //? Inkomst
            pstmt.setInt(6, person.getPersonID());     //? personID



            //! Glöm ej execute annars kommer inget att hända
            pstmt.executeUpdate();                                  //? Exekvera SQl-Frågan
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deletePerson(Person person) throws SQLException {
        //! Kontrollera först om det finns något i person tabellen

        if (person == null || person.getPersonID() <= 0){
            throw new IllegalArgumentException("Person ID is null or empty");
        }

        //! SQL-fråga för att ta bort något från din databas
        String queryDelete = "Delete from Person.person where person_id = ?";

        //! Skapa en anslutning till din server

        try (PreparedStatement pstmt = conn.prepareStatement(queryDelete)){
            //! Sätt in värdet för personID:t
            pstmt.setInt(1, person.getPersonID());

            //! Utför själva Delete-operationen
            int rowsAffected = pstmt.executeUpdate();

            //! Kontrollera om borttagningen fungerade eller inte
            if (rowsAffected == 0)
            {   //! Skriver ut om ingen person hittas
                System.out.println("Failed to Delete, No person found with this ID " + person.getPersonID());
            }
            else
            {   //! Tar bort utifrån personID
                System.out.println("Person with ID " + person.getPersonID() + " was deleted from the database.");
            }

        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    //! Hämta en specifik person från databasen utifrån personID

    //! getPerson kommer att använda en ResultSet för att hämta personen
    @Override
    public Person getPerson(int id) throws SQLException {
        //! Välj en specifik person från din databas där personID:t är ...
        String querySelect = "SELECT * FROM Person.person WHERE person_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(querySelect)){
            //! Sätt värdet för person_ID:t som SQL frågan kommer att leta efter
            pstmt.setInt(1, id);

            //! En resultset med en try används här
            try (ResultSet rs = pstmt.executeQuery()){
                if (rs.next()) {
                    //! Alla värden från tabellen behövs skrivas in
                    //! Annars blir det fel
                    return new Person(
                            //! En ny Person-objekt skapas med denna data nedan som hämtas via ResultSet
                            rs.getInt("person_id"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("gender"),
                            rs.getDate("dob"),
                            rs.getDouble("income")
                    );
                }
            }
        }
        //! Om ingen person hittas med den specifika personID:t som vi letar efter så kommer
        //! Metoden att returnera null
        return null;
    }

    @Override
    public List<Person> getPersons() throws SQLException {
        //! SQL-query med ett val att ta allt från Person.person tabellen
        String queryAllSelect = "SELECT * from Person.person";

        //! Vi skapar en Arraylist för att hålla allt
        List<Person> persons = new ArrayList<>();

        //! Skapa en koppling som innan
        try (PreparedStatement pstmt = conn.prepareStatement(queryAllSelect)){
            //! Vid hämtning av data så används alltid en ResultSet

            //! Exekvera SELECT-frågan som skickas tillDatabasen
            //! Få resultaten i en ResultSet

            try (ResultSet rs = pstmt.executeQuery()){

                //! While loop för att Iterera över alla rader i ResultSeten
                while (rs.next()) {

                    //! Skapa ett nytt Person-objekt baserat på databasvärdena
                    Person person = new Person(
                            rs.getInt("person_id"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("gender"),
                            rs.getDate("dob"),
                            rs.getDouble("income")
                    );

                    //! Lägg till den skapade person-objektet i listan
                    persons.add(person);
                }
            }
        }

        //! returnera listan
        return persons;
    }

}

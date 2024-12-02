package se.fredrik.databashantering.DAO;
import se.fredrik.databashantering.Person.Person;
import java.sql.SQLException;
import java.util.List;

public interface PersonDAO {

    //! Infoga en person
    void insertPerson(Person person) throws SQLException;

    //! Uppdatera en person
    void updatePerson(Person person) throws SQLException;

    //! Ta bort en person
    void deletePerson(Person person) throws SQLException;

    //! Hämta en person via ID
    Person getPerson(int id) throws SQLException;

    //! Hämta alla personer
    List<Person> getPersons() throws SQLException;

}

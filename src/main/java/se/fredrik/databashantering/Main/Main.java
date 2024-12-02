package se.fredrik.databashantering.Main;
import se.fredrik.databashantering.DAO.PersonDAOImpl;
import se.fredrik.databashantering.Person.Person;
import se.fredrik.databashantering.Tools.JDBCUtil;
import se.fredrik.databashantering.Tools.SimpleText;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        boolean running = true;
        Scanner scanner = new Scanner(System.in);

        try (Connection connection = JDBCUtil.getConnection()){

            //! Skapa en instans av din DAO-klass här
            //! Den använder sig av kopplingen till databasen
            PersonDAOImpl personDAO = new PersonDAOImpl(connection);

            while (running) {
                SimpleText.introText();
                String choice = scanner.nextLine();
                switch (choice) {
                    case "1" ->{ //! Hämta alla personer i Person.person tabellen
                        try {
                            List<Person> persons = personDAO.getPersons();

                            for (Person person : persons) {
                                System.out.println("ID: " + person.getPersonID());
                                System.out.println("Förnamn: " + person.getFirstName());
                                System.out.println("Efternamn: " + person.getLastName());
                                System.out.println("Kön " + person.getGender());
                                System.out.println("Födelsedatatum" + person.getDob());
                                System.out.println("Inkomst " + person.getIncome());
                                System.out.println("____________________________________");
                            }

                            if (persons.isEmpty()){
                                System.out.println("Hittade inga personer i denna databas");
                            }

                        }

                        catch (SQLException e)
                        {
                            System.err.println("Fel vid hämtning av personer : " + e.getMessage());
                        }
                    }
                    case "2" ->{}
                    case "3" ->{}
                    case "4" ->{}
                    case "5" ->{}
                    case "6" -> running = false;
                    default -> SimpleText.wrongChoiceNumber();
                };
            }
        }

        catch (SQLException e)
        {
            System.err.println("Fel vid anslutning till databasen: " + e.getMessage());
        }

    }
}




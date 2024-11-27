package se.fredrik.databashantering.Main;
import java.sql.*;

public class Main {
    // JDBC URL, användarnamn och lösenord
    private static final String DATABASE_URL = "jdbc:mysql://mysql-database.cbco6m888qzn.eu-central-1.rds.amazonaws.com:3306/";
    private static final String DATABASE_USER = "admin";
    private static final String DATABASE_PASSWORD = "abc12345";

    public static void main(String[] args) {
        Connection connection = null;
        Statement statement;

        try {
            // Etablera anslutning till databasen
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
            System.out.println("Anslutningen till databasen lyckades!");

            //! Skapa ett statement
            statement = connection.createStatement();

            //! Kod för att lägga till i Person person tabellen

            //? String sql = "INSERT INTO Person.person(first_name, last_name, gender, dob, income) " +
            //?        "VALUES ('John', 'Doe', 'M', '1985-06-15', 55000.00)";
            //? int rowsAffected = statement.executeUpdate(sql);
            //? System.out.println("Rader påverkade: " + rowsAffected);

            //! Kod för att uppdatera i tabellen

            //? String updateSQL = "UPDATE Person.person SET income = income * 1.1 WHERE last_name = 'Doe'";
            //? int rowsUpdated = statement.executeUpdate(updateSQL);
            //? System.out.println("Rows updated: " + rowsUpdated);

            //! Hämta d;ata från person-tabellen
            String query = "SELECT * FROM Person.person";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                //! Hämta Integer värde
                int personID = resultSet.getInt("person_id");
                System.out.println("ID: " + personID);

                //! Hämta sträng värdet
                String firstName = resultSet.getString("first_Name");
                String lastName = resultSet.getString("last_Name");
                String gender = resultSet.getString("gender");

                System.out.println("First Name: " + firstName);
                System.out.println("Last Name: " + lastName);
                System.out.println("Gender: " + gender);

                //! Hämta datum
                java.sql.Date dob = resultSet.getDate("dob");

                System.out.println("Date of birth: " + dob);

                //! Hämta inkomst

                double income = resultSet.getDouble("income");

                System.out.println("Income: " + income);
                System.out.println();
            }

            //! Stäng resurser
            statement.close();

        } catch (SQLException e) {
            System.err.println("Fel vid anslutning eller körning av SQL: " + e.getMessage());
        }

        //! Stänger ner servern när jag är klar
        finally {
            try {
                if (connection != null) {
                    connection.close();
                    System.out.println("Anslutningen är stängd.");
                }
            } catch (SQLException e) {
                System.err.println("Fel vid stängning av anslutningen: " + e.getMessage());
            }
        }
    }
}
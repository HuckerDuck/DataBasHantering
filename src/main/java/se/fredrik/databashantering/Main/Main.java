package se.fredrik.databashantering.Main;
import java.sql.*;

public class Main {
    // JDBC URL, användarnamn och lösenord
    private static final String DATABASE_URL = "jdbc:mysql://mysql-database.cbco6m888qzn.eu-central-1.rds.amazonaws.com:3306/";
    private static final String DATABASE_USER = "admin";
    private static final String DATABASE_PASSWORD = "abc12345";

    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;

        try {
            // Etablera anslutning till databasen
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
            System.out.println("Anslutningen till databasen lyckades!");

            //! Skapa ett statement
            statement = connection.createStatement();
            String query = "Select * from Books.books";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                System.out.println(resultSet.getString("title"));
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
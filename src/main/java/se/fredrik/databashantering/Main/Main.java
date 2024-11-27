package se.fredrik.databashantering.Main;
import se.fredrik.databashantering.Tools.JDBCUtil;

import java.sql.*;

public class Main {

    public static void main(String[] args) {
        //! Används för att koppla sig till servern senare
        Connection conn = null;
        //! PreparedStatement
        PreparedStatement pStmt;
        //! ResultSet behövs bara om vi hämtar data från databasen (SELECT:s)
        ResultSet rs = null;

        try {
            //! Sträng som används vid hämtning
            String query = "SELECT * FROM Person.person";

            //! Etablera anslutning till databasen
            conn = JDBCUtil.getConnection();
            System.out.println("Anslutningen till databasen lyckades!");

            //! Hämta produktnamn som ping-test
            String productName = JDBCUtil.getDatabaseProductName();
            System.out.println("Databasproduktnamn: " + productName); //! Skriver ut: "Databasproduktnamn: MySQL"

            //! Skapa ett PreparedStatement
            pStmt = conn.prepareStatement(query);

            //! Kod för att lägga till i Person tabellen

            //? try {
            //?               JDBCUtil.insertPerson("Fredrik", "Menot Brauer", "M", "1992-12-15", 00000.00);
            //?            } catch (SQLException e) {
            //?                System.err.println("Fel vid infogning i databasen: " + e.getMessage());
            //?            }




            //! Se typer av tabeller i databasen
            ResultSetMetaData rsmd = pStmt.getMetaData();

            //! Beskriv tabellen med en loop

            int columnCount = rsmd.getColumnCount();
            System.out.println("\n" + "Tabellstrukturen ");
            int i;
            for (i = 1; i <= columnCount; i++) {
                String columnType = rsmd.getColumnTypeName(i);
                System.out.print(columnType + " ");
                int precision = rsmd.getPrecision(i);
                System.out.print(precision + " ");
            }

            //! Skriv ut en specifik Tabelltyp
            int y = 1;
            String columnOne = rsmd.getColumnName(y);
            System.out.println("\n" + "\n" + "Kommulmn 1:" + columnOne);

            //! Kod för att uppdatera i tabellen

            //? String updateSQL = "UPDATE Person.person SET income = income * 1.1 WHERE last_name = 'Doe'";
            //? int rowsUpdated = statement.executeUpdate(updateSQL);
            //? System.out.println("Rows updated: " + rowsUpdated);

            //! Hämta data från person-tabellen
            //! Enkelt sätt

            ResultSet resultSetFirstname = pStmt.executeQuery();
            // Flytta till första raden
            resultSetFirstname.next();
            String firstName = resultSetFirstname.getString("First_name");
            System.out.println("\n" + "Första namnet är: " + firstName);

            //! Hämta data från person-tabellen
            //! Try loop

            try (ResultSet resultSet = pStmt.executeQuery()) {
                while (resultSet.next()) {
                    System.out.println();
                    System.out.println("ID: " + resultSet.getString(1));
                    System.out.println("First Name: " + resultSet.getString("First_name"));
                    System.out.println("Last Name: " + resultSet.getString("Last_name"));
                    System.out.println("Gender: " + resultSet.getString("Gender"));
                    System.out.println("Date of Birth: " + resultSet.getString("dob"));
                    System.out.println("Income: " + resultSet.getString("Income"));
                }
            }

            //! Stäng resurser
            pStmt.close();

        } catch (SQLException e) {
            System.err.println("Fel vid anslutning eller körning av SQL: " + e.getMessage());
        }

        JDBCUtil.closeConnection(conn);
    }
}
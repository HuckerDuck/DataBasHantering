package se.fredrik.databashantering.Main;
import com.mysql.cj.protocol.Resultset;
import se.fredrik.databashantering.Tools.JDBCUtil;

import java.sql.*;

public class Main {

    public static void main(String[] args) {
        //! Används för att koppla sig till servern senare
        Connection conn = null;
        //! Vanligt Statement
        Statement stmt = null;
        //! PreparedStatement
        PreparedStatement pStmt = null;
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

            //! Skapa ett statement
            pStmt = conn.prepareStatement(query);

            //! Kod för att lägga till i Person person tabellen

            //? String sql = "INSERT INTO Person.person(first_name, last_name, gender, dob, income) " +
            //?        "VALUES ('John', 'Doe', 'M', '1985-06-15', 55000.00)";
            //? int rowsAffected = statement.executeUpdate(sql);
            //? System.out.println("Rader påverkade: " + rowsAffected);

            //! Kod för att uppdatera i tabellen

            //? String updateSQL = "UPDATE Person.person SET income = income * 1.1 WHERE last_name = 'Doe'";
            //? int rowsUpdated = statement.executeUpdate(updateSQL);
            //? System.out.println("Rows updated: " + rowsUpdated);

            //! Hämta data från person-tabellen

            try (ResultSet resultSet = pStmt.executeQuery()) {
                while (resultSet.next()) {
                    System.out.println();
                    System.out.println(resultSet.getString(1));
                    System.out.println(resultSet.getString("First_name"));
                    System.out.println(resultSet.getString("Last_name"));
                    System.out.println(resultSet.getString("gender"));
                    System.out.println(resultSet.getString("dob"));
                    System.out.println(resultSet.getString("income"));
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
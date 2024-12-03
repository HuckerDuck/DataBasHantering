package se.fredrik.databashantering.Tools;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class JDBCUtilTest {

    private Connection conn;

    @BeforeEach
    public void setUp() throws SQLException {
        // Anslut till H2-databasen i minnet
        conn = JDBCUtil.getTestConnection();

        // Skapa tabellen PERSON2000 innan vi försöker göra INSERT
        try (Statement stmt = conn.createStatement()) {
            String createTableSQL = "CREATE TABLE IF NOT EXISTS person2000 ("
                    + "id INT PRIMARY KEY, "
                    + "name VARCHAR(100), "
                    + "gender VARCHAR(10), "
                    + "dob DATE, "
                    + "income DOUBLE)";
            stmt.execute(createTableSQL);
        }
    }

    @Test
    public void testInsertPerson() {
        String insertSQL = "INSERT INTO person2000 (id, name, gender, dob, income) "
                + "VALUES (1, 'John Doe', 'Male', '1990-12-12', 10000.00)";

        // Kör INSERT-frågan och kontrollera att den inte kastar undantag
        assertDoesNotThrow(() -> {
            try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
                pstmt.executeUpdate();
            }
        });

        // Efter INSERT, skriv ut vad som finns i tabellen
        printTableContents();
    }

    // Metod för att skriva ut alla rader i tabellen PERSON2000
    private void printTableContents() {
        String selectSQL = "SELECT * FROM person2000";

        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(selectSQL)) {
            List<String> rows = new ArrayList<>();
            while (rs.next()) {
                // Hämta värden från varje kolumn och lägg till i en lista
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String gender = rs.getString("gender");
                Date dob = rs.getDate("dob");
                double income = rs.getDouble("income");

                // Lägg till raden som en sträng
                rows.add(String.format("id: %d, name: %s, gender: %s, dob: %s, income: %.2f",
                        id, name, gender, dob, income));
            }

            // Skriv ut alla rader som finns i tabellen
            System.out.println("Contents of person2000 table:");
            for (String row : rows) {
                System.out.println(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

package se.fredrik.databashantering.Tools;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

class JDBCUtilTest {
    @BeforeEach
    public void setUp() throws SQLException {
        try (Connection conn = JDBCUtil.getTestConnection();
             Statement stmt = conn.createStatement()) {
            //! Skapa tabellen person
            stmt.execute("CREATE TABLE person2000 (id INT PRIMARY KEY, name VARCHAR(255), gender VARCHAR(10), dob DATE, income DECIMAL(10, 2))");
        }
    }

    @AfterEach
    public void cleanUp() {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = JDBCUtil.getTestConnection();
            stmt = conn.createStatement();
            //! Ta bort person-tabellen
            stmt.execute("DROP TABLE IF EXISTS person");
        }

        catch (SQLException e) {
            throw new RuntimeException(e);

        } finally
        {
            //! Stäng ned resurser
            JDBCUtil.closeStatement(stmt);
            JDBCUtil.closeConnection(conn);
        }
    }

    @Test
    public void testInsertPerson() throws SQLException{
        try (Connection conn = JDBCUtil.getTestConnection();
        Statement stmt = conn.createStatement()) {
            stmt.execute("Insert INTO person2000 (id, name, gender, dob, income) VALUES (1, 'John Doe', 'Male', '1990-12-12', 10000.00)");

        }
    }
}
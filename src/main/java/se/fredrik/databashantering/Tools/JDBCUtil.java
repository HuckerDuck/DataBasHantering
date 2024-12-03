package se.fredrik.databashantering.Tools;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

//! Hjälpklass för att koppla en mot servern
public class JDBCUtil {
    private static Properties properties = new Properties();


    //! Attribut för att koppla mig mot servern

    private static final String DATABASE_URL;
    private static final String DATABASE_USER;
    private static final String DATABASE_PASSWORD;
    public static Connection conn;

    public static Connection getTestConnection() throws SQLException {
        String database_URL = "jdbc:h2:mem:testdb;";
        String USER = "sa";      //? Standard användare för H2 databasen
        String PASSWORD = "sa"; //? Standard lösenord för H2 databasen

        return DriverManager.getConnection(database_URL, USER, PASSWORD);
    }

    //! Metod för att hämta en anslutning
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
    }

    //! Metod för att stänga en anslutning
    public static void closeConnection(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //! Metod för att stänga ett aktivt Statement-objekt
    //! Statement = Enkla SQL-frågor utan parametrar
    //! För att skicka data används INSERT, UPDATE eller DELETE
    //! Kan vara ett Statement eller PreparedStatement
    //! PreparedStatement är bättre mot SQL-Attacker

    public static void closeStatement(Statement stmt) {
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //! Stänger ett aktivt resultset
    //! ResultSet används för att hämta data
    //! För att hämta data används SELECT
    public static void closeResultSet(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //! Bekräftar och sparar ändringar i databasen
    public static void commit(Connection conn) {
        try {
            if (conn != null) {
                conn.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //! Ångra ändringar i databasen om något skulle gå fel
    public static void rollback(Connection conn) {
        try {
            if (conn != null) {
                conn.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //! Metod för att ansluta sig och hämta Metadata
    public static String getDatabaseProductName() throws SQLException{
        //! Starta anslutningen om den inte redan finns:

        //! Kontroller är kopplingen är null eller stängd innan man använder den i metoder
        if (conn == null || conn.isClosed()) {
            conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
        }

        //! Hämta metadatan från anslutningen
        DatabaseMetaData metaData = conn.getMetaData();

        //! Returnera produktnamnet
        return metaData.getDatabaseProductName();
    }

    static {
        try (InputStream input = JDBCUtil.class.getClassLoader().getResourceAsStream("properties")) {
            if (input == null) {
                throw new IOException("Unable to find application.properties");
            }
            properties.load(input);

            // Load properties into constants
            DATABASE_URL = properties.getProperty("db.url");
            DATABASE_USER = properties.getProperty("db.user");
            DATABASE_PASSWORD = properties.getProperty("db.password");

        } catch (IOException e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError("Failed to load database properties");
        }
    }
}

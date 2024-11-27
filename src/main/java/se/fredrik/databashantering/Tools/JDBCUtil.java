package se.fredrik.databashantering.Tools;

import java.sql.*;

//! Hjälpklass för att koppla en mot servern
public class JDBCUtil {

    //! Attribut för att koppla mig mot servern

    private static final String DATABASE_URL = "jdbc:mysql://mysql-database.cbco6m888qzn.eu-central-1.rds.amazonaws.com:3306/Person";
    private static final String DATABASE_USER = "admin";
    private static final String DATABASE_PASSWORD = "abc12345";
    private static Connection conn;

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

        if (conn == null){
            conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
        }

        //! Hämta metadatan från anslutningen
        DatabaseMetaData metaData = conn.getMetaData();

        //! Returnera produktnamnet
        return metaData.getDatabaseProductName();
    }
}

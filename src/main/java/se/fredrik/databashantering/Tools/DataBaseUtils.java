package se.fredrik.databashantering.Tools;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DataBaseUtils {
    public static void showAllTables(Connection connection) throws SQLException {
        List<String> tables = new ArrayList<>();
        Statement statement = connection.createStatement();


    }
}

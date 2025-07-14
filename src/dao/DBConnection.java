package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String DB_URL = "jdbc:derby:wellnessDB;create=true";

    public static Connection getConnection() {
        try {
            // Load the Apache Derby driver
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");

            // Return the connection
            return DriverManager.getConnection(DB_URL);
        } catch (ClassNotFoundException e) {
            System.out.println("Derby driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Database connection failed.");
            e.printStackTrace();
        }

        return null;
    }
}

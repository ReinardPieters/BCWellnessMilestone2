package dao;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

public class DBSetup {
    public static void initializeDatabase() {
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("CREATE TABLE Counselors (" +
                    "id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, " +
                    "name VARCHAR(100), " +
                    "specialization VARCHAR(100), " +
                    "availability VARCHAR(100))");

            stmt.executeUpdate("CREATE TABLE Appointments (" +
                    "id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, " +
                    "student VARCHAR(100), " +
                    "counselor_id INT, " +
                    "date VARCHAR(20), " +
                    "time VARCHAR(10), " +
                    "status VARCHAR(20), " +
                    "FOREIGN KEY (counselor_id) REFERENCES Counselors(id))");

            stmt.executeUpdate("CREATE TABLE Feedback (" +
                    "id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, " +
                    "student VARCHAR(100), " +
                    "rating INT, " +
                    "comments VARCHAR(255))");

            System.out.println("Tables created successfully.");
        } catch (SQLException e) {
            System.out.println("Tables may already exist or another error occurred:");
            e.printStackTrace();
        }
    }
}
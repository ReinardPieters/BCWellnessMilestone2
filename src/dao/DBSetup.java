package dao;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

public class DBSetup {
    public static void initializeDatabase() {
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            // Drop tables in reverse order of dependencies with try-catch to ignore errors
            try { stmt.executeUpdate("DROP TABLE Feedback"); } catch (SQLException ignored) {}
            try { stmt.executeUpdate("DROP TABLE Appointments"); } catch (SQLException ignored) {}
            try { stmt.executeUpdate("DROP TABLE Counselors"); } catch (SQLException ignored) {}

            // Create tables
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
                    "counselor_id INT, " +
                    "rating INT, " +
                    "comment VARCHAR(255), " +
                    "FOREIGN KEY (counselor_id) REFERENCES Counselors(id))");   

            System.out.println("Tables created successfully.");
        } catch (SQLException e) {
            System.out.println("Error occurred while setting up tables:");
            e.printStackTrace();
        }
    }
}

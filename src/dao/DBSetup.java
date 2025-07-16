package dao;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class DBSetup {
    public static void initializeDatabase() { //Initilize database
        try (Connection conn = DBConnection.getConnection();//Ensure connection and statement are closed automatically
            Statement stmt = conn.createStatement()) {
            try { stmt.executeUpdate("DROP TABLE Feedback"); } catch (SQLException ignored) {}
            try { stmt.executeUpdate("DROP TABLE Appointments"); } catch (SQLException ignored) {}
            try { stmt.executeUpdate("DROP TABLE Counselors"); } catch (SQLException ignored) {}
            stmt.executeUpdate("CREATE TABLE Counselors (" +
                    "id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, " +
                    "name VARCHAR(100), " +
                    "specialization VARCHAR(100), " +
                    "availability VARCHAR(100))");//Create Counselors table with auto generated ID,name,specialization and, availability

            stmt.executeUpdate("CREATE TABLE Appointments (" +
                    "id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, " +
                    "student VARCHAR(100), " +
                    "counselor_id INT, " +
                    "date VARCHAR(20), " +
                    "time VARCHAR(10), " +
                    "status VARCHAR(20), " +
                    "FOREIGN KEY (counselor_id) REFERENCES Counselors(id))");//Create Appointment table with a foreign key linking to Counselors

            stmt.executeUpdate("CREATE TABLE Feedback (" +
                    "id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, " +
                    "student VARCHAR(100), " +
                    "rating INT, " +
                    "comments VARCHAR(255), " +
                    "counselor_id INT, " +
                    "FOREIGN KEY (counselor_id) REFERENCES Counselors(id))");


            System.out.println("Tables created successfully.");//Notify successful table creation
        } catch (SQLException e) {
            System.out.println("Tables may already exist or another error occurred:");//Handle SQL exception such as a table existing
            e.printStackTrace();
        }
    }
    public static void printFeedbackColumns() {
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(
                 "SELECT COLUMNNAME, COLUMNDATATYPE " +
                 "FROM SYS.SYSCOLUMNS C " +
                 "JOIN SYS.SYSTABLES T ON C.REFERENCEID = T.TABLEID " +
                 "WHERE T.TABLENAME = 'FEEDBACK'"
             )) {

            System.out.println("Columns in Feedback table:");
            while (rs.next()) {
                String name = rs.getString("COLUMNNAME");
                String type = rs.getString("COLUMNDATATYPE");
                System.out.println(name + " - " + type);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
}

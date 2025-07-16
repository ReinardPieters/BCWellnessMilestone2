package dao;

import model.Counselor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CounselorDAO {
    
    public boolean deleteCounselorByName(String name) {
        String getIdSql = "SELECT id FROM Counselors WHERE name = ?";
        String deleteAppointmentsSql = "DELETE FROM Appointments WHERE counselor_id = ?";
        String deleteCounselorSql = "DELETE FROM Counselors WHERE id = ?";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            int counselorId = -1;

            // Get Counselor ID
            try (PreparedStatement getIdStmt = conn.prepareStatement(getIdSql)) {
                getIdStmt.setString(1, name);
                ResultSet rs = getIdStmt.executeQuery();
                if (rs.next()) {
                    counselorId = rs.getInt("id");
                } else {
                    System.out.println("Counselor not found: " + name);
                    return false;
                }
            }

            // Delete related appointments
            try (PreparedStatement deleteAppointmentsStmt = conn.prepareStatement(deleteAppointmentsSql)) {
                deleteAppointmentsStmt.setInt(1, counselorId);
                deleteAppointmentsStmt.executeUpdate();
            }

            // Delete counselor
            try (PreparedStatement deleteCounselorStmt = conn.prepareStatement(deleteCounselorSql)) {
                deleteCounselorStmt.setInt(1, counselorId);
                int rowsAffected = deleteCounselorStmt.executeUpdate();
                conn.commit(); 
                return rowsAffected > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



    public ArrayList<Counselor> getAllCounselors() {
        ArrayList<Counselor> list = new ArrayList<>();

        String sql = "SELECT * FROM Counselors";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Counselor counselor = new Counselor(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("specialization"),
                        rs.getString("availability")
                );
                list.add(counselor);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void insertCounselor(Counselor c) {
        String sql = "INSERT INTO Counselors (name, specialization, availability) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, c.getName());
            ps.setString(2, c.getSpecialization());
            ps.setString(3, c.getAvailability());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<String> getAllCounselorNames() {
        ArrayList<String> names = new ArrayList<>();
        String sql = "SELECT name FROM Counselors";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                names.add(rs.getString("name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return names;
    }
    public int getCounselorIdByName(String name) {
        String sql = "SELECT id FROM Counselors WHERE name = ?";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // not found
    }
    
    public Counselor getCounselorByName(String name) {
        String sql = "SELECT * FROM Counselors WHERE name = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Counselor(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("specialization"),
                    rs.getString("availability")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
}

package dao;

import model.Counselor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CounselorDAO {

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
}

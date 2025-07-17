package dao;

import model.Feedback;
import java.sql.*;
import java.util.ArrayList;

public class FeedbackDAO {

    public boolean submitFeedback(Feedback feedback) {
        String sql = "INSERT INTO Feedback (student, counselor_id, rating, comment) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, feedback.getStudent());
            ps.setInt(2, feedback.getCounselorID());
            ps.setInt(3, feedback.getRating());
            ps.setString(4, feedback.getComment());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Inside FeedbackDAO.java
    public ArrayList<String[]> getAllFeedbackEntries() {
        ArrayList<String[]> feedbackList = new ArrayList<>();

        String sql = "SELECT f.student, f.rating, f.comment, c.name AS counselor_name " +
                     "FROM Feedback f " +
                     "JOIN Counselors c ON f.counselor_id = c.id";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String student = rs.getString("student");
                String rating = String.valueOf(rs.getInt("rating"));
                String comment = rs.getString("comment");
                String counselorName = rs.getString("counselor_name");

                feedbackList.add(new String[]{student, counselorName, rating, comment});
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return feedbackList;
    }

    public ArrayList<Feedback> getAllFeedback() {
        ArrayList<Feedback> list = new ArrayList<>();
        String sql = "SELECT * FROM Feedback";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Feedback f = new Feedback(
                    rs.getInt("id"),
                    rs.getString("student"),
                    rs.getInt("counselor_id"),
                    rs.getInt("rating"),
                    rs.getString("comment")
                );
                list.add(f);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean updateFeedback(Feedback feedback) {
        String sql = "UPDATE Feedback SET student = ?, counselor_id = ?, rating = ?, comment = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, feedback.getStudent());
            ps.setInt(2, feedback.getCounselorID());
            ps.setInt(3, feedback.getRating());
            ps.setString(4, feedback.getComment());
            ps.setInt(5, feedback.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public ArrayList<String[]> getAllFeedbackEntriesWithID() {
    ArrayList<String[]> feedbackList = new ArrayList<>();

    String sql = "SELECT f.id, f.student, c.name AS counselor_name, f.rating, f.comment " +
                 "FROM Feedback f " +
                 "JOIN Counselors c ON f.counselor_id = c.id";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            String id = String.valueOf(rs.getInt("id"));
            String student = rs.getString("student");
            String counselor = rs.getString("counselor_name");
            String rating = String.valueOf(rs.getInt("rating"));
            String comment = rs.getString("comment");

            feedbackList.add(new String[]{student, counselor, rating, comment, id});
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return feedbackList;
    }
    
    public boolean deleteFeedback(int id) {
        String sql = "DELETE FROM Feedback WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
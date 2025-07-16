package dao;

import model.Feedback;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FeedbackDAO {

    public boolean submitFeedback(Feedback feedback) {
        String sql = "INSERT INTO Feedback (counselor_id, rating, comment) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, feedback.getCounselorID());
            ps.setInt(2, feedback.getRating());
            ps.setString(3, feedback.getComment());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public ArrayList<String[]> getAllFeedbackEntries() {
    ArrayList<String[]> feedbackList = new ArrayList<>();

    String sql = "SELECT f.rating, f.comment, c.name AS counselor_name " +
                 "FROM Feedback f " +
                 "JOIN counselors c ON f.counselor_id = c.id";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            String rating = String.valueOf(rs.getInt("rating"));
            String comment = rs.getString("comment");
            String counselorName = rs.getString("counselor_name");

            feedbackList.add(new String[]{counselorName, rating, comment});
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return feedbackList;
    }

    public boolean updateFeedback(int id, int newRating, String newComment) {
        String sql = "UPDATE Feedback SET rating = ?, comment = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, newRating);
            ps.setString(2, newComment);
            ps.setInt(3, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
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

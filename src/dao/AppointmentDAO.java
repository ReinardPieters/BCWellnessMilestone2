package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import model.Appointment;

public class AppointmentDAO {

    public ArrayList<String[]> getAllAppointments(){
        ArrayList<String[]> appointments = new ArrayList<>();
        String sql = "SELECT a.student, c.name AS counselor_name, a.date, a.time " +
                     "FROM appointments a JOIN counselors c ON a.counselor_id = c.id";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String student = rs.getString("student");
                String counselor = rs.getString("counselor_name");
                String date = rs.getString("date");
                String time = rs.getString("time");

                appointments.add(new String[] { student, counselor, date, time });
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return appointments;       
    }    
    public boolean deleteAppointment(String student, String counselor, String date, String time) {
        String sql = "DELETE FROM appointments WHERE student = ? AND counselor_id = (SELECT id FROM counselors WHERE name = ?) AND date = ? AND time = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, student);
            stmt.setString(2, counselor);
            stmt.setDate(3, java.sql.Date.valueOf(date));
            stmt.setTime(4, java.sql.Time.valueOf(time));

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addAppointment(Appointment a) {
        String sql = "INSERT INTO appointments (student, counselor_id, date, time, status) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, a.getStudent());
            stmt.setInt(2, a.getCounselorId());
            stmt.setDate(3, java.sql.Date.valueOf(a.getDate()));
            stmt.setTime(4, java.sql.Time.valueOf(a.getTime()));
            stmt.setString(5, a.getStatus());

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 🔹 Check if a counselor is already booked at the given date and time
    public boolean isCounselorBooked(int counselorId, Date date, Time time) {
        String sql = "SELECT * FROM appointments WHERE counselor_id = ? AND date = ? AND time = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, counselorId);
            stmt.setDate(2, date);
            stmt.setTime(3, time);

            ResultSet rs = stmt.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return true; // assume booked on failure
        }
    }

}

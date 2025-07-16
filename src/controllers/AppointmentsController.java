/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import dao.AppointmentDAO;
import dao.CounselorDAO;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import model.Appointment;
import model.Counselor;
import utils.TimeUtils;

/**
 *
 * @author reina
 */
public class AppointmentsController {

    public static String bookAppointment(String studentName, String counselorName, java.util.Date selectedDate, String timeStr) {
        if (studentName.isEmpty() || selectedDate == null || timeStr == null || counselorName == null) {
            return "Please fill in all fields.";
        }

        LocalDate date = selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalTime time = LocalTime.parse(timeStr);

        CounselorDAO counselorDAO = new CounselorDAO();
        int counselorId = counselorDAO.getCounselorIdByName(counselorName);
        Counselor counselor = counselorDAO.getCounselorByName(counselorName);

        if (counselorId == -1 || counselor == null) {
            return "Counselor not found in database.";
        }

        if (!TimeUtils.isWithinRange(counselor.getAvailability(), time)) {
            return "Selected time is outside the counselor's availability.";
        }

        AppointmentDAO appointmentDAO = new AppointmentDAO();
        boolean isBooked = appointmentDAO.isCounselorBooked(counselorId, java.sql.Date.valueOf(date), java.sql.Time.valueOf(time));

        if (isBooked) {
            return "This counselor is already booked at the selected time.";
        }

        Appointment appointment = new Appointment(studentName, counselorId, date, time, "Scheduled");
        appointmentDAO.addAppointment(appointment);

        return "SUCCESS";
    }  
    public static boolean deleteSelectedAppointment(String studentName, String counselorName, String date, String time) {
        AppointmentDAO dao = new AppointmentDAO();
        boolean success = dao.deleteAppointment(studentName, counselorName, date, time);
        return success;
    
    }
    public static ArrayList<String[]> getAllAppointments() {
        AppointmentDAO dao = new AppointmentDAO();
        return dao.getAllAppointments();
    }
}

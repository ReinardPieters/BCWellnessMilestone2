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
    //Calls methods from AppointmentDAO and CounselorDAO to handle appointment-related operations
    public static String bookAppointment(String studentName, String counselorName, java.util.Date selectedDate, String timeStr) {
        if (studentName.isEmpty() || selectedDate == null || timeStr == null || counselorName == null) {
            return "Please fill in all fields.";
        }//checks if the fields are empty

        LocalDate date = selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();//Date to LocalDate conversion
        LocalTime time = LocalTime.parse(timeStr);//String to LocalTime conversion

        CounselorDAO counselorDAO = new CounselorDAO();
        int counselorId = counselorDAO.getCounselorIdByName(counselorName);//Retrieves counselor ID by name
        Counselor counselor = counselorDAO.getCounselorByName(counselorName);//Retrieves counselor object by name

        if (counselorId == -1 || counselor == null) {
            return "Counselor not found in database.";
        }//Checks if the counselor exists in the database

        if (!TimeUtils.isWithinRange(counselor.getAvailability(), time)) {
            return "Selected time is outside the counselor's availability.";
        }//Checks if the selected time is within the counselor's availability

        AppointmentDAO appointmentDAO = new AppointmentDAO();
        boolean isBooked = appointmentDAO.isCounselorBooked(counselorId, java.sql.Date.valueOf(date), java.sql.Time.valueOf(time));

        if (isBooked) {
            return "This counselor is already booked at the selected time.";
        }//Checks if the counselor is already booked at the selected time

        Appointment appointment = new Appointment(studentName, counselorId, date, time, "Scheduled");
        appointmentDAO.addAppointment(appointment);//adds the appointment to the database

        return "SUCCESS";//Returns success message if the appointment is booked successfully
    } 
    //Method to delete an appointment
    public static boolean deleteSelectedAppointment(String studentName, String counselorName, String date, String time) {
        AppointmentDAO dao = new AppointmentDAO();
        boolean success = dao.deleteAppointment(studentName, counselorName, date, time);
        return success;
    
    }
    //Method to retrieve all appointments
    public static ArrayList<String[]> getAllAppointments() {
        AppointmentDAO dao = new AppointmentDAO();
        return dao.getAllAppointments();
    }
   
        
}

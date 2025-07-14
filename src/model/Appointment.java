/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.util.Date;
/**
 *
 * @author reina
 */
public class Appointment {
    private String studentName;
    private String counselorName;
    private Date date;
    private String time;
    
    public Appointment(String student, String counselor, Date date, String time) {
        this.studentName = student;
        this.counselorName = counselor;
        this.date = date;
        this.time = time;
    }    
}

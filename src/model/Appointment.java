package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Appointment {
    private String student;
    private int counselorId;
    private LocalDate date;
    private LocalTime time;
    private String status;

    public Appointment(String student, int counselorId, LocalDate date, LocalTime time, String status) {
        this.student = student;
        this.counselorId = counselorId;
        this.date = date;
        this.time = time;
        this.status = status;
    }

    // Getters
    public String getStudent() { return student; }
    public int getCounselorId() { return counselorId; }
    public LocalDate getDate() { return date; }
    public LocalTime getTime() { return time; }
    public String getStatus() { return status; }
}


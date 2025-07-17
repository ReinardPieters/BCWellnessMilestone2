package model;

public class Feedback {
    private int id;
    private String student;
    private int counselorId;
    private int rating; // 1–5
    private String comment;

    // Constructors
    public Feedback(int id, String student, int counselorId, int rating, String comment) {
        this.id = id;
        this.student = student;
        this.counselorId = counselorId;
        this.rating = rating;
        this.comment = comment;
    }

    public Feedback(String student, int counselorId, int rating, String comment) {
        this(-1, student, counselorId, rating, comment);
    }

    // Getters
    public int getId() { return id; }
    public String getStudent() { return student;}
    public int getCounselorID() { return counselorId; }
    public int getRating() { return rating; }
    public String getComment() { return comment; }
    
    // Setters
    public void setId(int id) { this.id = id; }
    public void setStudent(String student) { this.student = student; }
    public void setCounselorID(int counselorId) { this.counselorId = counselorId; }
    public void setRating(int rating) { this.rating = rating; }
    public void setComment(String comment) { this.comment = comment; }
}

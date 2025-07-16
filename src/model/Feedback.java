package model;

public class Feedback {
    private int id;
    private int counselorId;
    private int rating; // 1–5
    private String comment;

    public Feedback(int id, int counselorId, int rating, String comment) {
        this.id = id;
        this.counselorId = counselorId;
        this.rating = rating;
        this.comment = comment;
    }

    public Feedback(int counselorId, int rating, String comment) {
        this(-1, counselorId, rating, comment);
    }

    // Getters
    public int getId() { return id; }
    public int getCounselorID() { return counselorId; }
    public int getRating() { return rating; }
    public String getComment() { return comment; }
}

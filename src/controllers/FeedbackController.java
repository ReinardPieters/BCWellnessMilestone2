package controllers;

import dao.CounselorDAO;
import dao.FeedbackDAO;
import model.Feedback;

import java.util.ArrayList;

public class FeedbackController {

    public ArrayList<String[]> getAllFeedbackTableRows() {
        FeedbackDAO dao = new FeedbackDAO();
        return dao.getAllFeedbackEntries();
    }
    
    private final FeedbackDAO feedbackDAO;
    private final CounselorDAO counselorDAO;

    public FeedbackController() {
        feedbackDAO = new FeedbackDAO();
        counselorDAO = new CounselorDAO();
    }

    public String submitFeedback(String studentName, String counselorName, int rating, String comment) {
        if (studentName == null || studentName.isEmpty() ||
            counselorName == null || counselorName.isEmpty() ||
            comment == null || comment.isEmpty() ||
            rating < 1 || rating > 5) {
            return "All fields are required and rating must be between 1 and 5.";
        }

        int counselorId = counselorDAO.getCounselorIdByName(counselorName);
        if (counselorId == -1) {
            return "Counselor not found.";
        }

        Feedback feedback = new Feedback(studentName, counselorId, rating, comment);
        boolean success = feedbackDAO.submitFeedback(feedback);

        return success ? "SUCCESS" : "Failed to submit feedback.";
    }

    public ArrayList<Feedback> getAllFeedback() {
        return feedbackDAO.getAllFeedback();
    }

    public static String updateFeedback(int id, String studentName, String counselorName, int rating, String comment) {
        if (studentName == null || studentName.trim().isEmpty() || counselorName == null || counselorName.isEmpty()) {
            return "Please fill in all fields.";
        }

        int counselorId = new CounselorDAO().getCounselorIdByName(counselorName);

        if (counselorId == -1) {
            return "Counselor not found in the database.";
        }

        Feedback updatedFeedback = new Feedback(id, studentName.trim(), counselorId, rating, comment.trim());
        boolean success = new FeedbackDAO().updateFeedback(updatedFeedback);

        return success ? "SUCCESS" : "Failed to update feedback.";
    }

    public static boolean deleteFeedbackById(int id) {
    FeedbackDAO dao = new FeedbackDAO();
    return dao.deleteFeedback(id);
    }

    public static ArrayList<String[]> getAllFeedbackTableRowsWithID() {
        FeedbackDAO dao = new FeedbackDAO();
        return dao.getAllFeedbackEntriesWithID();  // Must return ID as 5th column
    }
}


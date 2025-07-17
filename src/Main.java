import dao.DBSetup;
//run this to make the database and only run once please
public class Main {
    public static void main(String[] args) {
        // Initialize the database
        DBSetup.initializeDatabase();
        // Print the feedback columns
        DBSetup.printFeedbackColumns();
    }
}
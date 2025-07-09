package view;

import javax.swing.*;

public class MainDashboard extends JFrame {

    public MainDashboard() {
        setTitle("Wellness Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create tabs
        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Appointments", new AppointmentPanel());
        tabbedPane.addTab("Counselors", new CounselorPanel());
        //tabbedPane.addTab("Feedback", new FeedbackPanel());

        add(tabbedPane);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainDashboard().setVisible(true);
        });
    }
}


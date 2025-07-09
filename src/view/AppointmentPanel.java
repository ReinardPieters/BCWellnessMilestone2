package view;

import view.dialogs.BookAppointmentDialog;

import javax.swing.*;
import java.awt.*;

public class AppointmentPanel extends JPanel {
    public AppointmentPanel() {
        setLayout(new BorderLayout());

        JLabel heading = new JLabel("Manage Appointments", SwingConstants.CENTER);
        heading.setFont(new Font("Arial", Font.BOLD, 20));
        add(heading, BorderLayout.NORTH);

        // Add buttons (example)
        JPanel buttonPanel = new JPanel();
        JButton btnBook = new JButton("Book Appointment");
        JButton btnView = new JButton("View Appointments");
        JButton btnUpdate = new JButton("Update Appointment");
        JButton btnCancel = new JButton("Cancel Appointment");

        btnBook.addActionListener(e->{
            new BookAppointmentDialog((JFrame) SwingUtilities.getWindowAncestor(this)).setVisible(true);
        });
        buttonPanel.add(btnBook);
        buttonPanel.add(btnView);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnCancel);

        add(buttonPanel, BorderLayout.CENTER);
    }
}

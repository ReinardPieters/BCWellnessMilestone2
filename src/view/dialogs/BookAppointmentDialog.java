package view.dialogs;
import dao.CounselorDAO;
import model.Counselor;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class BookAppointmentDialog extends JDialog {
    private JTextField studentField;
    private JComboBox<String> counselorBox;
    private JTextField dateField;
    private JTextField timeField;
    private JButton submitButton;
    private ArrayList<Counselor> counselors;

    public BookAppointmentDialog(JFrame parent) {
        super(parent, "Book Appointment", true);
        setSize(400, 350);
        setLocationRelativeTo(parent);

        // Main panel with vertical layout and padding
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20)); // top, left, bottom, right

        // Components
        studentField = new JTextField();
        counselorBox = new JComboBox<>();
        dateField = new JTextField();
        timeField = new JTextField();
        submitButton = new JButton();
        //Load counselor names from DB
        counselors = new CounselorDAO().getAllCounselors();
        for (Counselor c : counselors) {
            counselorBox.addItem(c.getName());
        }
        // Add components with spacing
        panel.add(createLabeledField("Student Name:", studentField));
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        panel.add(createLabeledField("Counselor:", counselorBox));
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        panel.add(createLabeledField("Date (yyyy-mm-dd):", dateField));
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        panel.add(createLabeledField("Time (mm:hh):", timeField));
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        panel.add(submitButton);

        // Add panel to dialog
        setContentPane(panel);

        // Action
        submitButton.addActionListener(e -> {
            if (studentField.getText().isEmpty() || dateField.getText().isEmpty() || timeField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields must be filled!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // TODO: Save appointment
            JOptionPane.showMessageDialog(this, "Appointment booked!");
            dispose();
        });
    }

    // Utility method to pair labels with fields
    private JPanel createLabeledField(String labelText, JComponent field) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(labelText);
        panel.add(label, BorderLayout.NORTH);
        panel.add(field, BorderLayout.CENTER);
        return panel;
    }
}

package view;

import dao.CounselorDAO;
import model.Counselor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CounselorPanel extends JPanel {
    private JTable counselorTable;
    private DefaultTableModel tableModel;

    public CounselorPanel() {
        setLayout(new BorderLayout());

        // Heading
        JLabel heading = new JLabel("Counselor Management", SwingConstants.CENTER);
        heading.setFont(new Font("Arial", Font.BOLD, 20));
        add(heading, BorderLayout.NORTH);

        // Table
        tableModel = new DefaultTableModel(new String[]{"ID", "Name", "Specialization", "Availability"}, 0);
        counselorTable = new JTable(tableModel);
        refreshTable();

        add(new JScrollPane(counselorTable), BorderLayout.CENTER);

        // Add button
        JButton btnAdd = new JButton("Add Counselor");
        btnAdd.addActionListener(e -> openAddDialog());

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(btnAdd);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Counselor> counselors = new CounselorDAO().getAllCounselors();
        for (Counselor c : counselors) {
            tableModel.addRow(new Object[]{
                    c.getId(), c.getName(), c.getSpecialization(), c.getAvailability()
            });
        }
    }

    private void openAddDialog() {
        JTextField nameField = new JTextField();
        JTextField specField = new JTextField();
        JTextField availField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Specialization:"));
        panel.add(specField);
        panel.add(new JLabel("Availability:"));
        panel.add(availField);

        int result = JOptionPane.showConfirmDialog(
                this, panel, "Add New Counselor", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText().trim();
            String spec = specField.getText().trim();
            String avail = availField.getText().trim();

            if (name.isEmpty() || spec.isEmpty() || avail.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields must be filled!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Counselor newC = new Counselor(0, name, spec, avail);
            new CounselorDAO().insertCounselor(newC);
            refreshTable();
        }
    }
}

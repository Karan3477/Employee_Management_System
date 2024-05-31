package com.karan.employee_management;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class AttendancePanel extends JPanel {

    private JTextField employeeIdField;
    private JButton markPresentButton, viewAttendanceButton;
    private JTextArea attendanceArea;

    public AttendancePanel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        setLayout(new BorderLayout(20, 20));
        setBackground(new Color(240, 240, 240));

        JLabel titleLabel = new JLabel("Employee Attendance Management", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(60, 63, 65));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setPreferredSize(new Dimension(400, 50));
        add(titleLabel, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        inputPanel.setBackground(new Color(250, 250, 250));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel employeeIdLabel = new JLabel("Employee ID:");
        employeeIdLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(employeeIdLabel, gbc);

        employeeIdField = new JTextField(15);
        employeeIdField.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 1;
        inputPanel.add(employeeIdField, gbc);

        markPresentButton = new JButton("Mark Present");
        markPresentButton.setFont(new Font("Arial", Font.BOLD, 16));
        markPresentButton.setBackground(new Color(100, 149, 237));
        markPresentButton.setForeground(Color.BLACK);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        inputPanel.add(markPresentButton, gbc);

        viewAttendanceButton = new JButton("View Attendance");
        viewAttendanceButton.setFont(new Font("Arial", Font.BOLD, 16));
        viewAttendanceButton.setBackground(new Color(100, 149, 237));
        viewAttendanceButton.setForeground(Color.BLACK);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        inputPanel.add(viewAttendanceButton, gbc);

        attendanceArea = new JTextArea();
        attendanceArea.setEditable(false);
        attendanceArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        attendanceArea.setMargin(new Insets(10, 10, 10, 10));
        JScrollPane scrollPane = new JScrollPane(attendanceArea);
        scrollPane.setPreferredSize(new Dimension(400, 200));

        add(inputPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        markPresentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = employeeIdField.getText();
                if (id.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid Employee ID.");
                    return;
                }

                DatabaseHelper.markAttendance(id);
                attendanceArea.append("Attendance marked for Employee ID: " + id + "\n");
                JOptionPane.showMessageDialog(null, "Attendance marked successfully!");

                employeeIdField.setText("");
            }
        });

        viewAttendanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = employeeIdField.getText();
                if (id.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid Employee ID.");
                    return;
                }

                String details = DatabaseHelper.getAttendanceDetails(id);
                attendanceArea.setText("Attendance Details for Employee ID: " + id + "\n" + details);
            }
        });
    }
}

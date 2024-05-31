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

public class PayrollPanel extends JPanel {

    private JTextField employeeIdField, salaryField;
    private JButton updateButton, viewButton;
    private JTextArea payrollArea;

    public PayrollPanel() {
        // Set the look and feel to the system's look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        setLayout(new BorderLayout(20, 20));
        setBackground(new Color(240, 240, 240));

        JLabel titleLabel = new JLabel("Employee Payroll Management", SwingConstants.CENTER);
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

        JLabel salaryLabel = new JLabel("Salary:");
        salaryLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(salaryLabel, gbc);

        salaryField = new JTextField(15);
        salaryField.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 1;
        inputPanel.add(salaryField, gbc);

        updateButton = new JButton("Update Salary");
        updateButton.setFont(new Font("Arial", Font.BOLD, 16));
        updateButton.setBackground(new Color(100, 149, 237));
        updateButton.setForeground(Color.BLACK);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        inputPanel.add(updateButton, gbc);

        viewButton = new JButton("View Payroll");
        viewButton.setFont(new Font("Arial", Font.BOLD, 16));
        viewButton.setBackground(new Color(100, 149, 237));
        viewButton.setForeground(Color.BLACK);
        gbc.gridy = 3;
        inputPanel.add(viewButton, gbc);

        payrollArea = new JTextArea();
        payrollArea.setEditable(false);
        payrollArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        payrollArea.setMargin(new Insets(10, 10, 10, 10));
        JScrollPane scrollPane = new JScrollPane(payrollArea);
        scrollPane.setPreferredSize(new Dimension(400, 200));
        
        add(inputPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = employeeIdField.getText();
                double salary;

                try {
                    salary = Double.parseDouble(salaryField.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid salary.");
                    return;
                }

                DatabaseHelper.updateEmployeeSalary(id, salary);
                payrollArea.append("Updated salary for Employee ID: " + id + " to " + salary + "\n");
                JOptionPane.showMessageDialog(null, "Salary updated successfully!");

                employeeIdField.setText("");
                salaryField.setText("");
            }
        });

        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = employeeIdField.getText();
                String details = DatabaseHelper.getPayrollDetails(id);
                payrollArea.append("Payroll details for Employee ID: " + id + ": " + details);
            }
        });
    }
}
//package com.karan.employee_management;
//
//import java.awt.BorderLayout;
//import java.awt.Font;
//import java.awt.GridLayout;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//import javax.swing.BorderFactory;
//import javax.swing.JButton;
//import javax.swing.JLabel;
//import javax.swing.JOptionPane;
//import javax.swing.JPanel;
//import javax.swing.JScrollPane;
//import javax.swing.JTextArea;
//import javax.swing.JTextField;
//import javax.swing.SwingConstants;
//
//public class EmployeePanel extends JPanel{
//	
//	private JTextField idField, nameField, deptField,salaryField;
//	private JButton addButton;
//    private JTextArea employeeArea;
//
//	
//	public EmployeePanel() {
//		
//		setLayout(new BorderLayout(10, 10));
//
//        JLabel titleLabel = new JLabel("Add New Employee", SwingConstants.CENTER);
//        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
//        add(titleLabel, BorderLayout.NORTH);
//        
//        
//        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 10));
//        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
//		
//		JLabel idLabel = new JLabel("Employee Id:");
//		JLabel nameLabel = new JLabel("Name:");
//		JLabel deptLabel = new JLabel("Department:");
//		JLabel salaryLabel = new JLabel("Salary:");
//		
//		idField = new JTextField();
//		nameField = new JTextField();
//		deptField = new JTextField();
//		salaryField = new JTextField();
//		
//		
//		addButton = new  JButton("Add Employee");
//		
//		
//		inputPanel.add(idLabel);
//		inputPanel.add(idField);
//		inputPanel.add(nameLabel);
//		inputPanel.add(nameField);
//		inputPanel.add(deptLabel);
//		inputPanel.add(deptField);
//		inputPanel.add(salaryLabel);
//		inputPanel.add(salaryField);
//		
//		inputPanel.add( new JLabel());
//		inputPanel.add(addButton);
//		
//		
//		employeeArea = new JTextArea();
//        employeeArea.setEditable(false);
//        employeeArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
//        JScrollPane scrollPane = new JScrollPane(employeeArea);
//        
//        add(inputPanel, BorderLayout.CENTER);
//        add(scrollPane, BorderLayout.SOUTH);
//        
//		
//		addButton.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				
//				String id = idField.getText();
//				String name = nameField.getText();
//				String department = deptField.getText();
//				double salary = Double.parseDouble(salaryField.getText());
//				
//				Employee employee = new Employee(id,name,department,salary);
//				
//				employeeArea.append("Added Employee - ID: " + id + ", Name: " + name + ", Dept: " + department + ", Salary: " + salary + "\n");
//                JOptionPane.showMessageDialog(null, "Employee added successfully!");
//				
//			}
//		});
//		
//	}
//
//}


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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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

public class EmployeePanel extends JPanel {

    private JTextField idField, nameField, deptField, salaryField;
    private JButton addButton;
    private JTextArea employeeArea;
    
    
    
    private final String URL = "jdbc:mysql://localhost:3309/employee_management";
    private final String USER = "root";
    private final String PASSWORD = "karan";

    public EmployeePanel() {
        // Set the look and feel to the system's look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        setLayout(new BorderLayout(20, 20));
        setBackground(new Color(240, 240, 240));

        JLabel titleLabel = new JLabel("Add New Employee", SwingConstants.CENTER);
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

        JLabel idLabel = new JLabel("Employee ID:");
        idLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(idLabel, gbc);

        idField = new JTextField(15);
        idField.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 1;
        inputPanel.add(idField, gbc);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(nameLabel, gbc);

        nameField = new JTextField(15);
        nameField.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 1;
        inputPanel.add(nameField, gbc);

        JLabel deptLabel = new JLabel("Department:");
        deptLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(deptLabel, gbc);

        deptField = new JTextField(15);
        deptField.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 1;
        inputPanel.add(deptField, gbc);

        JLabel salaryLabel = new JLabel("Salary:");
        salaryLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(salaryLabel, gbc);

        salaryField = new JTextField(15);
        salaryField.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 1;
        inputPanel.add(salaryField, gbc);

        addButton = new JButton("Add Employee");
        addButton.setFont(new Font("Arial", Font.BOLD, 16));
        addButton.setBackground(new Color(100, 149, 237));
        addButton.setForeground(Color.BLACK);
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        inputPanel.add(addButton, gbc);

        employeeArea = new JTextArea();
        employeeArea.setEditable(false);
        employeeArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        employeeArea.setMargin(new Insets(10, 10, 10, 10));
        JScrollPane scrollPane = new JScrollPane(employeeArea);
        scrollPane.setPreferredSize(new Dimension(400, 200));
        
        add(inputPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idField.getText();
                String name = nameField.getText();
                String department = deptField.getText();
                double salary;

                try {
                    salary = Double.parseDouble(salaryField.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid salary.");
                    return;
                }

                try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {
                    String query = "INSERT INTO employees (id, name, department, salary) VALUES (?, ?, ?, ?)";
                    
                    PreparedStatement preparedState = con.prepareStatement(query);
                    preparedState.setString(1, id);
                    preparedState.setString(2, name);
                    preparedState.setString(3, department);
                    preparedState.setDouble(4, salary);
                    
                    int row = preparedState.executeUpdate();
                    if (row > 0) {
                        employeeArea.append("Added Employee - ID: " + id + ", Name: " + name + ", Dept: " + department + ", Salary: " + salary + "\n");
                        JOptionPane.showMessageDialog(null, "Employee added successfully!");
                    }
                } catch (SQLException e1) {
                    if (e1.getErrorCode() == 1062) {  // Duplicate entry error code
                        JOptionPane.showMessageDialog(null, "Error: Duplicate entry for employee ID: " + id);
                    } else {
                        e1.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Error: Unable to add employee. " + e1.getMessage());
                    }
                }
                
                idField.setText("");
                nameField.setText("");
                deptField.setText("");
                salaryField.setText("");
            }
        });
    }
}








package com.karan.employee_management;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class MainFrame extends JFrame{

	private CardLayout cardLayout;
	private JPanel mainPanel;
	
	public MainFrame() {
		
		setTitle("Employee Management System");
		setSize(800,600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		cardLayout = new CardLayout();
		mainPanel = new JPanel(cardLayout);
		
		
		mainPanel.add(new EmployeePanel(),"EmployeePanel");
		mainPanel.add(new PayrollPanel(),"PayrollPanel");
		mainPanel.add(new AttendancePanel(),"AttendancePanel");
		
		
		add(mainPanel);
		creatMenuBar();
		
		setVisible(true);
	
	}

	private void creatMenuBar() {

		JMenuBar menuBar = new JMenuBar();
		
	    JMenu menu = new JMenu("Menu");
	    
	    JMenuItem employeeItem = new JMenuItem("Employee Details");
	    JMenuItem payrollItem = new JMenuItem("Payroll");

	    JMenuItem attendanceItem = new JMenuItem("Attendance");
	    
	    employeeItem.addActionListener(e -> cardLayout.show(mainPanel, "EmployeePanel"));
	    payrollItem.addActionListener(e -> cardLayout.show(mainPanel, "PayrollPanel"));
	    attendanceItem.addActionListener(e -> cardLayout.show(mainPanel, "AttendancePanel"));
	    
	    menu.add(employeeItem);
	    menu.add(payrollItem);
	    menu.add(attendanceItem);
	    
	    menuBar.add(menu);
	    
	    setJMenuBar(menuBar);
	}
	
	public static void main(String [] args) {
		
		DatabaseHelper.createNewDatabase();
        DatabaseHelper.createEmployeeTable();
        DatabaseHelper.createAttendanceTable();
        DatabaseHelper.createPayrollTable();
		new MainFrame();
	}
}

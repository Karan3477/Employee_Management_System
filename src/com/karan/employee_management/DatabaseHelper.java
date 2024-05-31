package com.karan.employee_management;

import java.sql.*;

import javax.swing.JOptionPane;

public class DatabaseHelper {

	 private static final String URL = "jdbc:mysql://localhost:3309/employee_management";
	    private static final String USER = "root";
	    private  static final String PASSWORD = "karan";
	    
    public static void createNewDatabase() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            if (conn != null) {
                System.out.println("A new database has been created.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createEmployeeTable() {
        String sql = "CREATE TABLE IF NOT EXISTS employees ("
                   + "id TEXT PRIMARY KEY,"
                   + "name TEXT NOT NULL,"
                   + "department TEXT NOT NULL,"
                   + "salary REAL NOT NULL"
                   + ");";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.execute();
            System.out.println("Employee table created.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createAttendanceTable() {
        String sql = "CREATE TABLE IF NOT EXISTS attendance ("
                   + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                   + "employee_id TEXT NOT NULL,"
                   + "date TEXT NOT NULL,"
                   + "FOREIGN KEY(employee_id) REFERENCES employees(id)"
                   + ");";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.execute();
            System.out.println("Attendance table created.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createPayrollTable() {
        String sql = "CREATE TABLE IF NOT EXISTS payroll ("
                   + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                   + "employee_id TEXT NOT NULL,"
                   + "salary REAL NOT NULL,"
                   + "date TEXT NOT NULL,"
                   + "FOREIGN KEY(employee_id) REFERENCES employees(id)"
                   + ");";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.execute();
            System.out.println("Payroll table created.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void addEmployee(String id, String name, String department, double salary) {
        String sql = "INSERT INTO employees(id, name, department, salary) VALUES(?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.setString(2, name);
            pstmt.setString(3, department);
            pstmt.setDouble(4, salary);
            pstmt.executeUpdate();
            System.out.println("Employee added.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void updateEmployeeSalary(String id, double salary) {
        String updateQuery = "UPDATE employees SET salary = ? WHERE id = ?";
        
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedState = con.prepareStatement(updateQuery)) {
            
            preparedState.setDouble(1, salary);
            preparedState.setString(2, id);
            
            int rowsAffected = preparedState.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Salary updated for Employee ID: " + id);
            } else {
                System.out.println("No employee found with ID: " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: Unable to update salary. " + e.getMessage());
        }
    }

    public static String getEmployeeDetails(String id) {
        String sql = "SELECT * FROM employees WHERE id = ?";
        StringBuilder details = new StringBuilder();

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                details.append("ID: ").append(rs.getString("id")).append("\n");
                details.append("Name: ").append(rs.getString("name")).append("\n");
                details.append("Department: ").append(rs.getString("department")).append("\n");
                details.append("Salary: ").append(rs.getDouble("salary")).append("\n");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return details.toString();
    }

    public static void markAttendance(String id) {
        String insertQuery = "INSERT INTO attendance (employee_id, date, status) VALUES (?, CURDATE(), 'Present')";
        
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedState = con.prepareStatement(insertQuery)) {
            
            preparedState.setString(1, id);
            preparedState.executeUpdate();
            
            System.out.println("Attendance marked for Employee ID: " + id);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: Unable to mark attendance. " + e.getMessage());
        }
    }

    public static String getAttendanceDetails(String id) {
        String selectQuery = "SELECT date, status FROM attendance WHERE employee_id = ?";
        StringBuilder attendanceDetails = new StringBuilder();
        
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedState = con.prepareStatement(selectQuery)) {
            
            preparedState.setString(1, id);
            ResultSet rs = preparedState.executeQuery();
            
            while (rs.next()) {
                attendanceDetails.append("Date: ").append(rs.getString("date")).append(", ")
                        .append("Status: ").append(rs.getString("status")).append("\n");
            }
            
            if (attendanceDetails.length() == 0) {
                attendanceDetails.append("No attendance records found for Employee ID: ").append(id).append("\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: Unable to retrieve attendance details. " + e.getMessage());
        }
        
        return attendanceDetails.toString();
    }

    public static void addPayrollRecord(String employeeId, double salary) {
        String sql = "INSERT INTO payroll(employee_id, salary, date) VALUES(?, ?, date('now'))";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, employeeId);
            pstmt.setDouble(2, salary);
            pstmt.executeUpdate();
            System.out.println("Payroll record added.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static String getPayrollDetails(String id) {
        String selectQuery = "SELECT id, name, department, salary FROM employees WHERE id = ?";
        StringBuilder payrollDetails = new StringBuilder();
        
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedState = con.prepareStatement(selectQuery)) {
            
            preparedState.setString(1, id);
            ResultSet rs = preparedState.executeQuery();
            
            if (rs.next()) {
                payrollDetails.append("ID: ").append(rs.getString("id")).append(", ")
                        .append("Name: ").append(rs.getString("name")).append(", ")
                        .append("Department: ").append(rs.getString("department")).append(", ")
                        .append("Salary: ").append(rs.getDouble("salary")).append("\n");
            } else {
                payrollDetails.append("No employee found with ID: ").append(id).append("\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: Unable to retrieve payroll details. " + e.getMessage());
        }
        
        return payrollDetails.toString();
    }
}

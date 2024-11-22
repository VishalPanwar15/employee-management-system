package com.employee.dao.impl;

import com.employee.dao.EmployeeDao;
import com.employee.databaseconnectivity.DbConnection;
import com.employee.exception.AlreadyExistException;
import com.employee.exception.NotFoundException;
import com.employee.pojo.Employee;
import com.employee.pojo.EmployeeAttendance;

import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepo implements EmployeeDao {


    //    Method return false when admin details already present into database
    public boolean registerAdmin(String emailId,String password) {
        try (Connection con = DbConnection.getDatabaseConnection()) {

            PreparedStatement pst1 = con.prepareStatement("SELECT * from admin where emailId=?");
            pst1.setString(1,emailId);

            ResultSet rs = pst1.executeQuery();
            if (rs.isBeforeFirst()) {
                throw new AlreadyExistException("Email id already exist ");
            }
            PreparedStatement pst = con.prepareStatement("INSERT INTO admin VALUES (?,?)");
            pst.setString(1, emailId);
            pst.setString(2, password);
            return pst.executeUpdate() > 0;
        } catch (SQLException s) {
            return false;
        }
    }


//  this method is use to provide the authentication whenever admin enetr username and password than this
//  method check the adminid and password from database if credential present in database than method
//  return true otherwise return false

    public boolean checkAdminLogin(String emailId,String password) {
        try (Connection con = DbConnection.getDatabaseConnection()) {
            PreparedStatement pst = con.prepareStatement("SELECT * FROM admin where emailId=? AND password=?");
            pst.setString(1, emailId);
            pst.setString(2, password);

            ResultSet rst = pst.executeQuery();

            if (rst.isBeforeFirst()) {
                return true;
            } else {
                throw new NotFoundException("Admin not found to this emailId :"+emailId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean saveEmployee(Employee employee){
        if(employee==null){
            return false;
        }
        try (Connection con = DbConnection.getDatabaseConnection()) {
            PreparedStatement pst = con.prepareStatement("INSERT INTO employee (first_name, last_name, emailId, age, departmentName, salary, joiningDate) VALUES (?, ?, ?, ?, ?, ?, ?)");
            pst.setString(1, employee.getEmpFirstName());
            pst.setString(2, employee.getEmpLastName());
            pst.setString(3, employee.getEmpEmailId());
            pst.setInt(4, employee.getAge());
            pst.setString(5, employee.getDepartmentName());
            pst.setDouble(6, employee.getSalary());
            pst.setString(7, employee.getJoiningString());

            int count = pst.executeUpdate();
            if (count > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            return false;
        }
    }


    public List<Employee> getAllEmployees(){
        List<Employee> employeeList = new ArrayList<>();
        try (Connection con = DbConnection.getDatabaseConnection()) {
            PreparedStatement pst = con.prepareStatement("SELECT * FROM employee");
            ResultSet rs = pst.executeQuery();

            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    Employee employee1 = new Employee();
                    employee1.setEmpId(rs.getInt("empId"));
                    employee1.setEmpFirstName(rs.getString("first_name"));
                    employee1.setEmpLastName(rs.getString("last_name"));
                    employee1.setEmpEmailId(rs.getString("emailId"));
                    employee1.setAge(rs.getInt("age"));
                    employee1.setDepartmentName(rs.getString("departmentName"));
                    employee1.setSalary(rs.getDouble("salary"));
                    employee1.setJoiningDate(rs.getString("joiningDate"));

                    employeeList.add(employee1);
                }
            }
            return employeeList;
        } catch (SQLException | NullPointerException exe) {
            exe.printStackTrace();
            return employeeList;
        }
    }


    public List<Employee> getAllEmployeeWithDepartment(String department){
        List<Employee> employeeList = new ArrayList<>();
        try(Connection connection = DbConnection.getDatabaseConnection()){
           PreparedStatement pst = connection.prepareStatement("Select * from employee where departmentName = ?");
           pst.setString(1,department);
           ResultSet rs = pst.executeQuery();

           if(rs.isBeforeFirst()){

               while (rs.next()){
                   Employee employee1 = new Employee();
                   employee1.setEmpId(rs.getInt("empId"));
                   employee1.setEmpFirstName(rs.getString("first_name"));
                   employee1.setEmpLastName(rs.getString("last_name"));
                   employee1.setEmpEmailId(rs.getString("emailId"));
                   employee1.setAge(rs.getInt("age"));
                   employee1.setDepartmentName(rs.getString("departmentName"));
                   employee1.setSalary(rs.getDouble("salary"));
                   employee1.setJoiningDate(rs.getString("joiningDate"));

                   employeeList.add(employee1);

               }
           }
           return employeeList;

        }
        catch (Exception e){
            e.printStackTrace();
            return employeeList;
        }

    }


    public Employee getEmployeeById(int empId) {
        Employee employee1 = new Employee();
        try (Connection con = DbConnection.getDatabaseConnection()) {
            PreparedStatement pst = con.prepareStatement("SELECT * FROM employee WHERE empId=?");
            pst.setInt(1, empId);
            ResultSet rs = pst.executeQuery();

            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                        employee1.setEmpId(rs.getInt("empId"));
                        employee1.setEmpFirstName(rs.getString("first_name"));
                        employee1.setEmpLastName(rs.getString("last_name"));
                        employee1.setEmpEmailId(rs.getString("emailId"));
                        employee1.setAge(rs.getInt("age"));
                        employee1.setDepartmentName(rs.getString("departmentName"));
                        employee1.setSalary(rs.getDouble("salary"));
                        employee1.setJoiningDate(rs.getString("joiningDate"));
                }
            }
            else{
                return null;
            }
            return employee1;

        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
            return employee1;
        }
    }
        public boolean updateEmployee(Employee employee1){
            try(Connection con = DbConnection.getDatabaseConnection()){

                PreparedStatement pst = con.prepareStatement("UPDATE   employee SET " +
                        "first_Name=?, last_Name = ?, emailid=?, age=? ,deptName=? , salary = ? , joining_Date=? where employeeId=?");

                pst.setString(1,employee1.getEmpFirstName());
                pst.setString(2,employee1.getEmpLastName());
                pst.setString(3,employee1.getEmpEmailId());
                pst.setInt(4,employee1.getAge());
                pst.setString(5,employee1.getDepartmentName());
                pst.setDouble(6,employee1.getSalary());
                pst.setString(7,employee1.getJoiningString());
                pst.setInt(8,employee1.getEmpId());

                int count = pst.executeUpdate();

                if(count>0){
                    return true;
                }
                else{
                    return false;
                }
            }
            catch (SQLException | NullPointerException ex){
                ex.printStackTrace();
                return false;
            }
        }



    public boolean takeInTimeAttendance(String emailId) {
        if (emailId == null || emailId.isEmpty()) {
            System.out.println("Invalid email ID provided");
            return false;
        }

        try (Connection con = DbConnection.getDatabaseConnection()) {
            EmployeeAttendance employeeAttendance = new EmployeeAttendance();

            // Step 1: Check if the employee exists
            String checkEmployeeQuery = "SELECT empId, emailId,departmentName FROM employee WHERE emailId = ?";
            try (PreparedStatement pst = con.prepareStatement(checkEmployeeQuery)) {
                pst.setString(1, emailId);
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        employeeAttendance.setEmpId(rs.getInt("empId"));
                        employeeAttendance.setEmailId(rs.getString("emailId"));
                        employeeAttendance.setDeptName(rs.getString("departmentName"));

                        System.out.println("Department Name :"+employeeAttendance.getDeptName());

                        employeeAttendance.setInTime(LocalTime.now()); // Current timestamp
                    } else {
                        System.out.println("No employee found with email ID: " + emailId);
                        return false;
                    }
                }
            }

            // Step 2: Check if attendance for today already exists
            String checkAttendanceQuery = "SELECT 1 FROM attendance WHERE empId = ? AND date = CURDATE()";
            try (PreparedStatement pst = con.prepareStatement(checkAttendanceQuery)) {
                pst.setInt(1, employeeAttendance.getEmpId());
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        System.out.println("Attendance for today has already been marked for email ID: " + emailId);
                        return false;
                    }
                }
            }

            // Step 3: Insert attendance record
            String insertAttendanceQuery = "INSERT INTO attendance(empId, emailId, departmentName, date, intime, outtime) VALUES (?, ?, ?, CURDATE(), ?, ?)";
            try (PreparedStatement pst = con.prepareStatement(insertAttendanceQuery)) {
                pst.setInt(1, employeeAttendance.getEmpId());
                pst.setString(2, employeeAttendance.getEmailId());
                pst.setString(3,employeeAttendance.getDeptName());
                pst.setTime(4, Time.valueOf(employeeAttendance.getInTime())); // Convert LocalTime to SQL Time
                pst.setNull(5, Types.TIME); // Assuming outTime is nullable
                pst.executeUpdate();
            }

            System.out.println("Attendance marked successfully for email ID: " + emailId);
            return true;
        } catch (SQLException e) {
            System.err.println("Database error occurred while marking attendance: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            return false;
        }
    }



    public boolean takeOutTimeAttendance(String emailId) {
        if (emailId == null || emailId.isEmpty()) {
            System.out.println("Invalid email ID provided");
            return false;
        }

        try (Connection con = DbConnection.getDatabaseConnection()) {
            // Step 1: Check if attendance exists for today
            String checkAttendanceQuery = "SELECT 1 FROM attendance WHERE emailId = ? AND date = CURDATE()";
            try (PreparedStatement pst = con.prepareStatement(checkAttendanceQuery)) {
                pst.setString(1, emailId);
                try (ResultSet rs = pst.executeQuery()) {
                    if (!rs.next()) { // Check if a record exists
                        System.out.println("No inTime found for today. Please mark your inTime first.");
                        return false;
                    }
                }
            }

            // Step 2: Update outTime for the current day
            String updateOutTimeQuery = "UPDATE attendance SET outTime = ? WHERE emailId = ? AND date = CURDATE()";
            try (PreparedStatement pst = con.prepareStatement(updateOutTimeQuery)) {
                pst.setTime(1, Time.valueOf(LocalTime.now())); // Set current time as outTime
                pst.setString(2, emailId);

                int rowsUpdated = pst.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("outTime marked successfully for email ID: " + emailId);
                    return true;
                } else {
                    System.out.println("Failed to mark outTime. Please try again.");
                    return false;
                }
            }
        } catch (SQLException e) {
            System.err.println("Database error occurred while marking outTime: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            return false;
        }
    }


    public List<EmployeeAttendance> attendanceList() {
        List<EmployeeAttendance> employeeAttendances = new ArrayList<>();

        try (Connection connection = DbConnection.getDatabaseConnection()) {
            String query = "SELECT \n" +
                    "    e.empId, \n" +
                    "    e.first_name, \n" +
                    "    e.departmentName, \n" +
                    "    DATE(a.inTime) AS date, \n" +
                    "    TIME(a.inTime) AS loginTime, \n" +
                    "    TIME(a.outTime) AS logoutTime, \n" +
                    "    TIMEDIFF(a.outTime, a.inTime) AS working_Hours\n" +
                    "FROM \n" +
                    "    employee AS e \n" +
                    "INNER JOIN \n" +
                    "    attendance AS a \n" +
                    "ON \n" +
                    "    e.empId = a.empId;";

            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    EmployeeAttendance attendance = new EmployeeAttendance();
                    attendance.setEmpId(rs.getInt("empId"));
                    attendance.setFirstName(rs.getString("first_name"));
                    attendance.setDeptName(rs.getString("departmentName"));
                    attendance.setDate(rs.getString("date"));

                    Time loginTime = rs.getTime("loginTime");
                    attendance.setInTime(loginTime != null ? loginTime.toLocalTime() : null);

                    Time logoutTime = rs.getTime("logoutTime");
                    attendance.setOutTIme(logoutTime != null ? logoutTime.toLocalTime() : null);

                    Time workingHours = rs.getTime("working_Hours");
                    attendance.setWorkingHours(workingHours != null ? workingHours.toLocalTime() : null);

                    employeeAttendances.add(attendance);
                    System.out.println(attendance);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employeeAttendances;
    }

    @Override
    public boolean assignTask(String taskName, String taskDescription, int empId) {
        String checkEmployeeQuery = "SELECT 1 FROM employee WHERE empId = ?";
        String insertTaskQuery = "INSERT INTO task (task_name, task_description, emp_id) VALUES (?, ?, ?)";

        try (Connection con = DbConnection.getDatabaseConnection();
             PreparedStatement checkStatement = con.prepareStatement(checkEmployeeQuery)) {

            // Check if employee exists
            checkStatement.setInt(1, empId);
            try (ResultSet rs = checkStatement.executeQuery()) {
                if (rs.isBeforeFirst()) { // Employee exists
                    try (PreparedStatement insertStatement = con.prepareStatement(insertTaskQuery)) {
                        insertStatement.setString(1, taskName);
                        insertStatement.setString(2, taskDescription);
                        insertStatement.setInt(3, empId);
                        return insertStatement.executeUpdate() > 0; // Task successfully assigned
                    }
                } else {
                    System.out.println("Employee with ID " + empId + " does not exist.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; // Task not assigned
    }


    public void viewAssignedTask(){
        String query = "select t.task_id,e.first_name as employee_name,t.task_name,t.task_description,e.departmentName from employee as e inner join task as t  on e.empId = t.emp_id;\n";
        try(Connection connection = DbConnection.getDatabaseConnection()){
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-10s | %-20s | %-20s | %-60s | %-20s |\n",
                    "task_id", "Employee Name","task Name","Task Description","Department");
            System.out.println("------------------------------------------------------------------------------------------------------------------------------------");

        while (resultSet.isBeforeFirst()){
            if (resultSet.next()){
                System.out.printf("| %-10d | %-20s | %-20s | %-60s  | %-20s |\n",
                        resultSet.getInt("task_id"),
                        resultSet.getString("employee_name"),
                        resultSet.getString("task_name"),
                        resultSet.getString("task_description"),
                        resultSet.getString("departmentName"));
            }
        }
        }
        catch (Exception e){
        e.printStackTrace();
        }
    }

}

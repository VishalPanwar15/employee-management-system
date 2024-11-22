package com.employee.dao;

import com.employee.databaseconnectivity.DbConnection;
import com.employee.pojo.Employee;
import com.employee.pojo.EmployeeAttendance;

import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public interface EmployeeDao {


    boolean registerAdmin(String emailId,String password);

    boolean checkAdminLogin(String emailId,String password);

    boolean saveEmployee(Employee employee);

    List<Employee> getAllEmployees();

    List<Employee> getAllEmployeeWithDepartment(String department);

    Employee getEmployeeById(int empId);

    boolean updateEmployee(Employee employee1);

    boolean takeInTimeAttendance(String emailId);

    boolean takeOutTimeAttendance(String emailId);

    List<EmployeeAttendance> attendanceList();

    boolean assignTask(String taskName, String taskDescription, int empId);

    void viewAssignedTask();
}

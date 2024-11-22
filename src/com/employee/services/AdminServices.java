package com.employee.services;

import com.employee.dao.EmployeeDao;
import com.employee.databaseconnectivity.DbConnection;
import com.employee.exception.AlreadyExistException;
import com.employee.exception.InvalidException;
import com.employee.exception.NotFoundException;
import com.employee.pojo.Admin;
import com.employee.pojo.Employee;
import com.employee.pojo.EmployeeAttendance;
import com.employee.dao.impl.EmployeeRepo;
import com.employee.pojo.Task;
import com.employee.utilityclass.UtilityMethods;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class AdminServices {

    EmployeeDao employeeRepo = new EmployeeRepo();
    Admin admin = new Admin();
    Scanner scanner = new Scanner(System.in);

    Employee employee;


    //    Method return false when admin details already present into database
    //    Method return false when admin details already present into database
    public boolean registerAdmin() {
        try (Connection con = DbConnection.getDatabaseConnection()) {
            System.out.println("Enter Your email ID  -> Ex: example@gmail.com");
            String emailId = scanner.next();
            System.out.println("Enter your password  -> Password must be at least 8 characters, e.g., demo@123");
            String password = scanner.next();

            // Validate email and password
            if (UtilityMethods.isValidEmail(emailId)) {
                if (UtilityMethods.isValidPassword(password)) {
                    admin.setEmailId(emailId);
                    admin.setPassword(password);
                } else {
                    throw new InvalidException("Password is invalid. It must contain one special character, one lowercase letter, one uppercase letter, and digits.");
                }
            } else {
                throw new InvalidException("Email ID is invalid. It must be in the format: example123@gmail.com");
            }

            // Check if email ID already exists
            System.out.println("Checking email: " + admin.getEmailId());
            PreparedStatement pst1 = con.prepareStatement("SELECT * FROM admin WHERE emailId = ?");
            pst1.setString(1, admin.getEmailId());

            ResultSet rs = pst1.executeQuery();
            if (rs.isBeforeFirst()) {
                System.out.println("Email ID already exists in the database.");
                throw new AlreadyExistException("Email ID already exists.");
            }

            // Insert the new admin
            PreparedStatement pst = con.prepareStatement("INSERT INTO admin (emailId, password) VALUES (?, ?)");
            pst.setString(1, admin.getEmailId());
            pst.setString(2, admin.getPassword());

            int count = pst.executeUpdate();
            if (count > 0) {
                System.out.println("Admin registered successfully.");
                return true;
            } else {
                System.out.println("Admin registration failed.");
                return false;
            }
        } catch (SQLException s) {
            System.err.println("SQL Exception: " + s.getMessage());
            return false;
        }
    }



//  this method is use to provide the authentication whenever admin enetr username and password than this
//  method check the adminid and password from database if credential present in database than method
//  return true otherwise return false

    public boolean checkAdminLogin() {
        try (Connection con = DbConnection.getDatabaseConnection()) {
            System.out.println("Enter your emailId ");
            String emailId = scanner.next();
            System.out.println("Enter your password");
            String password = scanner.next();

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

    public Employee getEmployeeData(){
        System.out.println("Enter employee first name");
        String firstName = scanner.next();
        System.out.println("Enter employee last name");
        String lastName = scanner.next();
        System.out.println("Enter employee email id");
        String email = scanner.next();
        System.out.println("Enter employee age");
        int age = scanner.nextInt();
        if(age< 18){
            throw new InvalidException("Employee is not valid age is must greater than 18");
        }
        scanner.nextLine();
        System.out.println("Enter employee department Only JAVA,IT,FrontEnd");
        String deptName = scanner.nextLine();

        if(deptName.equalsIgnoreCase("java") || deptName.equalsIgnoreCase("it")|| deptName.equalsIgnoreCase("frontend"))
        {
        }
        else{
            throw  new InvalidException("Department is not valid");
        }
        System.out.println("Enter employee salary per month");
        double salary = scanner.nextDouble();
        System.out.println("Enter employee joining date");
        String date = scanner.next();
        return  new Employee(firstName, lastName, email, age, deptName, salary, date);
    }


//    this method is help to add the new employee details into database  if data is successfully inserted then method return true otherwise return false
    public boolean addEmployee() {
        AdminServices adminServices = new AdminServices();
        try {
            employee = adminServices.getEmployeeData();
        }
        catch (InvalidException invalidException){
            System.err.println(invalidException.getMessage());
            return false;
        }
        return  employeeRepo.saveEmployee(employee);
    }

    //    to get all employee details from database
    public List<Employee> getAllEmployees() {

        return employeeRepo.getAllEmployees();
    }

    public List<Employee> getEmployeeByDepartment(String deptName){
        return employeeRepo.getAllEmployeeWithDepartment(deptName);
    }

    //    Get employee by employee Id we just provide the employeeId as parameter than this method return employee detail
    public Employee getEmployeeById(int empId) {
        Employee employee1 = employeeRepo.getEmployeeById(empId);
        if(employee1!=null){
            return employee1;
        }
        else{
            return null;
        }
    }

//    this method is help to update the employee details into database
        public boolean updateEmployee(int empNumber){
        AdminServices adminServices = new AdminServices();
        Employee employee = adminServices.getEmployeeById(empNumber);

        System.out.println("Old employee data ");

        System.out.println(employee);
        Employee employee1 = adminServices.getEmployeeData();
        employee1.setEmpId(empNumber);
        return employeeRepo.updateEmployee(employee1);
    }


//    Delete employee with the help of employee number
    public boolean deleteEmployee(int empId){
        Employee employee1 = new AdminServices().getEmployeeById(empId);
        try(Connection con = DbConnection.getDatabaseConnection()){
           PreparedStatement pst = con.prepareStatement("DELETE from employee where employeeId=?");
           pst.setInt(1,empId);
           int count = pst.executeUpdate();

           if(count > 0)
               return true;
           else
               return false;
        }
        catch (SQLException e){
            e.printStackTrace();
            return  false;
        }
    }


//    to sort the employee details by employee age in ascending order

    public List<Employee> sortEmployeeByAge(){
        AdminServices adminServices = new AdminServices();

        List<Employee> employeeList = adminServices.getAllEmployees();

       employeeList =  employeeList.stream().sorted(Comparator.comparingInt(Employee::getAge)).toList();

       return employeeList;

    }

//    to sort the employee details by employee firstName in ascending order
    public List<Employee> sortEmployeeByName(){
        AdminServices adminServices = new AdminServices();

        List<Employee> employeeList = adminServices.getAllEmployees();
        employeeList = employeeList.stream().sorted(Comparator.comparing(Employee::getEmpFirstName)).toList();
        return  employeeList;
    }


    public void printEmployeeData(List<Employee> employees) {
        if (employees.isEmpty()) {
            System.out.println("No data in employee list.");
        } else {
            // Print table header
            System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.printf("| %-10s | %-15s | %-15s | %-25s | %-5s | %-20s | %-10s | %-10s |\n",
                    "Emp No", "First Name", "Last Name", "Email", "Age", "Dpt", "Salary", "JoinDate");
            System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------");

// Print employee data
            for (Employee employee : employees) {
                System.out.printf("| %-10d | %-15s | %-15s | %-25s | %-5d | %-20s | %-10.2f | %-10s |\n",
                        employee.getEmpId(),
                        employee.getEmpFirstName(),
                        employee.getEmpLastName(),
                        employee.getEmpEmailId(),
                        employee.getAge(),
                        employee.getDepartmentName(),
                        employee.getSalary(),
                        employee.getJoiningString());
            }

            System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------");
        }
    }

    public String takeInTimeAttendance(){
        System.out.println("Enter valid email id to take attendance");
        String emailId = scanner.next();
        boolean takeAttendance = employeeRepo.takeInTimeAttendance(emailId);
        if(takeAttendance){
            return "Thank you ...";
        }
        else {
            return "Invalid emailId ";
        }
    }

    public String takeOutTimeAttendance(){
        System.out.println("Enter valid email id to take attendance");
        String emailId = scanner.next();
        boolean takeAttendance = employeeRepo.takeOutTimeAttendance(emailId);
        if(takeAttendance){
            return "Thank you ...";
        }
        else {
            return "Invalid emailId ";
        }
    }

    public List<EmployeeAttendance> attendanceList(){
        return employeeRepo.attendanceList();
    }


    public void printAttendance(List<EmployeeAttendance> employees) {
        if (employees.isEmpty()) {
            System.out.println("No data in employee list.");
        } else {
            // Print table header
            System.out.println("--------------------------------------------------------------------------------------------------------------");
            System.out.printf("| %-10s | %-15s | %-15s | %-10s | %-10s | %-10s | %15s |\n",
                    "Emp No", "First Name","departmentName","date","inTime","outTime","Working_hours");
            System.out.println("--------------------------------------------------------------------------------------------------------------");

// Print employee data
            for (EmployeeAttendance employee : employees) {
                System.out.printf("| %-10d | %-15s | %-15s | %-10s  | %-10s | %-10s | %-15s |\n",
                        employee.getEmpId(),
                        employee.getFirstName(),
                        employee.getDeptName(),
                        employee.getDate(),
                        employee.getInTime(),
                        employee.getOutTIme(),
                        employee.getWorkingHours());
            }

            System.out.println("--------------------------------------------------------------------------------------------------------------");
        }
    }

    public boolean assignTask(){
        System.out.println("Enter task Name");
        String taskName = scanner.nextLine();
        System.out.println("Enter task description");
        String taskDescription = scanner.nextLine();

        System.out.println("Enter employee Id to assing this task");
        int empId = scanner.nextInt();

        return employeeRepo.assignTask(taskName,taskDescription,empId);
    }

    public void viewAssignedTask() {
        employeeRepo.viewAssignedTask();
    }
}

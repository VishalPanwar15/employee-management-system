package com.employee.userinterface;

import com.employee.exception.InvalidException;
import com.employee.pojo.Employee;
import com.employee.pojo.EmployeeAttendance;
import com.employee.services.AdminServices;

import java.util.List;
import java.util.Scanner;

public class AdminInterface {

    AdminServices adminServices = new AdminServices();
    public void adminInterface(){
        System.out.println("WELCOME TO EMPLOYEE MANAGEMENT SYSTEM APPLICATION");
        while (true){
            Scanner scanner = new Scanner(System.in);
            System.out.println("You can manage the following function using this system");
            System.out.println("1. Add new Employee enter       ->  ADD");
            System.out.println("2. Delete old Employee enter    ->  DELETE");
            System.out.println("3. Update Employee Details enter->  UPDATE");
            System.out.println("4. Fetch All employee details   ->  FETCH");
            System.out.println("5. Assign task to employee - > ASSIGNTASK");
            System.out.println("6. View Assigned Task --->  VIEW" );
            System.out.println("7. Get Employee By ID : GETEMPLOYEE");
            System.out.println("8. Sort Employee details by Name -> SORT");
            System.out.println("9. Employee Attendance - attendance");

            System.out.println("Enter your choice ");
            String choice = scanner.next();
            choice = choice.toUpperCase();
            switch (choice.toUpperCase()){
                case "ADD" :
                    try {
                        if(adminServices.addEmployee()){
                            System.out.println("Employee added succefully");

                        }
                        else{
                            System.out.println("Employee not added into database");
                        }
                    }
                    catch (InvalidException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case "ASSIGNTASK":
                    if(adminServices.assignTask()){
                        System.out.println("Task assigned to employee");
                    }
                    else {
                        System.out.println("task is not assigned");
                    }
                    break;
                case "VIEW":
                    adminServices.viewAssignedTask();
                    break;
                case "FETCH":
                    System.out.println("Which department list you want ");
                    System.out.println("1. IT Department List ----> IT");
                    System.out.println("2. Java Department List -->JAVA");
                    System.out.println("3. FrontEnd employee List->FrontEnd");
                    System.out.println("4. All Employees List ---->All");

                    String dpt = scanner.next();
                    switch (dpt.toLowerCase()) {
                        case "it":
                            System.out.println(" ***** IT DEPARTMENT EMPLOYEES LIST *****");
                            List<Employee> employeeITList = adminServices.getEmployeeByDepartment(dpt);
                            adminServices.printEmployeeData(employeeITList);
                            break;
                        case "java":
                            System.out.println(" ***** JAVA DEPARTMENT EMPLOYEES LIST *****");
                            List<Employee> employeeJavaList = adminServices.getEmployeeByDepartment(dpt);
                            adminServices.printEmployeeData(employeeJavaList);
                            break;
                        case "frontend":
                            System.out.println(" *****FRONT END DEPARTMENT EMPLOYEES LIST *****");
                            List<Employee> FrontEndList = adminServices.getEmployeeByDepartment(dpt);
                            adminServices.printEmployeeData(FrontEndList);
                            break;
                        case "all":
                            System.out.println(" ***** EMPLOYEES LIST *****");
                            List<Employee> employeeList = adminServices.getAllEmployees();
                            adminServices.printEmployeeData(employeeList);
                            break;
                        default:
                            System.out.println("Department is not present");
                            break;
                    }
                    break;
                case "DELETE":
                    System.out.println("Enter employee id you want to delete");
                    int empId1 = scanner.nextInt();
                    if(adminServices.deleteEmployee(empId1)){
                        System.out.println("Employee Deleted Successfully");
                    }
                    else{
                        System.out.println("Data is not present");
                    }
                    break;

                case "GETEMPLOYEE":
                    System.out.println("Enter Employee Id to fetch all details");
                    int empId = scanner.nextInt();
                    Employee employee = adminServices.getEmployeeById(empId);
                    if(employee!=null){
                        System.out.println(employee);
                    }
                    else{
                        System.out.println("Data is not present");
                    }
                    break;
                case "UPDATE":
                    System.out.println("Enter employeeId you want to update");
                    int empid2 = scanner.nextInt();
                    boolean isupdated = adminServices.updateEmployee(empid2);
                    if(isupdated){
                        System.out.println("Updated successfully");
                    }
                    else{
                        System.out.println("Not updated");
                    }
                    break;
                case "SORT":
                    System.out.println("press 1 to sort by age ");
                    System.out.println("press 2 to sort by name");
                    int select = scanner.nextInt();
                    if(select==1){
                        List<Employee> employees = adminServices.sortEmployeeByAge();
                        System.out.println(" ****************** Employee Details ******************");
                        System.out.println(employees);
                    }
                    else if(select==2){
                        List<Employee> employees = adminServices.sortEmployeeByName();
                        System.out.println(" ****************** Employee Details ******************");
                        System.out.println(employees);
                    }
                    else{
                        System.out.println("Invalid choice");
                    }
                    break;

                case "ATTENDANCE":
                   List<EmployeeAttendance> attendanceList =  adminServices.attendanceList();
                    adminServices.printAttendance(attendanceList);
                    break;
                default:
                    System.out.println("Invalid choice ");
            }
        }
    }

}

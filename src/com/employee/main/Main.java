package com.employee.main;

import com.employee.exception.AlreadyExistException;
import com.employee.exception.InvalidException;
import com.employee.exception.NotFoundException;
import com.employee.services.AdminServices;
import com.employee.userinterface.AdminInterface;
import com.employee.userinterface.EmployeeInterface;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    // Method to safely read integer input
    private static int getValidatedInteger(Scanner scan) {
        while (true) {
            try {
                System.out.print("Enter your choice (only integers are allowed): ");
                return scan.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                scan.nextLine(); // Clear the invalid input
            }
        }
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        AdminServices adminServices = new AdminServices();
        AdminInterface adminInterface = new AdminInterface();
        EmployeeInterface employeeInterface = new EmployeeInterface();

        System.out.println("********* WELCOME TO EMPLOYEE MANAGEMENT PLATFORM *********");
        System.out.println("If you are an Employee, enter: employee");
        System.out.println("If you are an Admin, enter: admin");

        String userCategory = scan.next();
        switch (userCategory.toLowerCase()) {
            case "employee":
                employeeInterface.employeeHome();
                break;

            case "admin":
                System.out.println("\nWELCOME TO EMPLOYEE MANAGEMENT SYSTEM");
                int choice;
                do {
                    // Display admin menu
                    System.out.println("\n--- Admin Menu ---");
                    System.out.println("Press 1 to create an admin account");
                    System.out.println("Press 2 if you have a username and password");
                    System.out.println("Press -1 to exit from the application");

                    // Get validated integer input
                    choice = getValidatedInteger(scan);

                    // Handle admin choices
                    switch (choice) {
                        case 1:
                            try {
                                boolean isRegistered = adminServices.registerAdmin();
                                System.out.println("isRegistrerd :"+isRegistered);
                                if (isRegistered) {
                                    System.out.println("Admin registered successfully.");
                                } else {
                                    System.out.println("Admin registration failed. Email ID already exists.");
                                }
                            } catch (InvalidException e) {
                                System.err.println("Registration Error: " + e.getMessage());
                            } catch (AlreadyExistException e) {
                                System.err.println("Registration Error: " + e.getMessage());
                            }
                            break;

                        case 2:
                            try {
                                boolean login = adminServices.checkAdminLogin();
                                if (login) {
                                    adminInterface.adminInterface();
                                    choice = -1; // Exit loop after successful login
                                } else {
                                    System.out.println("Invalid credentials. Please try again.");
                                }
                            } catch (NotFoundException e) {
                                System.err.println("Login Error: " + e.getMessage());
                            }
                            break;

                        case -1:
                            System.out.println("Exiting the application...");
                            break;

                        default:
                            System.out.println("Invalid choice. Please select a valid option.");
                            break;
                    }
                } while (choice != -1);
                break;

            default:
                System.out.println("Invalid category. Please restart and choose either 'employee' or 'admin'.");
                break;
        }

        scan.close(); // Close Scanner to release resources
        System.out.println("Application terminated.");
    }
}

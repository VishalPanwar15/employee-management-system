package com.employee.userinterface;

import com.employee.services.AdminServices;
import java.util.InputMismatchException;
import java.util.Scanner;

public class EmployeeInterface {
    AdminServices services = new AdminServices();

    public void employeeHome() {
        Scanner scan = new Scanner(System.in);
        int choice = 0;

        do {
            // Display menu
            System.out.println("\n--- Employee Menu ---");
            System.out.println("PRESS 1: Employee Login");
            System.out.println("PRESS 2: Employee Logout");
            System.out.println("PRESS -1: Exit the application");

            // Input validation
            try {
                System.out.print("Enter your choice (only integers are allowed): ");
                choice = scan.nextInt();

                // Handle user choice
                switch (choice) {
                    case 1:
                        System.out.println(services.takeInTimeAttendance());
                        break;
                    case 2:
                        System.out.println(services.takeOutTimeAttendance());
                        break;
                    case -1:
                        System.out.println("Exiting the application...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                scan.nextLine(); // Clear invalid input
            }
        } while (choice != -1);

        // Close the scanner
        scan.close();
        System.out.println("Application terminated.");
    }
}

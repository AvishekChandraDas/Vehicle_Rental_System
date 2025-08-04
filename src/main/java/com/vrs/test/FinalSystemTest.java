package com.vrs.test;

import com.vrs.controller.UserController;
import com.vrs.controller.AdminController;
import com.vrs.controller.VehicleController;
import com.vrs.controller.BookingController;
import com.vrs.database.DatabaseConnection;
import com.vrs.model.Vehicle;
import com.vrs.model.Booking;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

public class FinalSystemTest {
    public static void main(String[] args) {
        System.out.println("=== Vehicle Rental System - Final System Test ===");
        System.out.println("Testing all critical functionality...\n");

        int passedTests = 0;
        int totalTests = 0;

        try {
            // Test 1: Database Connection
            totalTests++;
            System.out.print("1. Database Connection... ");
            Connection conn = DatabaseConnection.getConnection();
            if (conn != null && !conn.isClosed()) {
                System.out.println("✓ PASS");
                passedTests++;
            } else {
                System.out.println("✗ FAIL");
            }

            // Test 2: Admin Authentication
            totalTests++;
            System.out.print("2. Admin Authentication... ");
            AdminController adminController = new AdminController();
            boolean adminLogin = adminController.loginAdmin("admin", "admin123");
            if (adminLogin) {
                System.out.println("✓ PASS");
                passedTests++;
            } else {
                System.out.println("✗ FAIL");
            }

            // Test 3: User Registration and Login
            totalTests++;
            System.out.print("3. User Registration & Login... ");
            UserController userController = new UserController();
            String testUsername = "testuser_" + System.currentTimeMillis();
            boolean userReg = userController.registerUser(testUsername, "testpass", "Test", "User",
                    "test@example.com", "123-456-7890", "DL12345");
            boolean userLogin = userController.loginUser(testUsername, "testpass");
            if (userReg && userLogin) {
                System.out.println("✓ PASS");
                passedTests++;
            } else {
                System.out.println("✗ FAIL");
            }

            // Test 4: Vehicle Operations
            totalTests++;
            System.out.print("4. Vehicle Operations... ");
            VehicleController vehicleController = new VehicleController();
            List<Vehicle> vehicles = vehicleController.getAllVehicles();
            List<Vehicle> availableVehicles = vehicleController.getAvailableVehicles();
            if (!vehicles.isEmpty() && !availableVehicles.isEmpty()) {
                System.out.println(
                        "✓ PASS (" + vehicles.size() + " vehicles, " + availableVehicles.size() + " available)");
                passedTests++;
            } else {
                System.out.println("✗ FAIL");
            }

            // Test 5: Booking Operations
            totalTests++;
            System.out.print("5. Booking Operations... ");
            if (userLogin && !vehicles.isEmpty()) {
                BookingController bookingController = new BookingController();
                Vehicle testVehicle = vehicles.get(0);
                LocalDate startDate = LocalDate.now().plusDays(15); // Use future dates
                LocalDate endDate = LocalDate.now().plusDays(17);

                boolean bookingCreated = bookingController.createBooking(
                        userController.getCurrentUser(), testVehicle, startDate, endDate, "Test booking");

                if (bookingCreated) {
                    System.out.println("✓ PASS");
                    passedTests++;
                } else {
                    System.out.println("✗ FAIL");
                }
            } else {
                System.out.println("⚠ SKIP (prerequisite failed)");
            }

            // Test 6: Date Validation
            totalTests++;
            System.out.print("6. Date Validation... ");
            try {
                BookingController bookingController = new BookingController();
                // This should throw an exception
                bookingController.createBooking(
                        userController.getCurrentUser(),
                        vehicles.get(0),
                        LocalDate.now().minusDays(1), // Past date
                        LocalDate.now().plusDays(1),
                        "Invalid booking");
                System.out.println("✗ FAIL (should have rejected past date)");
            } catch (IllegalArgumentException e) {
                System.out.println("✓ PASS (correctly rejected past date)");
                passedTests++;
            }

            // Test 7: GUI Classes Compilation
            totalTests++;
            System.out.print("7. GUI Classes Available... ");
            try {
                Class.forName("com.vrs.view.LoginWindow");
                Class.forName("com.vrs.view.UserDashboard");
                Class.forName("com.vrs.view.AdminDashboard");
                System.out.println("✓ PASS");
                passedTests++;
            } catch (ClassNotFoundException e) {
                System.out.println("✗ FAIL");
            }

            // Results Summary
            System.out.println("\n=== Test Results ===");
            System.out.println("Passed: " + passedTests + "/" + totalTests);
            System.out.println("Success Rate: " + (passedTests * 100 / totalTests) + "%");

            if (passedTests == totalTests) {
                System.out.println("\n🎉 ALL TESTS PASSED! System is ready for use.");
                System.out.println("\nTo run the application:");
                System.out.println("  java -cp \"build/classes:lib/*\" com.vrs.view.LoginWindow");
                System.out.println("\nDefault Admin Login:");
                System.out.println("  Username: admin");
                System.out.println("  Password: admin123");
            } else {
                System.out.println("\n⚠️  Some tests failed. Please check the issues above.");
            }

        } catch (Exception e) {
            System.err.println("\n✗ Test error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

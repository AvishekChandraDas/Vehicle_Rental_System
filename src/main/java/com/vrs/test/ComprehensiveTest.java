package com.vrs.test;

import com.vrs.controller.UserController;
import com.vrs.controller.AdminController;
import com.vrs.controller.VehicleController;
import com.vrs.controller.BookingController;
import com.vrs.model.User;
import com.vrs.model.Admin;
import com.vrs.model.Vehicle;
import com.vrs.model.Booking;

import java.time.LocalDate;
import java.util.List;

public class ComprehensiveTest {
    public static void main(String[] args) {
        System.out.println("=== Vehicle Rental System - Comprehensive Test ===");

        try {
            // Test 1: Admin Authentication
            System.out.println("\n1. Testing Admin Authentication...");
            AdminController adminController = new AdminController();
            boolean adminLogin = adminController.loginAdmin("admin", "admin123");
            System.out.println("✓ Admin login: " + (adminLogin ? "SUCCESS" : "FAILED"));

            // Test 2: User Registration
            System.out.println("\n2. Testing User Registration...");
            UserController userController = new UserController();
            String username = "testuser" + System.currentTimeMillis();
            String password = "testpass";
            String firstName = "Test";
            String lastName = "User";
            String email = "test" + System.currentTimeMillis() + "@example.com";
            String phoneNumber = "123-456-7890";
            String licenseNumber = "DL12345";

            boolean userRegistered = userController.registerUser(username, password, firstName, lastName, email,
                    phoneNumber, licenseNumber);
            System.out.println("✓ User registration: " + (userRegistered ? "SUCCESS" : "FAILED"));

            // Test 3: User Authentication
            System.out.println("\n3. Testing User Authentication...");
            boolean userLogin = userController.loginUser(username, password);
            System.out.println("✓ User login: " + (userLogin ? "SUCCESS" : "FAILED"));

            // Test 4: Vehicle Operations
            System.out.println("\n4. Testing Vehicle Operations...");
            VehicleController vehicleController = new VehicleController();
            List<Vehicle> vehicles = vehicleController.getAllVehicles();
            System.out.println("✓ Retrieved " + vehicles.size() + " vehicles");

            List<Vehicle> availableVehicles = vehicleController.getAvailableVehicles();
            System.out.println("✓ Available vehicles: " + availableVehicles.size());

            // Test 5: Booking Operations (if we have vehicles and user)
            if (!vehicles.isEmpty() && userLogin) {
                System.out.println("\n5. Testing Booking Operations...");
                BookingController bookingController = new BookingController();

                Vehicle testVehicle = vehicles.get(0);
                LocalDate startDate = LocalDate.now().plusDays(10); // Use dates further in future to avoid conflicts
                LocalDate endDate = LocalDate.now().plusDays(12);

                boolean bookingCreated = bookingController.createBooking(
                        userController.getCurrentUser(),
                        testVehicle,
                        startDate,
                        endDate,
                        "Test booking");
                System.out.println("✓ Booking creation: " + (bookingCreated ? "SUCCESS" : "FAILED"));

                // Test booking retrieval
                List<Booking> userBookings = bookingController
                        .getUserBookings(userController.getCurrentUser().getUserId());
                System.out.println("✓ User bookings retrieved: " + userBookings.size());

                List<Booking> allBookings = bookingController.getAllBookings();
                System.out.println("✓ All bookings retrieved: " + allBookings.size());
            }

            // Test 6: Date Validation
            System.out.println("\n6. Testing Date Validation...");
            BookingController bookingController = new BookingController();
            try {
                // This should fail - past date
                Vehicle testVehicle = vehicles.get(0);
                bookingController.createBooking(
                        userController.getCurrentUser(),
                        testVehicle,
                        LocalDate.now().minusDays(1), // Past date
                        LocalDate.now().plusDays(1),
                        "Invalid booking");
                System.out.println("✗ Date validation FAILED - past date was accepted");
            } catch (Exception e) {
                System.out.println("✓ Date validation working - correctly rejected past date");
            }

            System.out.println("\n=== All Core Functionality Tests Completed ===");

        } catch (Exception e) {
            System.err.println("✗ Test error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

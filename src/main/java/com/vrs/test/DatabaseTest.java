package com.vrs.test;

import com.vrs.database.DatabaseConnection;
import com.vrs.database.UserDAO;
import com.vrs.database.AdminDAO;
import com.vrs.database.VehicleDAO;
import com.vrs.model.User;
import com.vrs.model.Admin;
import com.vrs.model.Vehicle;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class DatabaseTest {
    public static void main(String[] args) {
        System.out.println("=== Vehicle Rental System - Database Test ===");

        try {
            // Test database connection
            System.out.println("Testing database connection...");
            Connection conn = DatabaseConnection.getConnection();
            System.out.println("✓ Database connected successfully");

            // Test User operations
            System.out.println("\nTesting User operations...");
            UserDAO userDAO = new UserDAO();
            List<User> users = userDAO.getAllUsers();
            System.out.println("✓ Found " + users.size() + " users in database");

            // Test Admin operations
            System.out.println("\nTesting Admin operations...");
            AdminDAO adminDAO = new AdminDAO();
            Admin testAdmin = adminDAO.authenticateAdmin("admin", "admin123");
            if (testAdmin != null) {
                System.out.println("✓ Default admin login working: " + testAdmin.getUsername());
            } else {
                System.out.println("✗ Default admin login failed");
            }

            // Test Vehicle operations
            System.out.println("\nTesting Vehicle operations...");
            VehicleDAO vehicleDAO = new VehicleDAO();
            List<Vehicle> vehicles = vehicleDAO.getAllVehicles();
            System.out.println("✓ Found " + vehicles.size() + " vehicles in database");

            if (!vehicles.isEmpty()) {
                Vehicle firstVehicle = vehicles.get(0);
                System.out.println("  Sample vehicle: " + firstVehicle.toString());
            }

            System.out.println("\n=== All Tests Completed ===");

        } catch (SQLException e) {
            System.err.println("✗ Database error: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("✗ Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

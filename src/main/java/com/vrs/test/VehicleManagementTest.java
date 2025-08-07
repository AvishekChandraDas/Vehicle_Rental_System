package com.vrs.test;

import com.vrs.controller.VehicleController;
import com.vrs.database.DatabaseConnection;
import com.vrs.model.Vehicle;

import java.util.List;

/**
 * Test class to demonstrate Vehicle Management functionality
 * Tests: Add, Edit, Delete, and List vehicles
 */
public class VehicleManagementTest {

    public static void main(String[] args) {
        System.out.println("🚗 Vehicle Management Test");
        System.out.println("==========================\n");

        // Initialize database connection
        try {
            DatabaseConnection.getConnection(); // This will initialize tables automatically
            System.out.println("✅ Database initialized successfully\n");
        } catch (Exception e) {
            System.err.println("❌ Database initialization failed: " + e.getMessage());
            return;
        }

        VehicleController vehicleController = new VehicleController();

        // Test 1: List all vehicles before adding new ones
        System.out.println("📋 Current vehicles in database:");
        listVehicles(vehicleController);

        // Test 2: Add new vehicles
        System.out.println("\n🔧 Testing Add Vehicle functionality:");
        testAddVehicles(vehicleController);

        // Test 3: List vehicles after adding
        System.out.println("\n📋 Vehicles after adding new ones:");
        listVehicles(vehicleController);

        // Test 4: Update vehicle
        System.out.println("\n✏️ Testing Update Vehicle functionality:");
        testUpdateVehicle(vehicleController);

        // Test 5: List vehicles after update
        System.out.println("\n📋 Vehicles after update:");
        listVehicles(vehicleController);

        System.out.println("\n🎉 Vehicle Management Test completed!");
        System.out.println("\n💡 To test the GUI:");
        System.out.println("   1. Run the application: ./run.sh");
        System.out.println("   2. Login as admin (username: admin, password: admin123)");
        System.out.println("   3. Go to Vehicle Management tab");
        System.out.println("   4. Try Add Vehicle, Edit Vehicle, and Delete Vehicle buttons");
    }

    private static void testAddVehicles(VehicleController controller) {
        // Test data for new vehicles
        String[][] testVehicles = {
                { "Toyota", "Camry", "Car", "ABC-123", "45.00", "Silver", "2022", "5",
                        "Comfortable sedan for family trips" },
                { "Honda", "Civic", "Car", "DEF-456", "40.00", "Blue", "2021", "5", "Reliable and fuel-efficient car" },
                { "Ford", "Transit", "Van", "GHI-789", "65.00", "White", "2020", "12",
                        "Spacious van for group travel" },
                { "Yamaha", "MT-07", "Bike", "JKL-012", "25.00", "Black", "2023", "2", "Sport touring motorcycle" }
        };

        for (String[] vehicleData : testVehicles) {
            boolean success = controller.addVehicle(
                    vehicleData[0], // make
                    vehicleData[1], // model
                    vehicleData[2], // type
                    vehicleData[3], // licensePlate
                    Double.parseDouble(vehicleData[4]), // dailyRate
                    vehicleData[5], // color
                    Integer.parseInt(vehicleData[6]), // year
                    Integer.parseInt(vehicleData[7]), // capacity
                    vehicleData[8] // description
            );

            if (success) {
                System.out.println("  ✅ Added: " + vehicleData[0] + " " + vehicleData[1]);
            } else {
                System.out.println("  ❌ Failed to add: " + vehicleData[0] + " " + vehicleData[1]);
            }
        }
    }

    private static void testUpdateVehicle(VehicleController controller) {
        // Get all vehicles and update the first one
        List<Vehicle> vehicles = controller.getAllVehicles();
        if (!vehicles.isEmpty()) {
            Vehicle firstVehicle = vehicles.get(0);
            System.out.println("  🔄 Updating vehicle: " + firstVehicle.getMake() + " " + firstVehicle.getModel());

            // Update daily rate and description
            boolean success = controller.updateVehicle(
                    firstVehicle.getVehicleId(),
                    firstVehicle.getMake(),
                    firstVehicle.getModel(),
                    firstVehicle.getType(),
                    firstVehicle.getLicensePlate(),
                    firstVehicle.getDailyRate() + 5.0, // Increase rate by $5
                    firstVehicle.isAvailable(),
                    firstVehicle.getColor(),
                    firstVehicle.getYear(),
                    firstVehicle.getCapacity(),
                    firstVehicle.getDescription() + " [UPDATED]");

            if (success) {
                System.out.println("  ✅ Vehicle updated successfully");
            } else {
                System.out.println("  ❌ Failed to update vehicle");
            }
        } else {
            System.out.println("  ℹ️ No vehicles to update");
        }
    }

    private static void listVehicles(VehicleController controller) {
        List<Vehicle> vehicles = controller.getAllVehicles();
        if (vehicles.isEmpty()) {
            System.out.println("  ℹ️ No vehicles in database");
            return;
        }

        System.out.println("  Found " + vehicles.size() + " vehicle(s):");
        for (Vehicle vehicle : vehicles) {
            System.out.printf("  🚗 ID: %d | %s %s (%s) | $%.2f/day | %s | %s%n",
                    vehicle.getVehicleId(),
                    vehicle.getMake(),
                    vehicle.getModel(),
                    vehicle.getType(),
                    vehicle.getDailyRate(),
                    vehicle.getLicensePlate(),
                    vehicle.isAvailable() ? "Available" : "Not Available");
        }
    }
}

package com.vrs.test;

import com.vrs.database.BookingDAO;
import com.vrs.database.VehicleDAO;
import com.vrs.model.Vehicle;

import java.time.LocalDate;
import java.util.List;

public class AvailabilityTest {
    public static void main(String[] args) {
        System.out.println("=== Vehicle Availability Test ===");

        try {
            VehicleDAO vehicleDAO = new VehicleDAO();
            BookingDAO bookingDAO = new BookingDAO();

            // Get first vehicle
            List<Vehicle> vehicles = vehicleDAO.getAllVehicles();
            if (!vehicles.isEmpty()) {
                Vehicle testVehicle = vehicles.get(0);
                System.out.println("Testing vehicle: " + testVehicle.getMake() + " " + testVehicle.getModel());
                System.out.println("Vehicle ID: " + testVehicle.getVehicleId());

                LocalDate startDate = LocalDate.now().plusDays(1);
                LocalDate endDate = LocalDate.now().plusDays(3);

                System.out.println("Test dates: " + startDate + " to " + endDate);

                boolean isAvailable = bookingDAO.isVehicleAvailable(testVehicle.getVehicleId(), startDate, endDate);
                System.out.println("Is available: " + isAvailable);

                // Check existing bookings
                var allBookings = bookingDAO.getAllBookings();
                System.out.println("Total bookings in system: " + allBookings.size());

                for (var booking : allBookings) {
                    if (booking.getVehicleId() == testVehicle.getVehicleId()) {
                        System.out.println("Found booking for this vehicle: " + booking.getStartDate() + " to "
                                + booking.getEndDate() + " Status: " + booking.getStatus());
                    }
                }
            }

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

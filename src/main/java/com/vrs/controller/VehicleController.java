package com.vrs.controller;

import com.vrs.database.VehicleDAO;
import com.vrs.model.Vehicle;
import java.sql.SQLException;
import java.util.List;

public class VehicleController {
    private VehicleDAO vehicleDAO;

    public VehicleController() {
        this.vehicleDAO = new VehicleDAO();
    }

    public boolean addVehicle(String make, String model, String type, String licensePlate,
            double dailyRate, String color, int year, int capacity, String description) {
        try {
            validateVehicleInput(make, model, type, licensePlate, dailyRate, year, capacity);

            Vehicle vehicle = new Vehicle();
            vehicle.setMake(make);
            vehicle.setModel(model);
            vehicle.setType(type);
            vehicle.setLicensePlate(licensePlate);
            vehicle.setDailyRate(dailyRate);
            vehicle.setColor(color);
            vehicle.setYear(year);
            vehicle.setCapacity(capacity);
            vehicle.setDescription(description);
            vehicle.setAvailable(true);

            return vehicleDAO.addVehicle(vehicle);
        } catch (SQLException e) {
            System.err.println("Error adding vehicle: " + e.getMessage());
            return false;
        }
    }

    public List<Vehicle> getAllVehicles() {
        try {
            return vehicleDAO.getAllVehicles();
        } catch (SQLException e) {
            System.err.println("Error fetching vehicles: " + e.getMessage());
            return List.of();
        }
    }

    public List<Vehicle> getAvailableVehicles() {
        try {
            return vehicleDAO.getAvailableVehicles();
        } catch (SQLException e) {
            System.err.println("Error fetching available vehicles: " + e.getMessage());
            return List.of();
        }
    }

    public List<Vehicle> getVehiclesByType(String type) {
        try {
            return vehicleDAO.getVehiclesByType(type);
        } catch (SQLException e) {
            System.err.println("Error fetching vehicles by type: " + e.getMessage());
            return List.of();
        }
    }

    public Vehicle getVehicleById(int vehicleId) {
        try {
            return vehicleDAO.getVehicleById(vehicleId);
        } catch (SQLException e) {
            System.err.println("Error fetching vehicle: " + e.getMessage());
            return null;
        }
    }

    public boolean updateVehicle(int vehicleId, String make, String model, String type,
            String licensePlate, double dailyRate, boolean isAvailable,
            String color, int year, int capacity, String description) {
        try {
            validateVehicleInput(make, model, type, licensePlate, dailyRate, year, capacity);

            Vehicle vehicle = new Vehicle();
            vehicle.setVehicleId(vehicleId);
            vehicle.setMake(make);
            vehicle.setModel(model);
            vehicle.setType(type);
            vehicle.setLicensePlate(licensePlate);
            vehicle.setDailyRate(dailyRate);
            vehicle.setAvailable(isAvailable);
            vehicle.setColor(color);
            vehicle.setYear(year);
            vehicle.setCapacity(capacity);
            vehicle.setDescription(description);

            return vehicleDAO.updateVehicle(vehicle);
        } catch (SQLException e) {
            System.err.println("Error updating vehicle: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteVehicle(int vehicleId) {
        try {
            return vehicleDAO.deleteVehicle(vehicleId);
        } catch (SQLException e) {
            System.err.println("Error deleting vehicle: " + e.getMessage());
            return false;
        }
    }

    public boolean updateVehicleAvailability(int vehicleId, boolean isAvailable) {
        try {
            return vehicleDAO.updateVehicleAvailability(vehicleId, isAvailable);
        } catch (SQLException e) {
            System.err.println("Error updating vehicle availability: " + e.getMessage());
            return false;
        }
    }

    public List<Vehicle> searchVehicles(String searchTerm) {
        try {
            if (searchTerm == null || searchTerm.trim().isEmpty()) {
                return getAvailableVehicles();
            }
            return vehicleDAO.searchVehicles(searchTerm.trim());
        } catch (SQLException e) {
            System.err.println("Error searching vehicles: " + e.getMessage());
            return List.of();
        }
    }

    public List<String> getVehicleTypes() {
        return List.of("Car", "Bike", "Van");
    }

    private void validateVehicleInput(String make, String model, String type, String licensePlate,
            double dailyRate, int year, int capacity) {
        if (make == null || make.trim().isEmpty()) {
            throw new IllegalArgumentException("Make cannot be empty");
        }
        if (model == null || model.trim().isEmpty()) {
            throw new IllegalArgumentException("Model cannot be empty");
        }
        if (type == null || type.trim().isEmpty()) {
            throw new IllegalArgumentException("Type cannot be empty");
        }
        if (!getVehicleTypes().contains(type)) {
            throw new IllegalArgumentException("Invalid vehicle type");
        }
        if (licensePlate == null || licensePlate.trim().isEmpty()) {
            throw new IllegalArgumentException("License plate cannot be empty");
        }
        if (dailyRate <= 0) {
            throw new IllegalArgumentException("Daily rate must be greater than 0");
        }
        if (year < 1900 || year > java.time.Year.now().getValue() + 1) {
            throw new IllegalArgumentException("Invalid year");
        }
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than 0");
        }
    }
}

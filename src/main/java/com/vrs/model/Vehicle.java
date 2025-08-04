package com.vrs.model;

import java.time.LocalDate;
import java.util.Objects;

public class Vehicle {
    private int vehicleId;
    private String make;
    private String model;
    private String type; // Car, Bike, Van
    private String licensePlate;
    private double dailyRate;
    private boolean isAvailable;
    private String color;
    private int year;
    private int capacity;
    private String description;

    public Vehicle() {
    }

    public Vehicle(int vehicleId, String make, String model, String type, String licensePlate,
            double dailyRate, boolean isAvailable, String color, int year, int capacity, String description) {
        this.vehicleId = vehicleId;
        this.make = make;
        this.model = model;
        this.type = type;
        this.licensePlate = licensePlate;
        this.dailyRate = dailyRate;
        this.isAvailable = isAvailable;
        this.color = color;
        this.year = year;
        this.capacity = capacity;
        this.description = description;
    }

    // Getters and Setters
    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public double getDailyRate() {
        return dailyRate;
    }

    public void setDailyRate(double dailyRate) {
        this.dailyRate = dailyRate;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format("%s %s (%d) - %s - $%.2f/day",
                make, model, year, type, dailyRate);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Vehicle vehicle = (Vehicle) obj;
        return vehicleId == vehicle.vehicleId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(vehicleId);
    }
}

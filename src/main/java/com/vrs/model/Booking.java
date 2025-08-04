package com.vrs.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Booking {
    private int bookingId;
    private int userId;
    private int vehicleId;
    private LocalDate startDate;
    private LocalDate endDate;
    private double totalAmount;
    private BookingStatus status;
    private LocalDateTime bookingDate;
    private String notes;

    // References to related objects
    private User user;
    private Vehicle vehicle;

    public enum BookingStatus {
        PENDING, CONFIRMED, CANCELLED, COMPLETED
    }

    public Booking() {
        this.bookingDate = LocalDateTime.now();
        this.status = BookingStatus.PENDING;
    }

    public Booking(int bookingId, int userId, int vehicleId, LocalDate startDate, LocalDate endDate,
            double totalAmount, BookingStatus status) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.vehicleId = vehicleId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalAmount = totalAmount;
        this.status = status;
        this.bookingDate = LocalDateTime.now();
    }

    // Calculate total amount based on daily rate and duration
    public void calculateTotalAmount(double dailyRate) {
        if (startDate != null && endDate != null) {
            long days = ChronoUnit.DAYS.between(startDate, endDate) + 1; // +1 to include both start and end dates
            this.totalAmount = days * dailyRate;
        }
    }

    public long getDurationInDays() {
        if (startDate != null && endDate != null) {
            return ChronoUnit.DAYS.between(startDate, endDate) + 1;
        }
        return 0;
    }

    // Getters and Setters
    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public LocalDateTime getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDateTime bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    @Override
    public String toString() {
        return String.format("Booking #%d - %s to %s (%d days) - $%.2f - %s",
                bookingId, startDate, endDate, getDurationInDays(), totalAmount, status);
    }
}

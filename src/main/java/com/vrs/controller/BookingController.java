package com.vrs.controller;

import com.vrs.database.BookingDAO;
import com.vrs.model.Booking;
import com.vrs.model.Booking.BookingStatus;
import com.vrs.model.User;
import com.vrs.model.Vehicle;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class BookingController {
    private BookingDAO bookingDAO;

    public BookingController() {
        this.bookingDAO = new BookingDAO();
    }

    public boolean createBooking(User user, Vehicle vehicle, LocalDate startDate, LocalDate endDate, String notes) {
        try {
            validateBookingInput(startDate, endDate);

            // Check if vehicle is available for the requested dates
            if (!bookingDAO.isVehicleAvailable(vehicle.getVehicleId(), startDate, endDate)) {
                throw new IllegalArgumentException("Vehicle is not available for the selected dates");
            }

            Booking booking = new Booking();
            booking.setUserId(user.getUserId());
            booking.setVehicleId(vehicle.getVehicleId());
            booking.setStartDate(startDate);
            booking.setEndDate(endDate);
            booking.setNotes(notes);
            booking.setStatus(BookingStatus.PENDING);

            // Calculate total amount
            booking.calculateTotalAmount(vehicle.getDailyRate());

            return bookingDAO.createBooking(booking);
        } catch (SQLException e) {
            System.err.println("Error creating booking: " + e.getMessage());
            return false;
        }
    }

    public List<Booking> getUserBookings(int userId) {
        try {
            return bookingDAO.getBookingsByUserId(userId);
        } catch (SQLException e) {
            System.err.println("Error fetching user bookings: " + e.getMessage());
            return List.of();
        }
    }

    public List<Booking> getAllBookings() {
        try {
            return bookingDAO.getAllBookings();
        } catch (SQLException e) {
            System.err.println("Error fetching all bookings: " + e.getMessage());
            return List.of();
        }
    }

    public Booking getBookingById(int bookingId) {
        try {
            return bookingDAO.getBookingById(bookingId);
        } catch (SQLException e) {
            System.err.println("Error fetching booking: " + e.getMessage());
            return null;
        }
    }

    public boolean cancelBooking(int bookingId) {
        try {
            Booking booking = bookingDAO.getBookingById(bookingId);
            if (booking == null) {
                return false;
            }

            // Only allow cancellation for pending or confirmed bookings
            if (booking.getStatus() == BookingStatus.CANCELLED || booking.getStatus() == BookingStatus.COMPLETED) {
                throw new IllegalArgumentException(
                        "Cannot cancel a " + booking.getStatus().name().toLowerCase() + " booking");
            }

            // Check if booking is in the future (allow cancellation up to start date)
            if (booking.getStartDate().isBefore(LocalDate.now())) {
                throw new IllegalArgumentException("Cannot cancel past bookings");
            }

            return bookingDAO.cancelBooking(bookingId);
        } catch (SQLException e) {
            System.err.println("Error cancelling booking: " + e.getMessage());
            return false;
        }
    }

    public boolean confirmBooking(int bookingId) {
        try {
            Booking booking = bookingDAO.getBookingById(bookingId);
            if (booking == null) {
                return false;
            }

            if (booking.getStatus() != BookingStatus.PENDING) {
                throw new IllegalArgumentException("Only pending bookings can be confirmed");
            }

            return bookingDAO.confirmBooking(bookingId);
        } catch (SQLException e) {
            System.err.println("Error confirming booking: " + e.getMessage());
            return false;
        }
    }

    public boolean completeBooking(int bookingId) {
        try {
            return bookingDAO.updateBookingStatus(bookingId, BookingStatus.COMPLETED);
        } catch (SQLException e) {
            System.err.println("Error completing booking: " + e.getMessage());
            return false;
        }
    }

    public boolean isVehicleAvailable(int vehicleId, LocalDate startDate, LocalDate endDate) {
        try {
            return bookingDAO.isVehicleAvailable(vehicleId, startDate, endDate);
        } catch (SQLException e) {
            System.err.println("Error checking vehicle availability: " + e.getMessage());
            return false;
        }
    }

    public List<Booking> getBookingsByDateRange(LocalDate startDate, LocalDate endDate) {
        try {
            return bookingDAO.getBookingsByDateRange(startDate, endDate);
        } catch (SQLException e) {
            System.err.println("Error fetching bookings by date range: " + e.getMessage());
            return List.of();
        }
    }

    public double calculateBookingAmount(Vehicle vehicle, LocalDate startDate, LocalDate endDate) {
        validateBookingInput(startDate, endDate);

        Booking tempBooking = new Booking();
        tempBooking.setStartDate(startDate);
        tempBooking.setEndDate(endDate);
        tempBooking.calculateTotalAmount(vehicle.getDailyRate());

        return tempBooking.getTotalAmount();
    }

    public long calculateBookingDuration(LocalDate startDate, LocalDate endDate) {
        validateBookingInput(startDate, endDate);

        Booking tempBooking = new Booking();
        tempBooking.setStartDate(startDate);
        tempBooking.setEndDate(endDate);

        return tempBooking.getDurationInDays();
    }

    private void validateBookingInput(LocalDate startDate, LocalDate endDate) {
        if (startDate == null) {
            throw new IllegalArgumentException("Start date cannot be null");
        }
        if (endDate == null) {
            throw new IllegalArgumentException("End date cannot be null");
        }
        if (startDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Start date cannot be in the past");
        }
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("End date cannot be before start date");
        }
        if (startDate.isAfter(LocalDate.now().plusYears(1))) {
            throw new IllegalArgumentException("Booking too far in advance (max 1 year)");
        }
    }
}

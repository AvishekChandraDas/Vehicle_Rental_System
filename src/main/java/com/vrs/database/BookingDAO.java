package com.vrs.database;

import com.vrs.model.Booking;
import com.vrs.model.Booking.BookingStatus;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {

    public boolean createBooking(Booking booking) throws SQLException {
        String sql = """
                    INSERT INTO bookings (user_id, vehicle_id, start_date, end_date, total_amount, status, notes)
                    VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, booking.getUserId());
            pstmt.setInt(2, booking.getVehicleId());
            pstmt.setDate(3, Date.valueOf(booking.getStartDate()));
            pstmt.setDate(4, Date.valueOf(booking.getEndDate()));
            pstmt.setDouble(5, booking.getTotalAmount());
            pstmt.setString(6, booking.getStatus().name());
            pstmt.setString(7, booking.getNotes());

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                // Get the last inserted row ID using SQLite-specific function
                try (PreparedStatement idStmt = conn.prepareStatement("SELECT last_insert_rowid()");
                        ResultSet rs = idStmt.executeQuery()) {
                    if (rs.next()) {
                        booking.setBookingId(rs.getInt(1));
                    }
                }
                return true;
            }
        }
        return false;
    }

    public List<Booking> getBookingsByUserId(int userId) throws SQLException {
        List<Booking> bookings = new ArrayList<>();
        String sql = """
                    SELECT b.*, u.username, u.first_name, u.last_name,
                           v.make, v.model, v.type, v.license_plate, v.daily_rate
                    FROM bookings b
                    JOIN users u ON b.user_id = u.user_id
                    JOIN vehicles v ON b.vehicle_id = v.vehicle_id
                    WHERE b.user_id = ?
                    ORDER BY b.booking_date DESC
                """;

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    bookings.add(mapResultSetToBookingWithDetails(rs));
                }
            }
        }
        return bookings;
    }

    public List<Booking> getAllBookings() throws SQLException {
        List<Booking> bookings = new ArrayList<>();
        String sql = """
                    SELECT b.*, u.username, u.first_name, u.last_name,
                           v.make, v.model, v.type, v.license_plate, v.daily_rate
                    FROM bookings b
                    JOIN users u ON b.user_id = u.user_id
                    JOIN vehicles v ON b.vehicle_id = v.vehicle_id
                    ORDER BY b.booking_date DESC
                """;

        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                bookings.add(mapResultSetToBookingWithDetails(rs));
            }
        }
        return bookings;
    }

    public Booking getBookingById(int bookingId) throws SQLException {
        String sql = """
                    SELECT b.*, u.username, u.first_name, u.last_name,
                           v.make, v.model, v.type, v.license_plate, v.daily_rate
                    FROM bookings b
                    JOIN users u ON b.user_id = u.user_id
                    JOIN vehicles v ON b.vehicle_id = v.vehicle_id
                    WHERE b.booking_id = ?
                """;

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, bookingId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToBookingWithDetails(rs);
                }
            }
        }
        return null;
    }

    public boolean updateBookingStatus(int bookingId, BookingStatus status) throws SQLException {
        String sql = "UPDATE bookings SET status = ? WHERE booking_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, status.name());
            pstmt.setInt(2, bookingId);

            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean cancelBooking(int bookingId) throws SQLException {
        return updateBookingStatus(bookingId, BookingStatus.CANCELLED);
    }

    public boolean confirmBooking(int bookingId) throws SQLException {
        return updateBookingStatus(bookingId, BookingStatus.CONFIRMED);
    }

    public boolean isVehicleAvailable(int vehicleId, LocalDate startDate, LocalDate endDate) throws SQLException {
        String sql = """
                    SELECT COUNT(*) FROM bookings
                    WHERE vehicle_id = ?
                    AND status IN ('PENDING', 'CONFIRMED')
                    AND ((start_date <= ? AND end_date >= ?)
                         OR (start_date <= ? AND end_date >= ?)
                         OR (start_date >= ? AND end_date <= ?))
                """;

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, vehicleId);
            pstmt.setDate(2, Date.valueOf(startDate));
            pstmt.setDate(3, Date.valueOf(startDate));
            pstmt.setDate(4, Date.valueOf(endDate));
            pstmt.setDate(5, Date.valueOf(endDate));
            pstmt.setDate(6, Date.valueOf(startDate));
            pstmt.setDate(7, Date.valueOf(endDate));

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) == 0;
                }
            }
        }
        return false;
    }

    public List<Booking> getBookingsByDateRange(LocalDate startDate, LocalDate endDate) throws SQLException {
        List<Booking> bookings = new ArrayList<>();
        String sql = """
                    SELECT b.*, u.username, u.first_name, u.last_name,
                           v.make, v.model, v.type, v.license_plate, v.daily_rate
                    FROM bookings b
                    JOIN users u ON b.user_id = u.user_id
                    JOIN vehicles v ON b.vehicle_id = v.vehicle_id
                    WHERE b.start_date >= ? AND b.end_date <= ?
                    ORDER BY b.start_date
                """;

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDate(1, Date.valueOf(startDate));
            pstmt.setDate(2, Date.valueOf(endDate));

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    bookings.add(mapResultSetToBookingWithDetails(rs));
                }
            }
        }
        return bookings;
    }

    private Booking mapResultSetToBookingWithDetails(ResultSet rs) throws SQLException {
        Booking booking = new Booking();
        booking.setBookingId(rs.getInt("booking_id"));
        booking.setUserId(rs.getInt("user_id"));
        booking.setVehicleId(rs.getInt("vehicle_id"));
        booking.setStartDate(rs.getDate("start_date").toLocalDate());
        booking.setEndDate(rs.getDate("end_date").toLocalDate());
        booking.setTotalAmount(rs.getDouble("total_amount"));
        booking.setStatus(BookingStatus.valueOf(rs.getString("status")));

        Timestamp timestamp = rs.getTimestamp("booking_date");
        if (timestamp != null) {
            booking.setBookingDate(timestamp.toLocalDateTime());
        }

        booking.setNotes(rs.getString("notes"));

        // Create associated User and Vehicle objects
        com.vrs.model.User user = new com.vrs.model.User();
        user.setUserId(rs.getInt("user_id"));
        user.setUsername(rs.getString("username"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        booking.setUser(user);

        com.vrs.model.Vehicle vehicle = new com.vrs.model.Vehicle();
        vehicle.setVehicleId(rs.getInt("vehicle_id"));
        vehicle.setMake(rs.getString("make"));
        vehicle.setModel(rs.getString("model"));
        vehicle.setType(rs.getString("type"));
        vehicle.setLicensePlate(rs.getString("license_plate"));
        vehicle.setDailyRate(rs.getDouble("daily_rate"));
        booking.setVehicle(vehicle);

        return booking;
    }
}

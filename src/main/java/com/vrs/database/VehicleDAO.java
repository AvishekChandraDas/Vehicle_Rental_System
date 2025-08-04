package com.vrs.database;

import com.vrs.model.Vehicle;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehicleDAO {

    public boolean addVehicle(Vehicle vehicle) throws SQLException {
        String sql = """
                    INSERT INTO vehicles (make, model, type, license_plate, daily_rate, is_available,
                    color, year, capacity, description)
                    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, vehicle.getMake());
            pstmt.setString(2, vehicle.getModel());
            pstmt.setString(3, vehicle.getType());
            pstmt.setString(4, vehicle.getLicensePlate());
            pstmt.setDouble(5, vehicle.getDailyRate());
            pstmt.setBoolean(6, vehicle.isAvailable());
            pstmt.setString(7, vehicle.getColor());
            pstmt.setInt(8, vehicle.getYear());
            pstmt.setInt(9, vehicle.getCapacity());
            pstmt.setString(10, vehicle.getDescription());

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                // Get the last inserted row ID using SQLite-specific function
                try (PreparedStatement idStmt = conn.prepareStatement("SELECT last_insert_rowid()");
                        ResultSet rs = idStmt.executeQuery()) {
                    if (rs.next()) {
                        vehicle.setVehicleId(rs.getInt(1));
                    }
                }
                return true;
            }
        }
        return false;
    }

    public List<Vehicle> getAllVehicles() throws SQLException {
        List<Vehicle> vehicles = new ArrayList<>();
        String sql = "SELECT * FROM vehicles ORDER BY make, model";

        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                vehicles.add(mapResultSetToVehicle(rs));
            }
        }
        return vehicles;
    }

    public List<Vehicle> getAvailableVehicles() throws SQLException {
        List<Vehicle> vehicles = new ArrayList<>();
        String sql = "SELECT * FROM vehicles WHERE is_available = 1 ORDER BY make, model";

        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                vehicles.add(mapResultSetToVehicle(rs));
            }
        }
        return vehicles;
    }

    public List<Vehicle> getVehiclesByType(String type) throws SQLException {
        List<Vehicle> vehicles = new ArrayList<>();
        String sql = "SELECT * FROM vehicles WHERE type = ? AND is_available = 1 ORDER BY daily_rate";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, type);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    vehicles.add(mapResultSetToVehicle(rs));
                }
            }
        }
        return vehicles;
    }

    public Vehicle getVehicleById(int vehicleId) throws SQLException {
        String sql = "SELECT * FROM vehicles WHERE vehicle_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, vehicleId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToVehicle(rs);
                }
            }
        }
        return null;
    }

    public boolean updateVehicle(Vehicle vehicle) throws SQLException {
        String sql = """
                    UPDATE vehicles SET make = ?, model = ?, type = ?, license_plate = ?,
                    daily_rate = ?, is_available = ?, color = ?, year = ?, capacity = ?, description = ?
                    WHERE vehicle_id = ?
                """;

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, vehicle.getMake());
            pstmt.setString(2, vehicle.getModel());
            pstmt.setString(3, vehicle.getType());
            pstmt.setString(4, vehicle.getLicensePlate());
            pstmt.setDouble(5, vehicle.getDailyRate());
            pstmt.setBoolean(6, vehicle.isAvailable());
            pstmt.setString(7, vehicle.getColor());
            pstmt.setInt(8, vehicle.getYear());
            pstmt.setInt(9, vehicle.getCapacity());
            pstmt.setString(10, vehicle.getDescription());
            pstmt.setInt(11, vehicle.getVehicleId());

            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean deleteVehicle(int vehicleId) throws SQLException {
        String sql = "DELETE FROM vehicles WHERE vehicle_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, vehicleId);
            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean updateVehicleAvailability(int vehicleId, boolean isAvailable) throws SQLException {
        String sql = "UPDATE vehicles SET is_available = ? WHERE vehicle_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setBoolean(1, isAvailable);
            pstmt.setInt(2, vehicleId);

            return pstmt.executeUpdate() > 0;
        }
    }

    public List<Vehicle> searchVehicles(String searchTerm) throws SQLException {
        List<Vehicle> vehicles = new ArrayList<>();
        String sql = """
                    SELECT * FROM vehicles WHERE
                    (make LIKE ? OR model LIKE ? OR type LIKE ? OR description LIKE ?)
                    AND is_available = 1
                    ORDER BY make, model
                """;

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            String searchPattern = "%" + searchTerm + "%";
            pstmt.setString(1, searchPattern);
            pstmt.setString(2, searchPattern);
            pstmt.setString(3, searchPattern);
            pstmt.setString(4, searchPattern);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    vehicles.add(mapResultSetToVehicle(rs));
                }
            }
        }
        return vehicles;
    }

    private Vehicle mapResultSetToVehicle(ResultSet rs) throws SQLException {
        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleId(rs.getInt("vehicle_id"));
        vehicle.setMake(rs.getString("make"));
        vehicle.setModel(rs.getString("model"));
        vehicle.setType(rs.getString("type"));
        vehicle.setLicensePlate(rs.getString("license_plate"));
        vehicle.setDailyRate(rs.getDouble("daily_rate"));
        vehicle.setAvailable(rs.getBoolean("is_available"));
        vehicle.setColor(rs.getString("color"));
        vehicle.setYear(rs.getInt("year"));
        vehicle.setCapacity(rs.getInt("capacity"));
        vehicle.setDescription(rs.getString("description"));
        return vehicle;
    }
}

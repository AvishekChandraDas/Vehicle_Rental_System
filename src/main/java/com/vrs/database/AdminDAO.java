package com.vrs.database;

import com.vrs.model.Admin;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminDAO {

    public Admin authenticateAdmin(String username, String password) throws SQLException {
        String sql = "SELECT * FROM admins WHERE username = ? AND password = ? AND is_active = 1";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToAdmin(rs);
                }
            }
        }
        return null;
    }

    public boolean addAdmin(Admin admin) throws SQLException {
        String sql = """
                    INSERT INTO admins (username, password, first_name, last_name, email)
                    VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, admin.getUsername());
            pstmt.setString(2, admin.getPassword());
            pstmt.setString(3, admin.getFirstName());
            pstmt.setString(4, admin.getLastName());
            pstmt.setString(5, admin.getEmail());

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        admin.setAdminId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
        }
        return false;
    }

    public List<Admin> getAllAdmins() throws SQLException {
        List<Admin> admins = new ArrayList<>();
        String sql = "SELECT * FROM admins ORDER BY first_name, last_name";

        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                admins.add(mapResultSetToAdmin(rs));
            }
        }
        return admins;
    }

    public Admin getAdminById(int adminId) throws SQLException {
        String sql = "SELECT * FROM admins WHERE admin_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, adminId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToAdmin(rs);
                }
            }
        }
        return null;
    }

    public boolean updateAdmin(Admin admin) throws SQLException {
        String sql = """
                    UPDATE admins SET username = ?, first_name = ?, last_name = ?,
                    email = ?, is_active = ? WHERE admin_id = ?
                """;

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, admin.getUsername());
            pstmt.setString(2, admin.getFirstName());
            pstmt.setString(3, admin.getLastName());
            pstmt.setString(4, admin.getEmail());
            pstmt.setBoolean(5, admin.isActive());
            pstmt.setInt(6, admin.getAdminId());

            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean deleteAdmin(int adminId) throws SQLException {
        String sql = "UPDATE admins SET is_active = 0 WHERE admin_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, adminId);
            return pstmt.executeUpdate() > 0;
        }
    }

    private Admin mapResultSetToAdmin(ResultSet rs) throws SQLException {
        Admin admin = new Admin();
        admin.setAdminId(rs.getInt("admin_id"));
        admin.setUsername(rs.getString("username"));
        admin.setPassword(rs.getString("password"));
        admin.setFirstName(rs.getString("first_name"));
        admin.setLastName(rs.getString("last_name"));
        admin.setEmail(rs.getString("email"));
        admin.setActive(rs.getBoolean("is_active"));
        return admin;
    }
}

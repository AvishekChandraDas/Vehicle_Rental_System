package com.vrs.controller;

import com.vrs.database.AdminDAO;
import com.vrs.model.Admin;
import java.sql.SQLException;
import java.util.List;

public class AdminController {
    private AdminDAO adminDAO;
    private Admin currentAdmin;

    public AdminController() {
        this.adminDAO = new AdminDAO();
    }

    public boolean loginAdmin(String username, String password) {
        try {
            currentAdmin = adminDAO.authenticateAdmin(username, password);
            return currentAdmin != null;
        } catch (SQLException e) {
            System.err.println("Error during admin login: " + e.getMessage());
            return false;
        }
    }

    public void logoutAdmin() {
        currentAdmin = null;
    }

    public Admin getCurrentAdmin() {
        return currentAdmin;
    }

    public boolean isAdminLoggedIn() {
        return currentAdmin != null;
    }

    public boolean addAdmin(String username, String password, String firstName, String lastName, String email) {
        try {
            validateAdminInput(username, password, firstName, lastName, email);

            Admin admin = new Admin();
            admin.setUsername(username);
            admin.setPassword(password); // In a real application, hash the password
            admin.setFirstName(firstName);
            admin.setLastName(lastName);
            admin.setEmail(email);

            return adminDAO.addAdmin(admin);
        } catch (SQLException e) {
            System.err.println("Error adding admin: " + e.getMessage());
            return false;
        }
    }

    public List<Admin> getAllAdmins() {
        try {
            return adminDAO.getAllAdmins();
        } catch (SQLException e) {
            System.err.println("Error fetching admins: " + e.getMessage());
            return List.of();
        }
    }

    public Admin getAdminById(int adminId) {
        try {
            return adminDAO.getAdminById(adminId);
        } catch (SQLException e) {
            System.err.println("Error fetching admin: " + e.getMessage());
            return null;
        }
    }

    public boolean updateAdmin(int adminId, String username, String firstName, String lastName, String email,
            boolean isActive) {
        try {
            validateAdminUpdateInput(username, firstName, lastName, email);

            Admin admin = new Admin();
            admin.setAdminId(adminId);
            admin.setUsername(username);
            admin.setFirstName(firstName);
            admin.setLastName(lastName);
            admin.setEmail(email);
            admin.setActive(isActive);

            boolean success = adminDAO.updateAdmin(admin);

            // Update current admin if it's the same one being updated
            if (success && currentAdmin != null && currentAdmin.getAdminId() == adminId) {
                currentAdmin = adminDAO.getAdminById(adminId);
            }

            return success;
        } catch (SQLException e) {
            System.err.println("Error updating admin: " + e.getMessage());
            return false;
        }
    }

    public boolean deactivateAdmin(int adminId) {
        try {
            // Prevent self-deactivation
            if (currentAdmin != null && currentAdmin.getAdminId() == adminId) {
                throw new IllegalArgumentException("Cannot deactivate your own account");
            }

            return adminDAO.deleteAdmin(adminId);
        } catch (SQLException e) {
            System.err.println("Error deactivating admin: " + e.getMessage());
            return false;
        }
    }

    public boolean updateAdminProfile(String firstName, String lastName, String email) {
        if (currentAdmin == null) {
            return false;
        }

        try {
            validateAdminUpdateInput(currentAdmin.getUsername(), firstName, lastName, email);

            currentAdmin.setFirstName(firstName);
            currentAdmin.setLastName(lastName);
            currentAdmin.setEmail(email);

            boolean success = adminDAO.updateAdmin(currentAdmin);
            if (success) {
                // Refresh current admin data
                currentAdmin = adminDAO.getAdminById(currentAdmin.getAdminId());
            }
            return success;
        } catch (SQLException e) {
            System.err.println("Error updating admin profile: " + e.getMessage());
            return false;
        }
    }

    public boolean changeAdminPassword(String currentPassword, String newPassword) {
        if (currentAdmin == null) {
            return false;
        }

        if (!currentAdmin.getPassword().equals(currentPassword)) {
            throw new IllegalArgumentException("Current password is incorrect");
        }

        if (newPassword == null || newPassword.length() < 6) {
            throw new IllegalArgumentException("New password must be at least 6 characters long");
        }

        try {
            currentAdmin.setPassword(newPassword);
            return adminDAO.updateAdmin(currentAdmin);
        } catch (SQLException e) {
            System.err.println("Error changing admin password: " + e.getMessage());
            return false;
        }
    }

    private void validateAdminInput(String username, String password, String firstName, String lastName, String email) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (password == null || password.length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters long");
        }
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("First name cannot be empty");
        }
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("Last name cannot be empty");
        }
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid email address");
        }
    }

    private void validateAdminUpdateInput(String username, String firstName, String lastName, String email) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("First name cannot be empty");
        }
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("Last name cannot be empty");
        }
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid email address");
        }
    }
}

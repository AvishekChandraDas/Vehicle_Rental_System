package com.vrs.controller;

import com.vrs.database.UserDAO;
import com.vrs.model.User;
import java.sql.SQLException;
import java.util.List;

public class UserController {
    private UserDAO userDAO;
    private User currentUser;

    public UserController() {
        this.userDAO = new UserDAO();
    }

    public boolean registerUser(String username, String password, String firstName, String lastName,
            String email, String phoneNumber, String licenseNumber) {
        try {
            // Check if username is available
            if (!userDAO.isUsernameAvailable(username)) {
                throw new IllegalArgumentException("Username already exists");
            }

            // Check if email is available
            if (!userDAO.isEmailAvailable(email)) {
                throw new IllegalArgumentException("Email already exists");
            }

            // Validate input
            validateUserInput(username, password, firstName, lastName, email);

            User user = new User();
            user.setUsername(username);
            user.setPassword(password); // In a real application, hash the password
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setPhoneNumber(phoneNumber);
            user.setLicenseNumber(licenseNumber);

            return userDAO.registerUser(user);
        } catch (SQLException e) {
            System.err.println("Error registering user: " + e.getMessage());
            return false;
        }
    }

    public boolean loginUser(String username, String password) {
        try {
            currentUser = userDAO.authenticateUser(username, password);
            return currentUser != null;
        } catch (SQLException e) {
            System.err.println("Error during user login: " + e.getMessage());
            return false;
        }
    }

    public void logoutUser() {
        currentUser = null;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public boolean isUserLoggedIn() {
        return currentUser != null;
    }

    public boolean updateUserProfile(String firstName, String lastName, String email,
            String phoneNumber, String licenseNumber) {
        if (currentUser == null) {
            return false;
        }

        try {
            // Check if email is available (excluding current user's email)
            if (!email.equals(currentUser.getEmail()) && !userDAO.isEmailAvailable(email)) {
                throw new IllegalArgumentException("Email already exists");
            }

            currentUser.setFirstName(firstName);
            currentUser.setLastName(lastName);
            currentUser.setEmail(email);
            currentUser.setPhoneNumber(phoneNumber);
            currentUser.setLicenseNumber(licenseNumber);

            boolean success = userDAO.updateUser(currentUser);
            if (success) {
                // Refresh current user data
                currentUser = userDAO.getUserById(currentUser.getUserId());
            }
            return success;
        } catch (SQLException e) {
            System.err.println("Error updating user profile: " + e.getMessage());
            return false;
        }
    }

    public List<User> getAllUsers() {
        try {
            return userDAO.getAllUsers();
        } catch (SQLException e) {
            System.err.println("Error fetching users: " + e.getMessage());
            return List.of();
        }
    }

    public boolean deactivateUser(int userId) {
        try {
            return userDAO.deleteUser(userId);
        } catch (SQLException e) {
            System.err.println("Error deactivating user: " + e.getMessage());
            return false;
        }
    }

    private void validateUserInput(String username, String password, String firstName,
            String lastName, String email) {
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

    public boolean changePassword(String currentPassword, String newPassword) {
        if (currentUser == null) {
            return false;
        }

        if (!currentUser.getPassword().equals(currentPassword)) {
            throw new IllegalArgumentException("Current password is incorrect");
        }

        if (newPassword == null || newPassword.length() < 6) {
            throw new IllegalArgumentException("New password must be at least 6 characters long");
        }

        try {
            currentUser.setPassword(newPassword);
            return userDAO.updateUser(currentUser);
        } catch (SQLException e) {
            System.err.println("Error changing password: " + e.getMessage());
            return false;
        }
    }
}

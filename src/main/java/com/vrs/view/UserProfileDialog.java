package com.vrs.view;

import com.vrs.controller.UserController;
import com.vrs.model.User;
import com.vrs.util.FormatUtils;

import javax.swing.*;
import java.awt.*;

public class UserProfileDialog extends JDialog {
    private User user;
    private UserController userController;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JTextField licenseField;

    public UserProfileDialog(Frame parent, User user, UserController userController) {
        super(parent, "Edit Profile", true);
        this.user = user;
        this.userController = userController;
        initializeUI();
    }

    private void initializeUI() {
        setSize(400, 350);
        setLocationRelativeTo(getParent());
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        JLabel titleLabel = new JLabel("Edit Profile", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Username (read-only)
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        JTextField usernameField = new JTextField(user.getUsername(), 20);
        usernameField.setEditable(false);
        usernameField.setBackground(Color.LIGHT_GRAY);
        formPanel.add(usernameField, gbc);

        // First Name
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(new JLabel("First Name:*"), gbc);
        firstNameField = new JTextField(user.getFirstName(), 20);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(firstNameField, gbc);

        // Last Name
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(new JLabel("Last Name:*"), gbc);
        lastNameField = new JTextField(user.getLastName(), 20);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(lastNameField, gbc);

        // Email
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(new JLabel("Email:*"), gbc);
        emailField = new JTextField(user.getEmail(), 20);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(emailField, gbc);

        // Phone
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(new JLabel("Phone Number:"), gbc);
        phoneField = new JTextField(user.getPhoneNumber() != null ? user.getPhoneNumber() : "", 20);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(phoneField, gbc);

        // License Number
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(new JLabel("License Number:"), gbc);
        licenseField = new JTextField(user.getLicenseNumber() != null ? user.getLicenseNumber() : "", 20);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(licenseField, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton saveButton = new JButton("Save");
        saveButton.setPreferredSize(new Dimension(100, 30));
        saveButton.addActionListener(e -> saveProfile());

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setPreferredSize(new Dimension(100, 30));
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Required fields note
        JLabel noteLabel = new JLabel("* Required fields");
        noteLabel.setFont(new Font("Arial", Font.ITALIC, 10));

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(buttonPanel, BorderLayout.CENTER);
        southPanel.add(noteLabel, BorderLayout.SOUTH);
        mainPanel.add(southPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void saveProfile() {
        try {
            String firstName = firstNameField.getText().trim();
            String lastName = lastNameField.getText().trim();
            String email = emailField.getText().trim();
            String phone = phoneField.getText().trim();
            String license = licenseField.getText().trim();

            // Validate required fields
            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()) {
                showErrorMessage("Please fill in all required fields.");
                return;
            }

            // Validate email format
            if (!FormatUtils.isValidEmail(email)) {
                showErrorMessage("Please enter a valid email address.");
                return;
            }

            // Validate phone number if provided
            if (!phone.isEmpty() && !FormatUtils.isValidPhoneNumber(phone)) {
                showErrorMessage("Please enter a valid phone number.");
                return;
            }

            boolean success = userController.updateUserProfile(firstName, lastName, email, phone, license);

            if (success) {
                showSuccessMessage("Profile updated successfully!");
                dispose();
            } else {
                showErrorMessage("Failed to update profile. Email may already exist.");
            }

        } catch (IllegalArgumentException ex) {
            showErrorMessage(ex.getMessage());
        } catch (Exception ex) {
            showErrorMessage("An error occurred while updating profile: " + ex.getMessage());
        }
    }

    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Profile Update Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showSuccessMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Profile Update Success", JOptionPane.INFORMATION_MESSAGE);
    }
}

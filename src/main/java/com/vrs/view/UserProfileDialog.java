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
        setSize(800, 720);
        setLocationRelativeTo(getParent());
        setResizable(false);

        // Create main panel with gradient background
        JPanel mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, new Color(240, 248, 255), 0, getHeight(),
                        new Color(230, 240, 250));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Title with modern styling
        JLabel titleLabel = new JLabel("Edit Profile", JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(51, 102, 153));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Form panel with better styling
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(180, 200, 220), 2, true),
                        "User Information",
                        0, 0,
                        new Font("Segoe UI", Font.BOLD, 14),
                        new Color(51, 102, 153)),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Helper method to create styled labels
        Font labelFont = new Font("Segoe UI", Font.BOLD, 13);
        Color labelColor = new Color(60, 60, 60);

        // Username (read-only)
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(labelFont);
        usernameLabel.setForeground(labelColor);
        formPanel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JTextField usernameField = new JTextField(user.getUsername(), 25);
        usernameField.setEditable(false);
        usernameField.setBackground(new Color(245, 245, 245));
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        usernameField.setPreferredSize(new Dimension(280, 35));
        usernameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)));
        formPanel.add(usernameField, gbc);

        // First Name
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;
        JLabel firstNameLabel = new JLabel("First Name:*");
        firstNameLabel.setFont(labelFont);
        firstNameLabel.setForeground(labelColor);
        formPanel.add(firstNameLabel, gbc);

        firstNameField = createStyledTextField(user.getFirstName());
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(firstNameField, gbc);

        // Last Name
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;
        JLabel lastNameLabel = new JLabel("Last Name:*");
        lastNameLabel.setFont(labelFont);
        lastNameLabel.setForeground(labelColor);
        formPanel.add(lastNameLabel, gbc);

        lastNameField = createStyledTextField(user.getLastName());
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(lastNameField, gbc);

        // Email
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;
        JLabel emailLabel = new JLabel("Email:*");
        emailLabel.setFont(labelFont);
        emailLabel.setForeground(labelColor);
        formPanel.add(emailLabel, gbc);

        emailField = createStyledTextField(user.getEmail());
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(emailField, gbc);

        // Phone
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;
        JLabel phoneLabel = new JLabel("Phone Number:");
        phoneLabel.setFont(labelFont);
        phoneLabel.setForeground(labelColor);
        formPanel.add(phoneLabel, gbc);

        phoneField = createStyledTextField(user.getPhoneNumber() != null ? user.getPhoneNumber() : "");
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(phoneField, gbc);

        // License Number
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;
        JLabel licenseLabel = new JLabel("License Number:");
        licenseLabel.setFont(labelFont);
        licenseLabel.setForeground(labelColor);
        formPanel.add(licenseLabel, gbc);

        licenseField = createStyledTextField(user.getLicenseNumber() != null ? user.getLicenseNumber() : "");
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(licenseField, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Button panel with modern styling
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        JButton saveButton = createStyledButton("Save", new Color(40, 167, 69), Color.WHITE);
        saveButton.addActionListener(e -> saveProfile());

        JButton cancelButton = createStyledButton("Cancel", new Color(108, 117, 125), Color.WHITE);
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        // Required fields note
        JLabel noteLabel = new JLabel("* Required fields", JLabel.CENTER);
        noteLabel.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        noteLabel.setForeground(new Color(120, 120, 120));

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.setOpaque(false);
        southPanel.add(buttonPanel, BorderLayout.CENTER);
        southPanel.add(noteLabel, BorderLayout.SOUTH);
        mainPanel.add(southPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JTextField createStyledTextField(String text) {
        JTextField field = new JTextField(text, 25);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setPreferredSize(new Dimension(280, 35));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180)),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)));
        field.setBackground(Color.WHITE);

        // Add focus effects
        field.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                field.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(51, 102, 153), 2),
                        BorderFactory.createEmptyBorder(7, 11, 7, 11)));
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                field.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(180, 180, 180)),
                        BorderFactory.createEmptyBorder(8, 12, 8, 12)));
            }
        });

        return field;
    }

    private JButton createStyledButton(String text, Color backgroundColor, Color textColor) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(120, 35));
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setBackground(backgroundColor);
        button.setForeground(textColor);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add hover effects
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            private Color originalColor = backgroundColor;

            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(originalColor.darker());
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(originalColor);
            }
        });

        return button;
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

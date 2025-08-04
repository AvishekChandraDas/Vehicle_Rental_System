package com.vrs.view;

import com.vrs.controller.UserController;
import com.vrs.util.FormatUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserRegistrationWindow extends JDialog {
    private UserController userController;
    private LoginWindow parentWindow;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JTextField licenseField;

    public UserRegistrationWindow(LoginWindow parent) {
        super(parent, "User Registration", true);
        this.parentWindow = parent;
        this.userController = new UserController();
        initializeUI();
    }

    private void initializeUI() {
        setSize(650, 600);
        setLocationRelativeTo(parentWindow);
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        JLabel titleLabel = new JLabel("User Registration", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(60, 120, 180));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);

        // Username
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel usernameLabel = new JLabel("Username:*");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        formPanel.add(usernameLabel, gbc);

        usernameField = new JTextField(30);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 16));
        usernameField.setPreferredSize(new Dimension(350, 45));
        usernameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLoweredBevelBorder(),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(usernameField, gbc);

        // Password
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel passwordLabel = new JLabel("Password:*");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        formPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField(30);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordField.setPreferredSize(new Dimension(350, 45));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLoweredBevelBorder(),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(passwordField, gbc);

        // Confirm Password
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:*");
        confirmPasswordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        formPanel.add(confirmPasswordLabel, gbc);

        confirmPasswordField = new JPasswordField(30);
        confirmPasswordField.setFont(new Font("Arial", Font.PLAIN, 16));
        confirmPasswordField.setPreferredSize(new Dimension(350, 45));
        confirmPasswordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLoweredBevelBorder(),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(confirmPasswordField, gbc);

        // First Name
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel firstNameLabel = new JLabel("First Name:*");
        firstNameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        formPanel.add(firstNameLabel, gbc);

        firstNameField = new JTextField(30);
        firstNameField.setFont(new Font("Arial", Font.PLAIN, 16));
        firstNameField.setPreferredSize(new Dimension(350, 45));
        firstNameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLoweredBevelBorder(),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(firstNameField, gbc);

        // Last Name
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel lastNameLabel = new JLabel("Last Name:*");
        lastNameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        formPanel.add(lastNameLabel, gbc);

        lastNameField = new JTextField(30);
        lastNameField.setFont(new Font("Arial", Font.PLAIN, 16));
        lastNameField.setPreferredSize(new Dimension(350, 45));
        lastNameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLoweredBevelBorder(),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(lastNameField, gbc);

        // Email
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel emailLabel = new JLabel("Email:*");
        emailLabel.setFont(new Font("Arial", Font.BOLD, 14));
        formPanel.add(emailLabel, gbc);

        emailField = new JTextField(30);
        emailField.setFont(new Font("Arial", Font.PLAIN, 16));
        emailField.setPreferredSize(new Dimension(350, 45));
        emailField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLoweredBevelBorder(),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(emailField, gbc);

        // Phone
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel phoneLabel = new JLabel("Phone Number:");
        phoneLabel.setFont(new Font("Arial", Font.BOLD, 14));
        formPanel.add(phoneLabel, gbc);

        phoneField = new JTextField(30);
        phoneField.setFont(new Font("Arial", Font.PLAIN, 16));
        phoneField.setPreferredSize(new Dimension(350, 45));
        phoneField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLoweredBevelBorder(),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(phoneField, gbc);

        // License Number
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel licenseLabel = new JLabel("License Number:");
        licenseLabel.setFont(new Font("Arial", Font.BOLD, 14));
        formPanel.add(licenseLabel, gbc);

        licenseField = new JTextField(30);
        licenseField.setFont(new Font("Arial", Font.PLAIN, 16));
        licenseField.setPreferredSize(new Dimension(350, 45));
        licenseField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLoweredBevelBorder(),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(licenseField, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton registerButton = new JButton("Register");
        registerButton.setFont(new Font("Arial", Font.BOLD, 14));
        registerButton.setPreferredSize(new Dimension(120, 40));
        registerButton.setBackground(new Color(40, 150, 80));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);
        registerButton.addActionListener(new RegisterActionListener());

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Arial", Font.BOLD, 14));
        cancelButton.setPreferredSize(new Dimension(120, 40));
        cancelButton.setBackground(new Color(180, 60, 60));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFocusPainted(false);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        buttonPanel.add(registerButton);
        buttonPanel.add(cancelButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Required fields note
        JLabel noteLabel = new JLabel("* Required fields", JLabel.CENTER);
        noteLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        noteLabel.setForeground(new Color(100, 100, 100));
        noteLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

        // Create a separate panel for the note
        JPanel notePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        notePanel.add(noteLabel);
        mainPanel.add(notePanel, BorderLayout.PAGE_END);

        add(mainPanel);

        // Enter key support
        getRootPane().setDefaultButton(registerButton);
    }

    private class RegisterActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                // Get form data
                String username = usernameField.getText().trim();
                String password = new String(passwordField.getPassword());
                String confirmPassword = new String(confirmPasswordField.getPassword());
                String firstName = firstNameField.getText().trim();
                String lastName = lastNameField.getText().trim();
                String email = emailField.getText().trim();
                String phone = phoneField.getText().trim();
                String license = licenseField.getText().trim();

                // Validate required fields
                if (username.isEmpty() || password.isEmpty() || firstName.isEmpty() ||
                        lastName.isEmpty() || email.isEmpty()) {
                    showErrorMessage("Please fill in all required fields.");
                    return;
                }

                // Validate password confirmation
                if (!password.equals(confirmPassword)) {
                    showErrorMessage("Passwords do not match.");
                    confirmPasswordField.setText("");
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

                // Attempt registration
                boolean success = userController.registerUser(username, password, firstName,
                        lastName, email, phone, license);

                if (success) {
                    showSuccessMessage("Registration successful! You can now log in.");
                    parentWindow.refreshAfterRegistration();
                    dispose();
                } else {
                    showErrorMessage("Registration failed. Username or email may already exist.");
                }

            } catch (IllegalArgumentException ex) {
                showErrorMessage(ex.getMessage());
            } catch (Exception ex) {
                showErrorMessage("An error occurred during registration: " + ex.getMessage());
            }
        }
    }

    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Registration Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showSuccessMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Registration Success", JOptionPane.INFORMATION_MESSAGE);
    }
}

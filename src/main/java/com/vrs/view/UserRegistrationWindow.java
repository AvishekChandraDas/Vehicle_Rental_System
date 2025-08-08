package com.vrs.view;

import com.vrs.controller.UserController;
import com.vrs.util.FormatUtils;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.ParseException;
import java.util.regex.Pattern;

public class UserRegistrationWindow extends JDialog {
    // UI Components
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField emailField;
    private JFormattedTextField phoneField;
    private JTextField licenseField;

    // Validation labels
    private JLabel usernameValidation;
    private JLabel passwordValidation;
    private JLabel confirmPasswordValidation;
    private JLabel firstNameValidation;
    private JLabel lastNameValidation;
    private JLabel emailValidation;
    private JLabel phoneValidation;

    private JProgressBar passwordStrengthBar;
    private JLabel passwordStrengthLabel;

    // Controllers
    private UserController userController;
    private LoginWindow parentWindow;

    // Colors for validation
    private static final Color SUCCESS_COLOR = new Color(34, 139, 34);
    private static final Color ERROR_COLOR = new Color(220, 20, 60);
    private static final Color WARNING_COLOR = new Color(255, 140, 0);

    // Validation patterns
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9_]{3,20}$");
    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z\\s'-]{2,50}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\(\\d{3}\\) \\d{3}-\\d{4}$");
    private static final Pattern LICENSE_PATTERN = Pattern.compile("^[A-Z0-9]{6,15}$");

    public UserRegistrationWindow(LoginWindow parent) {
        super(parent, "User Registration", true);
        this.parentWindow = parent;
        this.userController = new UserController();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("User Registration - Smart Vehicle Rental System");
        setSize(700, 800);
        setLocationRelativeTo(getParent());
        setResizable(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Initialize form fields first
        initializeFormFields();

        // Main container with proper spacing
        JPanel mainPanel = new JPanel(new BorderLayout(0, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(245, 248, 252));

        // Header panel
        JPanel headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Form panel with improved layout
        JPanel formPanel = createFormPanel();
        JScrollPane scrollPane = new JScrollPane(formPanel);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Button panel with modern styling
        JPanel buttonPanel = createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Initialize validation
        initializeValidation();

        // Set focus to username field
        SwingUtilities.invokeLater(() -> usernameField.requestFocusInWindow());
    }

    private void initializeFormFields() {
        // Initialize text fields
        usernameField = new JTextField(20);
        usernameField.setPreferredSize(new Dimension(350, 35));
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        firstNameField = new JTextField(20);
        firstNameField.setPreferredSize(new Dimension(350, 35));
        firstNameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        lastNameField = new JTextField(20);
        lastNameField.setPreferredSize(new Dimension(350, 35));
        lastNameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        emailField = new JTextField(20);
        emailField.setPreferredSize(new Dimension(350, 35));
        emailField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        licenseField = new JTextField(20);
        licenseField.setPreferredSize(new Dimension(350, 35));
        licenseField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // Initialize password fields
        passwordField = new JPasswordField(20);
        passwordField.setPreferredSize(new Dimension(350, 35));
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        confirmPasswordField = new JPasswordField(20);
        confirmPasswordField.setPreferredSize(new Dimension(350, 35));
        confirmPasswordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // Initialize phone field with formatting
        try {
            MaskFormatter phoneFormatter = new MaskFormatter("(###) ###-####");
            phoneFormatter.setPlaceholderCharacter('_');
            phoneField = new JFormattedTextField(phoneFormatter);
        } catch (ParseException e) {
            phoneField = new JFormattedTextField();
        }
        phoneField.setPreferredSize(new Dimension(350, 35));
        phoneField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // Set borders for all fields
        Border fieldBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12));

        usernameField.setBorder(fieldBorder);
        firstNameField.setBorder(fieldBorder);
        lastNameField.setBorder(fieldBorder);
        emailField.setBorder(fieldBorder);
        licenseField.setBorder(fieldBorder);
        passwordField.setBorder(fieldBorder);
        confirmPasswordField.setBorder(fieldBorder);
        phoneField.setBorder(fieldBorder);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(245, 248, 252));

        JLabel titleLabel = new JLabel("Create Your Account");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(44, 62, 80));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel subtitleLabel = new JLabel("Join us to start your vehicle rental journey");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(127, 140, 141));
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(subtitleLabel, BorderLayout.SOUTH);

        return headerPanel;
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Account Information"),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 2, 5); // Reduced bottom inset for field rows

        int row = 0;

        // Username field
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.3;
        formPanel.add(createLabel("Username *"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        formPanel.add(usernameField, gbc);

        // Username validation (separate row with minimal spacing)
        row++;
        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.weightx = 0.7;
        gbc.insets = new Insets(0, 5, 8, 5); // No top margin, bottom margin for separation
        usernameValidation = createValidationLabel();
        formPanel.add(usernameValidation, gbc);

        // Password field
        row++;
        gbc.insets = new Insets(5, 5, 2, 5); // Reset to field spacing
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.3;
        formPanel.add(createLabel("Password *"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        formPanel.add(passwordField, gbc);

        // Password validation (separate row)
        row++;
        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.weightx = 0.7;
        gbc.insets = new Insets(0, 5, 2, 5);
        passwordValidation = createValidationLabel();
        formPanel.add(passwordValidation, gbc);

        // Password strength bar (separate row)
        row++;
        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.weightx = 0.7;
        gbc.insets = new Insets(0, 5, 8, 5);
        passwordStrengthBar = new JProgressBar(0, 100);
        passwordStrengthBar.setStringPainted(true);
        passwordStrengthBar.setString("Password Strength");
        passwordStrengthBar.setPreferredSize(new Dimension(350, 20));
        formPanel.add(passwordStrengthBar, gbc);

        // Confirm Password field
        row++;
        gbc.insets = new Insets(5, 5, 2, 5);
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.3;
        formPanel.add(createLabel("Confirm Password *"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        formPanel.add(confirmPasswordField, gbc);

        // Confirm password validation (separate row)
        row++;
        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.weightx = 0.7;
        gbc.insets = new Insets(0, 5, 8, 5);
        confirmPasswordValidation = createValidationLabel();
        formPanel.add(confirmPasswordValidation, gbc);

        // First Name field
        row++;
        gbc.insets = new Insets(5, 5, 2, 5);
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.3;
        formPanel.add(createLabel("First Name *"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        formPanel.add(firstNameField, gbc);

        // First name validation (separate row)
        row++;
        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.weightx = 0.7;
        gbc.insets = new Insets(0, 5, 8, 5);
        firstNameValidation = createValidationLabel();
        formPanel.add(firstNameValidation, gbc);

        // Last Name field
        row++;
        gbc.insets = new Insets(5, 5, 2, 5);
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.3;
        formPanel.add(createLabel("Last Name *"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        formPanel.add(lastNameField, gbc);

        // Last name validation (separate row)
        row++;
        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.weightx = 0.7;
        gbc.insets = new Insets(0, 5, 8, 5);
        lastNameValidation = createValidationLabel();
        formPanel.add(lastNameValidation, gbc);

        // Email field
        row++;
        gbc.insets = new Insets(5, 5, 2, 5);
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.3;
        formPanel.add(createLabel("Email Address *"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        formPanel.add(emailField, gbc);

        // Email validation (separate row)
        row++;
        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.weightx = 0.7;
        gbc.insets = new Insets(0, 5, 8, 5);
        emailValidation = createValidationLabel();
        formPanel.add(emailValidation, gbc);

        // Phone Number field
        row++;
        gbc.insets = new Insets(5, 5, 2, 5);
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.3;
        formPanel.add(createLabel("Phone Number"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        formPanel.add(phoneField, gbc);

        // License Number field
        row++;
        gbc.insets = new Insets(8, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.3;
        formPanel.add(createLabel("License Number"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        formPanel.add(licenseField, gbc);

        return formPanel;
    }

    private JLabel createValidationLabel() {
        JLabel label = new JLabel(" ");
        label.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        label.setPreferredSize(new Dimension(400, 18));
        label.setMinimumSize(new Dimension(400, 18));
        label.setMaximumSize(new Dimension(400, 18));
        label.setVerticalAlignment(SwingConstants.TOP);
        return label;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        return label;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(new Color(245, 248, 252));

        JButton createButton = new JButton("Create Account");
        createButton.setPreferredSize(new Dimension(140, 40));
        createButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        createButton.setBackground(new Color(52, 152, 219));
        createButton.setForeground(Color.WHITE);
        createButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        createButton.setFocusPainted(false);
        createButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        createButton.addActionListener(this::handleRegistration);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setPreferredSize(new Dimension(140, 40));
        cancelButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cancelButton.setBackground(new Color(236, 240, 241));
        cancelButton.setForeground(new Color(44, 62, 80));
        cancelButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
                BorderFactory.createEmptyBorder(9, 19, 9, 19)));
        cancelButton.setFocusPainted(false);
        cancelButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(createButton);
        buttonPanel.add(cancelButton);

        return buttonPanel;
    }

    private void initializeValidation() {
        // Add validation for username
        addFieldValidator(usernameField, usernameValidation, text -> {
            if (text.isEmpty()) {
                return new ValidationResult(false, "Username is required");
            }
            if (!USERNAME_PATTERN.matcher(text).matches()) {
                return new ValidationResult(false,
                        "Username must be 3-20 characters (letters, numbers, underscore only)");
            }
            return new ValidationResult(true, "✓ Username is available");
        });

        // Add validation for first name
        addFieldValidator(firstNameField, firstNameValidation, text -> {
            if (text.isEmpty()) {
                return new ValidationResult(false, "First name is required");
            }
            if (!NAME_PATTERN.matcher(text).matches()) {
                return new ValidationResult(false,
                        "First name must be 2-50 characters (letters, spaces, apostrophes, hyphens only)");
            }
            return new ValidationResult(true, "✓ Valid name");
        });

        // Add validation for last name
        addFieldValidator(lastNameField, lastNameValidation, text -> {
            if (text.isEmpty()) {
                return new ValidationResult(false, "Last name is required");
            }
            if (!NAME_PATTERN.matcher(text).matches()) {
                return new ValidationResult(false,
                        "Last name must be 2-50 characters (letters, spaces, apostrophes, hyphens only)");
            }
            return new ValidationResult(true, "✓ Valid name");
        });

        // Add validation for email
        addFieldValidator(emailField, emailValidation, text -> {
            if (text.isEmpty()) {
                return new ValidationResult(false, "Email is required");
            }
            if (!EMAIL_PATTERN.matcher(text).matches()) {
                return new ValidationResult(false, "Please enter a valid email address");
            }
            return new ValidationResult(true, "✓ Valid email");
        });

        // Add password validation with strength indicator
        addPasswordStrengthValidator(passwordField, passwordValidation, passwordStrengthBar);

        // Add confirm password validation
        addPasswordConfirmValidator(confirmPasswordField, confirmPasswordValidation);
    }

    // Functional interface for field validation
    @FunctionalInterface
    private interface FieldValidator {
        ValidationResult validate(String text);
    }

    // Validation result class
    private static class ValidationResult {
        final boolean isValid;
        final String message;

        ValidationResult(boolean isValid, String message) {
            this.isValid = isValid;
            this.message = message;
        }
    }

    private void addFieldValidator(JTextField field, JLabel validationLabel, FieldValidator validator) {
        field.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                validateField();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                validateField();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                validateField();
            }

            private void validateField() {
                SwingUtilities.invokeLater(() -> {
                    ValidationResult result = validator.validate(field.getText());
                    updateValidationLabel(validationLabel, result);
                });
            }
        });
    }

    private void addPasswordStrengthValidator(JPasswordField field, JLabel validationLabel, JProgressBar strengthBar) {
        field.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                validatePassword();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                validatePassword();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                validatePassword();
            }

            private void validatePassword() {
                SwingUtilities.invokeLater(() -> {
                    String password = new String(field.getPassword());
                    if (password.isEmpty()) {
                        validationLabel.setText("Password is required");
                        validationLabel.setForeground(ERROR_COLOR);
                        strengthBar.setValue(0);
                        strengthBar.setString("Password Strength");
                        return;
                    }

                    if (password.length() < 6) {
                        validationLabel.setText("Password must be at least 6 characters");
                        validationLabel.setForeground(ERROR_COLOR);
                        strengthBar.setValue(0);
                        strengthBar.setString("Too Short");
                        return;
                    }

                    int strength = calculatePasswordStrength(password);
                    strengthBar.setValue(strength);

                    if (strength < 25) {
                        validationLabel.setText("Password strength: Weak");
                        validationLabel.setForeground(ERROR_COLOR);
                        strengthBar.setString("Weak");
                        strengthBar.setForeground(ERROR_COLOR);
                    } else if (strength < 50) {
                        validationLabel.setText("Password strength: Fair");
                        validationLabel.setForeground(WARNING_COLOR);
                        strengthBar.setString("Fair");
                        strengthBar.setForeground(WARNING_COLOR);
                    } else if (strength < 75) {
                        validationLabel.setText("Password strength: Good");
                        validationLabel.setForeground(new Color(255, 193, 7));
                        strengthBar.setString("Good");
                        strengthBar.setForeground(new Color(255, 193, 7));
                    } else {
                        validationLabel.setText("✓ Password strength: Strong");
                        validationLabel.setForeground(SUCCESS_COLOR);
                        strengthBar.setString("Strong");
                        strengthBar.setForeground(SUCCESS_COLOR);
                    }
                });
            }
        });
    }

    private void addPasswordConfirmValidator(JPasswordField field, JLabel validationLabel) {
        DocumentListener validator = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                validateConfirmPassword();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                validateConfirmPassword();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                validateConfirmPassword();
            }

            private void validateConfirmPassword() {
                SwingUtilities.invokeLater(() -> {
                    String password = new String(passwordField.getPassword());
                    String confirmPassword = new String(field.getPassword());

                    if (confirmPassword.isEmpty()) {
                        validationLabel.setText("Please confirm your password");
                        validationLabel.setForeground(ERROR_COLOR);
                    } else if (!password.equals(confirmPassword)) {
                        validationLabel.setText("✗ Passwords do not match");
                        validationLabel.setForeground(ERROR_COLOR);
                    } else {
                        validationLabel.setText("✓ Passwords match");
                        validationLabel.setForeground(SUCCESS_COLOR);
                    }
                });
            }
        };

        field.getDocument().addDocumentListener(validator);
        passwordField.getDocument().addDocumentListener(validator);
    }

    private int calculatePasswordStrength(String password) {
        int strength = 0;

        // Length bonus
        if (password.length() >= 8)
            strength += 25;
        else if (password.length() >= 6)
            strength += 10;

        if (password.length() >= 12)
            strength += 10;

        // Character variety bonus
        if (password.matches(".*[a-z].*"))
            strength += 5;
        if (password.matches(".*[A-Z].*"))
            strength += 5;
        if (password.matches(".*[0-9].*"))
            strength += 10;
        if (password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*"))
            strength += 15;

        // Bonus for mixed case and numbers
        if (password.matches(".*[a-z].*") && password.matches(".*[A-Z].*"))
            strength += 5;
        if (password.matches(".*[0-9].*") && password.matches(".*[a-zA-Z].*"))
            strength += 5;

        // Penalty for common patterns
        if (password.toLowerCase().matches(".*(password|123456|qwerty).*"))
            strength -= 20;
        if (password.matches(".*([a-zA-Z0-9])\\1{2,}.*"))
            strength -= 10; // Repeated characters

        return Math.max(0, Math.min(100, strength));
    }

    private void updateValidationLabel(JLabel label, ValidationResult result) {
        label.setText(result.message);
        label.setForeground(result.isValid ? SUCCESS_COLOR : ERROR_COLOR);
    }

    private void handleRegistration(ActionEvent e) {
        if (validateForm()) {
            createUser();
        }
    }

    private boolean validateForm() {
        boolean isValid = true;
        StringBuilder errors = new StringBuilder();

        // Validate required fields
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String email = emailField.getText().trim();

        if (username.isEmpty() || !USERNAME_PATTERN.matcher(username).matches()) {
            errors.append("• Invalid username\n");
            isValid = false;
        }

        if (password.length() < 6) {
            errors.append("• Password must be at least 6 characters\n");
            isValid = false;
        }

        if (!password.equals(confirmPassword)) {
            errors.append("• Passwords do not match\n");
            isValid = false;
        }

        if (firstName.isEmpty() || !NAME_PATTERN.matcher(firstName).matches()) {
            errors.append("• Invalid first name\n");
            isValid = false;
        }

        if (lastName.isEmpty() || !NAME_PATTERN.matcher(lastName).matches()) {
            errors.append("• Invalid last name\n");
            isValid = false;
        }

        if (email.isEmpty() || !EMAIL_PATTERN.matcher(email).matches()) {
            errors.append("• Invalid email address\n");
            isValid = false;
        }

        if (!isValid) {
            JOptionPane.showMessageDialog(this,
                    "Please correct the following errors:\n\n" + errors.toString(),
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        return isValid;
    }

    private void createUser() {
        try {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());
            String firstName = firstNameField.getText().trim();
            String lastName = lastNameField.getText().trim();
            String email = emailField.getText().trim();
            String phone = phoneField.getText().trim();
            String license = licenseField.getText().trim();

            // Create user through controller
            boolean success = userController.registerUser(username, password, firstName, lastName, email, phone,
                    license);

            if (success) {
                JOptionPane.showMessageDialog(this,
                        "Account created successfully!\n\nYou can now log in with your credentials.",
                        "Registration Successful",
                        JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Registration failed. Username may already exist.",
                        "Registration Error",
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "An error occurred during registration: " + ex.getMessage(),
                    "Registration Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}

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
    private UserController userController;
    private LoginWindow parentWindow;

    // Form fields
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
    private JLabel emailValidation;
    private JLabel phoneValidation;

    // Password strength components
    private JProgressBar passwordStrengthBar;
    private JLabel passwordStrengthLabel;

    // Colors for validation
    private static final Color SUCCESS_COLOR = new Color(34, 139, 34);
    private static final Color ERROR_COLOR = new Color(220, 20, 60);
    private static final Color WARNING_COLOR = new Color(255, 140, 0);
    private static final Color NEUTRAL_COLOR = new Color(128, 128, 128);

    // Regex patterns for validation
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9_]{3,20}$");
    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z\\s'-]{2,50}$");
    private static final Pattern LICENSE_PATTERN = Pattern.compile("^[A-Z0-9]{6,15}$");

    public UserRegistrationWindow(LoginWindow parent) {
        super(parent, "User Registration", true);
        this.parentWindow = parent;
        this.userController = new UserController();
        initializeUI();
    }

    private void initializeUI() {
        setSize(700, 750);
        setLocationRelativeTo(parentWindow);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Main panel with modern styling
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // Header panel with gradient-like effect
        JPanel headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Form panel with improved layout
        JPanel formPanel = createImprovedFormPanel();
        JScrollPane scrollPane = new JScrollPane(formPanel);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Button panel with modern styling
        JPanel buttonPanel = createImprovedButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Set default button
        getRootPane().setDefaultButton((JButton) buttonPanel.getComponent(0));
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));

        // Title with modern styling
        JLabel titleLabel = new JLabel("Create Your Account", JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(45, 52, 54));

        // Subtitle
        JLabel subtitleLabel = new JLabel("Join us to start your vehicle rental journey", JLabel.CENTER);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(99, 110, 114));

        headerPanel.add(titleLabel, BorderLayout.NORTH);
        headerPanel.add(subtitleLabel, BorderLayout.CENTER);

        return headerPanel;
    }

    private JPanel createImprovedFormPanel() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;

        int row = 0;

        // Username field with validation
        addFormField(formPanel, gbc, row++, "Username", true,
                usernameField = createStyledTextField(25),
                usernameValidation = createValidationLabel());
        addFieldValidator(usernameField, usernameValidation, this::validateUsername);

        // Password field with strength indicator
        addFormField(formPanel, gbc, row++, "Password", true,
                passwordField = createStyledPasswordField(25),
                passwordValidation = createValidationLabel());

        // Password strength indicator
        gbc.gridx = 1;
        gbc.gridy = row++;
        passwordStrengthBar = new JProgressBar(0, 100);
        passwordStrengthBar.setStringPainted(true);
        passwordStrengthBar.setPreferredSize(new Dimension(300, 6));
        formPanel.add(passwordStrengthBar, gbc);

        gbc.gridy = row++;
        passwordStrengthLabel = new JLabel("Password strength: Weak");
        passwordStrengthLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        passwordStrengthLabel.setForeground(NEUTRAL_COLOR);
        formPanel.add(passwordStrengthLabel, gbc);

        addPasswordStrengthValidator(passwordField, passwordValidation, passwordStrengthBar, passwordStrengthLabel);

        // Confirm password field
        addFormField(formPanel, gbc, row++, "Confirm Password", true,
                confirmPasswordField = createStyledPasswordField(25),
                confirmPasswordValidation = createValidationLabel());
        addPasswordConfirmValidator(confirmPasswordField, confirmPasswordValidation);

        // First name field
        addFormField(formPanel, gbc, row++, "First Name", true,
                firstNameField = createStyledTextField(25),
                createValidationLabel());
        addFieldValidator(firstNameField, (JLabel) formPanel.getComponent(formPanel.getComponentCount() - 1),
                this::validateName);

        // Last name field
        addFormField(formPanel, gbc, row++, "Last Name", true,
                lastNameField = createStyledTextField(25),
                createValidationLabel());
        addFieldValidator(lastNameField, (JLabel) formPanel.getComponent(formPanel.getComponentCount() - 1),
                this::validateName);

        // Email field
        addFormField(formPanel, gbc, row++, "Email Address", true,
                emailField = createStyledTextField(25),
                emailValidation = createValidationLabel());
        addFieldValidator(emailField, emailValidation, this::validateEmail);

        // Phone field with formatting
        try {
            MaskFormatter phoneFormatter = new MaskFormatter("(###) ###-####");
            phoneFormatter.setPlaceholderCharacter('_');
            phoneField = new JFormattedTextField(phoneFormatter);
        } catch (ParseException e) {
            phoneField = new JFormattedTextField();
        }
        styleFormattedTextField(phoneField);
        addFormField(formPanel, gbc, row++, "Phone Number", false,
                phoneField,
                phoneValidation = createValidationLabel());
        addFieldValidator(phoneField, phoneValidation, this::validatePhone);

        // License number field
        addFormField(formPanel, gbc, row++, "License Number", false,
                licenseField = createStyledTextField(25),
                createValidationLabel());
        addFieldValidator(licenseField, (JLabel) formPanel.getComponent(formPanel.getComponentCount() - 1),
                this::validateLicense);

        return formPanel;
    }

    private void addFormField(JPanel panel, GridBagConstraints gbc, int row, String labelText,
            boolean required, JComponent field, JLabel validationLabel) {
        // Label
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;

        JLabel label = new JLabel(labelText + (required ? " *" : ""));
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setForeground(new Color(45, 52, 54));
        panel.add(label, gbc);

        // Field
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        panel.add(field, gbc);

        // Validation label
        gbc.gridy = row + 1;
        panel.add(validationLabel, gbc);
    }

    private JTextField createStyledTextField(int columns) {
        JTextField field = new JTextField(columns);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setPreferredSize(new Dimension(300, 40));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 221, 225), 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)));

        // Add focus styling
        field.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(74, 144, 226), 2),
                        BorderFactory.createEmptyBorder(7, 11, 7, 11)));
            }

            @Override
            public void focusLost(FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(220, 221, 225), 1),
                        BorderFactory.createEmptyBorder(8, 12, 8, 12)));
            }
        });

        return field;
    }

    private JPasswordField createStyledPasswordField(int columns) {
        JPasswordField field = new JPasswordField(columns);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setPreferredSize(new Dimension(300, 40));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 221, 225), 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)));

        // Add focus styling
        field.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(74, 144, 226), 2),
                        BorderFactory.createEmptyBorder(7, 11, 7, 11)));
            }

            @Override
            public void focusLost(FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(220, 221, 225), 1),
                        BorderFactory.createEmptyBorder(8, 12, 8, 12)));
            }
        });

        return field;
    }

    private void styleFormattedTextField(JFormattedTextField field) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setPreferredSize(new Dimension(300, 40));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 221, 225), 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)));

        // Add focus styling
        field.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(74, 144, 226), 2),
                        BorderFactory.createEmptyBorder(7, 11, 7, 11)));
            }

            @Override
            public void focusLost(FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(220, 221, 225), 1),
                        BorderFactory.createEmptyBorder(8, 12, 8, 12)));
            }
        });
    }

    private JLabel createValidationLabel() {
        JLabel label = new JLabel(" ");
        label.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        label.setPreferredSize(new Dimension(300, 16));
        return label;
    }

    // Validation methods
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
                    String text = field.getText().trim();
                    ValidationResult result = validator.validate(text);
                    updateValidationLabel(validationLabel, result);
                });
            }
        });
    }

    private void addFieldValidator(JFormattedTextField field, JLabel validationLabel, FieldValidator validator) {
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
                    String text = field.getText().trim();
                    ValidationResult result = validator.validate(text);
                    updateValidationLabel(validationLabel, result);
                });
            }
        });
    }

    private void addPasswordStrengthValidator(JPasswordField field, JLabel validationLabel,
            JProgressBar strengthBar, JLabel strengthLabel) {
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
                    ValidationResult result = UserRegistrationWindow.this.validatePassword(password);
                    int strength = calculatePasswordStrength(password);

                    updateValidationLabel(validationLabel, result);
                    updatePasswordStrength(strengthBar, strengthLabel, strength, password);
                });
            }
        });
    }

    private void addPasswordConfirmValidator(JPasswordField field, JLabel validationLabel) {
        field.getDocument().addDocumentListener(new DocumentListener() {
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
                    ValidationResult result = validatePasswordConfirmation(password, confirmPassword);
                    updateValidationLabel(validationLabel, result);
                });
            }
        });
    }

    private void updateValidationLabel(JLabel label, ValidationResult result) {
        label.setText(result.message);
        label.setForeground(result.isValid ? SUCCESS_COLOR : ERROR_COLOR);
        label.setIcon(result.isValid ? createIcon("✓", SUCCESS_COLOR) : createIcon("✗", ERROR_COLOR));
    }

    private void updatePasswordStrength(JProgressBar bar, JLabel label, int strength, String password) {
        bar.setValue(strength);

        Color color;
        String text;
        if (password.isEmpty()) {
            color = NEUTRAL_COLOR;
            text = "Enter password";
            bar.setValue(0);
        } else if (strength < 30) {
            color = ERROR_COLOR;
            text = "Weak";
        } else if (strength < 60) {
            color = WARNING_COLOR;
            text = "Fair";
        } else if (strength < 80) {
            color = new Color(255, 193, 7);
            text = "Good";
        } else {
            color = SUCCESS_COLOR;
            text = "Strong";
        }

        bar.setForeground(color);
        label.setText("Password strength: " + text);
        label.setForeground(color);
    }

    private Icon createIcon(String text, Color color) {
        return new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                g.setColor(color);
                g.setFont(new Font("Arial", Font.BOLD, 12));
                g.drawString(text, x, y + 10);
            }

            @Override
            public int getIconWidth() {
                return 12;
            }

            @Override
            public int getIconHeight() {
                return 12;
            }
        };
    }

    // Field validation implementations
    private ValidationResult validateUsername(String username) {
        if (username.isEmpty()) {
            return new ValidationResult(false, "Username is required");
        }
        if (username.length() < 3) {
            return new ValidationResult(false, "Username must be at least 3 characters");
        }
        if (username.length() > 20) {
            return new ValidationResult(false, "Username must be less than 20 characters");
        }
        if (!USERNAME_PATTERN.matcher(username).matches()) {
            return new ValidationResult(false, "Username can only contain letters, numbers, and underscores");
        }
        return new ValidationResult(true, "Username is available");
    }

    private ValidationResult validatePassword(String password) {
        if (password.isEmpty()) {
            return new ValidationResult(false, "Password is required");
        }
        if (password.length() < 6) {
            return new ValidationResult(false, "Password must be at least 6 characters");
        }

        int strength = calculatePasswordStrength(password);
        if (strength < 30) {
            return new ValidationResult(false, "Password is too weak");
        }

        return new ValidationResult(true, "Password meets requirements");
    }

    private ValidationResult validatePasswordConfirmation(String password, String confirmPassword) {
        if (confirmPassword.isEmpty()) {
            return new ValidationResult(false, "Please confirm your password");
        }
        if (!password.equals(confirmPassword)) {
            return new ValidationResult(false, "Passwords do not match");
        }
        return new ValidationResult(true, "Passwords match");
    }

    private ValidationResult validateName(String name) {
        if (name.isEmpty()) {
            return new ValidationResult(false, "Name is required");
        }
        if (name.length() < 2) {
            return new ValidationResult(false, "Name must be at least 2 characters");
        }
        if (name.length() > 50) {
            return new ValidationResult(false, "Name must be less than 50 characters");
        }
        if (!NAME_PATTERN.matcher(name).matches()) {
            return new ValidationResult(false, "Name can only contain letters, spaces, apostrophes, and hyphens");
        }
        return new ValidationResult(true, "Valid name");
    }

    private ValidationResult validateEmail(String email) {
        if (email.isEmpty()) {
            return new ValidationResult(false, "Email is required");
        }
        if (!FormatUtils.isValidEmail(email)) {
            return new ValidationResult(false, "Please enter a valid email address");
        }
        return new ValidationResult(true, "Valid email address");
    }

    private ValidationResult validatePhone(String phone) {
        if (phone.isEmpty() || phone.equals("(___) ___-____")) {
            return new ValidationResult(true, "Phone number is optional");
        }
        if (!FormatUtils.isValidPhoneNumber(phone)) {
            return new ValidationResult(false, "Please enter a valid phone number");
        }
        return new ValidationResult(true, "Valid phone number");
    }

    private ValidationResult validateLicense(String license) {
        if (license.isEmpty()) {
            return new ValidationResult(true, "License number is optional");
        }
        if (!LICENSE_PATTERN.matcher(license.toUpperCase()).matches()) {
            return new ValidationResult(false, "License number should be 6-15 characters, letters and numbers only");
        }
        return new ValidationResult(true, "Valid license number");
    }

    private int calculatePasswordStrength(String password) {
        if (password.isEmpty())
            return 0;

        int strength = 0;

        // Length bonus
        strength += Math.min(password.length() * 4, 25);

        // Character variety bonuses
        if (password.matches(".*[a-z].*"))
            strength += 5;
        if (password.matches(".*[A-Z].*"))
            strength += 5;
        if (password.matches(".*[0-9].*"))
            strength += 10;
        if (password.matches(".*[^a-zA-Z0-9].*"))
            strength += 15;

        // Length bonuses
        if (password.length() >= 8)
            strength += 10;
        if (password.length() >= 12)
            strength += 10;

        // Patterns penalty
        if (password.matches("^[a-z]+$") || password.matches("^[A-Z]+$") ||
                password.matches("^[0-9]+$"))
            strength -= 15;

        return Math.min(Math.max(strength, 0), 100);
    }

    private JPanel createImprovedButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        // Register button with modern styling
        JButton registerButton = new JButton("Create Account");
        registerButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        registerButton.setPreferredSize(new Dimension(150, 45));
        registerButton.setBackground(new Color(52, 152, 219));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);
        registerButton.setBorderPainted(false);
        registerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerButton.addActionListener(new RegisterActionListener());

        // Hover effect for register button
        registerButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                registerButton.setBackground(new Color(41, 128, 185));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                registerButton.setBackground(new Color(52, 152, 219));
            }
        });

        // Cancel button
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cancelButton.setPreferredSize(new Dimension(120, 45));
        cancelButton.setBackground(Color.WHITE);
        cancelButton.setForeground(new Color(99, 110, 114));
        cancelButton.setFocusPainted(false);
        cancelButton.setBorder(BorderFactory.createLineBorder(new Color(220, 221, 225), 1));
        cancelButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cancelButton.addActionListener(e -> dispose());

        // Hover effect for cancel button
        cancelButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cancelButton.setBackground(new Color(248, 249, 250));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                cancelButton.setBackground(Color.WHITE);
            }
        });

        buttonPanel.add(registerButton);
        buttonPanel.add(cancelButton);

        return buttonPanel;
    }

    // Helper classes
    private static class ValidationResult {
        final boolean isValid;
        final String message;

        ValidationResult(boolean isValid, String message) {
            this.isValid = isValid;
            this.message = message;
        }
    }

    @FunctionalInterface
    private interface FieldValidator {
        ValidationResult validate(String text);
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

                // Validate all fields
                if (!validateAllFields(username, password, confirmPassword, firstName, lastName, email, phone,
                        license)) {
                    return; // Validation failed, error message already shown
                }

                // Attempt registration
                boolean success = userController.registerUser(username, password, firstName,
                        lastName, email, phone.isEmpty() ? null : phone,
                        license.isEmpty() ? null : license);

                if (success) {
                    showSuccessMessage("Registration successful! You can now log in with your new account.");
                    parentWindow.refreshAfterRegistration();
                    dispose();
                } else {
                    showErrorMessage("Registration failed. Username or email may already exist.");
                }

            } catch (IllegalArgumentException ex) {
                showErrorMessage(ex.getMessage());
            } catch (Exception ex) {
                showErrorMessage("An error occurred during registration. Please try again.");
                ex.printStackTrace();
            }
        }

        private boolean validateAllFields(String username, String password, String confirmPassword,
                String firstName, String lastName, String email,
                String phone, String license) {
            StringBuilder errors = new StringBuilder();

            // Validate required fields
            if (!validateUsername(username).isValid)
                errors.append("• ").append(validateUsername(username).message).append("\n");
            if (!validatePassword(password).isValid)
                errors.append("• ").append(validatePassword(password).message).append("\n");
            if (!validatePasswordConfirmation(password, confirmPassword).isValid)
                errors.append("• ").append(validatePasswordConfirmation(password, confirmPassword).message)
                        .append("\n");
            if (!validateName(firstName).isValid)
                errors.append("• First Name: ").append(validateName(firstName).message).append("\n");
            if (!validateName(lastName).isValid)
                errors.append("• Last Name: ").append(validateName(lastName).message).append("\n");
            if (!validateEmail(email).isValid)
                errors.append("• ").append(validateEmail(email).message).append("\n");

            // Validate optional fields if provided
            if (!phone.isEmpty() && !validatePhone(phone).isValid)
                errors.append("• ").append(validatePhone(phone).message).append("\n");
            if (!license.isEmpty() && !validateLicense(license).isValid)
                errors.append("• ").append(validateLicense(license).message).append("\n");

            if (errors.length() > 0) {
                showErrorMessage("Please fix the following errors:\n\n" + errors.toString());
                return false;
            }

            return true;
        }
    }

    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Registration Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showSuccessMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Registration Success", JOptionPane.INFORMATION_MESSAGE);
    }
}

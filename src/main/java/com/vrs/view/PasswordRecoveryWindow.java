package com.vrs.view;

import com.vrs.controller.UserController;
import com.vrs.model.User;
import com.vrs.util.AlertUtils;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public class PasswordRecoveryWindow extends JFrame {
    private UserController userController;
    private JTextField usernameField;
    private JLabel questionLabel;
    private JTextField answerField;
    private JPasswordField newPasswordField;
    private JPasswordField confirmPasswordField;
    private JButton verifyButton;
    private JButton resetButton;
    private JButton backButton;
    private JPanel questionsPanel;
    private JPanel resetPasswordPanel;
    private User recoveryUser;

    public PasswordRecoveryWindow() {
        this.userController = new UserController();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Password Recovery - Vehicle Rental System");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 700);
        setLocationRelativeTo(null);
        setResizable(false);

        // Create main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        JLabel titleLabel = new JLabel("Password Recovery", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(60, 120, 180));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Create center panel
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        // Username panel
        JPanel usernamePanel = createUsernamePanel();
        centerPanel.add(usernamePanel);

        // Security questions panel
        questionsPanel = createQuestionsPanel();
        questionsPanel.setVisible(false);
        centerPanel.add(questionsPanel);

        // Reset password panel
        resetPasswordPanel = createResetPasswordPanel();
        resetPasswordPanel.setVisible(false);
        centerPanel.add(resetPasswordPanel);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Bottom panel with buttons
        JPanel bottomPanel = createBottomPanel();
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel createUsernamePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Step 1: Enter Username",
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
                new Font("Arial", Font.BOLD, 14)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        usernameField = new JTextField(20);
        panel.add(usernameField, gbc);

        gbc.gridx = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        JButton findUserButton = new JButton("Find User");
        findUserButton.setBackground(new Color(70, 130, 190));
        findUserButton.setForeground(Color.WHITE);
        findUserButton.addActionListener(this::findUser);
        panel.add(findUserButton, gbc);

        return panel;
    }

    private JPanel createQuestionsPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Security Question"),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5, 10, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Instructions
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel instructionLabel = new JLabel("Answer the security question to recover your account:");
        instructionLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(instructionLabel, gbc);

        // Question
        gbc.gridy++;
        gbc.gridwidth = 1;
        questionLabel = new JLabel("Question:");
        questionLabel.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(questionLabel, gbc);

        // Answer field
        gbc.gridy++;
        gbc.gridwidth = 2;
        answerField = new JTextField(30);
        panel.add(answerField, gbc);

        return panel;
    }

    private JPanel createResetPasswordPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Step 3: Set New Password",
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
                new Font("Arial", Font.BOLD, 14)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("New Password:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        newPasswordField = new JPasswordField(20);
        panel.add(newPasswordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        panel.add(new JLabel("Confirm Password:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        confirmPasswordField = new JPasswordField(20);
        panel.add(confirmPasswordField, gbc);

        return panel;
    }

    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new FlowLayout());

        verifyButton = new JButton("Verify Answers");
        verifyButton.setBackground(new Color(60, 120, 180));
        verifyButton.setForeground(Color.WHITE);
        verifyButton.setPreferredSize(new Dimension(130, 35));
        verifyButton.setVisible(false);
        verifyButton.addActionListener(this::verifySecurityAnswers);

        resetButton = new JButton("Reset Password");
        resetButton.setBackground(new Color(34, 139, 34));
        resetButton.setForeground(Color.WHITE);
        resetButton.setPreferredSize(new Dimension(130, 35));
        resetButton.setVisible(false);
        resetButton.addActionListener(this::resetPassword);

        backButton = new JButton("Back to Login");
        backButton.setBackground(new Color(128, 128, 128));
        backButton.setForeground(Color.WHITE);
        backButton.setPreferredSize(new Dimension(130, 35));
        backButton.addActionListener(e -> {
            dispose();
            new LoginWindow().setVisible(true);
        });

        panel.add(verifyButton);
        panel.add(resetButton);
        panel.add(backButton);

        return panel;
    }

    private void findUser(ActionEvent e) {
        String username = usernameField.getText().trim();
        if (username.isEmpty()) {
            AlertUtils.showError(this, "Please enter a username.");
            return;
        }

        recoveryUser = userController.getUserByUsername(username);
        if (recoveryUser == null) {
            AlertUtils.showError(this, "Username not found or account is inactive.");
            return;
        }

        // Check if user has security questions set up
        if (recoveryUser.getSecurityQuestion1() == null ||
                recoveryUser.getSecurityQuestion1().trim().isEmpty()) {
            AlertUtils.showError(this, "This account doesn't have security questions set up.\n" +
                    "Please contact support for password recovery.");
            return;
        }

        // Display security question
        questionLabel.setText(recoveryUser.getSecurityQuestion1());

        questionsPanel.setVisible(true);
        verifyButton.setVisible(true);
        usernameField.setEnabled(false);

        AlertUtils.showInfo(this, "Security question loaded. Please answer the question.");
    }

    private void verifySecurityAnswers(ActionEvent e) {
        String answer = answerField.getText().trim();

        if (answer.isEmpty()) {
            AlertUtils.showError(this, "Please answer the security question.");
            return;
        }

        boolean verified = userController.verifySecurityAnswer(
                recoveryUser.getUsername(), answer);

        if (verified) {
            resetPasswordPanel.setVisible(true);
            resetButton.setVisible(true);
            verifyButton.setEnabled(false);

            // Disable answer field
            answerField.setEnabled(false);

            AlertUtils.showSuccess(this, "Security answer verified! Please set your new password.");
        } else {
            AlertUtils.showError(this, "Security answer is incorrect. Please try again.");
            // Clear the field for retry
            answerField.setText("");
        }
    }

    private void resetPassword(ActionEvent e) {
        String newPassword = new String(newPasswordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
            AlertUtils.showError(this, "Please fill in both password fields.");
            return;
        }

        if (newPassword.length() < 6) {
            AlertUtils.showError(this, "Password must be at least 6 characters long.");
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            AlertUtils.showError(this, "Passwords do not match. Please try again.");
            confirmPasswordField.setText("");
            return;
        }

        boolean success = userController.resetPassword(recoveryUser.getUsername(), newPassword);
        if (success) {
            AlertUtils.showSuccess(this, "Password reset successfully!\nYou can now log in with your new password.");
            dispose();
            new LoginWindow().setVisible(true);
        } else {
            AlertUtils.showError(this, "Failed to reset password. Please try again.");
        }

        // Clear password fields for security
        newPasswordField.setText("");
        confirmPasswordField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new PasswordRecoveryWindow().setVisible(true);
        });
    }
}

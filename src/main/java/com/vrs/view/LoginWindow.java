package com.vrs.view;

import com.vrs.controller.AdminController;
import com.vrs.controller.UserController;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginWindow extends JFrame {
    private UserController userController;
    private AdminController adminController;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JRadioButton userRadio;
    private JRadioButton adminRadio;

    public LoginWindow() {
        this.userController = new UserController();
        this.adminController = new AdminController();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Vehicle Rental System - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(580, 520);
        setLocationRelativeTo(null);
        setResizable(false);

        // Create main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        JLabel titleLabel = new JLabel("Vehicle Rental System", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(60, 120, 180));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 30, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Center panel for form
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // User type selection
        JPanel userTypePanel = new JPanel(new FlowLayout());
        userTypePanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Login As",
                TitledBorder.CENTER, TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14)));
        userRadio = new JRadioButton("User", true);
        userRadio.setFont(new Font("Arial", Font.PLAIN, 14));
        adminRadio = new JRadioButton("Admin");
        adminRadio.setFont(new Font("Arial", Font.PLAIN, 14));
        ButtonGroup userTypeGroup = new ButtonGroup();
        userTypeGroup.add(userRadio);
        userTypeGroup.add(adminRadio);
        userTypePanel.add(userRadio);
        userTypePanel.add(adminRadio);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        centerPanel.add(userTypePanel, gbc);

        // Username
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        centerPanel.add(usernameLabel, gbc);

        usernameField = new JTextField(25);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 16));
        usernameField.setPreferredSize(new Dimension(280, 40));
        usernameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLoweredBevelBorder(),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        centerPanel.add(usernameField, gbc);

        // Password
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        centerPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField(25);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordField.setPreferredSize(new Dimension(280, 40));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLoweredBevelBorder(),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        centerPanel.add(passwordField, gbc);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginButton.setPreferredSize(new Dimension(120, 40));
        loginButton.setBackground(new Color(60, 120, 180));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.addActionListener(new LoginActionListener());

        JButton registerButton = new JButton("Register");
        registerButton.setFont(new Font("Arial", Font.BOLD, 16));
        registerButton.setPreferredSize(new Dimension(120, 40));
        registerButton.setBackground(new Color(40, 150, 80));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openRegistrationWindow();
            }
        });

        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Arial", Font.BOLD, 16));
        exitButton.setPreferredSize(new Dimension(120, 40));
        exitButton.setBackground(new Color(180, 60, 60));
        exitButton.setForeground(Color.WHITE);
        exitButton.setFocusPainted(false);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        buttonPanel.add(exitButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Default admin info panel
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Default Admin Login",
                TitledBorder.CENTER, TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 12)));
        infoPanel.setBackground(new Color(245, 245, 245));

        JLabel usernameInfo = new JLabel("Username: admin");
        usernameInfo.setFont(new Font("Arial", Font.PLAIN, 12));
        usernameInfo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel passwordInfo = new JLabel("Password: admin123");
        passwordInfo.setFont(new Font("Arial", Font.PLAIN, 12));
        passwordInfo.setAlignmentX(Component.CENTER_ALIGNMENT);

        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(usernameInfo);
        infoPanel.add(passwordInfo);
        infoPanel.add(Box.createVerticalStrut(5));

        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.add(mainPanel, BorderLayout.CENTER);
        wrapperPanel.add(infoPanel, BorderLayout.SOUTH);

        add(wrapperPanel);

        // Enter key support
        getRootPane().setDefaultButton(loginButton);
    }

    private class LoginActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                showErrorMessage("Please enter both username and password.");
                return;
            }

            boolean loginSuccess = false;

            if (userRadio.isSelected()) {
                // User login
                loginSuccess = userController.loginUser(username, password);
                if (loginSuccess) {
                    dispose();
                    new UserDashboard(userController).setVisible(true);
                }
            } else {
                // Admin login
                loginSuccess = adminController.loginAdmin(username, password);
                if (loginSuccess) {
                    dispose();
                    new AdminDashboard(adminController, userController).setVisible(true);
                }
            }

            if (!loginSuccess) {
                showErrorMessage("Invalid username or password.");
                passwordField.setText("");
            }
        }
    }

    private void openRegistrationWindow() {
        new UserRegistrationWindow(this).setVisible(true);
    }

    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Login Error", JOptionPane.ERROR_MESSAGE);
    }

    public void refreshAfterRegistration() {
        usernameField.setText("");
        passwordField.setText("");
        userRadio.setSelected(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            } catch (Exception e) {
                e.printStackTrace();
            }
            new LoginWindow().setVisible(true);
        });
    }
}

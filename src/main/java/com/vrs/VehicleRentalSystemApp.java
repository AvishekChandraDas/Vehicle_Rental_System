package com.vrs;

import com.vrs.database.DatabaseConnection;
import com.vrs.view.LoginWindow;

import javax.swing.*;

public class VehicleRentalSystemApp {

    public static void main(String[] args) {
        // Set system look and feel
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            System.err.println("Could not set system look and feel: " + e.getMessage());
        }

        // Initialize database connection
        try {
            DatabaseConnection.getConnection();
            System.out.println("Database initialized successfully.");
        } catch (Exception e) {
            System.err.println("Error initializing database: " + e.getMessage());
            JOptionPane.showMessageDialog(null,
                    "Failed to initialize database. Please check the application setup.",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        // Start the application on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            try {
                new LoginWindow().setVisible(true);
            } catch (Exception e) {
                System.err.println("Error starting application: " + e.getMessage());
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,
                        "Failed to start application: " + e.getMessage(),
                        "Application Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        // Add shutdown hook to close database connection
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            DatabaseConnection.closeConnection();
            System.out.println("Application shutdown complete.");
        }));
    }
}

package com.vrs.view;

import com.vrs.controller.AdminController;
import com.vrs.controller.BookingController;
import com.vrs.controller.UserController;
import com.vrs.controller.VehicleController;
import com.vrs.model.Admin;
import com.vrs.model.Booking;
import com.vrs.model.User;
import com.vrs.model.Vehicle;
import com.vrs.util.DateUtils;
import com.vrs.util.FormatUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AdminDashboard extends JFrame {
    private AdminController adminController;
    private UserController userController;
    private VehicleController vehicleController;
    private BookingController bookingController;
    private Admin currentAdmin;

    private JTabbedPane tabbedPane;
    private JTable vehicleTable;
    private JTable userTable;
    private JTable bookingTable;
    private DefaultTableModel vehicleTableModel;
    private DefaultTableModel userTableModel;
    private DefaultTableModel bookingTableModel;

    public AdminDashboard(AdminController adminController, UserController userController) {
        this.adminController = adminController;
        this.userController = userController;
        this.vehicleController = new VehicleController();
        this.bookingController = new BookingController();
        this.currentAdmin = adminController.getCurrentAdmin();

        initializeUI();
        loadVehicles();
        loadUsers();
        loadBookings();
    }

    private void initializeUI() {
        setTitle("Vehicle Rental System - Admin Dashboard - " + currentAdmin.getFirstName());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        // Create menu bar
        JMenuBar menuBar = new JMenuBar();

        JMenu accountMenu = new JMenu("Account");
        JMenuItem profileItem = new JMenuItem("Edit Profile");
        profileItem.addActionListener(e -> {
            // Open profile editor
        });
        JMenuItem logoutItem = new JMenuItem("Logout");
        logoutItem.addActionListener(e -> {
            logout();
        });

        accountMenu.add(profileItem);
        accountMenu.addSeparator();
        accountMenu.add(logoutItem);
        menuBar.add(accountMenu);

        setJMenuBar(menuBar);

        // Create tabbed pane
        tabbedPane = new JTabbedPane();

        // Vehicle Management tab
        tabbedPane.addTab("Vehicle Management", createVehicleManagementPanel());

        // User Management tab
        tabbedPane.addTab("User Management", createUserManagementPanel());

        // Booking Management tab
        tabbedPane.addTab("Booking Management", createBookingManagementPanel());

        add(tabbedPane);
    }

    private JPanel createVehicleManagementPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Vehicle table
        String[] columnNames = { "ID", "Make", "Model", "Type", "Year", "Daily Rate", "License Plate", "Available" };
        vehicleTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        vehicleTable = new JTable(vehicleTableModel);
        vehicleTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(vehicleTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addButton = new JButton("Add Vehicle");
        addButton.addActionListener(e -> {
            addVehicle();
        });
        JButton editButton = new JButton("Edit Vehicle");
        editButton.addActionListener(e -> {
            editVehicle();
        });
        JButton deleteButton = new JButton("Delete Vehicle");
        deleteButton.addActionListener(e -> {
            deleteVehicle();
        });
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> {
            loadVehicles();
        });

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createUserManagementPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // User table
        String[] columnNames = { "ID", "Username", "Full Name", "Email", "Phone", "Registration Date", "Active" };
        userTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        userTable = new JTable(userTableModel);
        userTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(userTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton viewButton = new JButton("View Details");
        viewButton.addActionListener(e -> {
            viewUserDetails();
        });
        JButton deactivateButton = new JButton("Deactivate User");
        deactivateButton.addActionListener(e -> {
            deactivateUser();
        });
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> {
            loadUsers();
        });

        buttonPanel.add(viewButton);
        buttonPanel.add(deactivateButton);
        buttonPanel.add(refreshButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createBookingManagementPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Booking table
        String[] columnNames = { "Booking ID", "User", "Vehicle", "Start Date", "End Date", "Total Amount", "Status" };
        bookingTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        bookingTable = new JTable(bookingTableModel);
        bookingTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(bookingTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton viewButton = new JButton("View Details");
        viewButton.addActionListener(e -> {
            viewBookingDetails();
        });
        JButton confirmButton = new JButton("Confirm Booking");
        confirmButton.addActionListener(e -> {
            confirmBooking();
        });
        JButton cancelButton = new JButton("Cancel Booking");
        cancelButton.addActionListener(e -> {
            cancelBooking();
        });
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> {
            loadBookings();
        });

        buttonPanel.add(viewButton);
        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(refreshButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void loadVehicles() {
        vehicleTableModel.setRowCount(0);
        List<Vehicle> vehicles = vehicleController.getAllVehicles();

        for (Vehicle vehicle : vehicles) {
            Object[] row = {
                    vehicle.getVehicleId(),
                    vehicle.getMake(),
                    vehicle.getModel(),
                    vehicle.getType(),
                    vehicle.getYear(),
                    FormatUtils.formatCurrency(vehicle.getDailyRate()),
                    vehicle.getLicensePlate(),
                    vehicle.isAvailable() ? "Yes" : "No"
            };
            vehicleTableModel.addRow(row);
        }
    }

    private void loadUsers() {
        userTableModel.setRowCount(0);
        List<User> users = userController.getAllUsers();

        for (User user : users) {
            Object[] row = {
                    user.getUserId(),
                    user.getUsername(),
                    user.getFullName(),
                    user.getEmail(),
                    user.getPhoneNumber() != null ? user.getPhoneNumber() : "N/A",
                    DateUtils.formatDateForDisplay(user.getRegistrationDate().toLocalDate()),
                    user.isActive() ? "Yes" : "No"
            };
            userTableModel.addRow(row);
        }
    }

    private void loadBookings() {
        bookingTableModel.setRowCount(0);
        List<Booking> bookings = bookingController.getAllBookings();

        for (Booking booking : bookings) {
            Object[] row = {
                    booking.getBookingId(),
                    booking.getUser().getFullName(),
                    booking.getVehicle().getMake() + " " + booking.getVehicle().getModel(),
                    DateUtils.formatDateForDisplay(booking.getStartDate()),
                    DateUtils.formatDateForDisplay(booking.getEndDate()),
                    FormatUtils.formatCurrency(booking.getTotalAmount()),
                    booking.getStatus().name()
            };
            bookingTableModel.addRow(row);
        }
    }

    private void addVehicle() {
        VehicleDialog dialog = new VehicleDialog(this, vehicleController);
        dialog.setVisible(true);
        if (dialog.getDialogResult()) {
            loadVehicles(); // Refresh the vehicle list
        }
    }

    private void editVehicle() {
        int selectedRow = vehicleTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a vehicle to edit.",
                    "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int vehicleId = (Integer) vehicleTableModel.getValueAt(selectedRow, 0);
        Vehicle selectedVehicle = vehicleController.getVehicleById(vehicleId);
        
        if (selectedVehicle != null) {
            VehicleDialog dialog = new VehicleDialog(this, vehicleController, selectedVehicle);
            dialog.setVisible(true);
            if (dialog.getDialogResult()) {
                loadVehicles(); // Refresh the vehicle list
            }
        } else {
            JOptionPane.showMessageDialog(this, "Unable to load vehicle details.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteVehicle() {
        int selectedRow = vehicleTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a vehicle to delete.",
                    "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int vehicleId = (Integer) vehicleTableModel.getValueAt(selectedRow, 0);
        String vehicleName = vehicleTableModel.getValueAt(selectedRow, 1) + " " +
                vehicleTableModel.getValueAt(selectedRow, 2);

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete " + vehicleName + "?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = vehicleController.deleteVehicle(vehicleId);
            if (success) {
                JOptionPane.showMessageDialog(this, "Vehicle deleted successfully!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                loadVehicles();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete vehicle.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void viewUserDetails() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user to view details.",
                    "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(this, "View User Details functionality - to be implemented",
                "Coming Soon", JOptionPane.INFORMATION_MESSAGE);
    }

    private void deactivateUser() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user to deactivate.",
                    "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int userId = (Integer) userTableModel.getValueAt(selectedRow, 0);
        String userName = (String) userTableModel.getValueAt(selectedRow, 2);

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to deactivate " + userName + "?",
                "Confirm Deactivation",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = userController.deactivateUser(userId);
            if (success) {
                JOptionPane.showMessageDialog(this, "User deactivated successfully!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                loadUsers();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to deactivate user.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void viewBookingDetails() {
        int selectedRow = bookingTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a booking to view details.",
                    "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int bookingId = (Integer) bookingTableModel.getValueAt(selectedRow, 0);
        Booking booking = bookingController.getBookingById(bookingId);

        if (booking != null) {
            // Show booking details in a simple dialog for now
            String details = String.format(
                    "Booking ID: %d\n" +
                            "User: %s\n" +
                            "Vehicle: %s\n" +
                            "Start Date: %s\n" +
                            "End Date: %s\n" +
                            "Duration: %d days\n" +
                            "Total Amount: %s\n" +
                            "Status: %s\n" +
                            "Booking Date: %s",
                    booking.getBookingId(),
                    booking.getUser().getFullName(),
                    booking.getVehicle().getMake() + " " + booking.getVehicle().getModel(),
                    DateUtils.formatDateForDisplay(booking.getStartDate()),
                    DateUtils.formatDateForDisplay(booking.getEndDate()),
                    booking.getDurationInDays(),
                    FormatUtils.formatCurrency(booking.getTotalAmount()),
                    booking.getStatus().name(),
                    DateUtils.formatDateForDisplay(booking.getBookingDate().toLocalDate()));

            JOptionPane.showMessageDialog(this, details, "Booking Details", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void confirmBooking() {
        int selectedRow = bookingTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a booking to confirm.",
                    "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int bookingId = (Integer) bookingTableModel.getValueAt(selectedRow, 0);
        String status = (String) bookingTableModel.getValueAt(selectedRow, 6);

        if (!"PENDING".equals(status)) {
            JOptionPane.showMessageDialog(this, "Only pending bookings can be confirmed.",
                    "Cannot Confirm", JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean success = bookingController.confirmBooking(bookingId);
        if (success) {
            JOptionPane.showMessageDialog(this, "Booking confirmed successfully!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            loadBookings();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to confirm booking.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cancelBooking() {
        int selectedRow = bookingTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a booking to cancel.",
                    "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int bookingId = (Integer) bookingTableModel.getValueAt(selectedRow, 0);
        String status = (String) bookingTableModel.getValueAt(selectedRow, 6);

        if ("CANCELLED".equals(status) || "COMPLETED".equals(status)) {
            JOptionPane.showMessageDialog(this, "Cannot cancel a " + status.toLowerCase() + " booking.",
                    "Cannot Cancel", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to cancel this booking?",
                "Confirm Cancellation",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = bookingController.cancelBooking(bookingId);
            if (success) {
                JOptionPane.showMessageDialog(this, "Booking cancelled successfully!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                loadBookings();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to cancel booking.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to logout?",
                "Confirm Logout",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            adminController.logoutAdmin();
            dispose();
            new LoginWindow().setVisible(true);
        }
    }
}

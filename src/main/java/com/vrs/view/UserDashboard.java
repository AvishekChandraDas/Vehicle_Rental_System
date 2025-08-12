package com.vrs.view;

import com.vrs.controller.BookingController;
import com.vrs.controller.UserController;
import com.vrs.controller.VehicleController;
import com.vrs.model.Booking;
import com.vrs.model.User;
import com.vrs.model.Vehicle;
import com.vrs.util.DateUtils;
import com.vrs.util.FormatUtils;
import com.vrs.util.VehicleImageManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;

public class UserDashboard extends JFrame {
    private UserController userController;
    private VehicleController vehicleController;
    private BookingController bookingController;
    private User currentUser;

    private JTabbedPane tabbedPane;
    private JTable vehicleTable;
    private JTable bookingTable;
    private DefaultTableModel vehicleTableModel;
    private DefaultTableModel bookingTableModel;
    private JTextField searchField;
    private JComboBox<String> typeFilter;
    private List<Vehicle> currentVehicleList; // Store current vehicles for selection

    public UserDashboard(UserController userController) {
        this.userController = userController;
        this.vehicleController = new VehicleController();
        this.bookingController = new BookingController();
        this.currentUser = userController.getCurrentUser();

        initializeUI();
        loadVehicles();
        loadUserBookings();
    }

    private void initializeUI() {
        setTitle("Vehicle Rental System - Welcome " + currentUser.getFirstName());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        // Create menu bar
        JMenuBar menuBar = new JMenuBar();

        JMenu accountMenu = new JMenu("Account");
        JMenuItem profileItem = new JMenuItem("Edit Profile");
        profileItem.addActionListener(e -> {
            openProfileEditor();
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

        // Browse Vehicles tab
        tabbedPane.addTab("Browse Vehicles", createVehiclePanel());

        // My Bookings tab
        tabbedPane.addTab("My Bookings", createBookingPanel());

        add(tabbedPane);
    }

    private JPanel createVehiclePanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Search and filter panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Search:"));
        searchField = new JTextField(20);
        searchPanel.add(searchField);

        searchPanel.add(new JLabel("Type:"));
        typeFilter = new JComboBox<>();
        typeFilter.addItem("All");
        typeFilter.addItem("Car");
        typeFilter.addItem("Bike");
        typeFilter.addItem("Van");
        searchPanel.add(typeFilter);

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new SearchActionListener());
        searchPanel.add(searchButton);

        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> loadVehicles());
        searchPanel.add(refreshButton);

        panel.add(searchPanel, BorderLayout.NORTH);

        // Vehicle table
        String[] columnNames = { "Image", "Make", "Model", "Type", "Year", "Daily Rate", "Capacity", "Available" };
        vehicleTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) { // Image column
                    return ImageIcon.class;
                }
                return String.class;
            }
        };
        vehicleTable = new JTable(vehicleTableModel);
        vehicleTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        vehicleTable.setRowHeight(60); // Make rows taller for images

        JScrollPane scrollPane = new JScrollPane(vehicleTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton viewDetailsButton = new JButton("View Details");
        viewDetailsButton.addActionListener(e -> viewVehicleDetails());
        JButton bookButton = new JButton("Book Vehicle");
        bookButton.addActionListener(e -> bookVehicle());

        buttonPanel.add(viewDetailsButton);
        buttonPanel.add(bookButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createBookingPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Booking table
        String[] columnNames = { "Booking ID", "Vehicle", "Start Date", "End Date", "Days", "Total Amount", "Status" };
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
        JButton viewDetailsButton = new JButton("View Details");
        viewDetailsButton.addActionListener(e -> viewBookingDetails());
        JButton cancelButton = new JButton("Cancel Booking");
        cancelButton.addActionListener(e -> cancelBooking());
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> loadUserBookings());

        buttonPanel.add(viewDetailsButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(refreshButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void loadVehicles() {
        vehicleTableModel.setRowCount(0);
        List<Vehicle> vehicles = vehicleController.getAvailableVehicles();
        currentVehicleList = new ArrayList<>(vehicles); // Store for selection

        for (Vehicle vehicle : vehicles) {
            // Load vehicle image (custom or default)
            String imagePath = vehicle.getImagePath();
            ImageIcon vehicleImage = VehicleImageManager.loadVehicleImage(imagePath, 50, 40);

            Object[] row = {
                    vehicleImage, // Image as first column
                    vehicle.getMake(),
                    vehicle.getModel(),
                    vehicle.getType(),
                    vehicle.getYear(),
                    FormatUtils.formatCurrency(vehicle.getDailyRate()),
                    vehicle.getCapacity(),
                    vehicle.isAvailable() ? "Yes" : "No"
            };
            vehicleTableModel.addRow(row);
        }
    }

    private void loadUserBookings() {
        bookingTableModel.setRowCount(0);
        List<Booking> bookings = bookingController.getUserBookings(currentUser.getUserId());

        for (Booking booking : bookings) {
            Object[] row = {
                    booking.getBookingId(),
                    booking.getVehicle().getMake() + " " + booking.getVehicle().getModel(),
                    DateUtils.formatDateForDisplay(booking.getStartDate()),
                    DateUtils.formatDateForDisplay(booking.getEndDate()),
                    booking.getDurationInDays(),
                    FormatUtils.formatCurrency(booking.getTotalAmount()),
                    booking.getStatus().name()
            };
            bookingTableModel.addRow(row);
        }
    }

    private class SearchActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String searchTerm = searchField.getText().trim();
            String selectedType = (String) typeFilter.getSelectedItem();

            vehicleTableModel.setRowCount(0);
            List<Vehicle> vehicles;

            if ("All".equals(selectedType)) {
                vehicles = searchTerm.isEmpty() ? vehicleController.getAvailableVehicles()
                        : vehicleController.searchVehicles(searchTerm);
            } else {
                vehicles = vehicleController.getVehiclesByType(selectedType);
                if (!searchTerm.isEmpty()) {
                    vehicles = vehicles.stream()
                            .filter(v -> v.getMake().toLowerCase().contains(searchTerm.toLowerCase()) ||
                                    v.getModel().toLowerCase().contains(searchTerm.toLowerCase()) ||
                                    v.getDescription().toLowerCase().contains(searchTerm.toLowerCase()))
                            .toList();
                }
            }

            currentVehicleList = new ArrayList<>(vehicles); // Update stored list

            for (Vehicle vehicle : vehicles) {
                // Load vehicle image (custom or default)
                String imagePath = vehicle.getImagePath();
                ImageIcon vehicleImage = VehicleImageManager.loadVehicleImage(imagePath, 50, 40);

                Object[] row = {
                        vehicleImage, // Image as first column
                        vehicle.getMake(),
                        vehicle.getModel(),
                        vehicle.getType(),
                        vehicle.getYear(),
                        FormatUtils.formatCurrency(vehicle.getDailyRate()),
                        vehicle.getCapacity(),
                        vehicle.isAvailable() ? "Yes" : "No"
                };
                vehicleTableModel.addRow(row);
            }
        }
    }

    private void viewVehicleDetails() {
        int selectedRow = vehicleTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a vehicle to view details.",
                    "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (currentVehicleList != null && selectedRow < currentVehicleList.size()) {
            Vehicle vehicle = currentVehicleList.get(selectedRow);
            new VehicleDetailsDialog(this, vehicle).setVisible(true);
        }
    }

    private void bookVehicle() {
        int selectedRow = vehicleTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a vehicle to book.",
                    "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Get vehicle from the stored list using the selected row index
        Vehicle vehicle = currentVehicleList.get(selectedRow);

        if (vehicle != null) {
            new BookingDialog(this, vehicle, currentUser, bookingController).setVisible(true);
            loadUserBookings(); // Refresh bookings after potential booking
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
            new BookingDetailsDialog(this, booking).setVisible(true);
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
            try {
                boolean success = bookingController.cancelBooking(bookingId);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Booking cancelled successfully!",
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                    loadUserBookings();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to cancel booking.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(),
                        "Cannot Cancel", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private void openProfileEditor() {
        new UserProfileDialog(this, currentUser, userController).setVisible(true);
        // Update title with potentially new name
        setTitle("Vehicle Rental System - Welcome " + currentUser.getFirstName());
    }

    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to logout?",
                "Confirm Logout",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            userController.logoutUser();
            dispose();
            new LoginWindow().setVisible(true);
        }
    }
}

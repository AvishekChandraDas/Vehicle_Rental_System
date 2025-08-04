package com.vrs.view;

import com.vrs.controller.BookingController;
import com.vrs.model.User;
import com.vrs.model.Vehicle;
import com.vrs.util.DateUtils;
import com.vrs.util.FormatUtils;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class BookingDialog extends JDialog {
    private Vehicle vehicle;
    private User user;
    private BookingController bookingController;
    private JTextField startDateField;
    private JTextField endDateField;
    private JTextArea notesArea;
    private JLabel totalAmountLabel;

    public BookingDialog(Frame parent, Vehicle vehicle, User user, BookingController bookingController) {
        super(parent, "Book Vehicle", true);
        this.vehicle = vehicle;
        this.user = user;
        this.bookingController = bookingController;
        initializeUI();
    }

    private void initializeUI() {
        setSize(450, 400);
        setLocationRelativeTo(getParent());
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        JLabel titleLabel = new JLabel("Book " + vehicle.getMake() + " " + vehicle.getModel(), JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Vehicle details (read-only)
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(new JLabel("Vehicle:"), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(new JLabel(vehicle.toString()), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(new JLabel("Daily Rate:"), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(new JLabel(FormatUtils.formatCurrency(vehicle.getDailyRate())), gbc);

        // Start Date
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(new JLabel("Start Date (YYYY-MM-DD):*"), gbc);
        startDateField = new JTextField(20);
        startDateField.setText(LocalDate.now().toString());
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(startDateField, gbc);

        // End Date
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(new JLabel("End Date (YYYY-MM-DD):*"), gbc);
        endDateField = new JTextField(20);
        endDateField.setText(LocalDate.now().plusDays(1).toString());
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(endDateField, gbc);

        // Calculate button
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton calculateButton = new JButton("Calculate Total");
        calculateButton.addActionListener(e -> calculateTotal());
        formPanel.add(calculateButton, gbc);

        // Total amount
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(new JLabel("Total Amount:"), gbc);
        totalAmountLabel = new JLabel("$0.00");
        totalAmountLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(totalAmountLabel, gbc);

        // Notes
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        formPanel.add(new JLabel("Notes:"), gbc);
        notesArea = new JTextArea(3, 20);
        notesArea.setWrapStyleWord(true);
        notesArea.setLineWrap(true);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(new JScrollPane(notesArea), gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton bookButton = new JButton("Book Now");
        bookButton.setPreferredSize(new Dimension(100, 30));
        bookButton.addActionListener(e -> bookVehicle());

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setPreferredSize(new Dimension(100, 30));
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(bookButton);
        buttonPanel.add(cancelButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Calculate initial total
        calculateTotal();
    }

    private void calculateTotal() {
        try {
            String startDateStr = startDateField.getText().trim();
            String endDateStr = endDateField.getText().trim();

            if (startDateStr.isEmpty() || endDateStr.isEmpty()) {
                totalAmountLabel.setText("$0.00");
                return;
            }

            LocalDate startDate = LocalDate.parse(startDateStr);
            LocalDate endDate = LocalDate.parse(endDateStr);

            double total = bookingController.calculateBookingAmount(vehicle, startDate, endDate);
            long days = bookingController.calculateBookingDuration(startDate, endDate);

            totalAmountLabel.setText(FormatUtils.formatCurrency(total) + " (" + days + " days)");

        } catch (Exception e) {
            totalAmountLabel.setText("Invalid dates");
        }
    }

    private void bookVehicle() {
        try {
            String startDateStr = startDateField.getText().trim();
            String endDateStr = endDateField.getText().trim();
            String notes = notesArea.getText().trim();

            if (startDateStr.isEmpty() || endDateStr.isEmpty()) {
                showErrorMessage("Please enter both start and end dates.");
                return;
            }

            LocalDate startDate = LocalDate.parse(startDateStr);
            LocalDate endDate = LocalDate.parse(endDateStr);

            // Check availability
            if (!bookingController.isVehicleAvailable(vehicle.getVehicleId(), startDate, endDate)) {
                showErrorMessage("Vehicle is not available for the selected dates.");
                return;
            }

            boolean success = bookingController.createBooking(user, vehicle, startDate, endDate, notes);

            if (success) {
                showSuccessMessage("Vehicle booked successfully!");
                dispose();
            } else {
                showErrorMessage("Failed to book vehicle. Please try again.");
            }

        } catch (Exception e) {
            showErrorMessage("Invalid date format. Please use YYYY-MM-DD format.");
        }
    }

    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Booking Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showSuccessMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Booking Success", JOptionPane.INFORMATION_MESSAGE);
    }
}

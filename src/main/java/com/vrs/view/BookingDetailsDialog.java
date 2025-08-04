package com.vrs.view;

import com.vrs.model.Booking;
import com.vrs.util.DateUtils;
import com.vrs.util.FormatUtils;

import javax.swing.*;
import java.awt.*;

public class BookingDetailsDialog extends JDialog {
    private Booking booking;

    public BookingDetailsDialog(Frame parent, Booking booking) {
        super(parent, "Booking Details", true);
        this.booking = booking;
        initializeUI();
    }

    private void initializeUI() {
        setSize(450, 350);
        setLocationRelativeTo(getParent());
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        JLabel titleLabel = new JLabel("Booking Details", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Details panel
        JPanel detailsPanel = new JPanel(new GridLayout(11, 2, 10, 10));

        detailsPanel.add(new JLabel("Booking ID:"));
        detailsPanel.add(new JLabel(String.valueOf(booking.getBookingId())));

        detailsPanel.add(new JLabel("User:"));
        detailsPanel.add(new JLabel(booking.getUser().getFullName()));

        detailsPanel.add(new JLabel("Email:"));
        detailsPanel.add(new JLabel(booking.getUser().getEmail()));

        detailsPanel.add(new JLabel("Vehicle:"));
        detailsPanel.add(new JLabel(booking.getVehicle().getMake() + " " + booking.getVehicle().getModel()));

        detailsPanel.add(new JLabel("Vehicle Type:"));
        detailsPanel.add(new JLabel(booking.getVehicle().getType()));

        detailsPanel.add(new JLabel("License Plate:"));
        detailsPanel.add(new JLabel(booking.getVehicle().getLicensePlate()));

        detailsPanel.add(new JLabel("Start Date:"));
        detailsPanel.add(new JLabel(DateUtils.formatDateForDisplay(booking.getStartDate())));

        detailsPanel.add(new JLabel("End Date:"));
        detailsPanel.add(new JLabel(DateUtils.formatDateForDisplay(booking.getEndDate())));

        detailsPanel.add(new JLabel("Duration:"));
        detailsPanel.add(new JLabel(booking.getDurationInDays() + " days"));

        detailsPanel.add(new JLabel("Total Amount:"));
        detailsPanel.add(new JLabel(FormatUtils.formatCurrency(booking.getTotalAmount())));

        detailsPanel.add(new JLabel("Status:"));
        JLabel statusLabel = new JLabel(booking.getStatus().name());
        statusLabel.setFont(new Font("Arial", Font.BOLD, 12));
        switch (booking.getStatus()) {
            case PENDING -> statusLabel.setForeground(Color.ORANGE);
            case CONFIRMED -> statusLabel.setForeground(Color.GREEN);
            case CANCELLED -> statusLabel.setForeground(Color.RED);
            case COMPLETED -> statusLabel.setForeground(Color.BLUE);
        }
        detailsPanel.add(statusLabel);

        mainPanel.add(detailsPanel, BorderLayout.CENTER);

        // Notes panel
        if (booking.getNotes() != null && !booking.getNotes().trim().isEmpty()) {
            JPanel notesPanel = new JPanel(new BorderLayout());
            notesPanel.setBorder(BorderFactory.createTitledBorder("Notes"));
            JTextArea notesArea = new JTextArea(booking.getNotes());
            notesArea.setEditable(false);
            notesArea.setWrapStyleWord(true);
            notesArea.setLineWrap(true);
            notesArea.setRows(3);
            notesPanel.add(new JScrollPane(notesArea), BorderLayout.CENTER);
            mainPanel.add(notesPanel, BorderLayout.SOUTH);
        }

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());
        buttonPanel.add(closeButton);

        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.add(mainPanel, BorderLayout.CENTER);
        if (booking.getNotes() == null || booking.getNotes().trim().isEmpty()) {
            wrapperPanel.add(buttonPanel, BorderLayout.SOUTH);
        } else {
            JPanel southPanel = new JPanel(new BorderLayout());
            southPanel.add(mainPanel.getComponent(2), BorderLayout.CENTER); // notes panel
            southPanel.add(buttonPanel, BorderLayout.SOUTH);
            wrapperPanel.add(southPanel, BorderLayout.SOUTH);
            mainPanel.remove(2); // remove notes panel from main
        }

        add(wrapperPanel);
    }
}

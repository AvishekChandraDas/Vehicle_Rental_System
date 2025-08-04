package com.vrs.view;

import com.vrs.model.Vehicle;
import com.vrs.util.FormatUtils;

import javax.swing.*;
import java.awt.*;

public class VehicleDetailsDialog extends JDialog {
    private Vehicle vehicle;

    public VehicleDetailsDialog(Frame parent, Vehicle vehicle) {
        super(parent, "Vehicle Details", true);
        this.vehicle = vehicle;
        initializeUI();
    }

    private void initializeUI() {
        setSize(400, 300);
        setLocationRelativeTo(getParent());
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        JLabel titleLabel = new JLabel("Vehicle Details", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Details panel
        JPanel detailsPanel = new JPanel(new GridLayout(9, 2, 10, 10));

        detailsPanel.add(new JLabel("Vehicle ID:"));
        detailsPanel.add(new JLabel(String.valueOf(vehicle.getVehicleId())));

        detailsPanel.add(new JLabel("Make:"));
        detailsPanel.add(new JLabel(vehicle.getMake()));

        detailsPanel.add(new JLabel("Model:"));
        detailsPanel.add(new JLabel(vehicle.getModel()));

        detailsPanel.add(new JLabel("Type:"));
        detailsPanel.add(new JLabel(vehicle.getType()));

        detailsPanel.add(new JLabel("Year:"));
        detailsPanel.add(new JLabel(String.valueOf(vehicle.getYear())));

        detailsPanel.add(new JLabel("Color:"));
        detailsPanel.add(new JLabel(vehicle.getColor() != null ? vehicle.getColor() : "N/A"));

        detailsPanel.add(new JLabel("Capacity:"));
        detailsPanel.add(new JLabel(vehicle.getCapacity() + " persons"));

        detailsPanel.add(new JLabel("Daily Rate:"));
        detailsPanel.add(new JLabel(FormatUtils.formatCurrency(vehicle.getDailyRate())));

        detailsPanel.add(new JLabel("License Plate:"));
        detailsPanel.add(new JLabel(vehicle.getLicensePlate()));

        mainPanel.add(detailsPanel, BorderLayout.CENTER);

        // Description panel
        if (vehicle.getDescription() != null && !vehicle.getDescription().trim().isEmpty()) {
            JPanel descPanel = new JPanel(new BorderLayout());
            descPanel.setBorder(BorderFactory.createTitledBorder("Description"));
            JTextArea descArea = new JTextArea(vehicle.getDescription());
            descArea.setEditable(false);
            descArea.setWrapStyleWord(true);
            descArea.setLineWrap(true);
            descArea.setRows(3);
            descPanel.add(new JScrollPane(descArea), BorderLayout.CENTER);
            mainPanel.add(descPanel, BorderLayout.SOUTH);
        }

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());
        buttonPanel.add(closeButton);

        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.add(mainPanel, BorderLayout.CENTER);
        wrapperPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(wrapperPanel);
    }
}

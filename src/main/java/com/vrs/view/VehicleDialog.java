package com.vrs.view;

import com.vrs.controller.VehicleController;
import com.vrs.model.Vehicle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class VehicleDialog extends JDialog {
    private VehicleController vehicleController;
    private Vehicle vehicle; // null for add mode, existing vehicle for edit mode
    private boolean isEditMode;
    private boolean dialogResult = false;

    // Form components
    private JTextField makeField;
    private JTextField modelField;
    private JComboBox<String> typeCombo;
    private JTextField licensePlateField;
    private JTextField dailyRateField;
    private JTextField colorField;
    private JTextField yearField;
    private JTextField capacityField;
    private JTextArea descriptionArea;
    private JCheckBox availableCheckBox;

    // Buttons
    private JButton saveButton;
    private JButton cancelButton;

    public VehicleDialog(Frame parent, VehicleController vehicleController) {
        this(parent, vehicleController, null);
    }

    public VehicleDialog(Frame parent, VehicleController vehicleController, Vehicle vehicle) {
        super(parent, vehicle == null ? "Add New Vehicle" : "Edit Vehicle", true);
        this.vehicleController = vehicleController;
        this.vehicle = vehicle;
        this.isEditMode = vehicle != null;
        initializeUI();
        if (isEditMode) {
            populateFields();
        }
    }

    private void initializeUI() {
        setSize(500, 650);
        setLocationRelativeTo(getParent());
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        JLabel titleLabel = new JLabel(isEditMode ? "Edit Vehicle" : "Add New Vehicle", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Form panel
        JPanel formPanel = createFormPanel();
        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        int row = 0;

        // Make
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel("Make: *"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        makeField = new JTextField(20);
        panel.add(makeField, gbc);

        row++;
        // Model
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        panel.add(new JLabel("Model: *"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        modelField = new JTextField(20);
        panel.add(modelField, gbc);

        row++;
        // Type
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        panel.add(new JLabel("Type: *"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        typeCombo = new JComboBox<>(new String[] { "Car", "Bike", "Van", "SUV", "Truck" });
        panel.add(typeCombo, gbc);

        row++;
        // License Plate
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        panel.add(new JLabel("License Plate: *"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        licensePlateField = new JTextField(20);
        panel.add(licensePlateField, gbc);

        row++;
        // Daily Rate
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        panel.add(new JLabel("Daily Rate ($): *"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        dailyRateField = new JTextField(20);
        panel.add(dailyRateField, gbc);

        row++;
        // Color
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        panel.add(new JLabel("Color:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        colorField = new JTextField(20);
        panel.add(colorField, gbc);

        row++;
        // Year
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        panel.add(new JLabel("Year: *"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        yearField = new JTextField(20);
        panel.add(yearField, gbc);

        row++;
        // Capacity
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        panel.add(new JLabel("Capacity: *"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        capacityField = new JTextField(20);
        panel.add(capacityField, gbc);

        row++;
        // Available checkbox (only show in edit mode)
        if (isEditMode) {
            gbc.gridx = 0;
            gbc.gridy = row;
            gbc.fill = GridBagConstraints.NONE;
            gbc.weightx = 0;
            panel.add(new JLabel("Available:"), gbc);
            gbc.gridx = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.weightx = 1.0;
            availableCheckBox = new JCheckBox("Vehicle is available for booking");
            panel.add(availableCheckBox, gbc);
            row++;
        }

        // Description
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        panel.add(new JLabel("Description:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        descriptionArea = new JTextArea(4, 20);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        JScrollPane descScrollPane = new JScrollPane(descriptionArea);
        descScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        panel.add(descScrollPane, gbc);

        // Required fields note
        row++;
        gbc.gridx = 0;
        gbc.gridy = row + 1;
        gbc.gridwidth = 2;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JLabel noteLabel = new JLabel("* Required fields");
        noteLabel.setFont(new Font("Arial", Font.ITALIC, 11));
        noteLabel.setForeground(Color.GRAY);
        panel.add(noteLabel, gbc);

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        cancelButton = new JButton("Cancel");
        cancelButton.setPreferredSize(new Dimension(100, 30));
        cancelButton.addActionListener(e -> {
            dialogResult = false;
            dispose();
        });

        saveButton = new JButton(isEditMode ? "Update" : "Add Vehicle");
        saveButton.setPreferredSize(new Dimension(120, 30));
        saveButton.addActionListener(new SaveActionListener());

        panel.add(cancelButton);
        panel.add(saveButton);

        return panel;
    }

    private void populateFields() {
        if (vehicle != null) {
            makeField.setText(vehicle.getMake());
            modelField.setText(vehicle.getModel());
            typeCombo.setSelectedItem(vehicle.getType());
            licensePlateField.setText(vehicle.getLicensePlate());
            dailyRateField.setText(String.valueOf(vehicle.getDailyRate()));
            colorField.setText(vehicle.getColor() != null ? vehicle.getColor() : "");
            yearField.setText(String.valueOf(vehicle.getYear()));
            capacityField.setText(String.valueOf(vehicle.getCapacity()));
            descriptionArea.setText(vehicle.getDescription() != null ? vehicle.getDescription() : "");
            if (availableCheckBox != null) {
                availableCheckBox.setSelected(vehicle.isAvailable());
            }
        }
    }

    private boolean validateInput() {
        StringBuilder errors = new StringBuilder();

        // Make validation
        if (makeField.getText().trim().isEmpty()) {
            errors.append("• Make is required\n");
        }

        // Model validation
        if (modelField.getText().trim().isEmpty()) {
            errors.append("• Model is required\n");
        }

        // License Plate validation
        if (licensePlateField.getText().trim().isEmpty()) {
            errors.append("• License Plate is required\n");
        }

        // Daily Rate validation
        try {
            double rate = Double.parseDouble(dailyRateField.getText().trim());
            if (rate <= 0) {
                errors.append("• Daily Rate must be greater than 0\n");
            }
        } catch (NumberFormatException e) {
            errors.append("• Daily Rate must be a valid number\n");
        }

        // Year validation
        try {
            int year = Integer.parseInt(yearField.getText().trim());
            int currentYear = LocalDate.now().getYear();
            if (year < 1900 || year > currentYear + 1) {
                errors.append("• Year must be between 1900 and " + (currentYear + 1) + "\n");
            }
        } catch (NumberFormatException e) {
            errors.append("• Year must be a valid number\n");
        }

        // Capacity validation
        try {
            int capacity = Integer.parseInt(capacityField.getText().trim());
            if (capacity <= 0) {
                errors.append("• Capacity must be greater than 0\n");
            }
        } catch (NumberFormatException e) {
            errors.append("• Capacity must be a valid number\n");
        }

        if (errors.length() > 0) {
            JOptionPane.showMessageDialog(this,
                    "Please fix the following errors:\n\n" + errors.toString(),
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private class SaveActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!validateInput()) {
                return;
            }

            try {
                String make = makeField.getText().trim();
                String model = modelField.getText().trim();
                String type = (String) typeCombo.getSelectedItem();
                String licensePlate = licensePlateField.getText().trim();
                double dailyRate = Double.parseDouble(dailyRateField.getText().trim());
                String color = colorField.getText().trim();
                int year = Integer.parseInt(yearField.getText().trim());
                int capacity = Integer.parseInt(capacityField.getText().trim());
                String description = descriptionArea.getText().trim();

                boolean success;
                if (isEditMode) {
                    // Update existing vehicle
                    boolean isAvailable = availableCheckBox != null ? availableCheckBox.isSelected()
                            : vehicle.isAvailable();
                    success = vehicleController.updateVehicle(vehicle.getVehicleId(), make, model, type,
                            licensePlate, dailyRate, isAvailable, color, year, capacity, description);

                    if (success) {
                        JOptionPane.showMessageDialog(VehicleDialog.this,
                                "Vehicle updated successfully!",
                                "Success",
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(VehicleDialog.this,
                                "Failed to update vehicle. Please try again.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    // Add new vehicle
                    success = vehicleController.addVehicle(make, model, type, licensePlate,
                            dailyRate, color, year, capacity, description);

                    if (success) {
                        JOptionPane.showMessageDialog(VehicleDialog.this,
                                "Vehicle added successfully!",
                                "Success",
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(VehicleDialog.this,
                                "Failed to add vehicle. Please check if license plate already exists.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }

                if (success) {
                    dialogResult = true;
                    dispose();
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(VehicleDialog.this,
                        "An error occurred: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public boolean getDialogResult() {
        return dialogResult;
    }
}

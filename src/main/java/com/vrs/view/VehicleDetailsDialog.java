package com.vrs.view;

import com.vrs.model.Vehicle;
import com.vrs.util.FormatUtils;
import com.vrs.util.VehicleImageManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class VehicleDetailsDialog extends JDialog {
    private Vehicle vehicle;
    private VehicleImageManager imageManager;

    public VehicleDetailsDialog(Frame parent, Vehicle vehicle) {
        super(parent, "Vehicle Details - " + vehicle.getMake() + " " + vehicle.getModel(), true);
        this.vehicle = vehicle;
        this.imageManager = new VehicleImageManager();
        initializeUI();
    }

    private void initializeUI() {
        setSize(750, 600);
        setLocationRelativeTo(getParent());
        setResizable(false);

        // Create main panel with gradient background
        JPanel mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, new Color(240, 248, 255), 0, getHeight(),
                        new Color(230, 240, 250));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        JLabel titleLabel = new JLabel("Vehicle Details", JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(51, 102, 153));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Content panel with image and details side by side
        JPanel contentPanel = new JPanel(new BorderLayout(20, 0));
        contentPanel.setOpaque(false);

        // Image panel (left side)
        JPanel imagePanel = createImagePanel();
        contentPanel.add(imagePanel, BorderLayout.WEST);

        // Details panel (right side)
        JPanel detailsPanel = createDetailsPanel();
        contentPanel.add(detailsPanel, BorderLayout.CENTER);

        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        JButton closeButton = createStyledButton("Close", new Color(108, 117, 125), Color.WHITE);
        closeButton.addActionListener(e -> dispose());

        JButton bookButton = createStyledButton("Book This Vehicle", new Color(40, 167, 69), Color.WHITE);
        bookButton.addActionListener(e -> {
            dispose();
            // This could trigger booking dialog if needed
            JOptionPane.showMessageDialog(getParent(),
                    "Use the 'Book Vehicle' button in the main dashboard to book this vehicle.",
                    "Information", JOptionPane.INFORMATION_MESSAGE);
        });

        buttonPanel.add(bookButton);
        buttonPanel.add(closeButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel createImagePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setPreferredSize(new Dimension(300, 400));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(180, 200, 220), 2, true),
                        "Vehicle Image",
                        0, 0,
                        new Font("Segoe UI", Font.BOLD, 14),
                        new Color(51, 102, 153)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));

        // Load and display vehicle image
        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setVerticalAlignment(JLabel.CENTER);

        try {
            BufferedImage image = imageManager.loadVehicleImage(vehicle);
            if (image != null) {
                // Scale image to fit the panel while maintaining aspect ratio
                Image scaledImage = scaleImageToFit(image, 270, 320);
                imageLabel.setIcon(new ImageIcon(scaledImage));
            } else {
                // Show placeholder if no image
                imageLabel.setText("<html><div style='text-align: center;'>" +
                        "<div style='font-size: 48px; color: #ccc;'>📷</div>" +
                        "<div style='color: #666;'>No Image Available</div></html>");
            }
        } catch (Exception e) {
            imageLabel.setText("<html><div style='text-align: center;'>" +
                    "<div style='font-size: 48px; color: #ccc;'>❌</div>" +
                    "<div style='color: #666;'>Image Load Error</div></html>");
        }

        // Add border to image
        imageLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        imageLabel.setBackground(Color.WHITE);
        imageLabel.setOpaque(true);

        panel.add(imageLabel, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createDetailsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(180, 200, 220), 2, true),
                        "Vehicle Specifications",
                        0, 0,
                        new Font("Segoe UI", Font.BOLD, 14),
                        new Color(51, 102, 153)),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));

        // Vehicle details in a grid layout
        JPanel detailsGrid = new JPanel(new GridBagLayout());
        detailsGrid.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;

        Font labelFont = new Font("Segoe UI", Font.BOLD, 13);
        Font valueFont = new Font("Segoe UI", Font.PLAIN, 13);
        Color labelColor = new Color(60, 60, 60);
        Color valueColor = new Color(80, 80, 80);

        // Add details rows
        addDetailRow(detailsGrid, gbc, 0, "Vehicle ID:", String.valueOf(vehicle.getVehicleId()), labelFont, valueFont,
                labelColor, valueColor);
        addDetailRow(detailsGrid, gbc, 1, "Make:", vehicle.getMake(), labelFont, valueFont, labelColor, valueColor);
        addDetailRow(detailsGrid, gbc, 2, "Model:", vehicle.getModel(), labelFont, valueFont, labelColor, valueColor);
        addDetailRow(detailsGrid, gbc, 3, "Type:", vehicle.getType(), labelFont, valueFont, labelColor, valueColor);
        addDetailRow(detailsGrid, gbc, 4, "Year:", String.valueOf(vehicle.getYear()), labelFont, valueFont, labelColor,
                valueColor);
        addDetailRow(detailsGrid, gbc, 5, "Color:", vehicle.getColor() != null ? vehicle.getColor() : "N/A", labelFont,
                valueFont, labelColor, valueColor);
        addDetailRow(detailsGrid, gbc, 6, "Capacity:", vehicle.getCapacity() + " persons", labelFont, valueFont,
                labelColor, valueColor);
        addDetailRow(detailsGrid, gbc, 7, "License Plate:", vehicle.getLicensePlate(), labelFont, valueFont, labelColor,
                valueColor);

        // Daily rate with special styling
        addDetailRow(detailsGrid, gbc, 8, "Daily Rate:", FormatUtils.formatCurrency(vehicle.getDailyRate()),
                labelFont, new Font("Segoe UI", Font.BOLD, 14), labelColor, new Color(40, 167, 69));

        // Availability status
        String availability = vehicle.isAvailable() ? "Available" : "Not Available";
        Color availabilityColor = vehicle.isAvailable() ? new Color(40, 167, 69) : new Color(220, 53, 69);
        addDetailRow(detailsGrid, gbc, 9, "Status:", availability, labelFont, new Font("Segoe UI", Font.BOLD, 13),
                labelColor, availabilityColor);

        panel.add(detailsGrid, BorderLayout.NORTH);

        // Description panel
        if (vehicle.getDescription() != null && !vehicle.getDescription().trim().isEmpty()) {
            JPanel descPanel = new JPanel(new BorderLayout());
            descPanel.setOpaque(false);
            descPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createTitledBorder(
                            BorderFactory.createLineBorder(new Color(180, 200, 220), 1),
                            "Description",
                            0, 0,
                            new Font("Segoe UI", Font.BOLD, 12),
                            new Color(51, 102, 153)),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)));

            JTextArea descArea = new JTextArea(vehicle.getDescription());
            descArea.setEditable(false);
            descArea.setWrapStyleWord(true);
            descArea.setLineWrap(true);
            descArea.setRows(4);
            descArea.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            descArea.setBackground(new Color(248, 249, 250));
            descArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

            JScrollPane scrollPane = new JScrollPane(descArea);
            scrollPane.setBorder(null);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

            descPanel.add(scrollPane, BorderLayout.CENTER);
            panel.add(descPanel, BorderLayout.CENTER);
        }

        return panel;
    }

    private void addDetailRow(JPanel panel, GridBagConstraints gbc, int row, String label, String value,
            Font labelFont, Font valueFont, Color labelColor, Color valueColor) {
        // Label
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel labelComponent = new JLabel(label);
        labelComponent.setFont(labelFont);
        labelComponent.setForeground(labelColor);
        panel.add(labelComponent, gbc);

        // Value
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel valueComponent = new JLabel(value);
        valueComponent.setFont(valueFont);
        valueComponent.setForeground(valueColor);
        panel.add(valueComponent, gbc);
    }

    private Image scaleImageToFit(BufferedImage originalImage, int maxWidth, int maxHeight) {
        int originalWidth = originalImage.getWidth();
        int originalHeight = originalImage.getHeight();

        // Calculate scaling factors
        double scaleX = (double) maxWidth / originalWidth;
        double scaleY = (double) maxHeight / originalHeight;
        double scale = Math.min(scaleX, scaleY);

        // Calculate new dimensions
        int newWidth = (int) (originalWidth * scale);
        int newHeight = (int) (originalHeight * scale);

        // Scale the image
        return originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
    }

    private JButton createStyledButton(String text, Color backgroundColor, Color textColor) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(150, 35));
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setBackground(backgroundColor);
        button.setForeground(textColor);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add hover effects
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            private Color originalColor = backgroundColor;

            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(originalColor.darker());
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(originalColor);
            }
        });

        return button;
    }
}

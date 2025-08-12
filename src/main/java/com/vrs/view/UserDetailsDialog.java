package com.vrs.view;

import com.vrs.controller.BookingController;
import com.vrs.model.Booking;
import com.vrs.model.User;
import com.vrs.util.DateUtils;
import com.vrs.util.FormatUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class UserDetailsDialog extends JDialog {
    private User user;
    private BookingController bookingController;

    public UserDetailsDialog(Frame parent, User user) {
        super(parent, "User Details - " + user.getUsername(), true);
        this.user = user;
        this.bookingController = new BookingController();
        initializeUI();
    }

    private void initializeUI() {
        setSize(700, 600);
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
        JLabel titleLabel = new JLabel("User Details", JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(51, 102, 153));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Create tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 12));

        // Personal Information Tab
        tabbedPane.addTab("Personal Information", createPersonalInfoPanel());

        // Booking History Tab
        tabbedPane.addTab("Booking History", createBookingHistoryPanel());

        // Account Status Tab
        tabbedPane.addTab("Account Status", createAccountStatusPanel());

        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        // Close button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
        JButton closeButton = createStyledButton("Close", new Color(108, 117, 125), Color.WHITE);
        closeButton.addActionListener(e -> dispose());
        buttonPanel.add(closeButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel createPersonalInfoPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(180, 200, 220), 2, true),
                        "Personal Information",
                        0, 0,
                        new Font("Segoe UI", Font.BOLD, 14),
                        new Color(51, 102, 153)),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        Font labelFont = new Font("Segoe UI", Font.BOLD, 13);
        Font valueFont = new Font("Segoe UI", Font.PLAIN, 13);
        Color labelColor = new Color(60, 60, 60);

        // User ID
        addDetailRow(panel, gbc, 0, "User ID:", String.valueOf(user.getUserId()), labelFont, valueFont, labelColor);

        // Username
        addDetailRow(panel, gbc, 1, "Username:", user.getUsername(), labelFont, valueFont, labelColor);

        // Full Name
        String fullName = user.getFirstName() + " " + user.getLastName();
        addDetailRow(panel, gbc, 2, "Full Name:", fullName, labelFont, valueFont, labelColor);

        // Email
        addDetailRow(panel, gbc, 3, "Email:", user.getEmail(), labelFont, valueFont, labelColor);

        // Phone Number
        String phone = user.getPhoneNumber() != null ? user.getPhoneNumber() : "Not provided";
        addDetailRow(panel, gbc, 4, "Phone Number:", phone, labelFont, valueFont, labelColor);

        // License Number
        String license = user.getLicenseNumber() != null ? user.getLicenseNumber() : "Not provided";
        addDetailRow(panel, gbc, 5, "License Number:", license, labelFont, valueFont, labelColor);

        // Registration Date
        String regDate = user.getRegistrationDate() != null ? user.getRegistrationDate().toLocalDate()
                .format(java.time.format.DateTimeFormatter.ofPattern("MMM dd, yyyy")) : "Unknown";
        addDetailRow(panel, gbc, 6, "Registration Date:", regDate, labelFont, valueFont, labelColor);

        // Account Status
        String status = user.isActive() ? "Active" : "Inactive";
        Color statusColor = user.isActive() ? new Color(40, 167, 69) : new Color(220, 53, 69);
        addDetailRow(panel, gbc, 7, "Account Status:", status, labelFont, valueFont, statusColor);

        return panel;
    }

    private JPanel createBookingHistoryPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Get user's bookings
        List<Booking> bookings = bookingController.getUserBookings(user.getUserId());

        // Create table
        String[] columnNames = { "Booking ID", "Vehicle", "Start Date", "End Date", "Total Amount", "Status" };
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (Booking booking : bookings) {
            Object[] row = {
                    booking.getBookingId(),
                    "Vehicle ID: " + booking.getVehicleId(),
                    DateUtils.formatDateForDisplay(booking.getStartDate()),
                    DateUtils.formatDateForDisplay(booking.getEndDate()),
                    FormatUtils.formatCurrency(booking.getTotalAmount()),
                    booking.getStatus().toString()
            };
            tableModel.addRow(row);
        }

        JTable bookingTable = new JTable(tableModel);
        bookingTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        bookingTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        bookingTable.setRowHeight(25);
        bookingTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(bookingTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(180, 200, 220), 1),
                "Booking History (" + bookings.size() + " bookings)",
                0, 0,
                new Font("Segoe UI", Font.BOLD, 14),
                new Color(51, 102, 153)));

        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createAccountStatusPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(180, 200, 220), 2, true),
                        "Account Statistics",
                        0, 0,
                        new Font("Segoe UI", Font.BOLD, 14),
                        new Color(51, 102, 153)),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.anchor = GridBagConstraints.WEST;

        Font labelFont = new Font("Segoe UI", Font.BOLD, 13);
        Font valueFont = new Font("Segoe UI", Font.PLAIN, 13);
        Color labelColor = new Color(60, 60, 60);

        // Get booking statistics
        List<Booking> userBookings = bookingController.getUserBookings(user.getUserId());
        int totalBookings = userBookings.size();

        long completedBookings = userBookings.stream()
                .filter(b -> b.getStatus().toString().equals("COMPLETED"))
                .count();

        long activeBookings = userBookings.stream()
                .filter(b -> b.getStatus().toString().equals("CONFIRMED") ||
                        b.getStatus().toString().equals("PENDING"))
                .count();

        long cancelledBookings = userBookings.stream()
                .filter(b -> b.getStatus().toString().equals("CANCELLED"))
                .count();

        double totalSpent = userBookings.stream()
                .filter(b -> b.getStatus().toString().equals("COMPLETED"))
                .mapToDouble(Booking::getTotalAmount)
                .sum();

        // Display statistics
        addDetailRow(panel, gbc, 0, "Total Bookings:", String.valueOf(totalBookings), labelFont, valueFont, labelColor);
        addDetailRow(panel, gbc, 1, "Completed Bookings:", String.valueOf(completedBookings), labelFont, valueFont,
                new Color(40, 167, 69));
        addDetailRow(panel, gbc, 2, "Active Bookings:", String.valueOf(activeBookings), labelFont, valueFont,
                new Color(255, 193, 7));
        addDetailRow(panel, gbc, 3, "Cancelled Bookings:", String.valueOf(cancelledBookings), labelFont, valueFont,
                new Color(220, 53, 69));
        addDetailRow(panel, gbc, 4, "Total Amount Spent:", FormatUtils.formatCurrency(totalSpent), labelFont, valueFont,
                new Color(51, 102, 153));

        return panel;
    }

    private void addDetailRow(JPanel panel, GridBagConstraints gbc, int row, String label, String value,
            Font labelFont, Font valueFont, Color valueColor) {
        // Label
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel labelComponent = new JLabel(label);
        labelComponent.setFont(labelFont);
        labelComponent.setForeground(new Color(60, 60, 60));
        panel.add(labelComponent, gbc);

        // Value
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel valueComponent = new JLabel(value);
        valueComponent.setFont(valueFont);
        valueComponent.setForeground(valueColor);
        panel.add(valueComponent, gbc);
    }

    private JButton createStyledButton(String text, Color backgroundColor, Color textColor) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(120, 35));
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

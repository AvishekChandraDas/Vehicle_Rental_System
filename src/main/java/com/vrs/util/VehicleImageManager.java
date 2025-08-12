package com.vrs.util;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class VehicleImageManager {
    private static final String DEFAULT_IMAGE_PATH = "/images/vehicles/default-car.png";
    private static final String VEHICLE_IMAGES_PATH = "/images/vehicles/";

    /**
     * Load and resize image for display in vehicle listings
     * 
     * @param imagePath - Custom image path (e.g., "my-car.jpg") or full path
     * @param width     - Desired width
     * @param height    - Desired height
     * @return Resized ImageIcon
     */
    public static ImageIcon loadVehicleImage(String imagePath, int width, int height) {
        try {
            InputStream imageStream = null;

            if (imagePath != null && !imagePath.trim().isEmpty()) {
                // Try to load the specific image
                String fullPath = imagePath.startsWith("/") ? imagePath : VEHICLE_IMAGES_PATH + imagePath;
                imageStream = VehicleImageManager.class.getResourceAsStream(fullPath);
            }

            // If custom image not found, try default
            if (imageStream == null) {
                imageStream = VehicleImageManager.class.getResourceAsStream(DEFAULT_IMAGE_PATH);
            }

            if (imageStream != null) {
                BufferedImage originalImage = ImageIO.read(imageStream);
                Image scaledImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                return new ImageIcon(scaledImage);
            } else {
                // Create a placeholder image if no image is found
                return createPlaceholderImage(width, height);
            }
        } catch (Exception e) {
            System.err.println("Error loading vehicle image: " + e.getMessage());
            return createPlaceholderImage(width, height);
        }
    }

    /**
     * Create a placeholder image when no vehicle image is available
     */
    private static ImageIcon createPlaceholderImage(int width, int height) {
        BufferedImage placeholder = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = placeholder.createGraphics();

        // Set background
        g2d.setColor(new Color(240, 240, 240));
        g2d.fillRect(0, 0, width, height);

        // Draw border
        g2d.setColor(new Color(200, 200, 200));
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRect(1, 1, width - 2, height - 2);

        // Draw car icon/text
        g2d.setColor(new Color(120, 120, 120));
        g2d.setFont(new Font("Arial", Font.BOLD, Math.min(width / 8, height / 8)));
        String text = "CAR";
        FontMetrics fm = g2d.getFontMetrics();
        int x = (width - fm.stringWidth(text)) / 2;
        int y = (height + fm.getAscent()) / 2;
        g2d.drawString(text, x, y);

        g2d.dispose();
        return new ImageIcon(placeholder);
    }

    /**
     * Get all supported image file extensions
     */
    public static String[] getSupportedImageExtensions() {
        return new String[] { "jpg", "jpeg", "png", "gif", "bmp" };
    }

    /**
     * Check if a file is a valid image file
     */
    public static boolean isValidImageFile(File file) {
        String name = file.getName().toLowerCase();
        for (String ext : getSupportedImageExtensions()) {
            if (name.endsWith("." + ext)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Load a BufferedImage for a vehicle
     * 
     * @param vehicle The vehicle object
     * @return BufferedImage or null if not found
     */
    public BufferedImage loadVehicleImage(com.vrs.model.Vehicle vehicle) {
        try {
            InputStream imageStream = null;
            String imagePath = vehicle.getImagePath();

            if (imagePath != null && !imagePath.trim().isEmpty()) {
                // Try to load the specific image
                String fullPath = imagePath.startsWith("/") ? imagePath : VEHICLE_IMAGES_PATH + imagePath;
                imageStream = VehicleImageManager.class.getResourceAsStream(fullPath);
            }

            // If custom image not found, try to get default image based on make
            if (imageStream == null) {
                String defaultImageName = vehicle.getMake().toLowerCase() + "-default.png";
                String defaultPath = VEHICLE_IMAGES_PATH + defaultImageName;
                imageStream = VehicleImageManager.class.getResourceAsStream(defaultPath);
            }

            // If brand default not found, use generic default
            if (imageStream == null) {
                imageStream = VehicleImageManager.class.getResourceAsStream(DEFAULT_IMAGE_PATH);
            }

            if (imageStream != null) {
                return ImageIO.read(imageStream);
            }
        } catch (Exception e) {
            System.err.println("Error loading vehicle image: " + e.getMessage());
        }
        return null;
    }
}

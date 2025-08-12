package com.vrs.util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/**
 * Utility to generate default brand images for the vehicle rental system
 */
public class BrandImageGenerator {

    public static void generateBrandImages() {
        String[] brands = {
                "Toyota", "Honda", "Ford", "BMW", "Mercedes", "Audi",
                "Volkswagen", "Nissan", "Hyundai", "Yamaha", "Harley-Davidson", "Kawasaki"
        };

        Color[] brandColors = {
                new Color(235, 10, 30), // Toyota Red
                new Color(0, 102, 204), // Honda Blue
                new Color(0, 51, 160), // Ford Blue
                new Color(0, 0, 0), // BMW Black
                new Color(0, 0, 0), // Mercedes Black
                new Color(187, 18, 43), // Audi Red
                new Color(0, 104, 180), // VW Blue
                new Color(200, 16, 46), // Nissan Red
                new Color(0, 44, 95), // Hyundai Blue
                new Color(0, 117, 201), // Yamaha Blue
                new Color(242, 101, 34), // Harley Orange
                new Color(57, 181, 74) // Kawasaki Green
        };

        try {
            File imageDir = new File("src/main/resources/images/vehicles");
            if (!imageDir.exists()) {
                imageDir.mkdirs();
            }

            for (int i = 0; i < brands.length; i++) {
                generateBrandImage(brands[i], brandColors[i % brandColors.length],
                        new File(imageDir, brands[i].toLowerCase().replace("-", "") + "-default.png"));
            }

            // Generate default car image
            generateDefaultCarImage(new File(imageDir, "default-car.png"));

            System.out.println("✅ Generated brand images successfully!");

        } catch (Exception e) {
            System.err.println("Error generating brand images: " + e.getMessage());
        }
    }

    private static void generateBrandImage(String brandName, Color brandColor, File outputFile) throws Exception {
        int width = 300;
        int height = 200;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();

        // Enable antialiasing
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // Background gradient
        GradientPaint gradient = new GradientPaint(0, 0, Color.WHITE, 0, height, new Color(245, 245, 245));
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, width, height);

        // Draw border
        g2d.setColor(new Color(200, 200, 200));
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRect(1, 1, width - 2, height - 2);

        // Draw car silhouette
        drawCarSilhouette(g2d, width, height, brandColor);

        // Draw brand name
        g2d.setColor(brandColor);
        g2d.setFont(new Font("Arial", Font.BOLD, 24));
        FontMetrics fm = g2d.getFontMetrics();
        String text = brandName.toUpperCase();
        int textX = (width - fm.stringWidth(text)) / 2;
        int textY = height - 20;
        g2d.drawString(text, textX, textY);

        g2d.dispose();
        ImageIO.write(image, "PNG", outputFile);
        System.out.println("Generated: " + outputFile.getName());
    }

    private static void drawCarSilhouette(Graphics2D g2d, int width, int height, Color color) {
        int carWidth = width - 60;
        int carHeight = height / 3;
        int carX = (width - carWidth) / 2;
        int carY = height / 3;

        // Set car color with transparency
        g2d.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 100));

        // Car body (simplified)
        g2d.fillRoundRect(carX, carY, carWidth, carHeight, 20, 20);

        // Car roof
        int roofWidth = carWidth - 80;
        int roofHeight = carHeight - 20;
        int roofX = carX + 40;
        int roofY = carY - roofHeight + 10;
        g2d.fillRoundRect(roofX, roofY, roofWidth, roofHeight, 15, 15);

        // Wheels
        int wheelRadius = 15;
        int wheelY = carY + carHeight - 5;

        g2d.setColor(color);
        g2d.fillOval(carX + 20, wheelY, wheelRadius * 2, wheelRadius * 2);
        g2d.fillOval(carX + carWidth - 20 - wheelRadius * 2, wheelY, wheelRadius * 2, wheelRadius * 2);

        // Wheel centers
        g2d.setColor(Color.WHITE);
        g2d.fillOval(carX + 20 + wheelRadius / 2, wheelY + wheelRadius / 2, wheelRadius, wheelRadius);
        g2d.fillOval(carX + carWidth - 20 - wheelRadius * 2 + wheelRadius / 2, wheelY + wheelRadius / 2, wheelRadius,
                wheelRadius);
    }

    private static void generateDefaultCarImage(File outputFile) throws Exception {
        int width = 300;
        int height = 200;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Background
        g2d.setColor(new Color(245, 245, 245));
        g2d.fillRect(0, 0, width, height);

        // Border
        g2d.setColor(new Color(200, 200, 200));
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRect(1, 1, width - 2, height - 2);

        // Default car
        drawCarSilhouette(g2d, width, height, new Color(100, 100, 100));

        // Text
        g2d.setColor(new Color(120, 120, 120));
        g2d.setFont(new Font("Arial", Font.BOLD, 18));
        FontMetrics fm = g2d.getFontMetrics();
        String text = "VEHICLE IMAGE";
        int textX = (width - fm.stringWidth(text)) / 2;
        int textY = height - 20;
        g2d.drawString(text, textX, textY);

        g2d.dispose();
        ImageIO.write(image, "PNG", outputFile);
        System.out.println("Generated: " + outputFile.getName());
    }

    public static void main(String[] args) {
        generateBrandImages();
    }
}

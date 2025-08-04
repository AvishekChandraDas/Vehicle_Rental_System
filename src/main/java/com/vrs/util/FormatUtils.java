package com.vrs.util;

import java.text.NumberFormat;
import java.util.Locale;

public class FormatUtils {
    private static final NumberFormat CURRENCY_FORMAT = NumberFormat.getCurrencyInstance(Locale.US);

    public static String formatCurrency(double amount) {
        return CURRENCY_FORMAT.format(amount);
    }

    public static String formatPercentage(double percentage) {
        return String.format("%.1f%%", percentage);
    }

    public static String capitalizeFirst(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    public static String formatPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            return "";
        }

        // Remove all non-digit characters
        String digits = phoneNumber.replaceAll("[^\\d]", "");

        if (digits.length() == 10) {
            return String.format("(%s) %s-%s",
                    digits.substring(0, 3),
                    digits.substring(3, 6),
                    digits.substring(6));
        } else if (digits.length() == 11 && digits.startsWith("1")) {
            return String.format("+1 (%s) %s-%s",
                    digits.substring(1, 4),
                    digits.substring(4, 7),
                    digits.substring(7));
        }

        return phoneNumber; // Return original if can't format
    }

    public static String truncateString(String str, int maxLength) {
        if (str == null || str.length() <= maxLength) {
            return str;
        }
        return str.substring(0, maxLength - 3) + "...";
    }

    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }

        // Simple email validation
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailRegex);
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            return false;
        }

        // Remove all non-digit characters
        String digits = phoneNumber.replaceAll("[^\\d]", "");

        // Valid if 10 digits or 11 digits starting with 1
        return digits.length() == 10 || (digits.length() == 11 && digits.startsWith("1"));
    }
}

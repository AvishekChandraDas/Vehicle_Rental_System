package com.vrs.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    private static final String DATABASE_URL = "jdbc:sqlite:vrs_database.db";
    private static Connection connection = null;

    private DatabaseConnection() {
    }

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection(DATABASE_URL);
                initializeTables();
            } catch (ClassNotFoundException e) {
                throw new SQLException("SQLite JDBC driver not found", e);
            }
        }
        return connection;
    }

    private static void initializeTables() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            // Create Users table
            stmt.execute("""
                        CREATE TABLE IF NOT EXISTS users (
                            user_id INTEGER PRIMARY KEY AUTOINCREMENT,
                            username TEXT UNIQUE NOT NULL,
                            password TEXT NOT NULL,
                            first_name TEXT NOT NULL,
                            last_name TEXT NOT NULL,
                            email TEXT UNIQUE NOT NULL,
                            phone_number TEXT,
                            license_number TEXT,
                            registration_date DATETIME DEFAULT CURRENT_TIMESTAMP,
                            is_active BOOLEAN DEFAULT 1
                        )
                    """);

            // Create Admins table
            stmt.execute("""
                        CREATE TABLE IF NOT EXISTS admins (
                            admin_id INTEGER PRIMARY KEY AUTOINCREMENT,
                            username TEXT UNIQUE NOT NULL,
                            password TEXT NOT NULL,
                            first_name TEXT NOT NULL,
                            last_name TEXT NOT NULL,
                            email TEXT UNIQUE NOT NULL,
                            is_active BOOLEAN DEFAULT 1
                        )
                    """);

            // Create Vehicles table
            stmt.execute("""
                        CREATE TABLE IF NOT EXISTS vehicles (
                            vehicle_id INTEGER PRIMARY KEY AUTOINCREMENT,
                            make TEXT NOT NULL,
                            model TEXT NOT NULL,
                            type TEXT NOT NULL,
                            license_plate TEXT UNIQUE NOT NULL,
                            daily_rate REAL NOT NULL,
                            is_available BOOLEAN DEFAULT 1,
                            color TEXT,
                            year INTEGER,
                            capacity INTEGER,
                            description TEXT
                        )
                    """);

            // Create Bookings table
            stmt.execute("""
                        CREATE TABLE IF NOT EXISTS bookings (
                            booking_id INTEGER PRIMARY KEY AUTOINCREMENT,
                            user_id INTEGER NOT NULL,
                            vehicle_id INTEGER NOT NULL,
                            start_date DATE NOT NULL,
                            end_date DATE NOT NULL,
                            total_amount REAL NOT NULL,
                            status TEXT DEFAULT 'PENDING',
                            booking_date DATETIME DEFAULT CURRENT_TIMESTAMP,
                            notes TEXT,
                            FOREIGN KEY (user_id) REFERENCES users(user_id),
                            FOREIGN KEY (vehicle_id) REFERENCES vehicles(vehicle_id)
                        )
                    """);

            // Insert default admin if not exists
            stmt.execute("""
                        INSERT OR IGNORE INTO admins (username, password, first_name, last_name, email)
                        VALUES ('admin', 'admin123', 'System', 'Administrator', 'admin@vrs.com')
                    """);

            // Insert sample vehicles if table is empty
            stmt.execute(
                    """
                                INSERT OR IGNORE INTO vehicles (make, model, type, license_plate, daily_rate, color, year, capacity, description)
                                VALUES
                                ('Toyota', 'Camry', 'Car', 'ABC123', 50.00, 'Silver', 2022, 5, 'Comfortable sedan for daily use'),
                                ('Honda', 'Civic', 'Car', 'XYZ789', 45.00, 'Blue', 2021, 5, 'Compact and fuel-efficient'),
                                ('Yamaha', 'YZF-R3', 'Bike', 'BIKE001', 25.00, 'Red', 2023, 2, 'Sport bike for adventure'),
                                ('Ford', 'Transit', 'Van', 'VAN001', 80.00, 'White', 2022, 8, 'Spacious van for group travel'),
                                ('BMW', 'X5', 'Car', 'BMW001', 120.00, 'Black', 2023, 7, 'Luxury SUV with premium features')
                            """);
        }
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Error closing database connection: " + e.getMessage());
        }
    }
}

# Smart Vehicle Rental System

A comprehensive desktop application for managing vehicle rentals built with Java and Swing. This system provides an intuitive interface for both users and administrators to handle vehicle bookings, user management, and rental operations.

## Features

### User Features

- **User Registration & Login**: Secure user authentication system
- **Browse Vehicles**: View available vehicles with detailed information
- **Search & Filter**: Find vehicles by type, make, model, or other criteria
- **Vehicle Booking**: Book vehicles for specific date ranges
- **Booking Management**: View, modify, and cancel existing bookings
- **Profile Management**: Update personal information and preferences

### Admin Features

- **Admin Dashboard**: Comprehensive overview of system operations
- **Vehicle Management**: Add, edit, delete, and manage vehicle inventory
- **User Management**: View user accounts and manage user status
- **Booking Management**: Confirm, cancel, and monitor all bookings
- **Real-time Availability**: Track vehicle availability in real-time

### Technical Features

- **Object-Oriented Design**: Clean, modular architecture following OOP principles
- **Database Integration**: SQLite database for reliable data persistence
- **Real-time Updates**: Live updates of vehicle availability and booking status
- **Data Validation**: Comprehensive input validation and error handling
- **Responsive UI**: User-friendly Swing interface with modern look and feel

## System Requirements

- **Java**: JDK 17 or higher
- **Maven**: 3.6 or higher (for building)
- **Operating System**: Windows, macOS, or Linux
- **Memory**: Minimum 512MB RAM
- **Storage**: 50MB free disk space

## Installation & Setup

### Prerequisites

1. Install Java JDK 17 or higher
2. Install Apache Maven 3.6+
3. Ensure JAVA_HOME environment variable is set

### Building the Application

```bash
# Clone or download the project
cd VRS

# Compile the project
mvn clean compile

# Run tests (optional)
mvn test

# Create executable JAR
mvn package
```

### Running the Application

#### Method 1: Using Maven

```bash
mvn exec:java -Dexec.mainClass="com.vrs.VehicleRentalSystemApp"
```

#### Method 2: Using compiled JAR

```bash
java -jar target/vehicle-rental-system-1.0.0-jar-with-dependencies.jar
```

#### Method 3: Direct execution

```bash
java -cp target/classes:target/dependency/* com.vrs.view.LoginWindow
```

## Default Login Credentials

### Admin Access

- **Username**: admin
- **Password**: admin123

### User Access

- Register a new user account through the registration form
- Or create test users through the admin panel

## Project Structure

```
VRS/
├── src/main/java/com/vrs/
│   ├── controller/          # Business logic controllers
│   │   ├── AdminController.java
│   │   ├── BookingController.java
│   │   ├── UserController.java
│   │   └── VehicleController.java
│   ├── database/            # Data access layer
│   │   ├── AdminDAO.java
│   │   ├── BookingDAO.java
│   │   ├── DatabaseConnection.java
│   │   ├── UserDAO.java
│   │   └── VehicleDAO.java
│   ├── model/              # Data models
│   │   ├── Admin.java
│   │   ├── Booking.java
│   │   ├── User.java
│   │   └── Vehicle.java
│   ├── util/               # Utility classes
│   │   ├── AlertUtils.java
│   │   ├── DateUtils.java
│   │   └── FormatUtils.java
│   ├── view/               # User interface
│   │   ├── AdminDashboard.java
│   │   ├── BookingDetailsDialog.java
│   │   ├── BookingDialog.java
│   │   ├── LoginWindow.java
│   │   ├── UserDashboard.java
│   │   ├── UserProfileDialog.java
│   │   ├── UserRegistrationWindow.java
│   │   └── VehicleDetailsDialog.java
│   └── VehicleRentalSystemApp.java
├── src/main/resources/     # Resource files
├── pom.xml                 # Maven configuration
└── README.md              # This file
```

## Database Schema

The application uses SQLite database with the following tables:

### Users Table

- user_id (Primary Key)
- username, password
- first_name, last_name
- email, phone_number
- license_number
- registration_date, is_active

### Vehicles Table

- vehicle_id (Primary Key)
- make, model, type
- license_plate, daily_rate
- is_available, color, year
- capacity, description

### Bookings Table

- booking_id (Primary Key)
- user_id (Foreign Key)
- vehicle_id (Foreign Key)
- start_date, end_date
- total_amount, status
- booking_date, notes

### Admins Table

- admin_id (Primary Key)
- username, password
- first_name, last_name
- email, is_active

## Usage Guide

### For Users

1. **Registration**: Create a new account with valid credentials
2. **Login**: Access the system using your username and password
3. **Browse Vehicles**: View available vehicles in the main dashboard
4. **Search**: Use filters to find specific vehicle types
5. **Book Vehicle**: Select dates and confirm booking
6. **Manage Bookings**: View and cancel bookings from "My Bookings" tab

### For Administrators

1. **Login**: Use admin credentials to access admin dashboard
2. **Vehicle Management**: Add new vehicles, edit existing ones, or remove vehicles
3. **User Management**: View user accounts and manage user status
4. **Booking Oversight**: Monitor all bookings, confirm or cancel as needed
5. **System Monitoring**: Track overall system usage and statistics

## Development Features

- **MVC Architecture**: Clean separation of concerns
- **Exception Handling**: Comprehensive error management
- **Input Validation**: Client-side and server-side validation
- **Database Transactions**: ACID compliance for data integrity
- **Logging**: Built-in logging for debugging and monitoring
- **Extensible Design**: Easy to add new features and modules

## Future Enhancements

- **Payment Integration**: Add payment processing capabilities
- **Email Notifications**: Automated booking confirmations and reminders
- **Mobile App**: Companion mobile application
- **Advanced Reporting**: Detailed analytics and reporting features
- **Multi-location Support**: Support for multiple rental locations
- **Vehicle Tracking**: GPS integration for vehicle monitoring

## Troubleshooting

### Common Issues

1. **Database Connection Error**

   - Ensure SQLite JDBC driver is in classpath
   - Check file permissions in application directory

2. **Java Version Issues**

   - Verify Java 17+ is installed and JAVA_HOME is set correctly
   - Use `java -version` to check current version

3. **Build Failures**

   - Run `mvn clean` before building
   - Ensure Maven dependencies are downloaded

4. **UI Display Issues**
   - Try different look and feel settings
   - Check display scaling settings on high-DPI monitors

### Support

For technical support or bug reports, please check the application logs and ensure all system requirements are met.

## License

This project is developed for educational purposes. All rights reserved.

## Contributors

- Developed as part of Java programming coursework
- Demonstrates real-world application development practices
- Showcases object-oriented programming principles

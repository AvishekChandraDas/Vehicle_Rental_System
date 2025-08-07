# 🚗 Smart Vehicle Rental System

[![Java](https://img.shields.io/badge/Java-17+-orange.svg)](https://www.oracle.com/java/)
[![SQLite](https://img.shields.io/badge/Database-SQLite-003B57.svg)](https://www.sqlite.org/)
[![Swing](https://img.shields.io/badge/GUI-Swing-blue.svg)](https://docs.oracle.com/javase/tutorial/uiswi## 📄 License

This project is developed for **educational purposes** as part of academic coursework. 

---

## 👨‍💻 Author & Acknowledgments

### Author
**Avishek Chandra Das**
- GitHub: [@AvishekChandraDas](https://github.com/AvishekChandraDas)
- Email: [Email](avishekchandradas@gmail.com)
- LinkedIn: [LinkedIn](https://www.linkedin.com/in/avishekchandradas)

### Academic Supervision
**Course Instructor/Supervisor**
- [Khudeja Khanom Anwara](https://metrouni.edu.bd/sites/university/faculty-members/department-of-computer-science-engineering/180)
- Institution: [Metropolitan University](https://metrouni.edu.bd)
- Course:Object-Oriented Programming
- Academic Year: 2025

### Project Context
This Vehicle Rental System was developed as part of academic coursework to demonstrate:
- Advanced Java programming concepts
- Object-oriented design principles
- Database integration with JDBC
- GUI development with Swing
- Software engineering best practices

---

- **Academic Use**: Free to use for learning and educational purposes
- **Commercial Use**: Not permitted without explicit permission
- **Modifications**: Allowed for educational and learning purposes
- **Distribution**: Please maintain attribution to original author

---

## 🌟 Acknowledgments

- **SQLite Team** for the lightweight database engine
- **Oracle** for Java development platform and Swing framework
- **Academic Institution** for providing the learning environment
- **Open Source Community** for inspiration and best practices

---

## 📊 Project Statistics

- **Total Lines of Code**: 5,000+ lines
- **Programming Language**: Java 17+
- **Database**: SQLite with JDBC
- **Architecture Pattern**: MVC (Model-View-Controller)
- **GUI Framework**: Java Swing
- **Development Time**: [Project Duration]
- **Features Implemented**: 20+ core features

---

*⭐ If you found this project helpful, please consider giving it a star on GitHub!*icense](https://img.shields.io/badge/License-Educational-green.svg)](#license)

A comprehensive desktop application for managing vehicle rentals built with Java and Swing. This system provides an intuitive interface for both users and administrators to handle vehicle bookings, user management, and rental operations.

> **Academic Project**: This project was developed as part of advanced Java programming coursework, demonstrating professional software development practices and design patterns.

## 📋 Table of Contents

- [Screenshots](#-screenshots)
- [Features](#-features)
- [Quick Start](#-quick-start)
- [System Requirements](#-system-requirements)
- [Installation & Setup](#️-installation--setup)
- [Project Structure](#-project-structure)
- [Database Schema](#-database-schema)
- [Usage Guide](#-usage-guide)
- [Development Features](#-development-features)
- [Future Enhancements](#-future-enhancements)
- [Troubleshooting](#-troubleshooting)
- [Author & Acknowledgments](#-author--acknowledgments)
- [Contributing](#-contributing)
- [License](#-license)

---

## 📸 Screenshots

### Login Interface
*Clean and user-friendly authentication system*

### User Dashboard
*Intuitive vehicle browsing and booking interface*

### Admin Panel
*Comprehensive management dashboard for administrators*

### Booking System
*Streamlined reservation process with date validation*

---

## 🎯 Features

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

---

## 🛠️ Installation & Setup

### Option 1: Quick Run (Recommended)
```bash
# Clone the repository
git clone https://github.com/AvishekChandraDas/Vehicle_Rental_System.git
cd Vehicle_Rental_System

# Run directly using provided scripts
./run.sh        # macOS/Linux
run.bat         # Windows
```

### Option 2: Maven Build

If you have Maven installed:

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

---

## 🏗️ Project Structure

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

---

## 🗄️ Database Schema

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

---

## 📖 Usage Guide

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

---

## 🔧 Development Features

- **MVC Architecture**: Clean separation of concerns
- **Exception Handling**: Comprehensive error management
- **Input Validation**: Client-side and server-side validation
- **Database Transactions**: ACID compliance for data integrity
- **Logging**: Built-in logging for debugging and monitoring
- **Extensible Design**: Easy to add new features and modules

---

## 🚀 Future Enhancements

## 🚀 Quick Start

### Prerequisites
- Java JDK 17 or higher
- Git (for cloning the repository)

### Clone and Run
```bash
# Clone the repository
git clone https://github.com/AvishekChandraDas/Vehicle_Rental_System.git
cd Vehicle_Rental_System

# Run the application
./run.sh        # On macOS/Linux
# OR
run.bat         # On Windows
```

### Default Login Credentials
- **Admin**: Username: `admin`, Password: `admin123`
- **User**: Register through the application or create via admin panel

---

## 💻 System Requirements

## Future Enhancements

- **Payment Integration**: Add payment processing capabilities
- **Email Notifications**: Automated booking confirmations and reminders
- **Mobile App**: Companion mobile application
- **Advanced Reporting**: Detailed analytics and reporting features
- **Multi-location Support**: Support for multiple rental locations
- **Vehicle Tracking**: GPS integration for vehicle monitoring

---

## 🔧 Troubleshooting

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

---

## 🤝 Contributing

This is an academic project, but suggestions and improvements are welcome! If you'd like to contribute:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/improvement`)
3. Commit your changes (`git commit -am 'Add some improvement'`)
4. Push to the branch (`git push origin feature/improvement`)
5. Create a Pull Request

---

## 📄 License

This project is developed for educational purposes. All rights reserved.

## Contributors

- Developed as part of Java programming coursework
- Demonstrates real-world application development practices
- Showcases object-oriented programming principles

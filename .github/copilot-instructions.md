<!-- Use this file to provide workspace-specific custom instructions to Copilot. For more details, visit https://code.visualstudio.com/docs/copilot/copilot-customization#_use-a-githubcopilotinstructionsmd-file -->

# Vehicle Rental System - Copilot Instructions

This is a Smart Vehicle Rental System built with Java and Swing that demonstrates:

## Architecture & Design Patterns

- **MVC Pattern**: Model-View-Controller architecture with clear separation of concerns
- **DAO Pattern**: Data Access Objects for database operations
- **Object-Oriented Design**: Proper encapsulation, inheritance, and polymorphism
- **Exception Handling**: Comprehensive error handling throughout the application

## Technology Stack

- **Language**: Java 17+
- **GUI Framework**: Java Swing for desktop interface
- **Database**: SQLite with JDBC for data persistence
- **Build Tool**: Apache Maven for dependency management
- **Testing**: JUnit for unit testing

## Key Components

### Models (`com.vrs.model`)

- `User`, `Admin`, `Vehicle`, `Booking` - Core domain models
- Follow JavaBean conventions with proper getters/setters
- Include validation logic and business rules

### Controllers (`com.vrs.controller`)

- Business logic layer between views and data access
- Handle user interactions and coordinate with DAOs
- Implement input validation and error handling

### Database Layer (`com.vrs.database`)

- `DatabaseConnection` - SQLite connection management
- DAO classes for CRUD operations on each entity
- SQL queries with prepared statements for security

### Views (`com.vrs.view`)

- Swing-based user interface components
- Separate windows for login, user dashboard, admin dashboard
- Dialog classes for detailed operations (booking, editing, etc.)

### Utilities (`com.vrs.util`)

- Helper classes for formatting, date handling, and alerts
- Reusable components across the application

## Coding Standards

- Use proper Java naming conventions (camelCase, PascalCase)
- Include comprehensive JavaDoc comments for public methods
- Follow SOLID principles and clean code practices
- Implement proper exception handling with meaningful error messages
- Use try-with-resources for database connections

## Database Design

- Normalized schema with foreign key relationships
- Proper indexing on frequently queried columns
- Default data initialization for testing and demonstration

## UI/UX Guidelines

- Consistent layout and styling across all windows
- Proper form validation with user-friendly error messages
- Responsive design that works on different screen sizes
- Intuitive navigation and clear action buttons

## Security Considerations

- Input validation to prevent SQL injection
- Basic authentication system (note: passwords should be hashed in production)
- Role-based access control (user vs admin)

## Testing Strategy

- Unit tests for business logic methods
- Integration tests for database operations
- Manual testing procedures for UI workflows

When working with this codebase:

1. Maintain the existing architectural patterns
2. Follow the established naming conventions
3. Add proper error handling for new features
4. Update documentation when adding new functionality
5. Ensure database operations use transactions where appropriate
6. Keep the UI consistent with existing design patterns

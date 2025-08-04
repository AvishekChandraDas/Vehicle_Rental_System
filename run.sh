#!/bin/bash

# Vehicle Rental System Startup Script

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "Java is not installed or not in PATH"
    echo "Please install Java 17 or higher"
    exit 1
fi

# Check Java version
JAVA_VERSION=$(java -version 2>&1 | grep "version" | awk '{print $3}' | tr -d '"' | cut -d'.' -f1)
if [ "$JAVA_VERSION" -lt 17 ]; then
    echo "Java 17 or higher is required. Current version: $JAVA_VERSION"
    exit 1
fi

# Get the directory of this script
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )"

# Check if compilation is needed
if [ ! -d "$DIR/target/classes" ] || [ ! "$(ls -A $DIR/target/classes)" ]; then
    echo "Compiling application..."
    mkdir -p "$DIR/target/classes"
    
    javac -cp "$DIR/lib/*" -d "$DIR/target/classes" $(find "$DIR/src/main/java" -name "*.java")
    
    if [ $? -ne 0 ]; then
        echo "Compilation failed!"
        exit 1
    fi
    
    echo "Compilation successful!"
fi

# Check if required dependencies exist
if [ ! -f "$DIR/lib/sqlite-jdbc-3.44.1.0.jar" ]; then
    echo "SQLite JDBC driver not found. Please ensure lib/sqlite-jdbc-3.44.1.0.jar exists."
    exit 1
fi

if [ ! -f "$DIR/lib/slf4j-api-1.7.36.jar" ]; then
    echo "SLF4J API not found. Please ensure lib/slf4j-api-1.7.36.jar exists."
    exit 1
fi

if [ ! -f "$DIR/lib/slf4j-simple-1.7.36.jar" ]; then
    echo "SLF4J Simple not found. Please ensure lib/slf4j-simple-1.7.36.jar exists."
    exit 1
fi

echo "Starting Vehicle Rental System..."
echo "Default admin login - Username: admin, Password: admin123"
echo "Close this terminal to stop the application."

# Run the application
java -cp "$DIR/target/classes:$DIR/lib/*" com.vrs.view.LoginWindow

# Check if SQLite JDBC driver exists
if [ ! -f "$DIR/lib/sqlite-jdbc-3.44.1.0.jar" ]; then
    echo "SQLite JDBC driver not found. Please ensure lib/sqlite-jdbc-3.44.1.0.jar exists."
    exit 1
fi

echo "Starting Vehicle Rental System..."
echo "Default admin login - Username: admin, Password: admin123"
echo "Close this terminal to stop the application."

# Run the application
java -cp "$DIR/target/classes:$DIR/lib/sqlite-jdbc-3.44.1.0.jar" com.vrs.view.LoginWindow

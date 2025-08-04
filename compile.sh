#!/bin/bash

# Compilation script for Vehicle Rental System

echo "Compiling Vehicle Rental System..."

# Get the directory of this script
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )"

# Create target directory
mkdir -p "$DIR/target/classes"

# Clean previous compilation
rm -rf "$DIR/target/classes/*"

# Compile Java files
echo "Compiling Java source files..."
javac -cp "$DIR/lib/sqlite-jdbc-3.44.1.0.jar" -d "$DIR/target/classes" $(find "$DIR/src/main/java" -name "*.java")

if [ $? -eq 0 ]; then
    echo "✅ Compilation successful!"
    echo "You can now run the application with: ./run.sh"
else
    echo "❌ Compilation failed!"
    exit 1
fi

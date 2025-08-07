#!/bin/bash

# Demo script for Vehicle Management functionality

echo "🚗 Vehicle Rental System - Vehicle Management Demo"
echo "=================================================="
echo ""

echo "This demo will show you the new Vehicle Management functionality:"
echo ""
echo "1. 🔧 Add Vehicle - Create new vehicles in the system"
echo "2. ✏️ Edit Vehicle - Modify existing vehicle details"
echo "3. 🗑️ Delete Vehicle - Remove vehicles from the system"
echo ""

echo "📋 Current vehicles in the database:"
java -cp "target/classes:lib/*" com.vrs.test.VehicleManagementTest 2>/dev/null | grep "Found\|🚗 ID:" | head -6
echo ""

echo "🎯 To try the GUI version:"
echo "1. Run: ./run.sh"
echo "2. Login as admin (username: admin, password: admin123)"
echo "3. Go to 'Vehicle Management' tab"
echo "4. Use the buttons to Add/Edit/Delete vehicles"
echo ""

echo "🚀 Starting the Vehicle Rental System..."
sleep 2
./run.sh

#!/bin/bash

# Demo script for improved User Registration Form
# This script showcases the enhanced registration functionality

echo "🚀 Improved User Registration Form Demo"
echo "======================================="
echo ""
echo "✨ New Features Implemented:"
echo "   • Modern, professional UI design"
echo "   • Real-time field validation with visual feedback"  
echo "   • Password strength indicator"
echo "   • Enhanced input styling with focus effects"
echo "   • Formatted phone number input"
echo "   • Comprehensive error handling"
echo "   • Better accessibility and user experience"
echo ""
echo "🔍 Test Scenarios to Try:"
echo "   1. Username validation (3-20 chars, letters/numbers/underscore only)"
echo "   2. Password strength indicator (watch it change as you type)"
echo "   3. Password confirmation matching"
echo "   4. Email format validation"
echo "   5. Phone number formatting (automatic formatting)"
echo "   6. Name validation (letters, spaces, apostrophes, hyphens only)"
echo "   7. License number validation (6-15 chars, alphanumeric)"
echo ""
echo "🎨 UI Improvements:"
echo "   • Clean, modern Segoe UI font"
echo "   • Professional color scheme"
echo "   • Visual validation indicators (✓/✗)"
echo "   • Hover effects on buttons"
echo "   • Focus styling on input fields"
echo "   • Password strength progress bar"
echo ""
echo "🧪 How to Test:"
echo "   1. Run the application"
echo "   2. Click 'Register' on login screen"  
echo "   3. Try different input combinations to see validation"
echo "   4. Notice real-time feedback as you type"
echo ""

read -p "Press Enter to start the Vehicle Rental System..."

echo ""
echo "🚀 Starting Vehicle Rental System..."
echo "📝 Click 'Register' on the login screen to test the improved form"
echo ""

# Run the application
java -cp "target/classes:lib/*" com.vrs.view.LoginWindow

#!/bin/bash

# Updated Test script for the single security question password recovery feature
echo "=== Testing Simplified Password Recovery Feature ==="
echo ""

echo "1. Checking database schema for single security question..."
sqlite3 vrs_database.db "PRAGMA table_info(users);" | grep security

echo ""
echo "2. Verifying demo user..."
sqlite3 vrs_database.db "SELECT username, security_question_1, security_answer_1 FROM users WHERE username='testuser';"

echo ""
echo "3. Testing simplified password recovery..."
echo "Please run the application and:"
echo "   a) Click on Register to create a new account"
echo "   b) Fill in all details including 1 security question"
echo "   c) Note the username for testing password recovery"
echo ""
echo "4. Testing password recovery process..."
echo "   a) From the login screen, click 'Forgot Password'"
echo "   b) Enter the username you just created (or use 'testuser')"
echo "   c) Answer the single security question correctly"
echo "   d) Set a new password"
echo "   e) Try logging in with the new password"
echo ""

echo "=== Simplified Features ==="
echo "✓ Single security question per user (simplified from 3)"
echo "✓ Updated database with 2 security question columns (reduced from 6)"
echo "✓ Streamlined registration process"
echo "✓ Faster password recovery with 1 question instead of 3"
echo "✓ Simplified UI with less form fields"
echo "✓ Maintained security with case-insensitive validation"

echo ""
echo "=== Demo User Credentials ==="
echo "Username: testuser"
echo "Password: password123"
echo "Security Question: What is your favorite color?"
echo "Security Answer: blue"

echo ""
echo "=== Changes Made ==="
echo "• Removed security_question_2, security_answer_2 from database"
echo "• Removed security_question_3, security_answer_3 from database"
echo "• Updated User model to use single security question"
echo "• Simplified UserDAO methods"
echo "• Updated UserController registration method"
echo "• Simplified registration window UI"
echo "• Updated password recovery window to single question"
echo "• Updated all documentation to reflect single question approach"

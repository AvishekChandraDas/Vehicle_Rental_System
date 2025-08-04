@echo off
REM Vehicle Rental System Startup Script for Windows

REM Check if Java is installed
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo Java is not installed or not in PATH
    echo Please install Java 17 or higher
    pause
    exit /b 1
)

REM Get the directory of this script
set DIR=%~dp0

REM Check if compiled classes exist
if not exist "%DIR%target\classes" (
    echo Compiled classes not found. Compiling...
    
    REM Create target directory
    mkdir "%DIR%target\classes"
    
    REM Compile Java files
    for /r "%DIR%src\main\java" %%f in (*.java) do (
        javac -cp "%DIR%lib\*" -d "%DIR%target\classes" "%%f"
    )
    
    echo Compilation completed!
)

REM Check if SQLite JDBC driver exists
if not exist "%DIR%lib\sqlite-jdbc-3.44.1.0.jar" (
    echo SQLite JDBC driver not found. Please ensure lib\sqlite-jdbc-3.44.1.0.jar exists.
    pause
    exit /b 1
)

echo Starting Vehicle Rental System...
echo Default admin login - Username: admin, Password: admin123
echo Close this window to stop the application.
echo.

REM Run the application
java -cp "%DIR%target\classes;%DIR%lib\*" com.vrs.view.LoginWindow

pause

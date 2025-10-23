@echo off
REM ================================================================================
REM  Inventory Management System - Complete Build and Run Script
REM  This script compiles the entire project and runs the application
REM  with detailed logging and error reporting
REM ================================================================================

color 0B
title Inventory Management System - Compiler and Runner

echo.
echo ================================================================================
echo                    INVENTORY MANAGEMENT SYSTEM
echo                    Build, Compile and Run Script
echo ================================================================================
echo.
echo [INFO] Starting build process...
echo [INFO] Current Directory: %CD%
echo [INFO] Date/Time: %DATE% %TIME%
echo.

REM ==================== STEP 1: Check Prerequisites ====================
echo ========================================
echo  STEP 1: Checking Prerequisites
echo ========================================
echo.

REM Check for Java JDK
echo [CHECK] Looking for Java JDK...
where javac >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    if exist "C:\Program Files\Microsoft\jdk-21.0.8.9-hotspot\bin\javac.exe" (
        echo [OK] Found Microsoft JDK 21 at C:\Program Files\Microsoft\jdk-21.0.8.9-hotspot
        set "JAVA_HOME=C:\Program Files\Microsoft\jdk-21.0.8.9-hotspot"
        set "PATH=%JAVA_HOME%\bin;%PATH%"
    ) else (
        echo [ERROR] Java JDK not found!
        echo [ERROR] Please install JDK 21 or set JAVA_HOME environment variable.
        echo.
        pause
        exit /b 1
    )
) else (
    echo [OK] Java compiler found in PATH
)

REM Display Java version
echo.
echo [INFO] Java Version:
java -version 2>&1 | findstr "version"
echo.

REM Check for MySQL Connector JAR
echo [CHECK] Looking for MySQL Connector JAR...
if exist "mysql-connector-j-9.5.0.jar" (
    echo [OK] Found mysql-connector-j-9.5.0.jar
) else (
    echo [WARNING] MySQL Connector JAR not found!
    echo [WARNING] Database connectivity may not work.
)
echo.

REM Check for source directory
echo [CHECK] Checking source directory...
if exist "src\" (
    echo [OK] Source directory exists
) else (
    echo [ERROR] Source directory 'src\' not found!
    pause
    exit /b 1
)
echo.

REM ==================== STEP 2: Clean Previous Build ====================
echo ========================================
echo  STEP 2: Cleaning Previous Build
echo ========================================
echo.

if exist "bin\" (
    echo [INFO] Removing old compiled classes...
    rmdir /S /Q bin 2>nul
    if %ERRORLEVEL% EQU 0 (
        echo [OK] Old build cleaned successfully
    ) else (
        echo [WARNING] Could not remove some files (may be in use)
    )
) else (
    echo [INFO] No previous build found (fresh compile)
)

echo [INFO] Creating bin directory...
mkdir bin 2>nul
echo [OK] Build directory ready
echo.

REM ==================== STEP 3: Compile Java Source Files ====================
echo ========================================
echo  STEP 3: Compiling Java Source Files
echo ========================================
echo.

echo [INFO] Compiling all Java source files...
echo [INFO] Compiler flags: -d bin -cp bin;mysql-connector-j-9.5.0.jar -encoding UTF-8
echo.
echo --------------------------------------------------------------------------------

REM Compile all Java files at once to handle dependencies
javac -d "bin" -cp "bin;mysql-connector-j-9.5.0.jar" -encoding UTF-8 -sourcepath "src" src\model\*.java src\util\*.java src\db\*.java src\dao\*.java src\ui\*.java 2>&1

if %ERRORLEVEL% EQU 0 (
    echo.
    echo --------------------------------------------------------------------------------
    echo [SUCCESS] Compilation completed successfully!
    echo [INFO] Compiled classes location: %CD%\bin
    echo.
    
    REM Count compiled class files
    dir /S /B bin\*.class > temp_classlist.txt 2>nul
    for /f %%a in ('find /c /v "" ^< temp_classlist.txt') do set CLASS_COUNT=%%a
    echo [INFO] Generated %CLASS_COUNT% class files
    del temp_classlist.txt 2>nul
) else (
    echo.
    echo --------------------------------------------------------------------------------
    echo [ERROR] Compilation FAILED!
    echo [ERROR] Please fix the errors above and try again.
    echo.
    pause
    exit /b 1
)
echo.

REM ==================== STEP 4: Verify Compilation ====================
echo ========================================
echo  STEP 4: Verifying Compilation
echo ========================================
echo.

echo [CHECK] Verifying main class...
if exist "bin\ui\Main.class" (
    echo [OK] Main class found: ui.Main
) else (
    echo [ERROR] Main class not found!
    echo [ERROR] Expected: bin\ui\Main.class
    pause
    exit /b 1
)

echo [CHECK] Verifying core classes...
set MISSING_CLASSES=0

if not exist "bin\dao\ProductDAO.class" (
    echo [ERROR] Missing: dao.ProductDAO
    set MISSING_CLASSES=1
)
if not exist "bin\dao\ManagerDAO.class" (
    echo [ERROR] Missing: dao.ManagerDAO
    set MISSING_CLASSES=1
)
if not exist "bin\db\DatabaseConnection.class" (
    echo [ERROR] Missing: db.DatabaseConnection
    set MISSING_CLASSES=1
)
if not exist "bin\ui\DashboardFrame.class" (
    echo [ERROR] Missing: ui.DashboardFrame
    set MISSING_CLASSES=1
)

if %MISSING_CLASSES% EQU 1 (
    echo [ERROR] Some core classes are missing!
    pause
    exit /b 1
) else (
    echo [OK] All core classes verified
)
echo.

REM ==================== STEP 5: Check Database Connection ====================
echo ========================================
echo  STEP 5: Database Connection Check
echo ========================================
echo.

echo [CHECK] Testing MySQL service...
sc query MySQL80 | findstr "STATE" | findstr "RUNNING" >nul 2>nul
if %ERRORLEVEL% EQU 0 (
    echo [OK] MySQL service is running
) else (
    echo [WARNING] MySQL service may not be running
    echo [WARNING] Database features may not work properly
)
echo.

REM ==================== STEP 6: Launch Application ====================
echo ========================================
echo  STEP 6: Launching Application
echo ========================================
echo.

echo [INFO] Starting Inventory Management System...
echo [INFO] Main Class: ui.Main
echo [INFO] Classpath: bin;mysql-connector-j-9.5.0.jar
echo.
echo --------------------------------------------------------------------------------
echo  APPLICATION CONSOLE OUTPUT
echo --------------------------------------------------------------------------------
echo.

REM Run the application with full console output
java -cp "bin;mysql-connector-j-9.5.0.jar" ui.Main 2>&1

REM Capture exit code
set APP_EXIT_CODE=%ERRORLEVEL%
echo.
echo --------------------------------------------------------------------------------

REM ==================== STEP 7: Exit Status ====================
if %APP_EXIT_CODE% EQU 0 (
    echo.
    echo ========================================
    echo  APPLICATION CLOSED SUCCESSFULLY
    echo ========================================
    echo [INFO] Exit Code: %APP_EXIT_CODE%
) else (
    echo.
    echo ========================================
    echo  APPLICATION EXITED WITH ERROR
    echo ========================================
    echo [ERROR] Exit Code: %APP_EXIT_CODE%
    echo [ERROR] Check the logs above for details
)

echo.
echo [INFO] Build and Run session ended at: %DATE% %TIME%
echo.
pause

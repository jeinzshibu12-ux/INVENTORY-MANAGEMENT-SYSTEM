# 🚀 How to Run the Inventory Management System

## Quick Start

Simply **double-click** on `compile-and-run.bat` to compile and run the application!

---

## What the Script Does

The `compile-and-run.bat` file is a comprehensive build and run script that:

### ✅ Step 1: Checks Prerequisites
- Verifies Java JDK installation (JDK 21 recommended)
- Checks for MySQL Connector JAR
- Validates source directory structure

### ✅ Step 2: Cleans Previous Build
- Removes old compiled classes from `bin\` folder
- Creates fresh build directory

### ✅ Step 3: Compiles Java Source Files
- Compiles all Java files in correct order:
  - `model` package (Product, Manager, Alert)
  - `util` package (PasswordHasher, DateUtil, FileUtil, ReportGenerator)
  - `db` package (DatabaseConnection)
  - `dao` package (ProductDAO, ManagerDAO)
  - `ui` package (LoginFrame, DashboardFrame, Dialogs)
- Shows compilation warnings and errors with detailed logging

### ✅ Step 4: Verifies Compilation
- Checks if main class (`ui.Main`) exists
- Verifies all core classes are compiled correctly

### ✅ Step 5: Database Connection Check
- Tests if MySQL service is running
- Warns if database is not available

### ✅ Step 6: Launches Application
- Runs the application with proper classpath
- Shows all console output and logs
- Displays any runtime errors or exceptions

---

## Available Batch Files

| File | Purpose |
|------|---------|
| **compile-and-run.bat** | ✅ Main script - Compiles and runs with full logging |
| **test-db.bat** | 🗄️ Tests database connection and shows statistics |

---

## Detailed Logging Features

The script provides:
- ✅ **Build Success/Failure Messages** - Clear indication of compilation status
- ⚠️ **Compiler Warnings** - Shows deprecated API usage and other warnings
- ❌ **Error Details** - Displays exact line numbers and error messages
- 📊 **Statistics** - Shows number of files compiled and classes generated
- 🗄️ **Database Status** - MySQL service running status
- 🖥️ **Application Logs** - All console output from the running application
- 📝 **Exit Codes** - Shows application exit status

---

## What to Look For

### ✅ Successful Build
```
[SUCCESS] Compilation completed successfully!
[INFO] Generated XX class files
[OK] All core classes verified
[OK] MySQL service is running
Database connected successfully!
```

### ❌ Build Errors
```
[ERROR] Compilation FAILED!
src\package\File.java:XX: error: cannot find symbol
```
- Check the error message for file name and line number
- Fix the error in the source code
- Run the script again

### ⚠️ Warnings
```
Note: Some files use or override a deprecated API.
Note: Recompile with -Xlint:deprecation for details.
```
- Warnings are informational and don't prevent the app from running
- Application will still work normally

---

## Default Login Credentials

**Username:** `admin`  
**Password:** `admin123`

*(Or any manager credentials you've created)*

---

## System Requirements

- ✅ **Windows 10/11** with Command Prompt or PowerShell
- ✅ **Java JDK 21** (or compatible version)
- ✅ **MySQL 8.0** running as a service
- ✅ **Database:** `inventory_db` with proper tables
- ✅ **MySQL User:** `user` with password `1234`

---

## Troubleshooting

### Error: "Java JDK not found"
**Solution:** Install JDK 21 or set `JAVA_HOME` environment variable

### Error: "MySQL service is not running"
**Solution:** Start MySQL service via Services panel (services.msc)

### Error: "Database connection failed"
**Solution:** 
1. Check MySQL service is running
2. Verify database credentials in `config.properties`
3. Run `test-db.bat` to test connection

### Application won't start after successful build
**Solution:**
1. Check if `bin\ui\Main.class` exists
2. Look for error messages in console output
3. Verify MySQL connector JAR is present

---

## Need Help?

Check the console output carefully - the script shows:
- **[INFO]** - Information messages
- **[OK]** - Success confirmations
- **[WARNING]** - Non-critical issues
- **[ERROR]** - Critical errors that need fixing
- **[CHECK]** - Validation steps

All errors are displayed with detailed descriptions to help you fix them!

---

**Happy Coding! 🎉**

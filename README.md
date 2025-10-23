# Inventory Management System

A full-featured desktop application for managing inventory with Java Swing GUI and MySQL database.

## 🚀 Features

- **User Authentication**: Secure login with SHA-256 password hashing
- **Forgot Password**: Reset password using security questions
- **Inventory Management**: 
  - View all products in a sortable table
  - Add new products
  - Edit existing products
  - Delete products with confirmation
  - Search by name, section, or supplier
- **Database Integration**: MySQL backend with CRUD operations
- **Modern GUI**: Java Swing with system look-and-feel

## Screenshot

<img width="507" height="321" alt="image" src="https://github.com/user-attachments/assets/f3479fd8-eca8-48e8-91bf-3457e8b8a2de" />
User Login Panel


<img width="1483" height="872" alt="image" src="https://github.com/user-attachments/assets/b10b4901-71ea-459e-9bcc-48267b8934f6" />
Main Panel

## 📋 Requirements

- **Java**: JDK 21 (LTS) or higher
- **MySQL**: 5.7+ or 8.0+
- **Operating System**: Windows (PowerShell required)

## 🛠️ Installation

1. **Install JDK 21** (if not already installed):
   ```bash
   winget install Microsoft.OpenJDK.21
   ```

2. **Setup MySQL Database**:
   ```bash
   mysql -u root -p < setup_database.sql
   ```
   
   Or manually:
   ```bash
   Get-Content setup_database.sql | mysql -u user -p1234
   ```

3. **Configure Database Connection**:
   Edit `config.properties` with your MySQL credentials:
   ```properties
   db.url=jdbc:mysql://localhost:3306/inventory_db
   db.username=user
   db.password=1234
   ```

## 🎮 Usage

### Quick Start (Recommended)
Double-click `start.bat` - This will automatically build and run the application!

### Individual Commands

**Build Only:**
```bash
build.bat
```

**Run Only (requires build first):**
```bash
run.bat
```

**Or use PowerShell scripts:**
```bash
powershell -ExecutionPolicy Bypass -File "scripts\build.ps1"
powershell -ExecutionPolicy Bypass -File "scripts\run.ps1"
```

## 🔐 Default Login Credentials

- **Username**: `admin`
- **Password**: `admin123`

### Forgot Password Recovery
1. Click "Forgot Password?" on login screen
2. Enter your username
3. Answer your security question
4. Set a new password

**Default Security Q&A:**
- Question: "What is your favorite color?"
- Answer: "blue"

## 📁 Project Structure

```
Inventory Management System/
├── src/
│   ├── dao/              # Data Access Objects
│   │   ├── ManagerDAO.java
│   │   └── ProductDAO.java
│   ├── db/               # Database connection
│   │   └── DatabaseConnection.java
│   ├── model/            # Data models
│   │   ├── Alert.java
│   │   ├── Manager.java
│   │   └── Product.java
│   ├── ui/               # User interface
│   │   ├── LoginFrame.java
│   │   ├── DashboardFrame.java
│   │   ├── ProductDialog.java
│   │   ├── ForgotPasswordDialog.java
│   │   └── Main.java
│   └── util/             # Utilities
│       ├── DateUtil.java
│       ├── FileUtil.java
│       └── PasswordHasher.java
├── scripts/              # Build/run scripts
│   ├── build.ps1
│   ├── run.ps1
│   └── set-java21.ps1
├── bin/                  # Compiled classes
├── Reports/              # Generated reports
├── config.properties     # Database configuration
├── setup_database.sql    # Database schema
├── build.bat            # Build script (Windows)
├── run.bat              # Run script (Windows)
└── start.bat            # Build & Run (Windows)
```

## 🗄️ Database Schema

### Tables

**managers**
- `id` (INT, Primary Key, Auto Increment)
- `username` (VARCHAR, Unique)
- `password_hash` (VARCHAR)
- `security_question` (VARCHAR)
- `security_answer` (VARCHAR)
- `created_at` (TIMESTAMP)

**products**
- `product_id` (INT, Primary Key, Auto Increment)
- `name` (VARCHAR)
- `section` (VARCHAR)
- `supplier` (VARCHAR)
- `quantity` (INT)
- `price` (DECIMAL)
- `expiry_date` (DATE)
- `created_at` (DATE)
- `updated_at` (TIMESTAMP)

**alerts**
- `alert_id` (INT, Primary Key, Auto Increment)
- `message` (TEXT)
- `type` (ENUM: 'LowStock', 'Expiry')
- `created_at` (TIMESTAMP)

## 🎯 Key Operations

### Add Product
1. Click "Add Product" button
2. Fill in product details
3. Click "Save"

### Edit Product
1. Select a product from the table
2. Click "Edit Product"
3. Modify details
4. Click "Save"

### Delete Product
1. Select a product from the table
2. Click "Delete Product"
3. Confirm deletion

### Search Products
1. Enter keyword in search field
2. Click "Search"
3. Click "Refresh" to view all products again

## 🔧 Development

### Build System
- **Compiler**: javac (JDK 21)
- **Source Encoding**: UTF-8
- **Output Directory**: `bin/`
- **Classpath**: Includes MySQL Connector/J 9.5.0

### VS Code Configuration
The project includes `.vscode/` configuration for:
- Java 21 runtime settings
- Build tasks
- Run tasks

Press `Ctrl+Shift+B` to build from VS Code.

## 📝 Sample Data

The database setup includes:
- 1 admin user
- 10 sample products across various sections
- 3 sample alerts

## 🐛 Troubleshooting

**Build fails:**
- Ensure JDK 21 is installed: `java -version`
- Check Java path in `.vscode/settings.json`

**Database connection fails:**
- Verify MySQL is running
- Check credentials in `config.properties`
- Ensure `inventory_db` database exists

**GUI doesn't appear:**
- Check console for error messages
- Ensure database is accessible
- Try running with: `.\start.bat`

## 📄 License

This is an educational project for learning Java Swing and database integration.

## 👨‍💻 Tech Stack

- **Language**: Java 21 (LTS)
- **GUI Framework**: Java Swing
- **Database**: MySQL 8.0+
- **JDBC Driver**: MySQL Connector/J 9.5.0
- **Build Tool**: PowerShell + Batch scripts
- **IDE Support**: VS Code with Java extensions

---

**Enjoy managing your inventory! 📦**

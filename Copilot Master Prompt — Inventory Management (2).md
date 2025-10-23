Copilot Master Prompt — Inventory Management System (Java 24, Swing, MySQL, JDBC)
# MASTER PROMPT: Inventory Management System (Java 24, Swing, MySQL, JDBC)

## GOAL
Develop a complete **Inventory Management System** desktop application using **Java 24**, **Swing (AWT + modern UI design)**, and **MySQL** connected via **JDBC**.  
The system should include **authentication (login + password reset)**, **stock alerts**, **report generation**, and **section-wise stock management**.  

The project must be **fully runnable** in **Windows 11 with VSCode** using a single batch file command:  
`./run.bat`

---

## TECHNOLOGY STACK
| Component | Tool / Framework |
|------------|------------------|
| Programming Language | Java 24 |
| GUI | Swing + AWT (Dark Mode Theme) |
| Backend | MySQL |
| Connectivity | JDBC (mysql-connector-j.jar) |
| IDE | Visual Studio Code |
| Build System | Manual (no Maven) |
| OS Target | Windows 11 |
| Batch File | `run.bat` for compilation + execution |

---

## PROJECT OVERVIEW
The system will help shop owners or warehouse managers manage inventory efficiently.  
The manager can:
- Add, edit, and delete product items.
- Organize items section-wise (Electronics, Stationery, Food, etc.).
- Search, filter, and sort items easily.
- Get **alerts when stock runs low** or **when food items are nearing expiry**.
- Generate and download **monthly reports** in human-readable bill format (PDF or TXT).
- Use a secure **login system** with password reset.

---

## KEY FEATURES

### 🧍‍♂️ Manager Login System
- Login screen with username and password.
- Password stored as hashed value in DB.
- “Forgot Password” option → resets via security question or OTP simulation (simple popup input).
- Manager dashboard opens after login.

---

### 📦 Inventory Management
- Add new products (name, quantity, price, supplier, section, expiry date if applicable).
- Edit or delete existing products.
- Display products in a **JTable** with color-coded rows:
  - ⚠ **Red row** → out of stock.
  - 🟡 **Yellow row** → near expiry (for food).
  - 🟢 **Green row** → normal stock.
- Search and sort products by:
  - Section
  - Supplier
  - Name
  - Quantity (low to high)
- Low stock alert popup and email/message simulation to admin (simple notification dialog).

---

### 🧾 Monthly Report Generator
- At month end, allow manager to **generate a sales/stock report**.
- Export as `.txt` or `.pdf` (simple text file in `Reports/` folder).
- Format similar to a **bill/invoice**:

Inventory Monthly Report - March 2025
Section: Electronics
Product Name | Qty | Price | Status
Charger 5 450 Low Stock ⚠
Mouse 40 350 OK
Total Items: 45
Generated On: 31-03-2025
- Show “Report Generated Successfully” dialog after saving.

---

### 🗂 Section-wise Management
- Categories/Sections: `Electronics`, `Stationery`, `Food`, `Grocery`, `Clothing`
- Each section has a filtered product view.
- Separate search/sort for each section panel.

---

### 🚨 Stock & Expiry Alerts
- If quantity < threshold (e.g., 10), trigger **Low Stock Alert** popup.
- For items with expiry date:
- If expiry within 7 days → highlight ⚠ in table + show alert.
- Auto-alert on login or refresh.
- Alerts stored in a “notifications” area (right panel).

---

### 💾 Database Design (MySQL)

```sql
CREATE DATABASE inventory_db;
USE inventory_db;

CREATE TABLE managers (
id INT AUTO_INCREMENT PRIMARY KEY,
username VARCHAR(50) UNIQUE,
password_hash VARCHAR(255),
security_question VARCHAR(255),
security_answer VARCHAR(255)
);

CREATE TABLE products (
product_id INT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(100),
section VARCHAR(50),
supplier VARCHAR(100),
quantity INT,
price DECIMAL(10,2),
expiry_date DATE NULL,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE alerts (
alert_id INT AUTO_INCREMENT PRIMARY KEY,
message VARCHAR(255),
type ENUM('LowStock','Expiry'),
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

PROJECT STRUCTURE
InventoryManagement/
├── lib/
│   └── mysql-connector-j.jar
├── src/
│   ├── Main.java
│   ├── db/
│   │   └── DatabaseConnection.java
│   ├── model/
│   │   ├── Product.java
│   │   ├── Manager.java
│   │   └── Alert.java
│   ├── dao/
│   │   ├── ProductDAO.java
│   │   ├── ManagerDAO.java
│   │   └── AlertDAO.java
│   ├── service/
│   │   ├── InventoryService.java
│   │   ├── ReportGenerator.java
│   │   └── AuthService.java
│   ├── ui/
│   │   ├── LoginFrame.java
│   │   ├── DashboardFrame.java
│   │   ├── AddProductDialog.java
│   │   ├── EditProductDialog.java
│   │   ├── AlertsPanel.java
│   │   ├── ReportPanel.java
│   │   └── CustomUIManager.java
│   └── util/
│       ├── PasswordHasher.java
│       ├── DateUtil.java
│       └── FileUtil.java
├── config.properties
├── run.bat
└── README.md

UI DESIGN (Swing + AWT)
LoginFrame

Fields: Username, Password.

Buttons: Login, Forgot Password, Exit.

Dark modern theme (UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel")).

Rounded buttons and panels.

DashboardFrame

Menu bar: “Inventory”, “Reports”, “Alerts”, “Logout”.

Left panel: sections list (Electronics, Stationery, Food, etc.)

Center: JTable showing inventory for selected section.

Right panel: alerts/notifications.

Bottom: status bar (DB connected, last sync time).

Add/Edit Dialogs

Text fields for all product info.

Date picker for expiry.

ComboBox for Section.

Save/Cancel buttons.

ALERT SYSTEM IMPLEMENTATION

InventoryService.checkLowStock() checks items with qty < 10.

InventoryService.checkExpiry() checks items expiring within 7 days.

Both functions trigger:

Popup alert (JOptionPane)

Database record in alerts table

Visual highlight in JTable row (red/yellow background)

MONTHLY REPORT MODULE

ReportGenerator.generateMonthlyReport(LocalDate month)

Queries all products.

Groups by section.

Writes formatted text file to /Reports/YYYY-MM.txt.

Uses FileUtil to ensure folder exists.

Optional PDF using iText if available, else fallback to .txt.

CONFIGURATION FILE

config.properties

db.url=jdbc:mysql://localhost:3306/inventory_db
db.username=root
db.password=yourpassword

ERROR HANDLING

Use try-with-resources for JDBC.

Display user-friendly error messages using JOptionPane.showMessageDialog.

If DB not reachable → show retry dialog.

All input fields validated (no empty product name, quantity ≥ 0).

COMPILE & RUN INSTRUCTIONS (Windows 11)

run.bat

@echo off
title Inventory Management System
echo Compiling project...
javac -cp ".;lib/mysql-connector-j.jar" -d bin src/**/*.java
if %errorlevel% neq 0 (
    echo Compilation failed.
    pause
    exit /b
)
echo Running application...
java -cp "bin;lib/mysql-connector-j.jar" Main
pause


Then just double-click run.bat.

DELIVERABLES FOR COPILOT TO GENERATE

Full Java source code in /src/ structure above.

config.properties file with DB credentials.

MySQL schema SQL file inventory_db.sql with sample data.

run.bat script for one-click run.

README.md with:

Setup instructions

MySQL configuration

How to use the app

Screenshots placeholder sections

Troubleshooting (JDBC errors)

Optional: /Reports/ folder for generated reports.

CODING STANDARDS

Follow OOP (Encapsulation, Abstraction, Inheritance, Polymorphism).

Use prepared statements only.

No TODOs left unimplemented.

No hardcoded passwords.

Modular and readable code.

UI and DB logic must be separated cleanly.
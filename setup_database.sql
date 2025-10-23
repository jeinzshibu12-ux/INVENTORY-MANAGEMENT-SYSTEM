-- Inventory Management System Database Schema
-- Drop database if exists and recreate
DROP DATABASE IF EXISTS inventory_db;
CREATE DATABASE inventory_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE inventory_db;

-- Managers table
CREATE TABLE managers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    security_question VARCHAR(255) NOT NULL,
    security_answer VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- Products table
CREATE TABLE products (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    section VARCHAR(50) NOT NULL,
    supplier VARCHAR(100) NOT NULL,
    quantity INT NOT NULL DEFAULT 0,
    price DECIMAL(10, 2) NOT NULL,
    expiry_date DATE,
    created_at DATE NOT NULL DEFAULT (CURRENT_DATE),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_section (section),
    INDEX idx_supplier (supplier),
    INDEX idx_expiry_date (expiry_date)
) ENGINE=InnoDB;

-- Alerts table
CREATE TABLE alerts (
    alert_id INT AUTO_INCREMENT PRIMARY KEY,
    message TEXT NOT NULL,
    type ENUM('LowStock', 'Expiry') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_type (type),
    INDEX idx_created_at (created_at)
) ENGINE=InnoDB;

-- Insert default manager (username: admin, password: admin123)
-- Password hash uses salt:hash format from PasswordHasher utility
INSERT INTO managers (username, password_hash, security_question, security_answer) 
VALUES (
    'admin',
    'ykLY4vYPVa9tTiK1Pbtm2g==:lTE5GefZq7LNnIwKAHf3Ad+69x3k52UJazaLYmp/tD4=',
    'What is your favorite color?',
    'blue'
);

-- Insert sample products
INSERT INTO products (name, section, supplier, quantity, price, expiry_date) VALUES
('Apple iPhone 14', 'Electronics', 'Apple Inc.', 50, 999.99, '2026-12-31'),
('Samsung Galaxy S23', 'Electronics', 'Samsung', 35, 899.99, '2026-11-30'),
('Dell XPS 13 Laptop', 'Electronics', 'Dell', 20, 1299.99, '2027-06-30'),
('Organic Apples', 'Produce', 'Local Farm Co.', 100, 4.99, '2025-11-01'),
('Fresh Milk', 'Dairy', 'Dairy Fresh Inc.', 80, 3.49, '2025-10-30'),
('Whole Wheat Bread', 'Bakery', 'Artisan Bakery', 45, 2.99, '2025-10-28'),
('Greek Yogurt', 'Dairy', 'Dairy Fresh Inc.', 60, 5.99, '2025-11-15'),
('Premium Coffee Beans', 'Beverages', 'Coffee Masters', 150, 12.99, '2026-03-31'),
('Orange Juice', 'Beverages', 'Juice Co.', 75, 4.49, '2025-11-10'),
('Chicken Breast', 'Meat', 'Fresh Meats Ltd.', 40, 8.99, '2025-10-26');

-- Insert sample alerts
INSERT INTO alerts (message, type) VALUES
('Low stock alert: Dell XPS 13 Laptop - Only 20 units remaining', 'LowStock'),
('Expiry warning: Whole Wheat Bread expires on 2025-10-28', 'Expiry'),
('Expiry warning: Chicken Breast expires on 2025-10-26', 'Expiry');

-- Display summary
SELECT 'Database setup completed!' AS Status;
SELECT COUNT(*) AS 'Total Managers' FROM managers;
SELECT COUNT(*) AS 'Total Products' FROM products;
SELECT COUNT(*) AS 'Total Alerts' FROM alerts;

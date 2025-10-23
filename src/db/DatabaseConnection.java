package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Database connection manager using Singleton pattern
 */
public class DatabaseConnection {
    
    private static DatabaseConnection instance;
    private Connection connection;
    private String url;
    private String username;
    private String password;
    
    private DatabaseConnection() {
        loadConfiguration();
    }
    
    /**
     * Load database configuration from config.properties
     */
    private void loadConfiguration() {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream("config.properties")) {
            props.load(fis);
            this.url = props.getProperty("db.url");
            this.username = props.getProperty("db.username");
            this.password = props.getProperty("db.password");
        } catch (IOException e) {
            System.err.println("Error loading configuration: " + e.getMessage());
            // Set default values
            this.url = "jdbc:mysql://localhost:3306/inventory_db";
            this.username = "root";
            this.password = "yourpassword";
        }
    }
    
    /**
     * Get the singleton instance
     */
    public static DatabaseConnection getInstance() {
        if (instance == null) {
            synchronized (DatabaseConnection.class) {
                if (instance == null) {
                    instance = new DatabaseConnection();
                }
            }
        }
        return instance;
    }
    
    /**
     * Get database connection
     */
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                // Load MySQL JDBC driver
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(url, username, password);
                System.out.println("Database connected successfully!");
            } catch (ClassNotFoundException e) {
                throw new SQLException("MySQL JDBC Driver not found", e);
            }
        }
        return connection;
    }
    
    /**
     * Test database connection
     */
    public boolean testConnection() {
        try (Connection conn = getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            System.err.println("Database connection test failed: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Close database connection
     */
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }
    
    /**
     * Get connection URL
     */
    public String getUrl() {
        return url;
    }
    
    /**
     * Get database username
     */
    public String getUsername() {
        return username;
    }
}

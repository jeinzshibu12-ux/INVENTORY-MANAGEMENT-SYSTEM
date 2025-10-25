package util;

import db.DatabaseConnection;
import java.sql.Connection;

/**
 * Quick test utility to verify database connectivity
 */
public class TestConnection {
    public static void main(String[] args) {
        System.out.println("=== Database Connection Test ===");
        System.out.println();
        
        DatabaseConnection dbConn = DatabaseConnection.getInstance();
        System.out.println("Database URL: " + dbConn.getUrl());
        System.out.println("Database User: " + dbConn.getUsername());
        System.out.println();
        
        try {
            System.out.println("Attempting connection...");
            Connection conn = dbConn.getConnection();
            
            if (conn != null && !conn.isClosed()) {
                System.out.println("✓ SUCCESS: Database connected!");
                System.out.println("Connection valid: " + conn.isValid(5));
                System.out.println("Catalog: " + conn.getCatalog());
                System.out.println("Schema: " + conn.getSchema());
            } else {
                System.out.println("✗ FAILED: Connection is null or closed");
            }
        } catch (Exception e) {
            System.out.println("✗ ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

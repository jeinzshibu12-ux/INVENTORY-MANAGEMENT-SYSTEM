package dao;

import db.DatabaseConnection;
import java.sql.*;
import model.Manager;
import util.PasswordHasher;

public class ManagerDAO {
    
    public Manager authenticate(String username, String password) throws SQLException {
        // First, get the user by username
        Manager manager = findByUsername(username);
        
        if (manager != null) {
            // Verify the password using PasswordHasher which handles salt:hash format
            if (PasswordHasher.verifyPassword(password, manager.getPasswordHash())) {
                return manager;
            }
        }
        return null;
    }
    
    public Manager findByUsername(String username) throws SQLException {
        String query = "SELECT * FROM managers WHERE username = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return new Manager(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password_hash"),
                    rs.getString("security_question"),
                    rs.getString("security_answer")
                );
            }
        }
        return null;
    }
    
    public boolean updatePassword(int managerId, String newPasswordHash) throws SQLException {
        String query = "UPDATE managers SET password_hash = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, newPasswordHash);
            stmt.setInt(2, managerId);
            return stmt.executeUpdate() > 0;
        }
    }
    
    public boolean createManager(Manager manager) throws SQLException {
        String query = "INSERT INTO managers (username, password_hash, security_question, security_answer) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, manager.getUsername());
            stmt.setString(2, manager.getPasswordHash());
            stmt.setString(3, manager.getSecurityQuestion());
            stmt.setString(4, manager.getSecurityAnswer());
            return stmt.executeUpdate() > 0;
        }
    }
}

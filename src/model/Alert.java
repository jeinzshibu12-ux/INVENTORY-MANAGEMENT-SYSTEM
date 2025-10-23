package model;

import java.time.LocalDateTime;

/**
 * Alert model class
 */
public class Alert {
    private int alertId;
    private String message;
    private AlertType type;
    private LocalDateTime createdAt;
    
    // Alert types enum
    public enum AlertType {
        LowStock,
        Expiry
    }
    
    // Constructors
    public Alert() {
    }
    
    public Alert(int alertId, String message, AlertType type, LocalDateTime createdAt) {
        this.alertId = alertId;
        this.message = message;
        this.type = type;
        this.createdAt = createdAt;
    }
    
    // Constructor without ID (for new alerts)
    public Alert(String message, AlertType type) {
        this.message = message;
        this.type = type;
        this.createdAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public int getAlertId() {
        return alertId;
    }
    
    public void setAlertId(int alertId) {
        this.alertId = alertId;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public AlertType getType() {
        return type;
    }
    
    public void setType(AlertType type) {
        this.type = type;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    @Override
    public String toString() {
        return "Alert{" +
                "alertId=" + alertId +
                ", message='" + message + '\'' +
                ", type=" + type +
                ", createdAt=" + createdAt +
                '}';
    }
}

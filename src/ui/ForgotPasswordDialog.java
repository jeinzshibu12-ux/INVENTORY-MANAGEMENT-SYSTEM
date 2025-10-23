package ui;

import dao.ManagerDAO;
import model.Manager;
import util.PasswordHasher;

import javax.swing.*;
import java.awt.*;

public class ForgotPasswordDialog extends JDialog {
    private JTextField usernameField;
    private JLabel questionLabel;
    private JTextField answerField;
    private JPasswordField newPasswordField;
    private JPasswordField confirmPasswordField;
    private JButton verifyButton;
    private JButton resetButton;
    private JButton cancelButton;
    private ManagerDAO managerDAO;
    private Manager manager;
    private boolean passwordReset = false;
    
    public ForgotPasswordDialog(Frame parent) {
        super(parent, "Forgot Password", true);
        managerDAO = new ManagerDAO();
        initComponents();
    }
    
    private void initComponents() {
        setSize(450, 350);
        setLocationRelativeTo(getParent());
        setResizable(false);
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Title
        JLabel titleLabel = new JLabel("Reset Your Password", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        
        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Username
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Username:"), gbc);
        
        gbc.gridx = 1;
        usernameField = new JTextField(20);
        formPanel.add(usernameField, gbc);
        
        // Security Question (initially hidden)
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Security Question:"), gbc);
        
        gbc.gridx = 1;
        questionLabel = new JLabel("");
        questionLabel.setForeground(Color.BLUE);
        formPanel.add(questionLabel, gbc);
        
        // Answer
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Your Answer:"), gbc);
        
        gbc.gridx = 1;
        answerField = new JTextField(20);
        answerField.setEnabled(false);
        formPanel.add(answerField, gbc);
        
        // New Password
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("New Password:"), gbc);
        
        gbc.gridx = 1;
        newPasswordField = new JPasswordField(20);
        newPasswordField.setEnabled(false);
        formPanel.add(newPasswordField, gbc);
        
        // Confirm Password
        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Confirm Password:"), gbc);
        
        gbc.gridx = 1;
        confirmPasswordField = new JPasswordField(20);
        confirmPasswordField.setEnabled(false);
        formPanel.add(confirmPasswordField, gbc);
        
        mainPanel.add(formPanel, BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        verifyButton = new JButton("Verify Username");
        verifyButton.addActionListener(e -> verifyUsername());
        buttonPanel.add(verifyButton);
        
        resetButton = new JButton("Reset Password");
        resetButton.setEnabled(false);
        resetButton.addActionListener(e -> resetPassword());
        buttonPanel.add(resetButton);
        
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());
        buttonPanel.add(cancelButton);
        
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private void verifyUsername() {
        String username = usernameField.getText().trim();
        
        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please enter your username.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            manager = managerDAO.findByUsername(username);
            
            if (manager != null) {
                // Display security question
                questionLabel.setText(manager.getSecurityQuestion());
                answerField.setEnabled(true);
                newPasswordField.setEnabled(true);
                confirmPasswordField.setEnabled(true);
                resetButton.setEnabled(true);
                usernameField.setEnabled(false);
                verifyButton.setEnabled(false);
                
                JOptionPane.showMessageDialog(this,
                    "Username verified! Please answer your security question.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                    "Username not found.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Error verifying username: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void resetPassword() {
        String answer = answerField.getText().trim();
        String newPassword = new String(newPasswordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());
        
        if (answer.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please fill in all fields.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Verify security answer (case-insensitive)
        if (!answer.equalsIgnoreCase(manager.getSecurityAnswer())) {
            JOptionPane.showMessageDialog(this,
                "Incorrect answer to security question.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Validate password match
        if (!newPassword.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this,
                "Passwords do not match.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Validate password length
        if (newPassword.length() < 6) {
            JOptionPane.showMessageDialog(this,
                "Password must be at least 6 characters long.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            // Hash new password and update
            String newPasswordHash = PasswordHasher.hashPassword(newPassword);
            manager.setPasswordHash(newPasswordHash);
            
            if (managerDAO.updatePassword(manager.getId(), newPasswordHash)) {
                passwordReset = true;
                JOptionPane.showMessageDialog(this,
                    "Password reset successfully! You can now login with your new password.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Failed to reset password. Please try again.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Error resetting password: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public boolean isPasswordReset() {
        return passwordReset;
    }
}

package ui;

import dao.ManagerDAO;
import model.Manager;
import util.PasswordHasher;

import javax.swing.*;
import java.awt.*;

public class RegisterManagerDialog extends JDialog {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JTextField securityQuestionField;
    private JTextField securityAnswerField;
    private JButton registerButton;
    private JButton cancelButton;
    private ManagerDAO managerDAO;
    private boolean registered = false;
    
    public RegisterManagerDialog(Frame parent) {
        super(parent, "Register New Manager", true);
        managerDAO = new ManagerDAO();
        initComponents();
    }
    
    private void initComponents() {
        setSize(450, 400);
        setLocationRelativeTo(getParent());
        setResizable(false);
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Title
        JLabel titleLabel = new JLabel("Create New Manager Account", SwingConstants.CENTER);
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
        
        // Password
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Password:"), gbc);
        
        gbc.gridx = 1;
        passwordField = new JPasswordField(20);
        formPanel.add(passwordField, gbc);
        
        // Confirm Password
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Confirm Password:"), gbc);
        
        gbc.gridx = 1;
        confirmPasswordField = new JPasswordField(20);
        formPanel.add(confirmPasswordField, gbc);
        
        // Security Question
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Security Question:"), gbc);
        
        gbc.gridx = 1;
        securityQuestionField = new JTextField(20);
        securityQuestionField.setToolTipText("e.g., What is your favorite color?");
        formPanel.add(securityQuestionField, gbc);
        
        // Security Answer
        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Security Answer:"), gbc);
        
        gbc.gridx = 1;
        securityAnswerField = new JTextField(20);
        formPanel.add(securityAnswerField, gbc);
        
        // Info label
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        JLabel infoLabel = new JLabel("<html><i>Password must be at least 6 characters</i></html>");
        infoLabel.setForeground(Color.GRAY);
        formPanel.add(infoLabel, gbc);
        
        mainPanel.add(formPanel, BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        registerButton = new JButton("Register");
        registerButton.addActionListener(e -> handleRegister());
        buttonPanel.add(registerButton);
        
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());
        buttonPanel.add(cancelButton);
        
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private void handleRegister() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());
        String securityQuestion = securityQuestionField.getText().trim();
        String securityAnswer = securityAnswerField.getText().trim();
        
        // Validation
        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() 
            || securityQuestion.isEmpty() || securityAnswer.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please fill in all fields.",
                "Validation Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Check username length
        if (username.length() < 3) {
            JOptionPane.showMessageDialog(this,
                "Username must be at least 3 characters long.",
                "Validation Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Check password match
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this,
                "Passwords do not match.",
                "Validation Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Check password length
        if (password.length() < 6) {
            JOptionPane.showMessageDialog(this,
                "Password must be at least 6 characters long.",
                "Validation Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            // Check if username already exists
            Manager existingManager = managerDAO.findByUsername(username);
            if (existingManager != null) {
                JOptionPane.showMessageDialog(this,
                    "Username already exists. Please choose a different username.",
                    "Registration Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Hash the password using the same method as login
            String passwordHash = PasswordHasher.hashPassword(password);
            
            // Create new manager
            Manager newManager = new Manager(username, passwordHash, securityQuestion, securityAnswer);
            
            if (managerDAO.createManager(newManager)) {
                registered = true;
                JOptionPane.showMessageDialog(this,
                    "Manager account created successfully!\nYou can now login with your credentials.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Failed to create manager account. Please try again.",
                    "Registration Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Error during registration: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    
    public boolean isRegistered() {
        return registered;
    }
}

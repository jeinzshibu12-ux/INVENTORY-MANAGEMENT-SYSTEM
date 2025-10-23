package ui;

import dao.ManagerDAO;
import java.awt.*;
import javax.swing.*;
import model.Manager;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private ManagerDAO managerDAO;
    
    public LoginFrame() {
        managerDAO = new ManagerDAO();
        initComponents();
    }
    
    private void initComponents() {
        setTitle("Inventory Management System - Login");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Title
        JLabel titleLabel = new JLabel("Inventory Management System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
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
        
        mainPanel.add(formPanel, BorderLayout.CENTER);
        
        // Bottom panel with buttons and links
        JPanel bottomPanel = new JPanel(new BorderLayout());
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(100, 30));
        loginButton.addActionListener(e -> handleLogin());
        buttonPanel.add(loginButton);
        
        JButton registerButton = new JButton("Register");
        registerButton.setPreferredSize(new Dimension(100, 30));
        registerButton.addActionListener(e -> showRegisterDialog());
        buttonPanel.add(registerButton);
        
        bottomPanel.add(buttonPanel, BorderLayout.CENTER);
        
        // Forgot password link
        JLabel forgotPasswordLabel = new JLabel("<html><u>Forgot Password?</u></html>");
        forgotPasswordLabel.setForeground(Color.BLUE);
        forgotPasswordLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        forgotPasswordLabel.setHorizontalAlignment(SwingConstants.CENTER);
        forgotPasswordLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                showForgotPasswordDialog();
            }
        });
        bottomPanel.add(forgotPasswordLabel, BorderLayout.SOUTH);
        
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        // Enter key login
        passwordField.addActionListener(e -> handleLogin());
        
        add(mainPanel);
    }
    
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please enter both username and password.", 
                "Login Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            // Pass plain password - DAO will verify using PasswordHasher
            Manager manager = managerDAO.authenticate(username, password);
            
            if (manager != null) {
                // Successful login
                JOptionPane.showMessageDialog(this, 
                    "Login successful! Welcome, " + manager.getUsername(), 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
                
                // Open main dashboard
                SwingUtilities.invokeLater(() -> {
                    new DashboardFrame(manager).setVisible(true);
                    dispose();
                });
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Invalid username or password.", 
                    "Login Failed", 
                    JOptionPane.ERROR_MESSAGE);
                passwordField.setText("");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Error during login: " + ex.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    
    private void showForgotPasswordDialog() {
        ForgotPasswordDialog dialog = new ForgotPasswordDialog(this);
        dialog.setVisible(true);
        
        if (dialog.isPasswordReset()) {
            JOptionPane.showMessageDialog(this,
                "Password reset successful! Please login with your new password.",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void showRegisterDialog() {
        RegisterManagerDialog dialog = new RegisterManagerDialog(this);
        dialog.setVisible(true);
        
        if (dialog.isRegistered()) {
            JOptionPane.showMessageDialog(this,
                "Registration successful! You can now login with your credentials.",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }
}

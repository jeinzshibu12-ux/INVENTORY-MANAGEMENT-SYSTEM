package ui;

import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.*;
import model.Product;

public class ProductDialog extends JDialog {
    private JTextField nameField;
    private JTextField sectionField;
    private JTextField supplierField;
    private JTextField quantityField;
    private JTextField priceField;
    private JTextField expiryDateField;
    private boolean confirmed = false;
    private Product product;
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    public ProductDialog(Frame parent, Product existingProduct) {
        super(parent, existingProduct == null ? "Add Product" : "Edit Product", true);
        this.product = existingProduct;
        initComponents();
        
        if (existingProduct != null) {
            populateFields(existingProduct);
        }
    }
    
    private void initComponents() {
        setSize(450, 400);
        setLocationRelativeTo(getParent());
        setResizable(false);
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Name
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Product Name:"), gbc);
        gbc.gridx = 1;
        nameField = new JTextField(20);
        formPanel.add(nameField, gbc);
        
        // Section
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Section:"), gbc);
        gbc.gridx = 1;
        sectionField = new JTextField(20);
        formPanel.add(sectionField, gbc);
        
        // Supplier
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Supplier:"), gbc);
        gbc.gridx = 1;
        supplierField = new JTextField(20);
        formPanel.add(supplierField, gbc);
        
        // Quantity
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Quantity:"), gbc);
        gbc.gridx = 1;
        quantityField = new JTextField(20);
        formPanel.add(quantityField, gbc);
        
        // Price
        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Price (₹):"), gbc);
        gbc.gridx = 1;
        priceField = new JTextField(20);
        formPanel.add(priceField, gbc);
        
        // Expiry Date
        gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(new JLabel("Expiry Date (yyyy-MM-dd):"), gbc);
        gbc.gridx = 1;
        expiryDateField = new JTextField(20);
        expiryDateField.setToolTipText("Format: yyyy-MM-dd (e.g., 2025-12-31)");
        formPanel.add(expiryDateField, gbc);
        
        mainPanel.add(formPanel, BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> save());
        buttonPanel.add(saveButton);
        
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());
        buttonPanel.add(cancelButton);
        
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private void populateFields(Product p) {
        nameField.setText(p.getName());
        sectionField.setText(p.getSection());
        supplierField.setText(p.getSupplier());
        quantityField.setText(String.valueOf(p.getQuantity()));
        priceField.setText(String.valueOf(p.getPrice()));
        if (p.getExpiryDate() != null) {
            expiryDateField.setText(p.getExpiryDate().format(DATE_FORMATTER));
        }
    }
    
    private void save() {
        try {
            // Validate fields
            String name = nameField.getText().trim();
            String section = sectionField.getText().trim();
            String supplier = supplierField.getText().trim();
            
            if (name.isEmpty() || section.isEmpty() || supplier.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Please fill in all required fields (Name, Section, Supplier).", 
                    "Validation Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int quantity;
            try {
                quantity = Integer.parseInt(quantityField.getText().trim());
                if (quantity < 0) throw new NumberFormatException();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, 
                    "Please enter a valid quantity (non-negative integer).", 
                    "Validation Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            double price;
            try {
                price = Double.parseDouble(priceField.getText().trim());
                if (price < 0) throw new NumberFormatException();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, 
                    "Please enter a valid price (non-negative number).", 
                    "Validation Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            LocalDate expiryDate = null;
            String expiryStr = expiryDateField.getText().trim();
            if (!expiryStr.isEmpty()) {
                try {
                    expiryDate = LocalDate.parse(expiryStr, DATE_FORMATTER);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, 
                        "Please enter a valid date in yyyy-MM-dd format.", 
                        "Validation Error", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            
            // Create product
            product = new Product(name, section, supplier, quantity, price, expiryDate);
            confirmed = true;
            dispose();
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Error: " + ex.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public boolean isConfirmed() {
        return confirmed;
    }
    
    public Product getProduct() {
        return product;
    }
}

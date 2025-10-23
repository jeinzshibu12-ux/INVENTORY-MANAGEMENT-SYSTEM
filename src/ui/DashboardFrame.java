package ui;

import dao.ProductDAO;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import model.Manager;
import model.Product;

public class DashboardFrame extends JFrame {
    private Manager currentManager;
    private ProductDAO productDAO;
    private JTable productTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JLabel statusLabel;
    private String currentCategory = "All";  // Track current category filter
    private JButton selectedCategoryButton = null;  // Track selected category button
    
    // Modern Color Scheme - Enhanced Visibility Theme
    private static final Color PRIMARY_COLOR = new Color(0, 102, 204);      // Deep Blue
    private static final Color PRIMARY_DARK = new Color(0, 76, 153);        // Darker Blue
    private static final Color ACCENT_COLOR = new Color(64, 158, 255);      // Bright Blue
    private static final Color BACKGROUND_COLOR = new Color(245, 248, 250); // Light Gray-Blue
    private static final Color CARD_COLOR = Color.WHITE;                     // White Cards
    private static final Color TEXT_PRIMARY = new Color(33, 37, 41);        // Dark Text
    private static final Color TEXT_SECONDARY = new Color(108, 117, 125);   // Gray Text
    private static final Color TABLE_HEADER_BG = new Color(41, 128, 185);   // Lighter Blue Header
    private static final Color TABLE_HEADER_FG = Color.WHITE;                // White Text (High Contrast)
    private static final Color TABLE_ROW_ODD = Color.WHITE;                  // White Rows
    private static final Color TABLE_ROW_EVEN = new Color(236, 240, 245);   // Light Blue-Gray Rows
    private static final Color TABLE_SELECTION = new Color(179, 217, 255);  // Selection Blue
    private static final Color TABLE_HOVER = new Color(230, 242, 255);      // Hover Light Blue
    private static final Color SUCCESS_COLOR = new Color(40, 167, 69);      // Green
    private static final Color DANGER_COLOR = new Color(220, 53, 69);       // Red
    private static final Color WARNING_COLOR = new Color(255, 193, 7);      // Yellow
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    public DashboardFrame(Manager manager) {
        this.currentManager = manager;
        this.productDAO = new ProductDAO();
        initComponents();
        loadProducts();
    }
    
    private void initComponents() {
        setTitle("Inventory Management System");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Set modern background
        getContentPane().setBackground(BACKGROUND_COLOR);
        setLayout(new BorderLayout(0, 0));
        
        // ==================== HEADER PANEL ====================
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);
        
        // ==================== MAIN CONTENT PANEL ====================
        JPanel mainContent = new JPanel(new BorderLayout(15, 15));
        mainContent.setBackground(BACKGROUND_COLOR);
        mainContent.setBorder(new EmptyBorder(20, 25, 20, 25));
        
        // Search and Filter Panel
        JPanel searchPanel = createSearchPanel();
        mainContent.add(searchPanel, BorderLayout.NORTH);
        
        // Create a container for categories and table
        JPanel centerContainer = new JPanel(new BorderLayout(0, 15));
        centerContainer.setBackground(BACKGROUND_COLOR);
        
        // Category Filter Panel
        JPanel categoryPanel = createCategoryPanel();
        centerContainer.add(categoryPanel, BorderLayout.NORTH);
        
        // Table Panel
        JPanel tablePanel = createTablePanel();
        centerContainer.add(tablePanel, BorderLayout.CENTER);
        
        mainContent.add(centerContainer, BorderLayout.CENTER);
        
        add(mainContent, BorderLayout.CENTER);
        
        // ==================== BOTTOM PANEL ====================
        JPanel bottomPanel = createBottomPanel();
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    // ==================== HEADER PANEL CREATION ====================
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                
                // Gradient background
                GradientPaint gradient = new GradientPaint(
                    0, 0, PRIMARY_COLOR,
                    getWidth(), 0, PRIMARY_DARK
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        headerPanel.setPreferredSize(new Dimension(0, 80));
        headerPanel.setLayout(new BorderLayout(20, 0));
        headerPanel.setBorder(new EmptyBorder(15, 30, 15, 30));
        
        // Title Label
        JLabel titleLabel = new JLabel("📊 Inventory Management System");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.WEST);
        
        // Welcome Panel
        JPanel welcomePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        welcomePanel.setOpaque(false);
        
        JLabel welcomeLabel = new JLabel("Welcome, " + currentManager.getUsername() + "!");
        welcomeLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        welcomeLabel.setForeground(Color.WHITE);
        welcomePanel.add(welcomeLabel);
        
        headerPanel.add(welcomePanel, BorderLayout.EAST);
        
        return headerPanel;
    }
    
    // ==================== SEARCH PANEL CREATION ====================
    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.X_AXIS));
        searchPanel.setBackground(CARD_COLOR);
        searchPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 210, 220), 1),
            new EmptyBorder(15, 20, 15, 20)
        ));
        
        JLabel searchLabel = new JLabel("🔍 Search:");
        searchLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        searchLabel.setForeground(TEXT_PRIMARY);
        searchPanel.add(searchLabel);
        
        searchPanel.add(Box.createHorizontalStrut(10));
        
        searchField = new JTextField();
        searchField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        searchField.setPreferredSize(new Dimension(300, 35));
        searchField.setMaximumSize(new Dimension(400, 35));
        searchField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(206, 212, 218), 1),
            new EmptyBorder(5, 10, 5, 10)
        ));
        searchPanel.add(searchField);
        
        searchPanel.add(Box.createHorizontalStrut(15));
        
        JButton searchButton = createModernButton("Search", PRIMARY_COLOR, PRIMARY_DARK);
        searchButton.addActionListener(e -> searchProducts());
        searchPanel.add(searchButton);
        
        searchPanel.add(Box.createHorizontalStrut(10));
        
        JButton refreshButton = createModernButton("Refresh", ACCENT_COLOR, PRIMARY_COLOR);
        refreshButton.addActionListener(e -> loadProducts());
        searchPanel.add(refreshButton);
        
        searchPanel.add(Box.createHorizontalGlue());
        
        return searchPanel;
    }
    
    // ==================== CATEGORY FILTER PANEL CREATION ====================
    private JPanel createCategoryPanel() {
        JPanel categoryPanel = new JPanel();
        categoryPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        categoryPanel.setBackground(CARD_COLOR);
        categoryPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 210, 220), 1),
            new EmptyBorder(10, 15, 10, 15)
        ));
        
        JLabel filterLabel = new JLabel("📂 Categories:");
        filterLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        filterLabel.setForeground(TEXT_PRIMARY);
        categoryPanel.add(filterLabel);
        
        // Define categories with their matching sections
        String[][] categories = {
            {"All", "All Products"},
            {"Beverages", "🥤 Beverages & Cold Drinks"},
            {"Biscuits|Snacks", "🍪 Biscuits & Snacks"},
            {"Ice Cream|Chocolates|Candies", "🍦 Ice Cream & Chocolates"},
            {"Bakery|Dairy", "🍞 Bakery & Dairy"},
            {"Groceries|Cereals|Instant Food", "🛒 Groceries & Food"},
            {"Personal Care|Daily Needs", "🧴 Personal Care & Cleaning"},
            {"Notebooks|Pens|Pencils|Erasers|Sharpeners|Markers|Paper|Files|Staplers|Adhesives|Scissors|Geometry|Art Supplies|Calculators|Organizers|Labels|Clips|Correction|Diaries|Registers", "📝 Stationery"}
        };
        
        for (String[] category : categories) {
            JButton categoryBtn = createCategoryButton(category[0], category[1]);
            categoryPanel.add(categoryBtn);
            
            // Set first button as selected by default
            if (category[0].equals("All") && selectedCategoryButton == null) {
                selectedCategoryButton = categoryBtn;
                categoryBtn.setBackground(PRIMARY_DARK);
            }
        }
        
        return categoryPanel;
    }
    
    // ==================== CATEGORY BUTTON CREATION ====================
    private JButton createCategoryButton(String filterValue, String displayText) {
        JButton button = new JButton(displayText);
        button.setFont(new Font("SansSerif", Font.PLAIN, 13));
        button.setForeground(Color.WHITE);
        button.setBackground(ACCENT_COLOR);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(new EmptyBorder(8, 16, 8, 16));
        
        // Add action listener to filter by category
        button.addActionListener(e -> {
            // Update selected button styling
            if (selectedCategoryButton != null) {
                selectedCategoryButton.setBackground(ACCENT_COLOR);
            }
            button.setBackground(PRIMARY_DARK);
            selectedCategoryButton = button;
            currentCategory = filterValue;
            
            // Filter products
            filterByCategory(filterValue);
        });
        
        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (button != selectedCategoryButton) {
                    button.setBackground(PRIMARY_COLOR);
                }
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (button != selectedCategoryButton) {
                    button.setBackground(ACCENT_COLOR);
                }
            }
        });
        
        return button;
    }
    
    // ==================== CATEGORY FILTERING METHOD ====================
    private void filterByCategory(String categoryFilter) {
        tableModel.setRowCount(0);
        List<Product> products;
        
        try {
            if (categoryFilter.equals("All")) {
                // Load all products
                products = productDAO.getAllProducts();
            } else {
                // Load products matching any of the category sections (separated by |)
                products = productDAO.getAllProducts();
                String[] sections = categoryFilter.split("\\|");
                products = products.stream()
                    .filter(p -> {
                        for (String section : sections) {
                            if (p.getSection().equalsIgnoreCase(section)) {
                                return true;
                            }
                        }
                        return false;
                    })
                    .collect(java.util.stream.Collectors.toList());
            }
            
            // Populate table with filtered products
            for (Product product : products) {
                tableModel.addRow(new Object[]{
                    product.getProductId(),
                    product.getName(),
                    product.getSection(),
                    product.getSupplier(),
                    product.getQuantity(),
                    String.format("₹%.2f", product.getPrice()),
                    product.getExpiryDate() != null ? product.getExpiryDate().format(DATE_FORMATTER) : "N/A"
                });
            }
            
            statusLabel.setText("Showing " + products.size() + " products");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error filtering products: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    
    // ==================== TABLE PANEL CREATION ====================
    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(CARD_COLOR);
        tablePanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 210, 220), 1),
            new EmptyBorder(0, 0, 0, 0)
        ));
        
        // Table Model
        String[] columns = {"ID", "Name", "Section", "Supplier", "Quantity", "Price", "Expiry Date"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        productTable = new JTable(tableModel);
        productTable.setFont(new Font("SansSerif", Font.PLAIN, 14));  // Larger font for better readability
        productTable.setRowHeight(45);  // Taller rows
        productTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        productTable.setShowVerticalLines(false);
        productTable.setShowHorizontalLines(true);
        productTable.setGridColor(new Color(220, 225, 230));  // Slightly darker grid lines
        productTable.setSelectionBackground(TABLE_SELECTION);
        productTable.setSelectionForeground(TEXT_PRIMARY);
        productTable.setIntercellSpacing(new Dimension(0, 1));
        
        // ==================== TABLE STYLING ====================
        // Header Styling with Enhanced Visibility
        JTableHeader header = productTable.getTableHeader();
        header.setFont(new Font("SansSerif", Font.BOLD, 15));  // Larger font
        header.setBackground(TABLE_HEADER_BG);
        header.setForeground(TABLE_HEADER_FG);
        header.setPreferredSize(new Dimension(0, 50));  // Taller header
        header.setReorderingAllowed(false);
        
        // Enhanced header rendering for better text visibility
        header.setDefaultRenderer(new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                label.setBackground(TABLE_HEADER_BG);
                label.setForeground(Color.WHITE);
                label.setFont(new Font("SansSerif", Font.BOLD, 15));
                label.setBorder(new EmptyBorder(10, 15, 10, 15));
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setOpaque(true);
                
                // Add subtle border for better separation
                label.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 2, 1, new Color(255, 255, 255, 30)),
                    new EmptyBorder(10, 15, 10, 15)
                ));
                
                return label;
            }
        });
        
        // Custom Cell Renderer for Alternating Row Colors with Better Visibility
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                // Enhanced font for better readability
                setFont(new Font("SansSerif", Font.PLAIN, 14));
                
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? TABLE_ROW_EVEN : TABLE_ROW_ODD);
                    c.setForeground(TEXT_PRIMARY);
                } else {
                    c.setBackground(TABLE_SELECTION);
                    c.setForeground(TEXT_PRIMARY);
                }
                
                setBorder(new EmptyBorder(8, 12, 8, 12));  // More padding
                setHorizontalAlignment(column == 0 || column == 4 ? SwingConstants.CENTER : SwingConstants.LEFT);
                
                return c;
            }
        };
        
        // Apply renderer to all columns
        for (int i = 0; i < productTable.getColumnCount(); i++) {
            productTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        
        // Column widths
        productTable.getColumnModel().getColumn(0).setPreferredWidth(60);  // ID
        productTable.getColumnModel().getColumn(1).setPreferredWidth(250); // Name
        productTable.getColumnModel().getColumn(2).setPreferredWidth(120); // Section
        productTable.getColumnModel().getColumn(3).setPreferredWidth(150); // Supplier
        productTable.getColumnModel().getColumn(4).setPreferredWidth(80);  // Quantity
        productTable.getColumnModel().getColumn(5).setPreferredWidth(100); // Price
        productTable.getColumnModel().getColumn(6).setPreferredWidth(120); // Expiry
        
        // ==================== HOVER EFFECT ====================
        productTable.addMouseMotionListener(new MouseAdapter() {
            private int hoveredRow = -1;
            
            @Override
            public void mouseMoved(MouseEvent e) {
                int row = productTable.rowAtPoint(e.getPoint());
                if (row != hoveredRow) {
                    hoveredRow = row;
                    productTable.repaint();
                }
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(productTable);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(CARD_COLOR);
        
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        
        return tablePanel;
    }
    
    // ==================== BOTTOM PANEL CREATION ====================
    private JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel(new BorderLayout(20, 0));
        bottomPanel.setBackground(CARD_COLOR);
        bottomPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(2, 0, 0, 0, new Color(220, 230, 240)),
            new EmptyBorder(20, 30, 20, 30)
        ));
        
        // ==================== BUTTONS STYLING ====================
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        buttonPanel.setOpaque(false);
        
        JButton addButton = createModernButton("➕ Add Product", SUCCESS_COLOR, new Color(33, 136, 56));
        addButton.addActionListener(e -> showAddDialog());
        buttonPanel.add(addButton);
        
        JButton editButton = createModernButton("✏️ Edit Product", WARNING_COLOR, new Color(217, 164, 6));
        editButton.addActionListener(e -> showEditDialog());
        buttonPanel.add(editButton);
        
        JButton deleteButton = createModernButton("🗑️ Delete Product", DANGER_COLOR, new Color(189, 33, 48));
        deleteButton.addActionListener(e -> deleteProduct());
        buttonPanel.add(deleteButton);
        
        JButton reportButton = createModernButton("📊 Generate Report", new Color(102, 51, 153), new Color(76, 38, 114));
        reportButton.addActionListener(e -> showReportDialog());
        buttonPanel.add(reportButton);
        
        JButton logoutButton = createModernButton("🚪 Logout", TEXT_SECONDARY, new Color(90, 98, 104));
        logoutButton.addActionListener(e -> logout());
        buttonPanel.add(logoutButton);
        
        bottomPanel.add(buttonPanel, BorderLayout.WEST);
        
        // Status Label
        statusLabel = new JLabel("Ready");
        statusLabel.setFont(new Font("SansSerif", Font.ITALIC, 13));
        statusLabel.setForeground(TEXT_SECONDARY);
        statusLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        bottomPanel.add(statusLabel, BorderLayout.EAST);
        
        return bottomPanel;
    }
    
    // ==================== MODERN BUTTON CREATION ====================
    private JButton createModernButton(String text, Color bgColor, Color hoverColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (getModel().isPressed()) {
                    g2d.setColor(hoverColor.darker());
                } else if (getModel().isRollover()) {
                    g2d.setColor(hoverColor);
                } else {
                    g2d.setColor(bgColor);
                }
                
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2d.dispose();
                
                super.paintComponent(g);
            }
        };
        
        button.setFont(new Font("SansSerif", Font.BOLD, 13));
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(140, 38));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(new EmptyBorder(8, 15, 8, 15));
        
        return button;
    }
    
    // ==================== DATA LOADING METHODS ====================
    private void loadProducts() {
        try {
            tableModel.setRowCount(0);
            List<Product> products = productDAO.getAllProducts();
            
            for (Product p : products) {
                Object[] row = {
                    p.getProductId(),
                    p.getName(),
                    p.getSection(),
                    p.getSupplier(),
                    p.getQuantity(),
                    String.format("₹%.2f", p.getPrice()),
                    p.getExpiryDate() != null ? p.getExpiryDate().format(DATE_FORMATTER) : "N/A"
                };
                tableModel.addRow(row);
            }
            statusLabel.setText("✓ Loaded " + products.size() + " products");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Error loading products: " + ex.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    
    private void searchProducts() {
        String keyword = searchField.getText().trim();
        if (keyword.isEmpty()) {
            loadProducts();
            return;
        }
        
        try {
            tableModel.setRowCount(0);
            List<Product> products = productDAO.searchProducts(keyword);
            
            for (Product p : products) {
                Object[] row = {
                    p.getProductId(),
                    p.getName(),
                    p.getSection(),
                    p.getSupplier(),
                    p.getQuantity(),
                    String.format("₹%.2f", p.getPrice()),
                    p.getExpiryDate() != null ? p.getExpiryDate().format(DATE_FORMATTER) : "N/A"
                };
                tableModel.addRow(row);
            }
            statusLabel.setText("🔍 Found " + products.size() + " products matching '" + keyword + "'");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Error searching products: " + ex.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // ==================== DIALOG METHODS ====================
    private void showAddDialog() {
        ProductDialog dialog = new ProductDialog(this, null);
        dialog.setVisible(true);
        
        if (dialog.isConfirmed()) {
            try {
                Product product = dialog.getProduct();
                if (productDAO.addProduct(product)) {
                    JOptionPane.showMessageDialog(this, 
                        "Product added successfully!", 
                        "Success", 
                        JOptionPane.INFORMATION_MESSAGE);
                    loadProducts();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, 
                    "Error adding product: " + ex.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void showEditDialog() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                "Please select a product to edit.", 
                "No Selection", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            int productId = (Integer) tableModel.getValueAt(selectedRow, 0);
            Product product = productDAO.getProductById(productId);
            
            if (product != null) {
                ProductDialog dialog = new ProductDialog(this, product);
                dialog.setVisible(true);
                
                if (dialog.isConfirmed()) {
                    Product updatedProduct = dialog.getProduct();
                    updatedProduct.setProductId(productId);
                    
                    if (productDAO.updateProduct(updatedProduct)) {
                        JOptionPane.showMessageDialog(this, 
                            "Product updated successfully!", 
                            "Success", 
                            JOptionPane.INFORMATION_MESSAGE);
                        loadProducts();
                    }
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Error editing product: " + ex.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void deleteProduct() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                "Please select a product to delete.", 
                "No Selection", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete this product?", 
            "Confirm Delete", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                int productId = (Integer) tableModel.getValueAt(selectedRow, 0);
                if (productDAO.deleteProduct(productId)) {
                    JOptionPane.showMessageDialog(this, 
                        "Product deleted successfully!", 
                        "Success", 
                        JOptionPane.INFORMATION_MESSAGE);
                    loadProducts();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, 
                    "Error deleting product: " + ex.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to logout?", 
            "Confirm Logout", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            SwingUtilities.invokeLater(() -> {
                new LoginFrame().setVisible(true);
                dispose();
            });
        }
    }
    
    // ==================== REPORT GENERATION ====================
    private void showReportDialog() {
        ReportDialog dialog = new ReportDialog(this);
        dialog.setVisible(true);
        // Refresh status after report generation
        statusLabel.setText("✓ Ready for next operation");
    }
}

package dao;

import db.DatabaseConnection;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.Product;

public class ProductDAO {
    
    public List<Product> getAllProducts() throws SQLException {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM products ORDER BY product_id";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                products.add(mapResultSetToProduct(rs));
            }
        }
        return products;
    }
    
    public Product getProductById(int id) throws SQLException {
        String query = "SELECT * FROM products WHERE product_id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToProduct(rs);
            }
        }
        return null;
    }
    
    public boolean addProduct(Product product) throws SQLException {
        String query = "INSERT INTO products (name, section, supplier, quantity, price, expiry_date) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, product.getName());
            stmt.setString(2, product.getSection());
            stmt.setString(3, product.getSupplier());
            stmt.setInt(4, product.getQuantity());
            stmt.setDouble(5, product.getPrice());
            stmt.setDate(6, product.getExpiryDate() != null ? Date.valueOf(product.getExpiryDate()) : null);
            
            return stmt.executeUpdate() > 0;
        }
    }
    
    public boolean updateProduct(Product product) throws SQLException {
        String query = "UPDATE products SET name=?, section=?, supplier=?, quantity=?, price=?, expiry_date=? WHERE product_id=?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, product.getName());
            stmt.setString(2, product.getSection());
            stmt.setString(3, product.getSupplier());
            stmt.setInt(4, product.getQuantity());
            stmt.setDouble(5, product.getPrice());
            stmt.setDate(6, product.getExpiryDate() != null ? Date.valueOf(product.getExpiryDate()) : null);
            stmt.setInt(7, product.getProductId());
            
            return stmt.executeUpdate() > 0;
        }
    }
    
    public boolean deleteProduct(int id) throws SQLException {
        String query = "DELETE FROM products WHERE product_id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }
    
    public List<Product> searchProducts(String keyword) throws SQLException {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM products WHERE name LIKE ? OR section LIKE ? OR supplier LIKE ?";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            String searchTerm = "%" + keyword + "%";
            stmt.setString(1, searchTerm);
            stmt.setString(2, searchTerm);
            stmt.setString(3, searchTerm);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                products.add(mapResultSetToProduct(rs));
            }
        }
        return products;
    }
    
    private Product mapResultSetToProduct(ResultSet rs) throws SQLException {
        Date expiryDate = rs.getDate("expiry_date");
        Date createdAt = rs.getDate("created_at");
        
        return new Product(
            rs.getInt("product_id"),
            rs.getString("name"),
            rs.getString("section"),
            rs.getString("supplier"),
            rs.getInt("quantity"),
            rs.getDouble("price"),
            expiryDate != null ? expiryDate.toLocalDate() : null,
            createdAt != null ? createdAt.toLocalDate() : LocalDate.now()
        );
    }
}

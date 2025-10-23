package model;

import java.time.LocalDate;

/**
 * Product model class
 */
public class Product {
    private int productId;
    private String name;
    private String section;
    private String supplier;
    private int quantity;
    private double price;
    private LocalDate expiryDate;
    private LocalDate createdAt;
    
    // Constructors
    public Product() {
    }
    
    public Product(int productId, String name, String section, String supplier, 
                   int quantity, double price, LocalDate expiryDate, LocalDate createdAt) {
        this.productId = productId;
        this.name = name;
        this.section = section;
        this.supplier = supplier;
        this.quantity = quantity;
        this.price = price;
        this.expiryDate = expiryDate;
        this.createdAt = createdAt;
    }
    
    // Constructor without ID (for new products)
    public Product(String name, String section, String supplier, 
                   int quantity, double price, LocalDate expiryDate) {
        this.name = name;
        this.section = section;
        this.supplier = supplier;
        this.quantity = quantity;
        this.price = price;
        this.expiryDate = expiryDate;
    }
    
    // Getters and Setters
    public int getProductId() {
        return productId;
    }
    
    public void setProductId(int productId) {
        this.productId = productId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getSection() {
        return section;
    }
    
    public void setSection(String section) {
        this.section = section;
    }
    
    public String getSupplier() {
        return supplier;
    }
    
    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public double getPrice() {
        return price;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
    
    public LocalDate getExpiryDate() {
        return expiryDate;
    }
    
    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }
    
    public LocalDate getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
    
    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", name='" + name + '\'' +
                ", section='" + section + '\'' +
                ", supplier='" + supplier + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", expiryDate=" + expiryDate +
                ", createdAt=" + createdAt +
                '}';
    }
}

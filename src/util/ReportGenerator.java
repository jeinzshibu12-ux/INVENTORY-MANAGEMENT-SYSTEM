package util;

import dao.ProductDAO;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Product;

/**
 * Utility class for generating monthly inventory reports
 */
public class ReportGenerator {
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final DateTimeFormatter MONTH_YEAR_FORMATTER = DateTimeFormatter.ofPattern("MMMM yyyy");
    private static final String REPORTS_DIR = "Reports";
    private static final int LOW_STOCK_THRESHOLD = 10;
    
    private ProductDAO productDAO;
    
    public ReportGenerator() {
        this.productDAO = new ProductDAO();
        ensureReportsDirectoryExists();
    }
    
    /**
     * Ensure Reports directory exists
     */
    private void ensureReportsDirectoryExists() {
        File reportsDir = new File(REPORTS_DIR);
        if (!reportsDir.exists()) {
            reportsDir.mkdirs();
        }
    }
    
    /**
     * Generate monthly inventory report
     * @param reportDate The date for which to generate the report
     * @return The file path of the generated report
     */
    public String generateMonthlyReport(LocalDate reportDate) throws Exception {
        List<Product> allProducts = productDAO.getAllProducts();
        
        if (allProducts.isEmpty()) {
            throw new Exception("No products found in inventory to generate report.");
        }
        
        // Group products by section
        Map<String, List<Product>> productsBySection = new HashMap<>();
        for (Product product : allProducts) {
            String section = product.getSection();
            productsBySection.computeIfAbsent(section, k -> new java.util.ArrayList<>()).add(product);
        }
        
        // Generate filename
        String fileName = String.format("%s/Inventory_Report_%s.txt", 
            REPORTS_DIR, 
            reportDate.format(DateTimeFormatter.ofPattern("MMM_yyyy")));
        
        // Write report
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writeReportHeader(writer, reportDate);
            writeReportBody(writer, productsBySection, allProducts.size());
            writeReportFooter(writer);
        }
        
        return fileName;
    }
    
    /**
     * Write report header
     */
    private void writeReportHeader(BufferedWriter writer, LocalDate reportDate) throws IOException {
        writer.write("╔═══════════════════════════════════════════════════════════════════════════╗\n");
        writer.write("║                    INVENTORY MONTHLY REPORT                               ║\n");
        writer.write("╠═══════════════════════════════════════════════════════════════════════════╣\n");
        writer.write(String.format("║  Report Period: %-58s║\n", reportDate.format(MONTH_YEAR_FORMATTER)));
        writer.write(String.format("║  Generated On:  %-58s║\n", LocalDate.now().format(DATE_FORMATTER)));
        writer.write("╚═══════════════════════════════════════════════════════════════════════════╝\n\n");
    }
    
    /**
     * Write report body with section-wise breakdown
     */
    private void writeReportBody(BufferedWriter writer, Map<String, List<Product>> productsBySection, 
                                  int totalProducts) throws IOException {
        int totalQuantity = 0;
        double totalValue = 0.0;
        int lowStockCount = 0;
        int expiringCount = 0;
        
        // Write each section
        for (Map.Entry<String, List<Product>> entry : productsBySection.entrySet()) {
            String section = entry.getKey();
            List<Product> products = entry.getValue();
            
            writer.write("═══════════════════════════════════════════════════════════════════════════\n");
            writer.write(String.format("SECTION: %s (%d items)\n", section.toUpperCase(), products.size()));
            writer.write("═══════════════════════════════════════════════════════════════════════════\n");
            writer.write(String.format("%-6s %-35s %-10s %-12s %-15s\n", 
                "ID", "Product Name", "Quantity", "Price (₹)", "Status"));
            writer.write("───────────────────────────────────────────────────────────────────────────\n");
            
            for (Product product : products) {
                String status = getProductStatus(product);
                writer.write(String.format("%-6d %-35s %-10d ₹%-11.2f %s\n",
                    product.getProductId(),
                    truncate(product.getName(), 35),
                    product.getQuantity(),
                    product.getPrice(),
                    status
                ));
                
                totalQuantity += product.getQuantity();
                totalValue += product.getQuantity() * product.getPrice();
                
                if (product.getQuantity() < LOW_STOCK_THRESHOLD) {
                    lowStockCount++;
                }
                if (isExpiringSoon(product)) {
                    expiringCount++;
                }
            }
            writer.write("\n");
        }
        
        // Write summary
        writer.write("═══════════════════════════════════════════════════════════════════════════\n");
        writer.write("                              SUMMARY                                      \n");
        writer.write("═══════════════════════════════════════════════════════════════════════════\n");
        writer.write(String.format("Total Products:           %-10d\n", totalProducts));
        writer.write(String.format("Total Sections:           %-10d\n", productsBySection.size()));
        writer.write(String.format("Total Stock Quantity:     %-10d\n", totalQuantity));
        writer.write(String.format("Total Inventory Value:    ₹%-10.2f\n", totalValue));
        writer.write(String.format("Low Stock Items (<%d):    %-10d ⚠\n", LOW_STOCK_THRESHOLD, lowStockCount));
        writer.write(String.format("Expiring Soon (7 days):   %-10d ⚠\n", expiringCount));
        writer.write("═══════════════════════════════════════════════════════════════════════════\n\n");
    }
    
    /**
     * Write report footer
     */
    private void writeReportFooter(BufferedWriter writer) throws IOException {
        writer.write("═══════════════════════════════════════════════════════════════════════════\n");
        writer.write("                  Inventory Management System v1.0                         \n");
        writer.write("                        End of Report                                      \n");
        writer.write("═══════════════════════════════════════════════════════════════════════════\n");
    }
    
    /**
     * Get product status string
     */
    private String getProductStatus(Product product) {
        if (product.getQuantity() == 0) {
            return "Out of Stock ❌";
        } else if (product.getQuantity() < LOW_STOCK_THRESHOLD) {
            return "Low Stock ⚠";
        } else if (isExpiringSoon(product)) {
            return "Expiring Soon ⚠";
        } else {
            return "OK ✓";
        }
    }
    
    /**
     * Check if product is expiring within 7 days
     */
    private boolean isExpiringSoon(Product product) {
        if (product.getExpiryDate() == null) {
            return false;
        }
        LocalDate expiryDate = product.getExpiryDate();
        LocalDate today = LocalDate.now();
        LocalDate weekFromNow = today.plusDays(7);
        
        return expiryDate.isAfter(today) && expiryDate.isBefore(weekFromNow);
    }
    
    /**
     * Truncate string to specified length
     */
    private String truncate(String str, int maxLength) {
        if (str.length() <= maxLength) {
            return str;
        }
        return str.substring(0, maxLength - 3) + "...";
    }
    
    /**
     * Generate a quick summary report for a specific section
     */
    public String generateSectionReport(String section, LocalDate reportDate) throws Exception {
        List<Product> allProducts = productDAO.searchProducts(section);
        
        if (allProducts.isEmpty()) {
            throw new Exception("No products found in section: " + section);
        }
        
        String fileName = String.format("%s/%s_Report_%s.txt", 
            REPORTS_DIR, 
            section.replaceAll("\\s+", "_"),
            reportDate.format(DateTimeFormatter.ofPattern("MMM_yyyy")));
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writeReportHeader(writer, reportDate);
            
            Map<String, List<Product>> sectionMap = new HashMap<>();
            sectionMap.put(section, allProducts);
            
            writeReportBody(writer, sectionMap, allProducts.size());
            writeReportFooter(writer);
        }
        
        return fileName;
    }
}

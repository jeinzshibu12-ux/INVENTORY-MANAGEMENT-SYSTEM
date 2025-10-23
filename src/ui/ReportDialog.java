package ui;

import java.awt.*;
import java.io.File;
import java.time.LocalDate;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import util.ReportGenerator;

/**
 * Dialog for generating monthly inventory reports
 */
public class ReportDialog extends JDialog {
    
    private JComboBox<String> monthComboBox;
    private JComboBox<Integer> yearComboBox;
    private JRadioButton fullReportRadio;
    private JRadioButton sectionReportRadio;
    private JComboBox<String> sectionComboBox;
    private JButton generateButton;
    private JButton cancelButton;
    
    // Modern color scheme
    private static final Color PRIMARY_COLOR = new Color(0, 102, 204);
    private static final Color SUCCESS_COLOR = new Color(40, 167, 69);
    private static final Color CARD_COLOR = Color.WHITE;
    private static final Color TEXT_PRIMARY = new Color(33, 37, 41);
    
    private String[] months = {
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    };
    
    private String[] sections = {
        "All Sections",
        "Notebooks", "Registers", "Diaries", "Pens", "Pencils",
        "Erasers", "Sharpeners", "Markers", "Paper", "Files",
        "Staplers", "Adhesives", "Scissors", "Geometry",
        "Art Supplies", "Calculators", "Organizers", "Labels",
        "Clips", "Correction", "Daily Needs"
    };
    
    public ReportDialog(JFrame parent) {
        super(parent, "Generate Monthly Report", true);
        initComponents();
        setSize(500, 400);
        setLocationRelativeTo(parent);
    }
    
    private void initComponents() {
        setLayout(new BorderLayout(15, 15));
        getContentPane().setBackground(CARD_COLOR);
        
        // Header Panel
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);
        
        // Form Panel
        JPanel formPanel = createFormPanel();
        add(formPanel, BorderLayout.CENTER);
        
        // Button Panel
        JPanel buttonPanel = createButtonPanel();
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(PRIMARY_COLOR);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JLabel titleLabel = new JLabel("📊 Monthly Report Generator");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        panel.add(titleLabel);
        
        return panel;
    }
    
    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(CARD_COLOR);
        panel.setBorder(new EmptyBorder(20, 30, 20, 30));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 5, 8, 5);
        
        // Month Selection
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel monthLabel = new JLabel("Select Month:");
        monthLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        panel.add(monthLabel, gbc);
        
        gbc.gridx = 1;
        monthComboBox = new JComboBox<>(months);
        monthComboBox.setFont(new Font("SansSerif", Font.PLAIN, 13));
        monthComboBox.setSelectedIndex(LocalDate.now().getMonthValue() - 1);
        panel.add(monthComboBox, gbc);
        
        // Year Selection
        gbc.gridx = 0; gbc.gridy = 1;
        JLabel yearLabel = new JLabel("Select Year:");
        yearLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        panel.add(yearLabel, gbc);
        
        gbc.gridx = 1;
        Integer[] years = new Integer[5];
        int currentYear = LocalDate.now().getYear();
        for (int i = 0; i < 5; i++) {
            years[i] = currentYear - i;
        }
        yearComboBox = new JComboBox<>(years);
        yearComboBox.setFont(new Font("SansSerif", Font.PLAIN, 13));
        panel.add(yearComboBox, gbc);
        
        // Report Type
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        JLabel typeLabel = new JLabel("Report Type:");
        typeLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        panel.add(typeLabel, gbc);
        
        gbc.gridy = 3;
        fullReportRadio = new JRadioButton("Full Inventory Report (All Sections)");
        fullReportRadio.setFont(new Font("SansSerif", Font.PLAIN, 13));
        fullReportRadio.setBackground(CARD_COLOR);
        fullReportRadio.setSelected(true);
        fullReportRadio.addActionListener(e -> sectionComboBox.setEnabled(false));
        panel.add(fullReportRadio, gbc);
        
        gbc.gridy = 4;
        sectionReportRadio = new JRadioButton("Section-Specific Report");
        sectionReportRadio.setFont(new Font("SansSerif", Font.PLAIN, 13));
        sectionReportRadio.setBackground(CARD_COLOR);
        sectionReportRadio.addActionListener(e -> sectionComboBox.setEnabled(true));
        panel.add(sectionReportRadio, gbc);
        
        ButtonGroup reportTypeGroup = new ButtonGroup();
        reportTypeGroup.add(fullReportRadio);
        reportTypeGroup.add(sectionReportRadio);
        
        // Section Selection
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        JLabel sectionLabel = new JLabel("Section:");
        sectionLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        panel.add(sectionLabel, gbc);
        
        gbc.gridx = 1;
        sectionComboBox = new JComboBox<>(sections);
        sectionComboBox.setFont(new Font("SansSerif", Font.PLAIN, 13));
        sectionComboBox.setEnabled(false);
        panel.add(sectionComboBox, gbc);
        
        return panel;
    }
    
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        panel.setBackground(CARD_COLOR);
        panel.setBorder(new EmptyBorder(10, 20, 20, 20));
        
        generateButton = createStyledButton("📄 Generate Report", SUCCESS_COLOR);
        generateButton.addActionListener(e -> generateReport());
        panel.add(generateButton);
        
        cancelButton = createStyledButton("Cancel", new Color(108, 117, 125));
        cancelButton.addActionListener(e -> dispose());
        panel.add(cancelButton);
        
        return panel;
    }
    
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (getModel().isPressed()) {
                    g2d.setColor(bgColor.darker());
                } else if (getModel().isRollover()) {
                    g2d.setColor(bgColor.brighter());
                } else {
                    g2d.setColor(bgColor);
                }
                
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2d.dispose();
                
                super.paintComponent(g);
            }
        };
        
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(180, 40));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        return button;
    }
    
    private void generateReport() {
        try {
            // Get selected month and year
            int month = monthComboBox.getSelectedIndex() + 1;
            int year = (Integer) yearComboBox.getSelectedItem();
            LocalDate reportDate = LocalDate.of(year, month, 1);
            
            // Generate report
            ReportGenerator generator = new ReportGenerator();
            String fileName;
            
            if (fullReportRadio.isSelected()) {
                fileName = generator.generateMonthlyReport(reportDate);
            } else {
                String section = (String) sectionComboBox.getSelectedItem();
                if (section.equals("All Sections")) {
                    fileName = generator.generateMonthlyReport(reportDate);
                } else {
                    fileName = generator.generateSectionReport(section, reportDate);
                }
            }
            
            // Show success message
            int choice = JOptionPane.showOptionDialog(this,
                "Report generated successfully!\n\n" +
                "File: " + fileName + "\n\n" +
                "Would you like to open the report file?",
                "Report Generated",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new String[]{"Open Report", "Close"},
                "Open Report");
            
            if (choice == JOptionPane.YES_OPTION) {
                openReport(fileName);
            }
            
            dispose();
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Error generating report:\n" + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    
    private void openReport(String fileName) {
        try {
            File file = new File(fileName);
            if (file.exists()) {
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(file);
                } else {
                    // Fallback for systems without Desktop support
                    Runtime.getRuntime().exec("notepad.exe " + fileName);
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Could not open report file. Please check the Reports folder manually.",
                "Warning",
                JOptionPane.WARNING_MESSAGE);
        }
    }
}

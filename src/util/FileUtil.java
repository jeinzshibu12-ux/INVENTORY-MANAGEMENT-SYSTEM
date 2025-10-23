package util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Utility class for file operations
 */
public class FileUtil {
    
    /**
     * Ensures a directory exists, creates it if it doesn't
     */
    public static boolean ensureDirectoryExists(String dirPath) {
        File directory = new File(dirPath);
        if (!directory.exists()) {
            return directory.mkdirs();
        }
        return true;
    }
    
    /**
     * Writes content to a file
     */
    public static boolean writeToFile(String filePath, String content) {
        try {
            // Ensure parent directory exists
            File file = new File(filePath);
            File parent = file.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            
            try (FileWriter writer = new FileWriter(filePath)) {
                writer.write(content);
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Reads content from a file
     */
    public static String readFromFile(String filePath) {
        try {
            return new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Checks if a file exists
     */
    public static boolean fileExists(String filePath) {
        return new File(filePath).exists();
    }
    
    /**
     * Deletes a file
     */
    public static boolean deleteFile(String filePath) {
        File file = new File(filePath);
        return file.exists() && file.delete();
    }
    
    /**
     * Gets the absolute path for the Reports directory
     */
    public static String getReportsDirectory() {
        return "Reports" + File.separator;
    }
}

package util;

public class GenerateHash {
    public static void main(String[] args) {
        String password = "admin123";
        String hash = PasswordHasher.hashPassword(password);
        System.out.println("Password: " + password);
        System.out.println("Hash: " + hash);
        System.out.println();
        System.out.println("SQL Update Command:");
        System.out.println("UPDATE managers SET password_hash = '" + hash + "' WHERE username = 'admin';");
    }
}

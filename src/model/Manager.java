package model;

/**
 * Manager model class
 */
public class Manager {
    private int id;
    private String username;
    private String passwordHash;
    private String securityQuestion;
    private String securityAnswer;
    
    // Constructors
    public Manager() {
    }
    
    public Manager(int id, String username, String passwordHash, 
                   String securityQuestion, String securityAnswer) {
        this.id = id;
        this.username = username;
        this.passwordHash = passwordHash;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
    }
    
    // Constructor without ID (for new managers)
    public Manager(String username, String passwordHash, 
                   String securityQuestion, String securityAnswer) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPasswordHash() {
        return passwordHash;
    }
    
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
    
    public String getSecurityQuestion() {
        return securityQuestion;
    }
    
    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }
    
    public String getSecurityAnswer() {
        return securityAnswer;
    }
    
    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }
    
    @Override
    public String toString() {
        return "Manager{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", securityQuestion='" + securityQuestion + '\'' +
                '}';
    }
}

# 👥 Manager Registration Feature Guide

## ✨ New Feature: Register New Manager

Your Inventory Management System now includes a **Manager Registration** feature that allows you to create multiple manager accounts!

## 🎯 How to Register a New Manager

### Step 1: Launch the Application
```batch
start.bat
```

### Step 2: Click "Register" Button
On the login screen, you'll now see TWO buttons:
- **Login** - For existing managers
- **Register** - For creating new managers ⭐ NEW!

### Step 3: Fill Registration Form
The registration dialog will ask for:

| Field | Required | Description |
|-------|----------|-------------|
| **Username** | ✅ Yes | Minimum 3 characters, must be unique |
| **Password** | ✅ Yes | Minimum 6 characters |
| **Confirm Password** | ✅ Yes | Must match password |
| **Security Question** | ✅ Yes | For password recovery (e.g., "What is your favorite color?") |
| **Security Answer** | ✅ Yes | Answer to your security question |

### Step 4: Click "Register"
- System validates all fields
- Checks if username already exists
- Hashes password using same secure method (SHA-256 with salt)
- Stores in database
- Shows success message

### Step 5: Login with New Account
After successful registration:
1. You'll be returned to login screen
2. Enter your new username and password
3. Click "Login"
4. Access the full dashboard!

## 🔒 Security Features

### Same Security for All Users
✅ **Password Hashing**: Every user's password uses SHA-256 with random salt (salt:hash format)  
✅ **Database Storage**: All users stored in same `managers` table  
✅ **Security Questions**: Every user has password recovery via security questions  
✅ **Unique Usernames**: System prevents duplicate usernames  

### Password Requirements
- Minimum 6 characters
- Must match confirmation
- Automatically hashed before storage
- Never stored in plain text

## 📊 Database Structure

All managers (new and existing) are stored identically:

```sql
Table: managers
- id (AUTO_INCREMENT)
- username (UNIQUE)
- password_hash (salt:hash format)
- security_question
- security_answer
- created_at (TIMESTAMP)
```

## 🎮 Example Usage

### Registering a New Manager:
```
Username: john_doe
Password: secure123
Confirm: secure123
Security Q: What is your pet's name?
Security A: Max
```

### After Registration:
- Login with: `john_doe` / `secure123`
- Full access to inventory management
- Can use "Forgot Password" with security question
- Same privileges as admin user

## 🔄 Multiple Managers

You can create **unlimited manager accounts**:
- Each with unique username
- Each with their own password
- All use the same secure hashing
- All stored in the same database
- All have equal system access

## ✅ Validation Checks

The system validates:
1. ✅ All fields filled
2. ✅ Username ≥ 3 characters
3. ✅ Username is unique
4. ✅ Password ≥ 6 characters
5. ✅ Passwords match
6. ✅ Security question provided
7. ✅ Security answer provided

## 🎯 Test It Now!

1. **Run the app**: `start.bat`
2. **Click "Register"**
3. **Create a test account**:
   - Username: testmanager
   - Password: test1234
   - Security Q: What is your favorite food?
   - Security A: pizza
4. **Login with new account**
5. **Access full dashboard!**

## 📝 Verify Registration in Database

Check new managers in database:
```bash
mysql -u user -p1234 -e "USE inventory_db; SELECT id, username, created_at FROM managers;"
```

You'll see all registered managers with their creation timestamps!

## 🔥 Key Benefits

✅ **Multiple Admins**: Create accounts for multiple team members  
✅ **Same Security**: Every user has the same password protection  
✅ **Easy Recovery**: All users can reset passwords via security questions  
✅ **Audit Trail**: Track when each manager was created  
✅ **No Limits**: Create as many manager accounts as needed  

---

**Your Inventory Management System now supports multi-user management! 🎊**

package Core;

import java.sql.*;
import java.util.Date;

public class User {
    private String username;
    private String email;
    private String password;
    private String bloodGroup;
    private Date date;

    // Constructors
    public User() {

    }

    public User(String username, String email, String password, String bloodGroup, Date date) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.bloodGroup = bloodGroup;
        this.date = date;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    // Database operations
    public static Connection connectDB() {
        Connection con = null;
        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection("jdbc:postgresql://roundhouse.proxy.rlwy.net:14900/railway", "postgres", "HKkOQFcntFfleAvDkmZEYvucyBQclYCk");
            if (con != null) {
                System.out.println("Connected to database");
            } else {
                System.out.println("Failed to connect to database");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }

    public static User login(String usernameOrEmail, String password) throws SQLException {
        Connection con = connectDB();
        String selectData = "SELECT username, email, password FROM userlist WHERE (username = ? OR email = ?) AND password = ?";
        try (PreparedStatement prepare = con.prepareStatement(selectData)) {
            prepare.setString(1, usernameOrEmail);
            prepare.setString(2, usernameOrEmail);
            prepare.setString(3, password);
            ResultSet rs = prepare.executeQuery();
            if (rs.next()) {
                return new User(rs.getString("username"), rs.getString("email"), rs.getString("password"), null, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean register() throws SQLException {
        Connection con = connectDB();
        String checkUsername = "SELECT * FROM userlist WHERE username = ?";
        try (PreparedStatement checkStmt = con.prepareStatement(checkUsername)) {
            checkStmt.setString(1, this.username);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                return false;
            } else {
                String insertData = "INSERT INTO userlist (email, username, password, bg, date) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement prepare = con.prepareStatement(insertData)) {
                    prepare.setString(1, this.email);
                    prepare.setString(2, this.username);
                    prepare.setString(3, this.password);
                    prepare.setString(4, this.bloodGroup);
                    prepare.setDate(5, new java.sql.Date(this.date.getTime()));
                    prepare.executeUpdate();
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}

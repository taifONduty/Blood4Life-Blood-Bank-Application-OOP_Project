package Core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class Donor extends User {
    private String phoneNumber;
    private long nidNumber;
    private String address;

    // Default constructor
    public Donor() {
        super();
    }

    // Parameterized constructor
    public Donor(String username, String email, String password, String bloodGroup, Date date, long nidNumber, String address, String phoneNumber) {
        super(username, email, password, bloodGroup, date);
        this.nidNumber = nidNumber;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    // Getters and Setters
    public long getNidNumber() {
        return nidNumber;
    }

    public void setNidNumber(long nidNumber) {
        this.nidNumber = nidNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    // Method to update donor details in the database
    public boolean updateDonorDetails() throws SQLException {
        String updateQuery = "UPDATE userlist SET nid_number = ?, phonenumber = ?, address = ? WHERE email = ?";
        try (Connection con = Core.User.connectDB();
             PreparedStatement prepare = con.prepareStatement(updateQuery)) {

            prepare.setLong(1, this.nidNumber);
            prepare.setString(2, this.phoneNumber);
            prepare.setString(3, this.address);
            prepare.setString(4, this.getEmail());

            int rowsAffected = prepare.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

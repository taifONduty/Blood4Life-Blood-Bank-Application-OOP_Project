package bloodbank.blood4life;

import Core.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.*;

public class VerifiedDonor {

    @FXML
    private AnchorPane verfiedDonor_form;

    @FXML
    private Button verifiedDonor_backBtn;

    @FXML
    private TextField verifiedDonor_Address;

    @FXML
    private TextField verifiedDonor_NIDNumber;

    @FXML
    private Button verifiedDonor_proceedBtn;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private Connection con;
    private ResultSet rs;
    private Statement st;
    private PreparedStatement prepare;
    private String userEmail;

    public void initialize() {
        userEmail = UserSession.getInstance().getUserEmail();
        System.out.println("VerifiedDonor initialized with user email: " + userEmail);
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
        UserSession.getInstance().setUserEmail(userEmail); // Ensure session is updated
        System.out.println("User email set: " + userEmail);
    }

    public void switchToHome(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        Parent root = loader.load();
        LoginController loginController = loader.getController();
        loginController.showHomepageForm();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void beADonor(ActionEvent event) throws IOException {
        long nidNumber = Long.parseLong(verifiedDonor_NIDNumber.getText());
        String address = verifiedDonor_Address.getText();

        try {
            storeDonorData(userEmail, nidNumber, address);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void storeDonorData(String userEmail, long nidNumber, String address) throws SQLException {
        con = Core.User.connectDB();
        String insertData = "UPDATE userlist SET nid_number = ?, address = ? WHERE email = ?";
        prepare = con.prepareStatement(insertData);

        prepare.setLong(1, nidNumber);
        prepare.setString(2, address);
        prepare.setString(3, userEmail);

        prepare.executeUpdate();
        prepare.close();
        con.close();
    }
}

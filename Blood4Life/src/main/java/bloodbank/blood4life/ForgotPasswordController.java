package bloodbank.blood4life;

import Core.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class ForgotPasswordController {

    @FXML
    private TextField forgot_confirmNewPassword;

    @FXML
    private AnchorPane forgot_form;

    @FXML
    private TextField forgot_newPassword;

    @FXML
    private Button forgot_proceedBtn1;

    private Connection con;
    private PreparedStatement prepare;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private String userEmail;

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
        System.out.println("User email set to: " + userEmail);
    }

    public void initialize() {
        String userEmail = UserSession.getInstance().getResetEmail();
        System.out.println("Resetting password for: " + userEmail);
    }

    public void handleProceedBtnAction(ActionEvent event) throws IOException {
        alertMessage alert = new alertMessage();
        if (forgot_confirmNewPassword.getText().isEmpty() || forgot_newPassword.getText().isEmpty()) {
            alert.errorMessage("Please fill all the fields");
        } else if (!forgot_confirmNewPassword.getText().equals(forgot_newPassword.getText())) {
            alert.errorMessage("Passwords do not match");
        } else {
            String newPassword = forgot_newPassword.getText();
            String userEmail = UserSession.getInstance().getResetEmail();

            try {
                updatePassword(userEmail, newPassword);
                UserSession.getInstance().clearSession();
                root = FXMLLoader.load(getClass().getResource("Login.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void updatePassword(String userEmail, String newPassword) throws SQLException {
        con = Core.User.connectDB();
        String updateData = "UPDATE userlist SET password = ? WHERE email = ?";
        prepare = con.prepareStatement(updateData);

        prepare.setString(1, newPassword);
        prepare.setString(2, userEmail);

        prepare.executeUpdate();
        prepare.close();
        con.close();
    }
}

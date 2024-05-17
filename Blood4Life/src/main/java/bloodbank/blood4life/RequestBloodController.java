package bloodbank.blood4life;

import Core.BloodRequest;
import Core.BloodRequestService;
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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RequestBloodController {
    @FXML
    private Button backBtn;

    @FXML
    private TextField bloodTypeField;

    @FXML
    private TextField contactField;

    @FXML
    private AnchorPane form;

    @FXML
    private TextField hospitalField;

    @FXML
    private TextField nameField;

    @FXML
    private Button submitBtn;
    private Connection con;
    private PreparedStatement prepare;
    private BloodRequestService bloodRequestService = BloodRequestService.getInstance();


    @FXML
    public void initialize() {
        submitBtn.setOnAction(e -> handleSubmit());
    }

    private void handleSubmit() {
        String name = nameField.getText();
        String bloodType = bloodTypeField.getText();
        String hospital = hospitalField.getText();
        String contact = contactField.getText();

        // Add to BloodRequestService
        BloodRequest request = new BloodRequest(name, bloodType, hospital, contact);
        bloodRequestService.addBloodRequest(request);

        try {
//            con = LoginController.connectDB();
            con = Core.User.connectDB();
            String insertData = "INSERT INTO feed1 " + "(name, blood, hospital, contact) " + "VALUES (?, ?, ?, ?)";

            prepare = con.prepareStatement(insertData);

            prepare.setString(1, name);
            prepare.setString(2, bloodType);
            prepare.setString(3, hospital);
            prepare.setString(4, contact);

            System.out.println("Added");
            prepare.executeUpdate();
            prepare.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        nameField.clear();
        bloodTypeField.clear();
        hospitalField.clear();
        contactField.clear();
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
}

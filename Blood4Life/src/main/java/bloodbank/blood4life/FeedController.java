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
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FeedController {
    @FXML
    private Button feed_backBtn;
    @FXML
    private ListView<String> feedListView;

    private BloodRequestService bloodRequestService = BloodRequestService.getInstance();

    private final String DB_URL = "jdbc:postgresql://roundhouse.proxy.rlwy.net:14900/railway";
    private final String USER = "postgres";
    private final String PASS = "HKkOQFcntFfleAvDkmZEYvucyBQclYCk";

    @FXML
    public void initialize() {
        // Retrieve data from database and populate ListView
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String sql = "SELECT name, blood, hospital, contact FROM feed1";
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    String name = rs.getString("name");
                    String blood = rs.getString("blood");
                    String hospital = rs.getString("hospital");
                    String contact = rs.getString("contact");

                    BloodRequest request = new BloodRequest(name, blood, hospital, contact);
                    feedListView.getItems().add(request.toString());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

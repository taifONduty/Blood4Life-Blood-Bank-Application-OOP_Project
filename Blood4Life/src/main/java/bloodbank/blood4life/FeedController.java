package bloodbank.blood4life;

import Core.BloodRequest;
import Core.BloodRequestService;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FeedController {

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
}

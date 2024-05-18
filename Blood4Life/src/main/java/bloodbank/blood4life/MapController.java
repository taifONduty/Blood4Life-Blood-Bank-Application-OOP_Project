package bloodbank.blood4life;

import Core.UserSession;
import com.gluonhq.maps.MapPoint;
import com.gluonhq.maps.MapView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MapController {
    @FXML
    private TextField find_hospitalName;

    @FXML
    private ListView<String> find_list;

    @FXML
    private ComboBox<String> list_BG;

    @FXML
    private Button map_backBtn;

    @FXML
    private AnchorPane map_form;

    @FXML
    private VBox map_show;

    private double latitude;
    private double longitude;
    private final MapPoint point = new MapPoint(23.8041, 90.4152);
    private String[] BGList = {"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};

    public void sBG() {
        List<String> list = new ArrayList<>();
        Collections.addAll(list, BGList);
        ObservableList<String> ListData = FXCollections.observableArrayList(list);
        list_BG.setItems(ListData);
    }

    public void initialize() {
        sBG();
        MapView mapView = createMapView();
        map_show.getChildren().add(mapView);
        VBox.setVgrow(mapView, Priority.ALWAYS);
        String userEmail = UserSession.getInstance().getUserEmail();
        System.out.println("MapController initialized with user email: " + userEmail);

        find_list.setItems(getDonorsByBloodGroup(null));

        // Add listener to ComboBox for filtering
        list_BG.setOnAction(event -> {
            String selectedBloodGroup = list_BG.getValue();
            ObservableList<String> filteredDonors = getDonorsByBloodGroup(selectedBloodGroup);
            find_list.setItems(filteredDonors);
        });

        // Add listener to ListView for item selection
        find_list.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showDonorProfile(newValue);
            }
        });
    }

    public MapView createMapView() {
        MapView mapView = new MapView();
        mapView.setPrefSize(420, 420);
        mapView.setZoom(10);
        mapView.flyTo(0, point, 0.1);
        return mapView;
    }

    public ObservableList<String> getDonorsByBloodGroup(String bloodGroup) {
        ObservableList<String> donors = FXCollections.observableArrayList();
        String query;
        if (bloodGroup == null || bloodGroup.isEmpty()) {
            query = "SELECT username, address, nid_number FROM userlist WHERE nid_number IS NOT NULL AND address IS NOT NULL";
        } else {
            query = "SELECT username, address, nid_number FROM userlist WHERE bg = ? AND nid_number IS NOT NULL AND address IS NOT NULL";
        }

        try (Connection conn = Core.User.connectDB();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            if (bloodGroup != null && !bloodGroup.isEmpty()) {
                pstmt.setString(1, bloodGroup);
            }

            ResultSet rs = pstmt.executeQuery();

            System.out.println("Query executed: " + query);

            while (rs.next()) {
                String donor = "Username: " + rs.getString("username") + ", Address: " + rs.getString("address") + ", NID: " + rs.getString("nid_number");
                donors.add(donor);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return donors;
    }

    private void showDonorProfile(String donorDetails) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DonorProfile.fxml"));
            Parent root = loader.load();

            // Pass donor details to the new controller
            DonorProfileController donorProfileController = loader.getController();
            donorProfileController.setDonorDetails(donorDetails);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Donor Profile");
            stage.show();

        } catch (IOException e) {
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

//package bloodbank.blood4life;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Node;
//import javafx.scene.Parent;
//import javafx.scene.control.Button;
//import javafx.scene.layout.AnchorPane;
//import javafx.stage.Stage;
//import javafx.scene.Scene;
//
//import java.io.IOException;
//
//public class MapController {
//    @FXML
//    private AnchorPane map_form;
//
//    @FXML
//    private Button map_backBtn;
//
//    private Stage stage;
//    private Scene scene;
//    private Parent root;
//
//    public void switchToHome(ActionEvent event) throws IOException {
//        root = FXMLLoader.load(getClass().getResource("Login.fxml"));
//        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
//        scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
//    }
//    public void switchToMap(ActionEvent event) throws IOException {
//        Parent root = FXMLLoader.load(getClass().getResource("Map.fxml"));
//        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
//        scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
//    }
//}
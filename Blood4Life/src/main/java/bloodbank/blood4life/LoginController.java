package bloodbank.blood4life;

import Core.User;
import Core.UserSession;
import com.gluonhq.maps.MapPoint;
import com.gluonhq.maps.MapView;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class LoginController implements Initializable {
    @FXML
    private TextField forgot_OTP;
    @FXML
    private Button forgot_backBtn;
    @FXML
    private TextField forgot_email;
    @FXML
    private AnchorPane forgot_form;
    @FXML
    private Button forgot_proceedBtn;
    @FXML
    private Button forgot_sendOTP;
    @FXML
    private Button homepage_DonateBlood;
    @FXML
    private Button homepage_Request;
    @FXML
    private Button homepage_feed;
    @FXML
    private Button homepage_findDonorNearYou;
    @FXML
    private AnchorPane homepage_form;
    @FXML
    private Button homepage_organizations;
    @FXML
    private ImageView imageForm;
    @FXML
    private Button login_btn;
    @FXML
    private Button login_createAccount;
    @FXML
    private Hyperlink login_forgotPassword;
    @FXML
    private AnchorPane login_form;
    @FXML
    private PasswordField login_password;
    @FXML
    private CheckBox login_selectShowPassword;
    @FXML
    private TextField login_showPassword;
    @FXML
    private ImageView login_user;
    @FXML
    private TextField login_username;
    @FXML
    private AnchorPane main_form;
    @FXML
    private AnchorPane map_form;
    @FXML
    private Button map_backBtn;
    @FXML
    private Label menu;
    @FXML
    private Label menuBack;
    @FXML
    private Button signup_btn;
    @FXML
    private PasswordField signup_cPassword;
    @FXML
    private TextField signup_email;
    @FXML
    private AnchorPane signup_form;
    @FXML
    private Button signup_loginAccount;
    @FXML
    private PasswordField signup_password;
    @FXML
    private ComboBox<String> signup_sBG;
    @FXML
    private TextField signup_username;
    @FXML
    private AnchorPane slider;
    @FXML
    private VBox map_show;
    @FXML
    private JFXButton homepage_verifiedDonor;

    private final MapPoint Dhaka = new MapPoint(23.8041, 90.4152);
    private MapView mapView;

    private String otp;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private String userEmail;
    private String userName;

    public void switchToVerifiedDonor(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("verifiedDonor.fxml"));
        Parent root = loader.load();
        VerifiedDonor controller = loader.getController();
        controller.setUserEmail(userEmail); // Pass the user email to the controller
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToMap(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Map.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToRequestBlood(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("RequestBlood.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToFeed(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Feed.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToOrganizations(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Organization.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void login() throws SQLException {
        alertMessage alert = new alertMessage();
        if (login_username.getText().isEmpty() && getPasswordField().getText().isEmpty()) {
            alert.errorMessage("Username and Password are Required");
        } else {
            User user = User.login(login_username.getText(), getPasswordField().getText());
            if (user != null) {
                this.userName = user.getUsername();
                this.userEmail = user.getEmail(); // Set userEmail before using it
                UserSession.getInstance().setUserEmail(this.userEmail);
                login_form.setVisible(false);
                forgot_form.setVisible(false);
                signup_form.setVisible(false);
                homepage_form.setVisible(true);
            } else {
                alert.errorMessage("Incorrect Username or Password");
            }
        }
    }


    private TextField getPasswordField() {
        return login_showPassword.isVisible() ? login_showPassword : login_password;
    }

    public void showPassword() throws SQLException {
        if (login_selectShowPassword.isSelected()) {
            login_showPassword.setText(login_password.getText());
            login_showPassword.setVisible(true);
            login_password.setVisible(false);
        } else {
            login_password.setText(login_showPassword.getText());
            login_showPassword.setVisible(false);
            login_password.setVisible(true);
        }
    }

    public void signup() throws SQLException {
        alertMessage alert = new alertMessage();
        if (signup_email.getText().isEmpty() || signup_username.getText().isEmpty() ||
                signup_password.getText().isEmpty() || signup_cPassword.getText().isEmpty() ||
                signup_sBG.getSelectionModel().getSelectedItem() == null) {
            alert.errorMessage("Please enter all the fields");
        } else if (!signup_password.getText().equals(signup_cPassword.getText())) {
            alert.errorMessage("Passwords do not match");
        } else if (signup_password.getText().length() < 8) {
            alert.errorMessage("Password should be at least 8 characters");
        } else {
            User user = new User(signup_username.getText(), signup_email.getText(), signup_password.getText(),
                    signup_sBG.getSelectionModel().getSelectedItem(), new Date());
            if (user.register()) {
                alert.successMessage("Registered Successfully");
                signupClearFields();
            } else {
                alert.errorMessage(signup_username.getText() + " already exists");
            }
        }
    }

    public static String generateOTP() {
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            otp.append((int) (Math.random() * 10));
        }
        return otp.toString();
    }

    public void sendOTP() {
        alertMessage alert = new alertMessage();
        String recipientEmail = forgot_email.getText();
        if (recipientEmail.isEmpty()) {
            alert.errorMessage("Please enter email");
        } else if (!recipientEmail.endsWith("@gmail.com")) {
            alert.errorMessage("Please enter a valid gmail address");
        } else {
            otp = generateOTP();
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            Session session = Session.getInstance(props,
                    new Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication("teratura961@gmail.com", "wmry avum fmml axoa");
                        }
                    });
            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("teratura961@gmail.com"));
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(recipientEmail));
                message.setSubject("Blood4Life Email Verification");
                message.setText("Your OTP is: " + otp);

                Transport.send(message);
                System.out.println("Email sent successfully!");
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void showHomepageForm() {
        homepage_form.setVisible(true);
        login_form.setVisible(false);
        signup_form.setVisible(false);
        forgot_form.setVisible(false);
    }

    public void verifyOTP(ActionEvent event) throws IOException {
        alertMessage alert = new alertMessage();
        if (forgot_OTP.getText().isEmpty()) {
            alert.errorMessage("Please enter your OTP");
        } else if (forgot_OTP.getText().equals(otp)) {
            alert.successMessage("OTP verified");
            userEmail = forgot_email.getText();
            UserSession.getInstance().setUserEmail(userEmail);
            forgotClearFields();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("ForgotPassword.fxml"));
            Parent root = loader.load();

            // Verify the controller is correctly set and the method exists
            ForgotPasswordController controller = loader.getController();
            if (controller != null) {
                controller.setUserEmail(userEmail);
            } else {
                System.out.println("Controller is null");
            }

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            alert.errorMessage("OTP does not match");
        }
    }

    public void forgotClearFields() {
        forgot_email.setText("");
        forgot_OTP.setText("");
    }

    public void signupClearFields() {
        signup_email.setText("");
        signup_username.setText("");
        signup_password.setText("");
        signup_cPassword.setText("");
        signup_sBG.getSelectionModel().clearSelection();
    }

    public void switchForm(ActionEvent event) {
        if (event.getSource() == signup_loginAccount) {
            signup_form.setVisible(false);
            login_form.setVisible(true);
            forgot_form.setVisible(false);
        } else if (event.getSource() == login_createAccount) {
            signup_form.setVisible(true);
            login_form.setVisible(false);
            forgot_form.setVisible(false);
        } else if (event.getSource() == login_forgotPassword) {
            signup_form.setVisible(false);
            login_form.setVisible(false);
            forgot_form.setVisible(true);
        } else if (event.getSource() == forgot_backBtn) {
            signup_form.setVisible(false);
            login_form.setVisible(true);
            forgot_form.setVisible(false);
        } else if (event.getSource() == forgot_proceedBtn) {
            forgot_form.setVisible(false);
            signup_form.setVisible(false);
            login_form.setVisible(true);
        } else if (event.getSource() == homepage_findDonorNearYou) {
            signup_form.setVisible(false);
            login_form.setVisible(false);
            forgot_form.setVisible(false);
            map_form.setVisible(true);
            homepage_form.setVisible(false);
        } else if (event.getSource() == map_backBtn) {
            signup_form.setVisible(false);
            login_form.setVisible(false);
            forgot_form.setVisible(false);
            map_form.setVisible(false);
            homepage_form.setVisible(true);
        }
    }

    private String[] BGList = {"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};

    public void sBG() {
        List<String> list = new ArrayList<>();
        Collections.addAll(list, BGList);
        ObservableList<String> ListData = FXCollections.observableArrayList(list);
        signup_sBG.setItems(ListData);
    }

    private boolean isMenuVisible = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sBG();
        addMapViewToVBox();
        userEmail = UserSession.getInstance().getUserEmail();
        System.out.println("VerifiedDonor initialized with user email: " + userEmail);

    }

    private void addMapViewToVBox() {
        mapView = createMapView();
        map_show.getChildren().add(mapView);
        VBox.setVgrow(mapView, Priority.ALWAYS);
    }

    private MapView createMapView() {
        MapView mapView = new MapView();
        mapView.setCenter(Dhaka);
        mapView.setPrefSize(400, 500);
        mapView.setZoom(10);
        return mapView;
    }
}

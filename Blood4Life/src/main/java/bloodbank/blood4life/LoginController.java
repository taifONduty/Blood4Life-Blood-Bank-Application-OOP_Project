package bloodbank.blood4life;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

import com.gluonhq.maps.MapPoint;
import com.gluonhq.maps.MapView;
import com.jfoenix.controls.JFXButton;
import javafx.animation.TranslateTransition;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
//import javafx.scene.web.WebEngine;
//import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

import java.net.URL;
import java.util.ResourceBundle;


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
    private ComboBox<?> signup_sBG;

    @FXML
    private TextField signup_username;

    @FXML
    private AnchorPane slider;

    @FXML
    private VBox map_show;
    @FXML
    private JFXButton homepage_verifiedDonor;

    private final MapPoint Dhaka = new MapPoint(23.8041,90.4152);
    private MapView mapView;


    private Connection con;
    private PreparedStatement prepare;
    private ResultSet rs;
    private Statement st;
    private String otp;
//    Map map_back = new Map();

    private Stage stage;
    private Scene scene;
    private Parent root;
    private String userEmail;
    private String userName;

//    public void switchToHome(ActionEvent event) throws IOException {
//        root = FXMLLoader.load(getClass().getResource("Login.fxml"));
//        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
//        scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
//    }
    public void switchToVerifiedDonor(ActionEvent event) throws IOException {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("verifiedDonor.fxml"));
            Parent root = loader.load();
            VerifiedDonor controller = loader.getController();
            controller.setUserEmail(userEmail); // Pass the user email to the controller
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

    }

    public void switchToMap(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Map.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static Connection connectDB() {
        Connection con = null;
        try{
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection("jdbc:postgresql://roundhouse.proxy.rlwy.net:14900/railway", "postgres", "HKkOQFcntFfleAvDkmZEYvucyBQclYCk");

            if(con != null){
                System.out.println("Connected to database");
            }else{
                System.out.println("Failed to connect to database");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }

    public void login() throws SQLException {
        alertMessage alert = new alertMessage();

        if (login_username.getText().isEmpty() && getPasswordField().getText().isEmpty()) {
            alert.errorMessage("Username and Password are Required");
        } else {
            if(!login_username.getText().endsWith("@gmail.com")){
                userName = login_username.getText();
            }else{
                userEmail = login_username.getText();
            }
            String selectData = "SELECT username,email,password FROM userlist WHERE "
                    + "(username = ? OR email=?) and password = ?";
            con = connectDB();

            try {
                prepare = connectDB().prepareStatement(selectData);
                prepare.setString(1, login_username.getText());
                prepare.setString(2, login_username.getText()); // assuming username is used for email as well
                prepare.setString(3, getPasswordField().getText());
                rs = prepare.executeQuery();
                if (rs.next()) {
                    login_form.setVisible(false);
                    forgot_form.setVisible(false);
                    signup_form.setVisible(false);
                    homepage_form.setVisible(true);
                } else {
                    alert.errorMessage("Incorrect Username or Password");
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    private TextField getPasswordField() {
        return login_showPassword.isVisible() ? login_showPassword : login_password;
    }



    public void  showPassword() throws SQLException {
        if(login_selectShowPassword.isSelected()){
            login_showPassword.setText(login_password.getText());
            login_showPassword.setVisible(true);
            login_password.setVisible(false);
        }else{
            login_password.setText(login_showPassword.getText());
            login_showPassword.setVisible(false);
            login_password.setVisible(true);
        }
    }

    public void signup() throws SQLException {

        alertMessage alert = new alertMessage();

        if(signup_email.getText().isEmpty() || signup_username.getText().isEmpty()
                || signup_password.getText().isEmpty()
                || signup_cPassword.getText().isEmpty()
                || signup_sBG.getSelectionModel().getSelectedItem()==null) {

            alert.errorMessage("Please enter all the fields");
        }else if(signup_password.getText() == signup_cPassword.getText()){
            alert.errorMessage("Passwords do not match");
        }else if(signup_password.getText().length()<8){
            alert.errorMessage("Password should be at least 8 characters");
        }else{
            String checkUsername = "SELECT * FROM userlist WHERE username = '"
                    + signup_username.getText() + "'";

            con = connectDB();

            try{
                st = connectDB().createStatement();
                rs = st.executeQuery(checkUsername);

                if(rs.next()) {
                    alert.errorMessage(signup_username.getText() + " already exists");
                }else{
                    String insertData = "INSERT INTO userlist "
                            + "(email, username, password, bg,date) "
                            + "VALUES(?,?,?,?,?)";

                    prepare = con.prepareStatement(insertData);
                    prepare.setString(1, signup_email.getText());
                    prepare.setString(2, signup_username.getText());
                    prepare.setString(3, signup_password.getText());
                    prepare.setString(4,
                            (String) signup_sBG.getSelectionModel().getSelectedItem());

                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                    prepare.setDate(5,sqlDate);
                    prepare.executeUpdate();

                    alert.successMessage("Registered Successfully");

                    signupClearFields();

                }

            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void forgotPassword() throws SQLException {
        alertMessage alert = new alertMessage();
//        if()
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
        }else if (!recipientEmail.endsWith("@gmail.com")) {
            alert.errorMessage("Please enter a valid gmail address");}
        else{
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
    public void verifyOTP(){
        alertMessage alert = new alertMessage();
        if(forgot_OTP.getText().isEmpty()) {
            alert.errorMessage("Please enter your OTP");
        }else if(forgot_OTP.getText().equals(otp)){
            alert.successMessage("OTP verified");
            forgotClearFields();
        }else{
            alert.errorMessage("OTP does not match");
        }
    }

    public void forgotClearFields(){
        forgot_email.setText("");
        forgot_OTP.setText("");
    }

    public void signupClearFields(){
        signup_email.setText("");
        signup_username.setText("");
        signup_password.setText("");
        signup_cPassword.setText("");
        signup_sBG.getSelectionModel().clearSelection();
    }

    public void switchForm(ActionEvent event) {
        if(event.getSource() == signup_loginAccount){
            signup_form.setVisible(false);
            login_form.setVisible(true);
            forgot_form.setVisible(false);
        }else if(event.getSource() == login_createAccount){
            signup_form.setVisible(true);
            login_form.setVisible(false);
            forgot_form.setVisible(false);

        }else if(event.getSource() == login_forgotPassword){
            signup_form.setVisible(false);
            login_form.setVisible(false);
            forgot_form.setVisible(true);
        }else if(event.getSource() == forgot_backBtn){
            signup_form.setVisible(false);
            login_form.setVisible(true);
            forgot_form.setVisible(false);
        } else if (event.getSource() == forgot_proceedBtn) {
            forgot_form.setVisible(false);
            signup_form.setVisible(false);
            login_form.setVisible(true);
        }
        else if(event.getSource() == homepage_findDonorNearYou){
            signup_form.setVisible(false);
            login_form.setVisible(false);
            forgot_form.setVisible(false);
            map_form.setVisible(true);
            homepage_form.setVisible(false);

        }else if(event.getSource() == map_backBtn){
            signup_form.setVisible(false);
            login_form.setVisible(false);
            forgot_form.setVisible(false);
            map_form.setVisible(false);
            homepage_form.setVisible(true);
        }
    }




    private String[] BGList = {"A+","A-","B+","B-","AB+","AB-","O+","O-"};
    public void sBG() {
        List<String> list = new ArrayList<String>();

        for(String data : BGList){
            list.add(data);
        }
        ObservableList ListData = FXCollections.observableArrayList(list);
        signup_sBG.setItems(ListData);

    }
    private boolean isMenuVisible = false;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    //TOdo
        sBG();
        addMapViewToVBox();
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

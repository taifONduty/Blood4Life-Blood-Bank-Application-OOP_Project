package bloodbank.blood4life;

import java.net.URL;
import java.sql.*;
//import java.sql.Date;
import java.util.*;
import java.util.Date;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.image.ImageView;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.*;

public class LoginController implements Initializable {
    @FXML
    private Button forgot_backBtn;

    @FXML
    private TextField forgot_email;

    @FXML
    private AnchorPane forgot_form;

    @FXML
    private Button forgot_proceedBtn;

    @FXML
    private TextField forgot_username;

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
    private TextField login_username;

    @FXML
    private AnchorPane main_form;

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
    private ImageView imageForm;
    @FXML
    private ImageView login_user;


    private Connection con;
    private PreparedStatement prepare;
    private ResultSet rs;
    private Statement st;

    public Connection connectDB() {
        Connection con = null;
        try{
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/"+ "BloodManage","postgres","nawshedislam1");
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

        if(login_username.getText().isEmpty() || login_password.getText().isEmpty()) {
            alert.errorMessage("Username and Password are Required");
        }else{
            String selectData = "SELECT username, password FROM userlist WHERE "
                    + "(username = ? OR email=?) and password = ?";
            con = connectDB();

            try{
                prepare = connectDB().prepareStatement(selectData);
                prepare.setString(1, login_username.getText());
                prepare.setString(2,login_username.getText());
                prepare.setString(3, login_password.getText());
                rs = prepare.executeQuery();
                if(rs.next()) {
                    alert.successMessage("Login Successful");
                }else{
                    alert.errorMessage("Incorrect Username or Password");
                }
            }catch (Exception e){
                System.out.println(e);
            }
        }


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

    public void sendOTP(){
        String otp = generateOTP();
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("teratura961@gmail.com", "wmry avum fmml axoa");
                    }
                });
        try {
            // Compose message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("teratura961@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("ahmedtaif437@gmail.com"));
            message.setSubject("OTP Verification");
            message.setText("Your OTP is: " + otp);

            // Send message
            Transport.send(message);

            System.out.println("Email sent successfully!");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
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
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    //TOdo
        sBG();
    }
}

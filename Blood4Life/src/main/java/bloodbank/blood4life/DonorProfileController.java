package bloodbank.blood4life;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.net.Socket;

public class DonorProfileController {

    @FXML
    private Label donorDetailsLabel;

    @FXML
    private TextArea chatTextArea;
    private String reciever;

    public void setDonorDetails(String donorDetails) {
        donorDetailsLabel.setText(donorDetails);
    }

    @FXML
    private void sendMessage() throws IOException {

//        ChatBoxController controller = new ChatBoxController();
//        Socket socket = new Socket("localhost", 12345);
//        String message = chatTextArea.getText();
//        Client client = new Client(reciever,socket);
//        controller.setClient(client);
        String message = chatTextArea.getText();
        if (!message.trim().isEmpty()) {
            // Implement the logic to send the message to the donor here
            System.out.println("Message sent: " + message);
            chatTextArea.clear();
        }
    }
}

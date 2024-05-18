package bloodbank.blood4life;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class DonorProfileController {

    @FXML
    private Label donorDetailsLabel;

    @FXML
    private TextArea chatTextArea;

    public void setDonorDetails(String donorDetails) {
        donorDetailsLabel.setText(donorDetails);
    }

    @FXML
    private void sendMessage() {
        String message = chatTextArea.getText();
        if (!message.trim().isEmpty()) {
            // Implement the logic to send the message to the donor here
            System.out.println("Message sent: " + message);
            chatTextArea.clear();
        }
    }
}

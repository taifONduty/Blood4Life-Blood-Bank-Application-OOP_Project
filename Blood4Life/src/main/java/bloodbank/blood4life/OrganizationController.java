package bloodbank.blood4life;

import Core.Organization;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;

public class OrganizationController {

    @FXML
    private TextField searchBox;

    @FXML
    private ListView<String> organizationListView;

    @FXML
    private TextField orgName;

    @FXML
    private TextField orgAddress;

    @FXML
    private TextField orgPhone;

    @FXML
    private TextField orgEmail;

    private ObservableList<String> organizationList = FXCollections.observableArrayList();
    private List<Organization> allOrganizations = new ArrayList<>();

    @FXML
    public void initialize() {
        // Load organization data (this should be fetched from your data source)
        allOrganizations.add(new Organization("BADHAN Blood Bank", "BADHAN, Central Office, T.S.C(Ground Floor), University of Dhaka, Dhaka-1000", "+8801534982674", "info@badhan.org"));
        allOrganizations.add(new Organization("Bangladesh Red Crescent Society Blood Bank", "7/5, Aurongzeb Road, Mohammadpur", "+88029139940", "info@bdrcs.org"));
        allOrganizations.add(new Organization("Quantum Blood Bank", "31/V, (1st floor), Shilpacharya Zainul Abedin Sarak, Shantinagar, (Beside Eastern Plus Market), Dhaka–1217", "+8801714010869", "blood@quantummethod.org.bd"));
        // Add more organizations as needed

        // Add organization names to the ListView
        for (Organization org : allOrganizations) {
            organizationList.add(org.getName());
        }
        organizationListView.setItems(organizationList);
    }

    @FXML
    public void searchOrganizations() {
        String searchText = searchBox.getText().toLowerCase();
        organizationList.clear();

        for (Organization org : allOrganizations) {
            if (org.getName().toLowerCase().contains(searchText)) {
                organizationList.add(org.getName());
            }
        }
    }

    @FXML
    public void displayOrganizationDetails(MouseEvent event) {
        String selectedOrgName = organizationListView.getSelectionModel().getSelectedItem();
        if (selectedOrgName != null) {
            for (Organization org : allOrganizations) {
                if (org.getName().equals(selectedOrgName)) {
                    orgName.setText(org.getName());
                    orgAddress.setText(org.getAddress());
                    orgPhone.setText(org.getPhone());
                    orgEmail.setText(org.getEmail());
                    break;
                }
            }
        }
    }
}

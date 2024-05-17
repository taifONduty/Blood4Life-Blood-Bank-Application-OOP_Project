package Core;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class BloodRequestService {
    private static BloodRequestService instance = new BloodRequestService();
    private ObservableList<BloodRequest> bloodRequests = FXCollections.observableArrayList();

    private BloodRequestService() {}

    public static BloodRequestService getInstance() {
        return instance;
    }

    public ObservableList<BloodRequest> getBloodRequests() {
        return bloodRequests;
    }

    public void addBloodRequest(BloodRequest request) {
        bloodRequests.add(request);
    }
}

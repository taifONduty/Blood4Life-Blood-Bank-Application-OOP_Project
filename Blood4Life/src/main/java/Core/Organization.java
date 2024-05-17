package Core;

import java.util.ArrayList;

public class Organization {
    private String name;
    private String type;
    private ArrayList<String> availableBlood;

    // Constructor
    public Organization(String name, String type) {
        this.name = name;
        this.type = type;
        this.availableBlood = new ArrayList<>();
    }

    // Getter and setter methods for name and type (if needed)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public ArrayList<String> getAvailableBlood() {
        return availableBlood;
    }

    public void setAvailableBloodTypes(ArrayList<String> availableBloodTypes) {
        this.availableBlood = availableBlood;
    }

    // Method to add a blood type to the available blood types list
    public void addAvailableBloodType(String bloodType) {
        availableBlood.add(bloodType);
    }

    // Method to remove a blood type from the available blood types list
    public void removeAvailableBloodType(String bloodType) {
        availableBlood.remove(bloodType);
    }

}

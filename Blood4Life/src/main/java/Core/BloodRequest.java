package Core;

public class BloodRequest {
    private String name;
    private String bloodType;
    private String hospital;
    private String contact;

    public BloodRequest(String name, String bloodType, String hospital, String contact) {
        this.name = name;
        this.bloodType = bloodType;
        this.hospital = hospital;
        this.contact = contact;
    }

    public String getName() {
        return name;
    }

    public String getBloodType() {
        return bloodType;
    }

    public String getHospital() {
        return hospital;
    }

    public String getContact() {
        return contact;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Blood Type: " + bloodType + ", Hospital: " + hospital + ", Contact: " + contact;
    }
}

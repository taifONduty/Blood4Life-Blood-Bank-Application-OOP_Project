package Core;

public class BloodRequest {
    private String name;
    private String bloodType;
    private String hospital;
    private String contact;
    private String description;

    public BloodRequest(String name, String bloodType, String hospital, String contact, String description) {
        this.name = name;
        this.bloodType = bloodType;
        this.hospital = hospital;
        this.contact = contact;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return String.format(
                "Name: %-20s\nBlood Type: %-30s Hospital: %-20s\nContact: %-30sDescription: %s",
                name,
                bloodType,
                hospital,
                contact,
                description
        );
    }

}

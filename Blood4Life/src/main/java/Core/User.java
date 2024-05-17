package Core;

import java.time.LocalDate;

public class User {

        private String name;
        private LocalDate dob;
        //private String bloodType;

        // Constructor
        public User(String name, LocalDate dob) {
            this.name = name;
            this.dob = dob;
            // this.bloodType = bloodType;
        }

        // Getter for name
        public String getName() {
            return name;
        }

        // Setter for name
        public void setName(String name) {
            this.name = name;
        }

        // Getter for dob
        public LocalDate getDob() {
            return dob;
        }

        // Setter for dob
        public void setDob(LocalDate dob) {
            this.dob = dob;
        }

        // Getter for bloodType
    /*public String getBloodType() {
        return bloodType;
    }

    // Setter for bloodType
    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }*/

}

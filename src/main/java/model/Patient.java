package model;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Patient {
    @NotEmpty(message = "Please enter a ID")
    private Integer id;
    private String name;
    private Integer age;
    private String gender;
    private String contactDetails;
    private String emergencyContact;
    private String medicalHistory;

    public Patient(String name, Integer age, String gender, String contactDetails, String emergencyContact, String medicalHistory) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.contactDetails = contactDetails;
        this.emergencyContact = emergencyContact;
        this.medicalHistory = medicalHistory;
    }

    public Patient(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}

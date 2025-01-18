package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Patient {
    private Integer id;
    private String name;
    private Integer age;
    private String gender;
    private String contactDetails;
    private String emergencyContact ;
    private String medicalHistory;

    public Patient(String name, Integer age, String gender, String contactDetails, String emergencyContact, String medicalHistory) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.contactDetails = contactDetails;
        this.emergencyContact = emergencyContact;
        this.medicalHistory = medicalHistory;
    }
}

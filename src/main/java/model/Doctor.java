package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Doctor {
    private Integer id;
    private String name;
    private String specialty ;
    private String availability ;
    private String qualifications ;
    private String contact_details  ;

    public Doctor(String name, String specialty, String availability, String qualifications, String contact_details) {
        this.name = name;
        this.specialty = specialty;
        this.availability = availability;
        this.qualifications = qualifications;
        this.contact_details = contact_details;
    }

    public Doctor(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}

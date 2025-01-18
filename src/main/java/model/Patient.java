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
}

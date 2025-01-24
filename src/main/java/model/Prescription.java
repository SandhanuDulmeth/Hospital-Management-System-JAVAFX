package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Prescription {
    private Integer id;
    private Integer pId;
    private Integer dID;
    private String medicine;
    private String dosage;
    private String duration;

    public Prescription(Integer pId, Integer dID, String medicine, String dosage, String duration) {
        this.pId = pId;
        this.dID = dID;
        this.medicine = medicine;
        this.dosage = dosage;
        this.duration = duration;
    }

//    public Prescription(String date, String time) {
//        this.date = date;
//        this.time = time;
//    }
//
//    public Prescription(Integer pId, Integer dID, String date, String time) {
//        this.pId = pId;
//        this.dID = dID;
//        this.date = date;
//        this.time = time;
//    }
}

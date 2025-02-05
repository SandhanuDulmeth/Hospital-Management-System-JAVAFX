package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class PrescriptionEntity {

    private Integer id;
    private Integer pId;
    private Integer dID;
    private String medicine;
    private String dosage;
    private String duration;

    public PrescriptionEntity(Integer pId, Integer dID, String medicine, String dosage, String duration) {
        this.pId = pId;
        this.dID = dID;
        this.medicine = medicine;
        this.dosage = dosage;
        this.duration = duration;
    }
}

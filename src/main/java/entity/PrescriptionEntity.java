package entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class PrescriptionEntity {
    @Id
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

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
public class AppointmentEntity {
    @Id
    private Integer id;
    private Integer pId;
    private Integer dID;
    private String date;
    private String time;

    public AppointmentEntity(String date, String time) {
        this.date = date;
        this.time = time;
    }

    public AppointmentEntity(Integer pId, Integer dID, String date, String time) {
        this.pId = pId;
        this.dID = dID;
        this.date = date;
        this.time = time;
    }
}

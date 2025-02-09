package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class BillingEntity {

    private Integer id;
    private Integer pId;
    private Double totalAmount;
    private String paymentStatus;
    private String date;

    public BillingEntity(Integer pId, Double totalAmount, String paymentStatus, String date) {
        this.pId = pId;
        this.totalAmount = totalAmount;
        this.paymentStatus = paymentStatus;
        this.date = date;
    }
}

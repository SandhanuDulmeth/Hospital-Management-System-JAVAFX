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
public class Billing {
    @NotEmpty(message = "Please enter a ID")
    private Integer id;
    private Integer pId;
    private Double totalAmount;
    private String paymentStatus;
    private String date;

    public Billing(Integer pId, Double totalAmount, String paymentStatus, String date) {
        this.pId = pId;
        this.totalAmount = totalAmount;
        this.paymentStatus = paymentStatus;
        this.date = date;
    }
}

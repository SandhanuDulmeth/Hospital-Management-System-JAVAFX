package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Billing {
    private Integer id;
    private Integer pId;
    private Double totalAmount;
    private String paymentStatus;
    private String date;


}

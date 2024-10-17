package edu.icet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDetailTblDto {

    private Integer num;
    private String productId;
    private String productName;
    private Double unitPrice;
    private Integer qty;
    private Double totalAmount;
}

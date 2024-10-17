package edu.icet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDto {

    private String orderID;
    private Double netTotal;
    private String orderDate;
    private String returnDate;
    private String paymentType;
    private Integer num;
}

package edu.icet.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Orders {

    @Id
    private String orderID;
    private Double netTotal;
    private LocalDate orderDate;
    private LocalDate returnDate;
    private String paymentType;
    private String userId;
}

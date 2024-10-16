package edu.icet.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.File;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductDto {

    private String productID;
    private String name;
    private String productSize;
    private Double price;
    private Integer quantityOnHand;
    private String image;
    private String category;
    private String supplierID;
    private Integer num;
}

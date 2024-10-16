package edu.icet.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.File;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Product {

    @Id
    private String productID;
    private String name;
    private String productSize;
    private Double price;
    private Integer quantityOnHand;
    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String image;
    private String categoryID;
    private String supplierID;
}

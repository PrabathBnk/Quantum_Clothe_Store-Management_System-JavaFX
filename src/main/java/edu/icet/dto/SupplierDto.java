package edu.icet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SupplierDto {

    private String supplierID;
    private String name;
    private String companyName;
    private String emailAddress;
    private Integer num;
}

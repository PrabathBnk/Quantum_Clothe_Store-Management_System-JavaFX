package edu.icet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmployeeDto {

     private String employeeId;
     private String name;
     private String emailAddress;
     private String contact;
     private Integer num;

     public EmployeeDto(String employeeId, String name, String emailAddress, String contact) {
          this.employeeId = employeeId;
          this.name = name;
          this.emailAddress = emailAddress;
          this.contact = contact;
     }
}

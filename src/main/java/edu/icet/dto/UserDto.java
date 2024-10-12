package edu.icet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private String employeeID;
    private String userID;
    private String userType;
    private String password;

    public UserDto(String userID, String password, String userType) {
        this.userID = userID;
        this.password = password;
        this.userType = userType;
    }
}

package edu.icet.entity;

import edu.icet.util.UserID;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@IdClass(UserID.class)
public class User {
    @Id
    private String employeeID;
    @Id
    private String userID;
    private String userType;
    private String password;
}
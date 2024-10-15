package edu.icet.service.custom;

import edu.icet.dto.CreateAccountDto;
import edu.icet.dto.LoginDto;
import edu.icet.dto.UserDto;
import edu.icet.dto.UserTableDto;
import edu.icet.service.SuperService;

import java.util.List;

public interface UserService extends SuperService {
    boolean authenticateUser(LoginDto loginDto);
    boolean createUserAccount(CreateAccountDto createAccountDto);
    boolean sendOTPToUser(String userEmail);
    boolean verifyOTP(String userOTP);
    boolean updatePassword(String email, String newPassword, String confirmPassword);
    List<UserTableDto> getAllUsers();
    String generateUserId();
    boolean addNewUser(UserDto userDto);
}

package edu.icet.service.custom;

import edu.icet.dto.CreateAccountDto;
import edu.icet.dto.LoginDto;
import edu.icet.service.SuperService;

public interface UserService extends SuperService {
    boolean authenticateUser(LoginDto loginDto);
    boolean createUserAccount(CreateAccountDto createAccountDto);
    boolean sendOTPToUser(String userEmail);
    boolean verifyOTP(String userOTP);
    boolean updatePassword(String email, String newPassword, String confirmPassword);
}

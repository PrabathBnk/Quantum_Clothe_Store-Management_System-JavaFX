package edu.icet.service.custom.impl;

import edu.icet.dto.CreateAccountDto;
import edu.icet.dto.LoginDto;
import edu.icet.entity.User;
import edu.icet.entity.UserLog;
import edu.icet.repository.DaoFactory;
import edu.icet.repository.custom.EmployeeDao;
import edu.icet.repository.custom.UserDao;
import edu.icet.repository.custom.UserLogDao;
import edu.icet.service.custom.UserService;
import edu.icet.util.DaoType;
import edu.icet.util.EmailSenderUtil;
import edu.icet.util.OTPUtil;
import edu.icet.util.PasswordUtil;
import javafx.scene.control.Alert;
import org.passay.*;

import java.time.LocalDateTime;

public class UserServiceImpl implements UserService {

    private final UserDao userDao = DaoFactory.getInstance().getDao(DaoType.USER);

    @Override
    public boolean authenticateUser(LoginDto loginDto) {
        if(null==loginDto.getUserType()|| loginDto.getEmail().isEmpty() || loginDto.getPassword().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Make sure all fields are filled!").show();
            return false;
        }


        User user = getUserIfValid(loginDto.getEmail());
        if (null==user) return false;

        if (null==user.getPassword()) {
            new Alert(Alert.AlertType.ERROR, "There is no user account found for this email address! Please create an account.").show();
            return false;
        } else if (!user.getUserType().equals(loginDto.getUserType()) || !PasswordUtil.checkPassword(loginDto.getPassword(), user.getPassword())) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong! Check whether your credentials are correct.").show();
            return false;
        }

        UserLogDao userLogDao = DaoFactory.getInstance().getDao(DaoType.USERLOG);
        return userLogDao.save(new UserLog(user.getUserID(), LocalDateTime.now()));
    }

    @Override
    public boolean createUserAccount(CreateAccountDto createAccountDto) {

        if(null==createAccountDto.getUserType() || createAccountDto.getEmail().isEmpty() || createAccountDto.getPassword().isEmpty() || createAccountDto.getConfirmPassword().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Make sure all fields are filled!").show();
            return false;
        }

        if (validatePassword(createAccountDto.getPassword())) {
            new Alert(Alert.AlertType.ERROR, "Password is not valid!").show();
            return false;
        }

        if (!createAccountDto.getPassword().equals(createAccountDto.getConfirmPassword())) {
            new Alert(Alert.AlertType.ERROR, "Passwords are not matching!").show();
            return false;
        }


        User user = getUserIfValid(createAccountDto.getEmail());
        if (null==user) return false;

        if (null!=user.getPassword()) {
            new Alert(Alert.AlertType.ERROR, "This user has already created account!").show();
            return false;
        } else if (!user.getUserType().equals(createAccountDto.getUserType())) {
            new Alert(Alert.AlertType.ERROR, "Incorrect account type!").show();
            return false;
        }

        user.setPassword(PasswordUtil.encryptPassword(createAccountDto.getPassword()));
        userDao.update(user);
        return true;
    }

    @Override
    public boolean sendOTPToUser(String userEmail) {
        User user = getUserIfValid(userEmail);
        if(null==user) return false;

        String message = "We received a request to reset your password. To proceed, please enter the one-time password (OTP) provided below:\n\n \tYour OTP: " + OTPUtil.generateOTP() + "\nThis OTP is valid for the next 5 minutes. If you did not request a password reset, please ignore this email.";

        return EmailSenderUtil.sendEmail("Password Reset: OTP Code", userEmail, message);
    }

    @Override
    public boolean verifyOTP(String userOTP) {
        if (OTPUtil.isOTPExpired()) {
            new Alert(Alert.AlertType.ERROR, "OTP expired!").show();
            return false;
        } else if (!OTPUtil.checkOTP(userOTP)) {
            new Alert(Alert.AlertType.ERROR, "Wrong OTP Code!").show();
            return false;
        }

        return true;
    }

    @Override
    public boolean updatePassword(String email, String newPassword, String confirmPassword) {
        if (!validatePassword(newPassword)) {
            new Alert(Alert.AlertType.ERROR, "Password is not valid!").show();
            return false;
        } else if (!newPassword.equals(confirmPassword)) {
            new Alert(Alert.AlertType.ERROR, "Passwords are not matching!").show();
            return false;
        }

        User user = getUserIfValid(email);
        user.setPassword(PasswordUtil.encryptPassword(newPassword));

        return userDao.update(user);
    }

    private User getUserIfValid(String email){
        EmployeeDao employeeDao = DaoFactory.getInstance().getDao(DaoType.EMPLOYEE);
        String employeeId = employeeDao.getEmployeeByEmail(email);

        if(null == employeeDao.getEmployeeByEmail(email)) {
            new Alert(Alert.AlertType.ERROR, "Invalid Email Address!").show();
            return null;
        }

        User user = userDao.getUserByEmployeeId(employeeId);
        if(user==null) {
            new Alert(Alert.AlertType.ERROR, "There is no registered user found for this email address!").show();
            return null;
        }

        return user;
    }

    private boolean validatePassword(String password) {
        PasswordValidator passwordValidator = new PasswordValidator(
                new LengthRule(8,16),
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                new CharacterRule(EnglishCharacterData.LowerCase, 1),
                new CharacterRule(EnglishCharacterData.Digit, 1),
                new CharacterRule(EnglishCharacterData.Special, 1),
                new WhitespaceRule()
        );

        return passwordValidator.validate(new PasswordData(password)).isValid();
    }
}

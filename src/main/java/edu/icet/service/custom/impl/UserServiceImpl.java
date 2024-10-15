package edu.icet.service.custom.impl;

import edu.icet.dto.CreateAccountDto;
import edu.icet.dto.LoginDto;
import edu.icet.dto.UserDto;
import edu.icet.dto.UserTableDto;
import edu.icet.entity.Employee;
import edu.icet.entity.User;
import edu.icet.entity.UserLog;
import edu.icet.repository.DaoFactory;
import edu.icet.repository.SuperDao;
import edu.icet.repository.custom.EmployeeDao;
import edu.icet.repository.custom.UserDao;
import edu.icet.repository.custom.UserLogDao;
import edu.icet.service.ServiceFactory;
import edu.icet.service.custom.EmployeeService;
import edu.icet.service.custom.UserService;
import edu.icet.util.*;
import javafx.scene.control.Alert;
import org.modelmapper.ModelMapper;
import org.passay.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<UserTableDto> getAllUsers() {
        List<User> userList = userDao.getAllUsers();
        List<UserTableDto> userTableDtoList = new ArrayList<>();

        EmployeeDao employeeDao = DaoFactory.getInstance().getDao(DaoType.EMPLOYEE);
        for (int i = 0; i < userList.size(); i++) {
            Employee employee = employeeDao.getEmployeeById(userList.get(i).getEmployeeID());
            userTableDtoList.add(new UserTableDto(
                    i + 1,
                    userList.get(i).getUserID(),
                    employee.getName(),
                    employee.getEmailAddress()
            ));
        }

        return userTableDtoList;
    }

    @Override
    public String generateUserId() {

        return String.format("UID%03d", (Integer.parseInt(userDao.getLastUserID().substring(3)) + 1));
    }

    @Override
    public boolean addNewUser(UserDto userDto) {
        System.out.println(userDto.getEmployeeID());

        if (null==userDto.getEmployeeID()) {
            new Alert(Alert.AlertType.ERROR, "Please choose an employee!").show();
            return false;
        }

        return userDao.save(new ModelMapper().map(userDto, User.class));
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

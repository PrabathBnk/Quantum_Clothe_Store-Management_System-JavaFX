package edu.icet.service.custom.impl;

import edu.icet.dto.CreateAccountDto;
import edu.icet.dto.LoginDto;
import edu.icet.entity.User;
import edu.icet.repository.DaoFactory;
import edu.icet.repository.custom.EmployeeDao;
import edu.icet.repository.custom.UserDao;
import edu.icet.service.custom.UserService;
import edu.icet.util.DaoType;
import edu.icet.util.PasswordUtil;
import javafx.scene.control.Alert;
import org.passay.*;

public class UserServiceImpl implements UserService {

    private final UserDao userDao = DaoFactory.getInstance().getDao(DaoType.USER);

    @Override
    public boolean authenticateUser(LoginDto loginDto) {
        if(null==loginDto.getUserType()|| loginDto.getEmail().isEmpty() || loginDto.getPassword().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Make sure all fields are filled!").show();
            return false;
        }


        User user = userDao.getUserByEmployeeId(getEmployeeIdIfValidEmail(loginDto.getEmail()));

        if(user==null) {
            new Alert(Alert.AlertType.ERROR, "There is no registered user found for this email address!").show();
            return false;
        } else if (null==user.getPassword()) {
            new Alert(Alert.AlertType.ERROR, "There is no user account found for this email address! Please create an account.").show();
            return false;
        } else if (!user.getUserType().equals(loginDto.getUserType()) || !PasswordUtil.checkPassword(loginDto.getPassword(), user.getPassword())) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong! Check whether your credentials are correct.").show();
            return false;
        }

        return true;
    }

    @Override
    public boolean createUserAccount(CreateAccountDto createAccountDto) {

        if(null==createAccountDto.getUserType() || createAccountDto.getEmail().isEmpty() || createAccountDto.getPassword().isEmpty() || createAccountDto.getConfirmPassword().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Make sure all fields are filled!").show();
            return false;
        }

        PasswordValidator passwordValidator = new PasswordValidator(
                new LengthRule(8,16),
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                new CharacterRule(EnglishCharacterData.LowerCase, 1),
                new CharacterRule(EnglishCharacterData.Digit, 1),
                new CharacterRule(EnglishCharacterData.Special, 1),
                new WhitespaceRule()
        );

        if (!passwordValidator.validate(new PasswordData(createAccountDto.getPassword())).isValid()) {
            new Alert(Alert.AlertType.ERROR, "Passwords is not valid!").show();
            return false;
        }

        if (!createAccountDto.getPassword().equals(createAccountDto.getConfirmPassword())) {
            new Alert(Alert.AlertType.ERROR, "Passwords are not matching!").show();
            return false;
        }

        String employeeId = getEmployeeIdIfValidEmail(createAccountDto.getEmail());
        if (null==employeeId) return false;
        User user = userDao.getUserByEmployeeId(employeeId);

        if(user==null) {
            new Alert(Alert.AlertType.ERROR, "There is no registered user found for this email address!").show();
            return false;
        } else if (null!=user.getPassword()) {
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

    private String getEmployeeIdIfValidEmail(String email){
        EmployeeDao employeeDao = DaoFactory.getInstance().getDao(DaoType.EMPLOYEE);
        String employeeId = employeeDao.getEmployeeByEmail(email);

        if(null == employeeDao.getEmployeeByEmail(email)) {
            new Alert(Alert.AlertType.ERROR, "Invalid Email Address!").show();
            return null;
        }

        return employeeId;
    }

}

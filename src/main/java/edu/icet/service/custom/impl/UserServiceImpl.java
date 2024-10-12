package edu.icet.service.custom.impl;

import edu.icet.dto.LoginDto;
import edu.icet.dto.UserDto;
import edu.icet.entity.User;
import edu.icet.repository.DaoFactory;
import edu.icet.repository.SuperDao;
import edu.icet.repository.custom.EmployeeDao;
import edu.icet.repository.custom.UserDao;
import edu.icet.service.custom.UserService;
import edu.icet.util.DaoType;
import javafx.scene.control.Alert;

public class UserServiceImpl implements UserService {

    @Override
    public boolean authenticateUser(LoginDto loginDto) {
        EmployeeDao employeeDao = DaoFactory.getInstance().getDao(DaoType.EMPLOYEE);
        String employeeId = employeeDao.getEmployeeByEmail(loginDto.getEmail());

        if(employeeId==null) {
            new Alert(Alert.AlertType.ERROR, "Invalid Email Address!").show();
            return false;
        }

        UserDao userDao = DaoFactory.getInstance().getDao(DaoType.USER);
        User user = userDao.getUserById(employeeId);

        if(user==null) {
            new Alert(Alert.AlertType.ERROR, "There is no registered user found for this email address!").show();
            return false;
        } else if (user.getPassword().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "There is no user account found for this email address! Please create an account.").show();
            return false;
        } else if (!user.getUserType().equals(loginDto.getUserType()) || !user.getPassword().equals(loginDto.getPassword())) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong! Check whether your credentials are correct.").show();
            return false;
        }

        return true;
    }
}

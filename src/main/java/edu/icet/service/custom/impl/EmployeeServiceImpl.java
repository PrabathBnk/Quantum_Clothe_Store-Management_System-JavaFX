package edu.icet.service.custom.impl;

import edu.icet.dto.EmployeeDto;
import edu.icet.entity.Employee;
import edu.icet.repository.DaoFactory;
import edu.icet.repository.SuperDao;
import edu.icet.repository.custom.EmployeeDao;
import edu.icet.service.custom.EmployeeService;
import edu.icet.util.DaoType;
import edu.icet.util.ValidationUtil;
import javafx.scene.control.Alert;
import org.modelmapper.ModelMapper;

public class EmployeeServiceImpl implements EmployeeService {


    private final EmployeeDao employeeDao = DaoFactory.getInstance().getDao(DaoType.EMPLOYEE);

    @Override
    public boolean addEmployee(EmployeeDto employeeDto) {
        if (employeeDto.getName().isEmpty() || employeeDto.getEmailAddress().isEmpty() || employeeDto.getContact().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Make sure to fill the fields!").show();
            return false;
        }

        if (null!=employeeDao.getEmployeeByEmail(employeeDto.getEmailAddress())) {
            new Alert(Alert.AlertType.ERROR, "An employee already exists with is email address!").show();
            return false;
        }

        if (!ValidationUtil.isValidEmail(employeeDto.getEmailAddress())) {
            new Alert(Alert.AlertType.ERROR, "Invalid email address!").show();
            return false;
        }

        if (!ValidationUtil.isValidContactNumber(employeeDto.getContact())) {
            new Alert(Alert.AlertType.ERROR, "Invalid contact number!").show();
            return false;
        }

        Employee employee = new ModelMapper().map(employeeDto, Employee.class);
        return employeeDao.save(employee);
    }

    @Override
    public String generateEmployeeId() {

        Integer count = employeeDao.countAll();
        return String.format("EMP%03d", ++count);
    }
}

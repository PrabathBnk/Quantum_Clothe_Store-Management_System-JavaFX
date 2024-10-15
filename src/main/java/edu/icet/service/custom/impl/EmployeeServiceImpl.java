package edu.icet.service.custom.impl;

import edu.icet.dto.EmployeeDto;
import edu.icet.entity.Employee;
import edu.icet.repository.DaoFactory;
import edu.icet.repository.SuperDao;
import edu.icet.repository.custom.EmployeeDao;
import edu.icet.repository.custom.UserDao;
import edu.icet.service.custom.EmployeeService;
import edu.icet.util.DaoType;
import edu.icet.util.ValidationUtil;
import javafx.scene.control.Alert;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class EmployeeServiceImpl implements EmployeeService {


    private final EmployeeDao employeeDao = DaoFactory.getInstance().getDao(DaoType.EMPLOYEE);

    @Override
    public boolean addEmployee(EmployeeDto employeeDto) {
        if (!isAllFieldsFilled(employeeDto)) return false;

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
        String employeeId = employeeDao.getLastEmployeeID();
        return String.format("EMP%03d", (Integer.parseInt(employeeId.substring(3)) + 1));
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        List<Employee> employeeList = employeeDao.getAll();
        List<EmployeeDto> employeeDtoList = new ArrayList<>();

        for (int i = 0; i < employeeList.size(); i++) {
            employeeDtoList.add(new ModelMapper().map(employeeList.get(i), EmployeeDto.class));
            employeeDtoList.get(i).setNum(i+1);
        }

        return employeeDtoList;
    }

    @Override
    public boolean updateEmployee(EmployeeDto employeeDto) {
        if (!isAllFieldsFilled(employeeDto)) return false;

        if (!ValidationUtil.isValidEmail(employeeDto.getEmailAddress())) {
            new Alert(Alert.AlertType.ERROR, "Invalid email address!").show();
            return false;
        }

        if (!ValidationUtil.isValidContactNumber(employeeDto.getContact())) {
            new Alert(Alert.AlertType.ERROR, "Invalid contact number!").show();
            return false;
        }

        return employeeDao.update(new ModelMapper().map(employeeDto, Employee.class));
    }

    @Override
    public boolean deleteEmployee(EmployeeDto employeeDto) {

        return employeeDao.delete(new ModelMapper().map(employeeDto, Employee.class));
    }

    @Override
    public EmployeeDto getEmployeeIfNonUser(String id) {
        Employee employee = employeeDao.getEmployeeById(id);

        if(null==employee) {
            new Alert(Alert.AlertType.ERROR, "Employee not found!").show();
            return null;
        }

        UserDao userDao = DaoFactory.getInstance().getDao(DaoType.USER);
        if (null!=userDao.getUserByEmployeeId(id)) {
            new Alert(Alert.AlertType.ERROR, "This employee is already registered!").show();
            return null;
        }

        return new ModelMapper().map(employee, EmployeeDto.class);
    }

    private boolean isAllFieldsFilled(EmployeeDto employeeDto) {
        if (employeeDto.getName().isEmpty() || employeeDto.getEmailAddress().isEmpty() || employeeDto.getContact().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Make sure to fill the fields!").show();
            return false;
        }
        return true;
    }
}

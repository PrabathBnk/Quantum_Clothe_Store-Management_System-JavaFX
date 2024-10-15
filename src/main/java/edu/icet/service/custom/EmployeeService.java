package edu.icet.service.custom;

import edu.icet.dto.EmployeeDto;
import edu.icet.service.SuperService;

import java.util.List;

public interface EmployeeService extends SuperService {
    boolean addEmployee(EmployeeDto employeeDto);
    String generateEmployeeId();
    List<EmployeeDto> getAllEmployees();
    boolean updateEmployee(EmployeeDto employeeDto);
    boolean deleteEmployee(EmployeeDto employeeDto);
    EmployeeDto getEmployeeIfNonUser(String id);
}

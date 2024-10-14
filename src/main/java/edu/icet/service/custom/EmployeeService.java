package edu.icet.service.custom;

import edu.icet.dto.EmployeeDto;
import edu.icet.service.SuperService;

public interface EmployeeService extends SuperService {
    boolean addEmployee(EmployeeDto employeeDto);
    String generateEmployeeId();
}

package edu.icet.repository.custom;

import edu.icet.entity.Employee;
import edu.icet.repository.CrudDao;

public interface EmployeeDao extends CrudDao<Employee> {
    String getEmployeeByEmail(String email);
    Employee getEmployeeById(String id);
}

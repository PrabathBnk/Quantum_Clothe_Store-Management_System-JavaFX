package edu.icet.repository.custom;

import edu.icet.entity.Employee;
import edu.icet.repository.CrudDao;

import java.util.List;

public interface EmployeeDao extends CrudDao<Employee> {
    String getEmployeeByEmail(String email);
    Employee getEmployeeById(String id);
    List<Employee> getAll();
    String getLastEmployeeID();
}

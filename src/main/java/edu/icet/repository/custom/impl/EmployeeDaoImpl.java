package edu.icet.repository.custom.impl;

import edu.icet.db.DBConnection;
import edu.icet.entity.Employee;
import edu.icet.repository.custom.EmployeeDao;
import edu.icet.util.HibernateUtil;
import jakarta.persistence.criteria.CriteriaQuery;
import org.hibernate.Session;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class EmployeeDaoImpl implements EmployeeDao {

    @Override
    public String getEmployeeByEmail(String id) {
        String SQL = "SELECT EmployeeID FROM Employee WHERE EmailAddress=?";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            psTm.setObject(1, id);

            ResultSet resultSet = psTm.executeQuery();
            return resultSet.next() ? resultSet.getString("EmployeeID"): null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Employee getEmployeeById(String id) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        Employee employee = session.get(Employee.class, id);
        session.getTransaction().commit();
        session.close();
        return employee;
    }

    @Override
    public List<Employee> getAll() {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        HibernateCriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
        criteriaQuery.from(Employee.class);
        List<Employee> employeeList = session.createQuery(criteriaQuery).getResultList();
        session.close();
        return employeeList;
    }

    @Override
    public boolean delete(Employee employee) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.remove(employee);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public String getLastId() {
        String SQL = "SELECT EmployeeID FROM Employee ORDER BY EmployeeID DESC LIMIT 1";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);

            ResultSet resultSet = psTm.executeQuery();
            return resultSet.next() ? resultSet.getString("EmployeeID"): "EMP000";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(Employee employee) {
        try {
            Session session = HibernateUtil.getSession();
            session.beginTransaction();
            session.merge(employee);
            session.getTransaction().commit();
            return true;
        } catch (RuntimeException exception) {
            return false;
        }
    }

    @Override
    public boolean save(Employee employee) {
        try {
            Session session = HibernateUtil.getSession();
            session.beginTransaction();
            session.persist(employee);
            session.getTransaction().commit();
            session.close();

            return true;

        } catch (RuntimeException exception) {
            return false;
        }
    }

    @Override
    public Integer countAll() {
        String SQL = "SELECT COUNT(*) FROM Employee";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);

            ResultSet resultSet = psTm.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}

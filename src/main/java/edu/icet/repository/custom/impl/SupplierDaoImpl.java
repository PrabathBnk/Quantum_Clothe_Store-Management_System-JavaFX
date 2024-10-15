package edu.icet.repository.custom.impl;

import edu.icet.db.DBConnection;
import edu.icet.entity.Supplier;
import edu.icet.repository.custom.SupplierDao;
import edu.icet.util.HibernateUtil;
import org.hibernate.Session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierDaoImpl implements SupplierDao {
    @Override
    public String getSupplierByName(String name) {
        String SQL = "SELECT SupplierID FROM Supplier WHERE Name=?";

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            psTm.setObject(1, name);
            ResultSet resultSet = psTm.executeQuery();

            return resultSet.next() ? resultSet.getString(1): null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getSupplierByName(String id, String name) {
        String SQL = "SELECT SupplierID FROM Supplier WHERE Name=? && SupplierID!=?";

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            psTm.setObject(1, name);
            psTm.setObject(2, id);
            ResultSet resultSet = psTm.executeQuery();

            return resultSet.next() ? resultSet.getString(1): null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(Supplier supplier) {
        try {
            Session session = HibernateUtil.getSession();
            session.beginTransaction();
            session.merge(supplier);
            session.getTransaction().commit();
            session.close();
            return true;
        } catch (RuntimeException exception) {
            return false;
        }
    }

    @Override
    public boolean save(Supplier supplier) {
        try {
            Session session = HibernateUtil.getSession();
            session.beginTransaction();
            session.persist(supplier);
            session.getTransaction().commit();
            session.close();

            return true;
        } catch (RuntimeException exception) {
            return false;
        }
    }

    @Override
    public Integer countAll() {
        return 0;
    }

    @Override
    public boolean delete(Supplier supplier) {
        try {
            Session session = HibernateUtil.getSession();
            session.beginTransaction();
            session.remove(supplier);
            session.getTransaction().commit();
            session.close();
            return true;
        } catch (RuntimeException exception) {
            return false;
        }
    }

    @Override
    public String getLastId() {
        String SQL = "SELECT SupplierID FROM Supplier ORDER BY SupplierID DESC LIMIT 1";

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            ResultSet resultSet = psTm.executeQuery();

            return resultSet.next() ? resultSet.getString(1): "SUP000";

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Supplier> getAll() {
        String SQL = "SELECT * FROM Supplier";

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            ResultSet resultSet = psTm.executeQuery();
            List<Supplier> supplierList = new ArrayList<>();
            while (resultSet.next()) {
                supplierList.add(new Supplier(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4)
                ));
            }
            return supplierList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

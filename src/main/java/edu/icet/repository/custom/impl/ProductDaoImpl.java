package edu.icet.repository.custom.impl;

import edu.icet.db.DBConnection;
import edu.icet.entity.Product;
import edu.icet.repository.custom.ProductDao;
import edu.icet.util.HibernateUtil;
import org.hibernate.Session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl implements ProductDao {
    @Override
    public String getProductByName(String name) {
        String SQL = "SELECT ProductID FROM Product WHERE Name=?";
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
    public String getProductByName(String id, String name) {
        String SQL = "SELECT ProductID FROM Product WHERE Name=? AND ProductID!=?";
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
    public boolean deleteProductById(String id) {
        String SQL = "DELETE FROM Product WHERE ProductID=?";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            psTm.setObject(1, id);

            return psTm.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public boolean update(Product product) {
        try {
            Session session = HibernateUtil.getSession();
            session.beginTransaction();
            session.merge(product);
            session.getTransaction().commit();
            session.close();
            return true;
        } catch (RuntimeException exception) {
            return false;
        }
    }

    @Override
    public boolean save(Product product) {
        try {
            Session session = HibernateUtil.getSession();
            session.beginTransaction();
            session.persist(product);
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
    public boolean delete(Product product) {
        try {
            Session session = HibernateUtil.getSession();
            session.beginTransaction();
            session.remove(product);
            session.getTransaction().commit();
            session.close();
            return true;
        } catch (RuntimeException exception) {
            return false;
        }
    }

    @Override
    public String getLastId() {
        String SQL = "SELECT ProductID FROM Product ORDER BY ProductID DESC LIMIT 1";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);

            ResultSet resultSet = psTm.executeQuery();
            return resultSet.next() ? resultSet.getString(1): "PID000";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> getAll() {
        String SQL = "SELECT * FROM Product;";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);

            ResultSet resultSet = psTm.executeQuery();
            List<Product> productList = new ArrayList<>();
            while (resultSet.next()) {
                productList.add(new Product(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDouble(4),
                        resultSet.getInt(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getString(8)
                ));
            }

            return productList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

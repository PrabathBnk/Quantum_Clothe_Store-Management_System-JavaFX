package edu.icet.repository.custom.impl;

import edu.icet.db.DBConnection;
import edu.icet.entity.Orders;
import edu.icet.repository.custom.OrderDao;
import edu.icet.util.HibernateUtil;
import org.hibernate.Session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OrderDaoImpl implements OrderDao {
    @Override
    public boolean update(Orders orders) {
        return false;
    }

    @Override
    public boolean save(Orders order) {
        try {
            Session session = HibernateUtil.getSession();
            session.beginTransaction();
            session.persist(order);
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
    public boolean delete(Orders orders) {
        return false;
    }

    @Override
    public String getLastId() {
        String SQL = "SELECT OrderID FROM Orders ORDER BY OrderID DESC LIMIT 1";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);

            ResultSet resultSet = psTm.executeQuery();
            return resultSet.next() ? resultSet.getString(1): "OID000";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Orders> getAll() {
        return List.of();
    }
}

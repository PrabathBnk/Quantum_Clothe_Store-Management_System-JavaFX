package edu.icet.repository.custom.impl;

import edu.icet.db.DBConnection;
import edu.icet.entity.Orders;
import edu.icet.repository.custom.OrderDao;
import edu.icet.util.HibernateUtil;
import org.hibernate.Session;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl implements OrderDao {
    @Override
    public boolean update(Orders order) {
        String SQL = "UPDATE Orders SET NetTotal=?, ReturnDate=?, PaymentType=? WHERE OrderID=?";

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            psTm.setDouble(1, order.getNetTotal());
            psTm.setDate(2, null!= order.getReturnDate() ? Date.valueOf(order.getReturnDate()): null);
            psTm.setObject(3, order.getPaymentType());
            psTm.setObject(4, order.getOrderID());

            return psTm.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
    public boolean delete(Orders order) {
        try {
            Session session = HibernateUtil.getSession();
            session.beginTransaction();
            session.remove(order);
            session.getTransaction().commit();
            session.close();
            return true;
        } catch (RuntimeException exception) {
            return false;
        }
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
        String SQL = "SELECT * FROM Orders";

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            ResultSet resultSet = psTm.executeQuery();

            List<Orders> ordersList = new ArrayList<>();
            while (resultSet.next()) {
                ordersList.add(new Orders(
                        resultSet.getString(1),
                        resultSet.getDouble(2),
                        resultSet.getDate(3).toLocalDate(),
                        null!=resultSet.getDate(4) ? resultSet.getDate(4).toLocalDate():null,
                        resultSet.getString(5),
                        resultSet.getString(6)
                ));
            }

            return ordersList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}

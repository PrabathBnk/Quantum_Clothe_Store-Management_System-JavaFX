package edu.icet.repository.custom.impl;

import edu.icet.db.DBConnection;
import edu.icet.entity.OrderDetail;
import edu.icet.repository.custom.OrderDetailDao;
import edu.icet.util.HibernateUtil;
import org.hibernate.Session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailDaoImpl implements OrderDetailDao {
    @Override
    public boolean update(OrderDetail orderDetail) {
        String SQL = "UPDATE OrderDetail SET Qty=? WHERE OrderId=? AND ProductId=?";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            psTm.setObject(1, orderDetail.getQty());
            psTm.setObject(2, orderDetail.getOrderID());
            psTm.setObject(3, orderDetail.getProductID());

            return psTm.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean save(OrderDetail orderDetail) {
        String SQL = "INSERT INTO OrderDetail VALUES (?,?,?)";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            psTm.setObject(1, orderDetail.getOrderID());
            psTm.setObject(2, orderDetail.getProductID());
            psTm.setObject(3, orderDetail.getQty());

            return psTm.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Integer countAll() {
        return 0;
    }

    @Override
    public boolean delete(OrderDetail orderDetail) {
        String SQL = "DELETE FROM OrderDetail WHERE OrderId=?";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            psTm.setObject(1, orderDetail.getOrderID());

            return psTm.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getLastId() {
        return "";
    }

    @Override
    public List<OrderDetail> getAll() {
        String SQL = "SELECT * FROM OrderDetail";

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            ResultSet resultSet = psTm.executeQuery();

            List<OrderDetail> orderDetailList = new ArrayList<>();
            while (resultSet.next()) {
                orderDetailList.add(new OrderDetail(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getInt(3)
                ));
            }

            return orderDetailList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}

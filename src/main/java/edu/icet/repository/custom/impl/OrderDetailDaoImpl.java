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
import java.util.List;

public class OrderDetailDaoImpl implements OrderDetailDao {
    @Override
    public boolean update(OrderDetail orderDetail) {
        return false;
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
        return false;
    }

    @Override
    public String getLastId() {
        return "";
    }

    @Override
    public List<OrderDetail> getAll() {
        return List.of();
    }

}

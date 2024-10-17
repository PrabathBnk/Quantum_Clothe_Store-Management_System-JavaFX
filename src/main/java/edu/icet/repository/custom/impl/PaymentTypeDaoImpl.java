package edu.icet.repository.custom.impl;

import edu.icet.db.DBConnection;
import edu.icet.repository.custom.PaymentTypeDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentTypeDaoImpl implements PaymentTypeDao {
    @Override
    public List<String> getAllPaymentTypeNames() {
        String SQL = "SELECT TypeName FROM PaymentType";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            ResultSet resultSet = psTm.executeQuery();

            List<String> paymentTypeNameList = new ArrayList<>();
            while (resultSet.next()) paymentTypeNameList.add(resultSet.getString(1));

            return paymentTypeNameList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getPaymentTypeName(String id) {
        String SQL = "SELECT TypeName FROM PaymentType WHERE PaymentTypeID=?";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            psTm.setObject(1, id);
            ResultSet resultSet = psTm.executeQuery();

            return resultSet.next() ? resultSet.getString(1): null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getPaymentTypeId(String name) {
        String SQL = "SELECT PaymentTypeID FROM PaymentType WHERE TypeName=?";
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
}

package edu.icet.repository.custom.impl;

import edu.icet.db.DBConnection;
import edu.icet.repository.custom.CategoryDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDaoImpl implements CategoryDao {
    @Override
    public List<String> getAllCategoryNames() {
        String SQL = "SELECT Name FROM Category";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            ResultSet resultSet = psTm.executeQuery();

            List<String> categoryNameList = new ArrayList<>();
            while (resultSet.next()) {
                categoryNameList.add(resultSet.getString(1));
            }

            return categoryNameList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public String getCategoryId(String name) {
        String SQL = "SELECT CategoryID FROM Category WHERE Name=?";
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
    public String getCategoryName(String id) {
        String SQL = "SELECT Name FROM Category WHERE CategoryID=?";
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


    //CRUD Dao Methods
    @Override
    public boolean update(Object o) {
        return false;
    }

    @Override
    public boolean save(Object o) {
        return false;
    }

    @Override
    public Integer countAll() {
        return 0;
    }

    @Override
    public boolean delete(Object o) {
        return false;
    }

    @Override
    public String getLastId() {
        return "";
    }

    @Override
    public List getAll() {
        return List.of();
    }
}

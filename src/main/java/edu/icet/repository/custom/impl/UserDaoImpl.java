package edu.icet.repository.custom.impl;

import edu.icet.db.DBConnection;
import edu.icet.entity.User;
import edu.icet.repository.custom.UserDao;
import edu.icet.util.HibernateUtil;
import org.hibernate.Session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {

    @Override
    public User getUserByEmployeeId(String id) {
        String SQL = "SELECT * FROM User WHERE EmployeeId=?";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            psTm.setObject(1, id);

            ResultSet resultSet = psTm.executeQuery();

            return resultSet.next() ? new User(
                    resultSet.getString("EmployeeID"),
                    resultSet.getString("UserID"),
                    resultSet.getString("UserType"),
                    resultSet.getString("Password")
            ): null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(User user) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.merge(user);
        session.getTransaction().commit();

        return true;
    }

    @Override
    public boolean save(User user) {
        try{
            Session session = HibernateUtil.getSession();
            session.beginTransaction();
            session.persist(user);
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
    public boolean delete(User user) {
        try {
            Session session = HibernateUtil.getSession();
            session.beginTransaction();
            session.remove(user);
            session.getTransaction().commit();
            return true;
        } catch (RuntimeException exception) {
            return false;
        }
    }

    @Override
    public List<User> getAllUsers() {
        String SQL = "SELECT EmployeeID, UserID FROM User;";

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            ResultSet resultSet = psTm.executeQuery();
            List<User> userList = new ArrayList<>();
            while (resultSet.next()) {
                userList.add(new User(
                        resultSet.getString(1),
                        resultSet.getString(2)
                ));
            }

            return userList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getLastUserID() {
        String SQL = "SELECT UserID FROM User ORDER BY UserID DESC LIMIT 1";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);

            ResultSet resultSet = psTm.executeQuery();
            return resultSet.next() ? resultSet.getString("UserID"): "UID000";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

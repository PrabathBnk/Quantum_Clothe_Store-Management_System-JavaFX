package edu.icet.repository.custom.impl;

import edu.icet.db.DBConnection;
import edu.icet.entity.UserLog;
import edu.icet.repository.custom.UserLogDao;
import edu.icet.util.HibernateUtil;
import org.hibernate.Session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserLogDaoImpl implements UserLogDao {
    @Override
    public boolean update(UserLog userLog) {
        return false;
    }

    @Override
    public boolean save(UserLog userLog) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.persist(userLog);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public Integer countAll() {
        return 0;
    }

    @Override
    public boolean delete(UserLog userLog) {
        return false;
    }

    @Override
    public String getLastId() {
        return "";
    }

    @Override
    public List<UserLog> getAll() {
        return List.of();
    }


    @Override
    public String getLastLogUserId() {
        String SQL = "SELECT UserID FROM UserLog ORDER BY LogDateandTime DESC";

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            ResultSet resultSet = psTm.executeQuery();

            return resultSet.next() ? resultSet.getString(1): null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getLastLogUserType() {
        String SQL = "SELECT UserType FROM User INNER JOIN UserLog ON User.UserID = UserLog.UserID ORDER BY LogDateandTime DESC LIMIT 1";

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            ResultSet resultSet = psTm.executeQuery();
            return resultSet.next() ? resultSet.getString(1): null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

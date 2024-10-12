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
}

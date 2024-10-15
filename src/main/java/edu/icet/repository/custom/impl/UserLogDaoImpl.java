package edu.icet.repository.custom.impl;

import edu.icet.entity.UserLog;
import edu.icet.repository.custom.UserLogDao;
import edu.icet.util.HibernateUtil;
import org.hibernate.Session;

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


}

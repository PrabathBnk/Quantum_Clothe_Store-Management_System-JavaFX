package edu.icet.repository.custom;

import edu.icet.entity.User;
import edu.icet.repository.CrudDao;

import java.util.List;

public interface UserDao extends CrudDao<User> {
    User getUserByEmployeeId(String id);
    List<User> getAllUsers();
    String getLastUserID();
}

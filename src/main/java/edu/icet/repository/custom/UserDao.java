package edu.icet.repository.custom;

import edu.icet.entity.User;
import edu.icet.repository.CrudDao;

public interface UserDao extends CrudDao<User> {
    User getUserByEmployeeId(String id);
}

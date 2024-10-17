package edu.icet.repository.custom;

import edu.icet.entity.UserLog;
import edu.icet.repository.CrudDao;

public interface UserLogDao extends CrudDao<UserLog> {
    String getLastLogUserId();
}

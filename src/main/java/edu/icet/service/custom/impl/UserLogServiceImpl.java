package edu.icet.service.custom.impl;

import edu.icet.repository.DaoFactory;
import edu.icet.repository.custom.UserLogDao;
import edu.icet.service.custom.UserLogService;
import edu.icet.util.DaoType;

public class UserLogServiceImpl implements UserLogService {
    @Override
    public String lastLoggedUser() {
        UserLogDao userLogDao = DaoFactory.getInstance().getDao(DaoType.USERLOG);
        return userLogDao.getLastLogUserId();
    }
}

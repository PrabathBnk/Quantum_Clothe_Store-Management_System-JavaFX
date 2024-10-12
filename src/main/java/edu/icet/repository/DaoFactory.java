package edu.icet.repository;

import edu.icet.repository.custom.impl.EmployeeDaoImpl;
import edu.icet.repository.custom.impl.UserDaoImpl;
import edu.icet.util.DaoType;

public class DaoFactory {

    private static DaoFactory instance;
    private DaoFactory() {};

    public static DaoFactory getInstance() {
        return null==instance? instance=new DaoFactory(): instance;
    }

    public <T extends SuperDao>T getDao(DaoType type){
        switch (type) {
            case EMPLOYEE: return  (T) new EmployeeDaoImpl();
            case USER: return (T) new UserDaoImpl();
            default: return null;
        }
    }
}

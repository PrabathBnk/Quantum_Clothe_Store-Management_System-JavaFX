package edu.icet.repository;


import edu.icet.repository.custom.impl.*;
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
            case USERLOG: return (T) new UserLogDaoImpl();
            case SUPPLIER: return (T) new SupplierDaoImpl();
            case PRODUCT: return (T) new ProductDaoImpl();
            case CATEGORY: return (T) new CategoryDaoImpl();
            case ORDERS: return (T) new OrderDaoImpl();
            case PAYMENT_TYPE: return (T) new PaymentTypeDaoImpl();
            case ORDER_DETAIL: return (T) new OrderDetailDaoImpl();
            default: return null;
        }
    }
}

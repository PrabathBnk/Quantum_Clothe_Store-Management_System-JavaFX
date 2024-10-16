package edu.icet.service;

import edu.icet.service.custom.impl.*;
import edu.icet.util.ServiceType;

public class ServiceFactory {

    private static ServiceFactory instance;
    private ServiceFactory() {}

    public static ServiceFactory getInstance() {
        return null==instance? instance = new ServiceFactory(): instance;
    }

    public <T extends SuperService> T getService(ServiceType type){
        switch (type) {
            case USER: return (T) new UserServiceImpl();
            case EMPLOYEE: return (T) new EmployeeServiceImpl();
            case SUPPLIER: return (T) new SupplierServiceImpl();
            case PRODUCT: return (T) new ProductServiceImpl();
            case CATEGORY: return (T) new CategoryServiceImpl();
            default: return null;
        }
    }
}

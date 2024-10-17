package edu.icet.service.custom.impl;

import edu.icet.repository.DaoFactory;
import edu.icet.repository.custom.PaymentTypeDao;
import edu.icet.service.custom.PaymentTypeService;
import edu.icet.util.DaoType;

import java.util.List;

public class PaymentTypeServiceImpl implements PaymentTypeService {

    PaymentTypeDao paymentTypeDao = DaoFactory.getInstance().getDao(DaoType.PAYMENT_TYPE);

    @Override
    public List<String> getAllPaymentTypeNames() {
        return paymentTypeDao.getAllPaymentTypeNames();
    }

    @Override
    public String getPaymentTypeName(String id) {
        return paymentTypeDao.getPaymentTypeName(id);
    }

    @Override
    public String getPaymentTypeId(String name) {
        return paymentTypeDao.getPaymentTypeId(name);
    }
}

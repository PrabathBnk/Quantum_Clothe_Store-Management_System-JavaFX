package edu.icet.repository.custom;


import edu.icet.repository.SuperDao;

import java.util.List;

public interface PaymentTypeDao extends SuperDao {
    List<String> getAllPaymentTypeNames();
    String getPaymentTypeName(String id);
    String getPaymentTypeId(String name);
}

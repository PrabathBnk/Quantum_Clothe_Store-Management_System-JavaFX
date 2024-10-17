package edu.icet.service.custom;

import edu.icet.service.SuperService;

import java.util.List;

public interface PaymentTypeService extends SuperService {
    List<String> getAllPaymentTypeNames();
    String getPaymentTypeName(String id);
    String getPaymentTypeId(String name);
}

package edu.icet.service.custom;

import edu.icet.dto.SupplierDto;
import edu.icet.service.SuperService;

import java.util.List;

public interface SupplierService extends SuperService {
    List<SupplierDto> getAllSuppliers();
    String getLastSupplierId();
    boolean addNewSupplier(SupplierDto supplierDto);
    boolean updateSupplier(SupplierDto supplierDto);
    boolean deleteSupplier(SupplierDto supplierDto);
}

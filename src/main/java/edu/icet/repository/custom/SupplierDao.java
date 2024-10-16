package edu.icet.repository.custom;

import edu.icet.entity.Supplier;
import edu.icet.repository.CrudDao;

public interface SupplierDao extends CrudDao<Supplier> {
    String getSupplierByName(String name);
    String getSupplierByName(String id, String name);
    String getSupplierNameById(String id);
}

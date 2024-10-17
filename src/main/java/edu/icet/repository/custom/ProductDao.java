package edu.icet.repository.custom;

import edu.icet.entity.Product;
import edu.icet.repository.CrudDao;

import java.util.List;

public interface ProductDao extends CrudDao<Product> {
    String getProductByName(String name);
    String getProductByName(String id, String name);
    Product getProduct(String nameOrId);
    boolean deleteProductById(String id);
}

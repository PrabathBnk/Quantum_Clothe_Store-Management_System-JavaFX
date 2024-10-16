package edu.icet.service.custom;

import edu.icet.dto.ProductDto;
import edu.icet.service.SuperService;

import java.util.List;

public interface ProductService extends SuperService {
    List<ProductDto> getAllProducts();
    String getLastProductId();
    boolean addNewProduct(ProductDto productDto);
    boolean deleteProductById(String id);
    boolean updateProduct(ProductDto productDto);
    List<ProductDto> getAllProductsOfSupplier(String id);
}

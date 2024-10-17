package edu.icet.service.custom.impl;

import edu.icet.dto.ProductDto;
import edu.icet.entity.Product;
import edu.icet.repository.DaoFactory;
import edu.icet.repository.custom.ProductDao;
import edu.icet.service.ServiceFactory;
import edu.icet.service.custom.CategoryService;
import edu.icet.service.custom.ProductService;
import edu.icet.util.DaoType;
import edu.icet.util.ServiceType;
import javafx.scene.control.Alert;
import org.modelmapper.ModelMapper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProductServiceImpl implements ProductService {

    private ProductDao productDao = DaoFactory.getInstance().getDao(DaoType.PRODUCT);

    @Override
    public List<ProductDto> getAllProducts() {
        CategoryService categoryService = ServiceFactory.getInstance().getService(ServiceType.CATEGORY);

        List<Product> productList = productDao.getAll();
        List<ProductDto> productDtoList = new ArrayList<>();

        for (int i = 0; i < productList.size(); i++) {
            productDtoList.add(new ModelMapper().map(productList.get(i), ProductDto.class));
            productDtoList.get(i).setNum(i+1);
            productDtoList.get(i).setCategory(categoryService.getCategoryName(productList.get(i).getCategoryID()));
        }

        return productDtoList;
    }

    @Override
    public String getLastProductId() {
        return productDao.getLastId();
    }


    @Override
    public boolean addNewProduct(ProductDto productDto) {
        if (!isAllFieldsFilled(productDto)) return false;

        if (null!=productDao.getProductByName(productDto.getName())) {
            new Alert(Alert.AlertType.ERROR, "A product already exists with this name!").show();
            return false;
        }

        CategoryService categoryService = ServiceFactory.getInstance().getService(ServiceType.CATEGORY);

        Product product = new ModelMapper().map(productDto, Product.class);
        product.setCategoryID(categoryService.getCategoryId(productDto.getCategory()));
        return productDao.save(product);
    }

    @Override
    public boolean deleteProductById(String id) {
        return productDao.deleteProductById(id);
    }

    @Override
    public boolean updateProduct(ProductDto productDto) {
        if (!isAllFieldsFilled(productDto)) return false;

        if (null!=productDao.getProductByName(productDto.getProductID(), productDto.getName())) {
            new Alert(Alert.AlertType.ERROR, "A product already exists with this name!").show();
            return false;
        }

        CategoryService categoryService = ServiceFactory.getInstance().getService(ServiceType.CATEGORY);

        Product product = new ModelMapper().map(productDto, Product.class);
        product.setCategoryID(categoryService.getCategoryId(productDto.getCategory()));

        return productDao.update(product);
    }

    @Override
    public List<ProductDto> getAllProductsOfSupplier(String id) {
        List<ProductDto> allProducts = getAllProducts();
        List<ProductDto> supplierProductDtoList = new ArrayList<>();
        for (int i = 0, j = 0; i < allProducts.size(); i++) {
            if (allProducts.get(i).getSupplierID().equals(id)) {
                supplierProductDtoList.add(allProducts.get(i));
                supplierProductDtoList.get(j).setNum(++j);
            }
        }
        return supplierProductDtoList;
    }

    @Override
    public ProductDto getProduct(String nameOrId) {
        Product product = productDao.getProduct(nameOrId);
        return null==product ? null: new ModelMapper().map(productDao.getProduct(nameOrId), ProductDto.class);
    }

    private boolean isAllFieldsFilled(ProductDto productDto){
        if (productDto.getName().isEmpty() || null==productDto.getImage() || productDto.getSupplierID().isEmpty() || productDto.getCategory().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Make sure to fill all fields!").show();
            return false;
        }

        return true;
    }
}

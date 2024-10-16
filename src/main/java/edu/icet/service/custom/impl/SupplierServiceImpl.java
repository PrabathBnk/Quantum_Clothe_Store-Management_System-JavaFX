package edu.icet.service.custom.impl;

import edu.icet.dto.SupplierDto;
import edu.icet.entity.Supplier;
import edu.icet.repository.DaoFactory;
import edu.icet.repository.custom.SupplierDao;
import edu.icet.service.custom.SupplierService;
import edu.icet.util.DaoType;
import edu.icet.util.ValidationUtil;
import javafx.scene.control.Alert;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class SupplierServiceImpl implements SupplierService{

    private final SupplierDao supplierDao = DaoFactory.getInstance().getDao(DaoType.SUPPLIER);

    @Override
    public List<SupplierDto> getAllSuppliers() {

        List<Supplier> supplierList = supplierDao.getAll();
        List<SupplierDto> supplierDtoList = new ArrayList<>();
        for (int i = 0; i < supplierList.size(); i++) {
                supplierDtoList.add(new ModelMapper().map(supplierList.get(i), SupplierDto.class));
                supplierDtoList.get(i).setNum(i + 1);
        }

        return supplierDtoList;
    }

    @Override
    public String getLastSupplierId() {
        return supplierDao.getLastId();
    }

    @Override
    public boolean addNewSupplier(SupplierDto supplierDto) {
        if (!validateSupplierDto(supplierDto)) return false;

        if (null != supplierDao.getSupplierByName(supplierDto.getName())) {
            new Alert(Alert.AlertType.ERROR, "A supplier already exists with this name!").show();
            return false;
        }

        return supplierDao.save(new ModelMapper().map(supplierDto, Supplier.class));
    }

    @Override
    public boolean updateSupplier(SupplierDto supplierDto) {
        if (!validateSupplierDto(supplierDto)) return false;

        if (null != supplierDao.getSupplierByName(supplierDto.getSupplierID(), supplierDto.getName())) {
            new Alert(Alert.AlertType.ERROR, "A supplier already exists with this name!").show();
            return false;
        }

        return supplierDao.update(new ModelMapper().map(supplierDto, Supplier.class));
    }

    @Override
    public boolean deleteSupplier(SupplierDto supplierDto) {

        return supplierDao.delete(new ModelMapper().map(supplierDto, Supplier.class));
    }

    @Override
    public String getSupplierNameById(String id) {

        return supplierDao.getSupplierNameById(id);
    }

    private boolean validateSupplierDto(SupplierDto supplierDto) {
        if (supplierDto.getName().isEmpty() || supplierDto.getCompanyName().isEmpty() || supplierDto.getEmailAddress().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Make sure to fill the fields!").show();
            return false;
        }

        if (!ValidationUtil.isValidEmail(supplierDto.getEmailAddress())){
            new Alert(Alert.AlertType.ERROR, "Invalid email address!").show();
            return false;
        }

        return true;
    }

}

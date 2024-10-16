package edu.icet.service.custom.impl;

import edu.icet.repository.DaoFactory;
import edu.icet.repository.custom.CategoryDao;
import edu.icet.service.custom.CategoryService;
import edu.icet.util.DaoType;

import java.util.List;

public class CategoryServiceImpl implements CategoryService {

    CategoryDao categoryDao = DaoFactory.getInstance().getDao(DaoType.CATEGORY);

    @Override
    public List<String> getAllCategoryNames() {
        return categoryDao.getAllCategoryNames();
    }

    @Override
    public String getCategoryId(String name) {
        return categoryDao.getCategoryId(name);
    }

    @Override
    public String getCategoryName(String id) {
        return categoryDao.getCategoryName(id);
    }
}

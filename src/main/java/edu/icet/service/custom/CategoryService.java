package edu.icet.service.custom;

import edu.icet.service.SuperService;

import java.util.List;

public interface CategoryService extends SuperService {
    List<String> getAllCategoryNames();
    String getCategoryId(String name);
    String getCategoryName(String id);
}

package edu.icet.repository.custom;

import edu.icet.entity.Category;
import edu.icet.repository.CrudDao;

import java.util.List;

public interface CategoryDao extends CrudDao {
    List<String> getAllCategoryNames();
    String getCategoryId(String name);
    String getCategoryName(String id);
}

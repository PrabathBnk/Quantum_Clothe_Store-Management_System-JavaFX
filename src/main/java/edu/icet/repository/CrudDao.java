package edu.icet.repository;

import java.util.List;

public interface CrudDao <T> extends SuperDao{
    boolean update(T t);
    boolean save(T t);
    Integer countAll();
    boolean delete(T t);
    String getLastId();
    List<T> getAll();
}

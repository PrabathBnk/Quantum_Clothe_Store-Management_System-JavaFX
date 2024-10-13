package edu.icet.repository;

public interface CrudDao <T> extends SuperDao{
    boolean update(T t);
    boolean save(T t);
    Integer countAll();
}

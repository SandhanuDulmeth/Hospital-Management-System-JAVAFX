package dao;

import javafx.collections.ObservableList;


public interface CrudDao<T, Id> extends SuperDao {

    boolean save(T entity);

    boolean delete(Id id);

    ObservableList<T> gettAll();

    boolean update(T entity);

    T search(Id id);

    Integer getNextId();
}

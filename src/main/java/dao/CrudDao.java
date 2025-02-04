package dao;

import java.sql.SQLException;
import java.util.ArrayList;


public interface CrudDao<T, Id> extends SuperDao {

    boolean save(T entity) throws SQLException;

    boolean delete(Id id) throws SQLException;

    ArrayList<T> gettAll();

    boolean update(T entity);

    T search(Id id);

    Integer getNextId();
}

package dao;

public interface   CrudDao<T,ID> extends SuperDao {
    boolean save(T entity);
    boolean delete(ID id);
}

package service.custom;


import model.Resource;
import service.SuperService;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ResourceService extends SuperService {
    boolean addResource(Resource resource) throws SQLException;

    boolean deleteResource(Integer id) throws SQLException;

    ArrayList<Resource> getAll();

    boolean UpdateResource(Resource resource);

    Resource searchResource(Integer id);

    Integer getNextId();
}

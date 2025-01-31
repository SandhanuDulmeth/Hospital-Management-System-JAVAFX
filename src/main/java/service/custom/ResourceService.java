package service.custom;

import javafx.collections.ObservableList;
import model.Resource;
import service.SuperService;

public interface ResourceService extends SuperService {
    boolean addResource(Resource resource);

    boolean deleteResource(Integer id);

    ObservableList<Resource> getAll();

    boolean UpdateResource(Resource resource);

    Resource searchResource(Integer id);

    Integer getNextId();
}

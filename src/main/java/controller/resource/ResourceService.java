package controller.resource;

import javafx.collections.ObservableList;
import model.Resource;

public interface ResourceService {
    boolean addResource(Resource resource);

    boolean deleteResource(Integer id);

    ObservableList<Resource> getAll();

    boolean UpdateResource(Resource resource);

    Resource searchResource(Integer id);
}

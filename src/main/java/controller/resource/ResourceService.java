package controller.resource;

import javafx.collections.ObservableList;
import model.Resource;

public interface ResourceService {
    boolean addCustomer(Resource resource);

    boolean deleteCustomer(Integer id);

    ObservableList<Resource> getAll();

    boolean UpdateCustomer(Resource resource);

    Resource searchCustomer(Integer id);
}

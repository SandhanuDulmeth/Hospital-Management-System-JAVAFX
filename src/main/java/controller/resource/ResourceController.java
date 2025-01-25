package controller.resource;


import controller.CurdUtil.CrudUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Resource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ResourceController implements ResourceService {
    public static ResourceController insance;

    private ResourceController() {
    }

    public static ResourceController getInstance() {
        return insance == null ? insance = new ResourceController() : insance;

    }

    @Override
    public boolean addCustomer(Resource resource) {


        try {
            return CrudUtil.execute("INSERT INTO Resources VALUES(?,?,?,?,?)",
                    resource.getId(),
                    resource.getName(),
                    resource.getType(),
                    resource.getStatus(),
                    resource.getAllocatedTo()

            );

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean deleteCustomer(Integer id) {


        try {
            return CrudUtil.execute("DELETE FROM Resources WHERE resource_id = ?", id);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public ObservableList<Resource> getAll() {

        ObservableList<Resource> resourceObservableList = FXCollections.observableArrayList();
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM Resources");

            while (resultSet.next()) {

                resourceObservableList.add(new Resource(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getInt(5)


                ));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resourceObservableList;
    }

    @Override
    public boolean UpdateCustomer(Resource resource) {

        try {
            return CrudUtil.execute("UPDATE Resources SET resource_type = ?, resource_name = ?, status = ?, allocated_to = ? WHERE resource_id = ?;",
                    resource.getType(),
                    resource.getName(),
                    resource.getStatus(),
                    resource.getAllocatedTo(),
                    resource.getId()
            );

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Resource searchCustomer(Integer id) {

        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM Resources WHERE resource_id=? ", id);

            if (resultSet.next()) {

                return new Resource(
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getInt(5)

                );
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public Integer getNextId() {
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT IFNULL(MAX(resource_id), 0) + 1 AS next_id FROM Resources");

            resultSet.next();

            return resultSet.getInt(1);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

}

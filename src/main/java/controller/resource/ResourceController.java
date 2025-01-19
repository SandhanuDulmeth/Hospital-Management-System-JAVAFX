package controller.resource;

import db.DBConnection;
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

        Connection connection = null;
        try {
            connection = DBConnection.getINSTANCE().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Resources VALUES(?,?,?,?,?)");
            preparedStatement.setInt(1, resource.getId());
            preparedStatement.setString(2, resource.getName());
            preparedStatement.setString(3, resource.getType());
            preparedStatement.setString(4, resource.getStatus());
            preparedStatement.setInt(5, resource.getAllocatedTo());



            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean deleteCustomer(Integer id) {

        Connection connection = null;

        try {
            connection = DBConnection.getINSTANCE().getConnection();

            PreparedStatement stm = connection.prepareStatement("DELETE FROM Resources WHERE resource_id = ?");
            stm.setObject(1, id);

            return stm.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public ObservableList<Resource> getAll() {

        ObservableList<Resource> resourceObservableList = FXCollections.observableArrayList();
        try {
            ResultSet resultSet = DBConnection.getINSTANCE()
                    .getConnection().
                    createStatement()
                    .executeQuery("SELECT * FROM Resources");

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
        Connection connection = null;
        try {
            connection = DBConnection.getINSTANCE().getConnection();

            PreparedStatement stm = connection.prepareStatement("UPDATE Resources SET resource_type = ?, resource_name = ?, status = ?, allocated_to = ? WHERE resource_id = ?;");
            stm.setObject(5, resource.getId());
            stm.setObject(1, resource.getType());
            stm.setObject(2, resource.getName());
            stm.setObject(3, resource.getStatus());
            stm.setObject(4, resource.getAllocatedTo());


            System.out.println("UpdateCustomer Ok");
            return stm.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Resource searchCustomer(Integer id) {
        Connection connection = null;
        try {
            connection = DBConnection.getINSTANCE().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Resources WHERE resource_id=? ");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Resource resource = new Resource(
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getInt(5)


                );

                return resource;
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public Integer getNextId() {
        try {
            ResultSet resultSet = DBConnection.getINSTANCE().getConnection().createStatement().executeQuery(" SELECT IFNULL(MAX(resource_id), 0) + 1 AS next_id FROM Resources");
            resultSet.next();

            return resultSet.getInt(1);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

}

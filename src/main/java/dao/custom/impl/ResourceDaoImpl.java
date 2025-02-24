package dao.custom.impl;

import dao.custom.ResourceDao;
import entity.ResourceEntity;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ResourceDaoImpl implements ResourceDao {
    @Override
    public boolean save(ResourceEntity resourceEntity) throws SQLException {
        try {
            return CrudUtil.execute("INSERT INTO Resources VALUES(?,?,?,?,?)",
                    resourceEntity.getId(),
                    resourceEntity.getName(),
                    resourceEntity.getType(),
                    resourceEntity.getStatus(),
                    resourceEntity.getAllocatedTo()

            );

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(String id) throws SQLException {

        try {
            return CrudUtil.execute("DELETE FROM Resources WHERE resource_id = ?", id);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArrayList<ResourceEntity> gettAll() {
        ArrayList<ResourceEntity> resourceArrayList = new ArrayList<>();
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM Resources");

            while (resultSet.next()) {

                resourceArrayList.add(new ResourceEntity(
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

        return resourceArrayList;
    }

    @Override
    public boolean update(ResourceEntity resourceEntity) {
        try {
            return CrudUtil.execute("UPDATE Resources SET resource_type = ?, resource_name = ?, status = ?, allocated_to = ? WHERE resource_id = ?;",
                    resourceEntity.getType(),
                    resourceEntity.getName(),
                    resourceEntity.getStatus(),
                    resourceEntity.getAllocatedTo(),
                    resourceEntity.getId()
            );

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResourceEntity search(String id) {
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM Resources WHERE resource_id=? ", id);

            if (resultSet.next()) {

                return new ResourceEntity(
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

    @Override
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

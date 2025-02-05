package dao.custom.impl;

import dao.custom.UserDao;
import entity.UserEntity;
import model.Users;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDaoImpl implements UserDao {
    @Override
    public boolean save(UserEntity entity) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String s) throws SQLException {
        return false;
    }

    @Override
    public ArrayList<UserEntity> gettAll() {
        return null;
    }

    @Override
    public boolean update(UserEntity entity) {
        return false;
    }

    @Override
    public UserEntity search(String s) {
        return null;
    }

    @Override
    public Integer getNextId() {
        return 0;
    }

    @Override
    public ArrayList<UserEntity> getUser(String email) {

        ArrayList<UserEntity> UserList = new ArrayList<>();

        try {
            ResultSet resultSet = CrudUtil.execute("Select * from users where email=?",email);

            while (resultSet.next()) {
                System.out.println("nextUsrDaoImpl");
                UserList.add(new UserEntity(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4)

                ));

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return UserList;
    }

    @Override
    public Integer geLastUserId() {
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT IFNULL (MAX(id), 0) + 1 AS id FROM users");

            resultSet.next();

            return resultSet.getInt(1);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    public Boolean addUser(UserEntity userEntity) {
        Integer id = geLastUserId()+1;

        try {
            return CrudUtil.execute("INSERT INTO users VALUES(?,?,?,?)",
                    id,
                    userEntity.getName(),
                    userEntity.getEmail(),
                    userEntity.getPassword());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}

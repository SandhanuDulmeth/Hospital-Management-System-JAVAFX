package dao.custom;

import dao.CrudDao;
import entity.UserEntity;
import model.Users;

import java.util.ArrayList;

public interface UserDao extends CrudDao<UserEntity,String> {
    ArrayList<UserEntity> getUser(String name);
    Integer geLastUserId();
    Boolean addUser(UserEntity userEntity);
}

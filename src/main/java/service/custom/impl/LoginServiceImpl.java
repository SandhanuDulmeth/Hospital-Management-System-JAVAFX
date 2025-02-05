package service.custom.impl;

import dao.DaoFactory;
import dao.custom.UserDao;
import org.modelmapper.ModelMapper;
import util.CrudUtil;
import model.Users;
import service.custom.LoginService;
import util.DaoType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoginServiceImpl implements LoginService {
    public static LoginServiceImpl insance;

    UserDao userDao = DaoFactory.getInstance().getDaoType(DaoType.USER);

    private LoginServiceImpl() {
    }

    public static LoginServiceImpl getInstance() {
        return insance == null ? insance = new LoginServiceImpl() : insance;

    }


    @Override
    public ArrayList<Users> getUser(String email) {
        ArrayList<Users> UserList = new ArrayList<>();
        userDao.getUser(email).forEach(userEntity -> UserList.add(new ModelMapper().map(userEntity, Users.class)));

        return UserList;
    }

}

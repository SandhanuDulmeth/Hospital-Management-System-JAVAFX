package service.custom.impl;

import dao.DaoFactory;
import dao.custom.UserDao;
import entity.UserEntity;
import org.modelmapper.ModelMapper;

import model.Users;
import service.custom.RegisterService;
import util.DaoType;


public class RegisterSerivceImpl implements RegisterService {
    public static RegisterSerivceImpl insance;
    UserDao userDao = DaoFactory.getInstance().getDaoType(DaoType.USER);

    private RegisterSerivceImpl() {
    }

    public static RegisterSerivceImpl getInstance() {
        return insance == null ? insance = new RegisterSerivceImpl() : insance;
    }


    @Override
    public Integer geLastUserId() {
        return userDao.geLastUserId();
    }

    @Override
    public Boolean addUser(Users users) {

        return userDao.addUser(new ModelMapper().map(users, UserEntity.class));

    }
}

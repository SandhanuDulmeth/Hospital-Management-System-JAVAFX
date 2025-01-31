package service.custom.impl;

import Util.CrudUtil;
import model.Users;
import service.custom.LoginService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoginServiceImpl implements LoginService {
    public static LoginServiceImpl insance;

    private LoginServiceImpl() {
    }

    public static LoginServiceImpl getInstance() {
        return insance == null ? insance = new LoginServiceImpl() : insance;

    }



    @Override
    public List<Users> getUser(String email) {
        List<Users> UserList = new ArrayList<>();

        try {
            ResultSet resultSet = CrudUtil.execute("Select * from users where email=?",email);

            while (resultSet.next()) {

                UserList.add(new Users(resultSet.getInt(1),
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

}

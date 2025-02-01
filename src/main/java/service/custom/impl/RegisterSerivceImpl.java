package service.custom.impl;

import util.CrudUtil;
import model.Users;
import service.custom.RegisterService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RegisterSerivceImpl implements RegisterService {
    public static RegisterSerivceImpl insance;

    private RegisterSerivceImpl() {
    }

    public static RegisterSerivceImpl getInstance() {
        return insance == null ? insance = new RegisterSerivceImpl() : insance;
    }




    @Override
    public List<Users> getAllUser() {
        List<Users> UserList = new ArrayList<>();

        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM users");

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

        if (UserList.isEmpty()) {
            UserList.add(new Users(0));
        }
        return UserList;
    }

    @Override
    public Boolean addUser(Users users) {

        Integer id = getAllUser().get(getAllUser().size() - 1).getId() + 1;

        try {
            return CrudUtil.execute("INSERT INTO users VALUES(?,?,?,?)", id, users.getName(), users.getEmail(), users.getPassword());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}

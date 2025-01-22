package controller.register;

import model.Users;

import java.util.List;

public interface registerService {
    List<Users> getAllUser();
    Boolean addUser(Users users);

}

package controller.Login;

import model.Users;

import java.util.List;

public interface LoginService {
    List<Users> getAllUser();
    List<Users> getUser(String name);

}

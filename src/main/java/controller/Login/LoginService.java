package controller.Login;

import model.Users;

import java.util.List;

public interface LoginService {
    List<Users> getUser(String name);

}

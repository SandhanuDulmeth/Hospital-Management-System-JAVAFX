package service.custom;

import model.Users;
import service.SuperService;

import java.util.List;

public interface LoginService extends SuperService {
    List<Users> getUser(String name);

}

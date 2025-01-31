package service.custom;

import model.Users;
import service.SuperService;

import java.util.List;

public interface RegisterService extends SuperService {
    List<Users> getAllUser();
    Boolean addUser(Users users);

}

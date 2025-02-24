package service.custom;

import model.Users;
import service.SuperService;


public interface RegisterService extends SuperService {
    Integer geLastUserId();

    Boolean addUser(Users users);

}

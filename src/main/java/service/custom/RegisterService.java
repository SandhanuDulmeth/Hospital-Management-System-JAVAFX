package service.custom;

import model.Users;
import service.SuperService;

import java.util.ArrayList;
import java.util.List;

public interface RegisterService extends SuperService {
    Integer geLastUserId();
    Boolean addUser(Users users);

}

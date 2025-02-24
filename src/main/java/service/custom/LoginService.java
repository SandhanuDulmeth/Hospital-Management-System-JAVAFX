package service.custom;

import model.Users;
import service.SuperService;

import java.util.ArrayList;

public interface LoginService extends SuperService {
    ArrayList<Users> getUser(String email);

    Boolean isInsertNewPassword(String email, String newPassword);
}

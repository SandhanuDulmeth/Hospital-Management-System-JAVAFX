package controller.register;

import db.DBConnection;
import model.Users;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class registerController implements registerService{
    public static registerController insance;

    private registerController(){

    }

    public static registerController getInstance()  {
        return  insance == null ? insance = new registerController(): insance;

    }
    @Override
    public List<Users> getAllUser() {
        List<Users> UserList =new ArrayList<>();

        try {
         String  SQL ="SELECT * FROM users";
            ResultSet resultSet = DBConnection.getINSTANCE().getConnection().createStatement().executeQuery(SQL);

            // ResultSet resultSet= CrudUtil.execute(SQL);
            while (resultSet.next()){

                UserList.add(new Users(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4)

                ));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (UserList.isEmpty()){UserList.add(new Users(0));}
        return UserList;
    }
    @Override
    public Boolean addUser(Users users) {

        Integer id = getAllUser().get(getAllUser().size()-1).getId()+1;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = DBConnection.getINSTANCE().getConnection().prepareStatement("INSERT INTO users VALUES(?,?,?,?)");
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, users.getName());
            preparedStatement.setString(3, users.getEmail());
            preparedStatement.setString(4, users.getPassword());
            return  preparedStatement.executeUpdate()>0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}

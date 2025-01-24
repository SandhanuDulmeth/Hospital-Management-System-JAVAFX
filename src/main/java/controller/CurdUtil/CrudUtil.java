package controller.CurdUtil;

import db.DBConnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CrudUtil {

    public static <T> T execute(String SQL, Object... val) throws SQLException{

        PreparedStatement psTm = DBConnection.getINSTANCE().getConnection().prepareStatement(SQL);

        if(SQL.startsWith("SELECT") || SQL.startsWith("select")|| SQL.startsWith("Select")){
            return (T) psTm.executeQuery();
        }else{
            for (int i = 0; i < val.length; i++) {
                psTm.setObject(i+1,val[i]);
            }
            return (T) (Boolean) (psTm.executeUpdate()>0);
        }
    }
}

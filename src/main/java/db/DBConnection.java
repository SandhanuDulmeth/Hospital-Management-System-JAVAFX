package db;


import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnection {
    private static DBConnection dbConnection;

    @Getter
    private final Connection connection;

    private DBConnection() throws SQLException {
   connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/HospitalManagement","root","1234");
    }

    public static DBConnection getINSTANCE() throws SQLException {
        return dbConnection==null?dbConnection=new DBConnection():dbConnection;
    }

}

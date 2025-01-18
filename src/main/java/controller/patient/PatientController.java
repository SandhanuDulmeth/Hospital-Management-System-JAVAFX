package controller.patient;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Patient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class PatientController implements PatientService{
    public static PatientController insance;

    private PatientController() {
    }

    public static PatientController getInstance() {
        return insance == null ? insance = new PatientController() : insance;

    }

    @Override
    public boolean addCustomer(Patient patient) {

//        Connection connection = null;
//        try {
//            connection = DBConnection.getINSTANCE().getConnection();
//            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO customer VALUES(?,?,?,?,?,?,?,?,?)");
//            preparedStatement.setString(1, patient.getCustID());
//            preparedStatement.setString(2, patient.getCustTitle());
//            preparedStatement.setString(3, patient.getCustName());
//            preparedStatement.setDate(4, (java.sql.Date) patient.getDOB());
//            preparedStatement.setDouble(5, patient.getSalary());
//            preparedStatement.setString(6, patient.getCustAddress());
//            preparedStatement.setString(7, patient.getCity());
//            preparedStatement.setString(8, patient.getProvince());
//            preparedStatement.setString(9, patient.getPostalCode());
//            return preparedStatement.executeUpdate() > 0;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        return false;
    }

    @Override
    public boolean deleteCustomer(String id) {

//        Connection connection = null;
//        //int i = 0;
//        try {
//            connection = DBConnection.getINSTANCE().getConnection();
//
//            PreparedStatement stm = connection.prepareStatement("DELETE FROM customer WHERE CustID = ?");
//            stm.setObject(1, id);
//            //  i = stm.executeUpdate();
//            return stm.executeUpdate() > 0;
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
return false;

    }

    @Override
    public ObservableList<Patient> getAll() {

        ObservableList<Patient> patientObservableList = FXCollections.observableArrayList();
        try {
            ResultSet resultSet = DBConnection.getINSTANCE()
                    .getConnection().
                    createStatement()
                    .executeQuery("SELECT * FROM Patient");

            while (resultSet.next()) {

                patientObservableList.add(new Patient(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getInt(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7)

                ));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return patientObservableList;
    }

    @Override
    public boolean UpdateCustomer(Patient patient) {
//        Connection connection = null;
//        try {
//            connection = DBConnection.getINSTANCE().getConnection();
//
//            PreparedStatement stm = connection.prepareStatement("UPDATE customer SET CustTitle = ?, CustName = ?, DOB = ?, salary = ?, CustAddress = ?,City = ?,Province = ?,PostalCode=? WHERE CustID = ?");
//            stm.setObject(9, patient.getCustID());
//            stm.setObject(1, patient.getCustTitle());
//            stm.setObject(2, patient.getCustName());
//            stm.setObject(3, patient.getDOB());
//            stm.setObject(4, patient.getSalary());
//            stm.setObject(5, patient.getCustAddress());
//            stm.setObject(6, patient.getCity());
//            stm.setObject(7, patient.getProvince());
//            stm.setObject(8, patient.getPostalCode());
//            //  int i = stm.executeUpdate();
//
//            System.out.println("u");
//            return stm.executeUpdate() > 0;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        return false;
    }

    @Override
    public Patient searchCustomer(String CusID) {
//        Connection connection = null;
//        try {
//            connection = DBConnection.getINSTANCE().getConnection();
//            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Customer WHERE CustID=? ");
//            preparedStatement.setString(1, CusID);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            if (resultSet.next()) {
//                Patient patient = new Patient(CusID,
//                        resultSet.getString(2),
//                        resultSet.getString(3),
//                        (Date) resultSet.getObject(4),
//                        Double.valueOf(resultSet.getString(5)),
//                        resultSet.getString(6),
//                        resultSet.getString(7),
//                        resultSet.getString(8),
//                        resultSet.getString(9)
//                );

//                return patient;
//            }
//
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        return null;
    }

}

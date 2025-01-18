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

public class PatientController implements PatientService {
    public static PatientController insance;

    private PatientController() {
    }

    public static PatientController getInstance() {
        return insance == null ? insance = new PatientController() : insance;

    }

    @Override
    public boolean addCustomer(Patient patient) {

        Connection connection = null;
        try {
            connection = DBConnection.getINSTANCE().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Patient VALUES(?,?,?,?,?,?,?)");
            preparedStatement.setInt(1, patient.getId());
            preparedStatement.setString(2, patient.getName());
            preparedStatement.setInt(3, patient.getAge());
            preparedStatement.setString(4, patient.getGender());
            preparedStatement.setString(5, patient.getEmergencyContact());
            preparedStatement.setString(6, patient.getEmergencyContact());
            preparedStatement.setString(7, patient.getMedicalHistory());

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean deleteCustomer(Integer id) {

        Connection connection = null;

        try {
            connection = DBConnection.getINSTANCE().getConnection();

            PreparedStatement stm = connection.prepareStatement("DELETE FROM Patient WHERE patient_id = ?");
            stm.setObject(1, id);

            return stm.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


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
        Connection connection = null;
        try {
            connection = DBConnection.getINSTANCE().getConnection();

            PreparedStatement stm = connection.prepareStatement("UPDATE patient SET name = ?, age = ?, gender = ?, contact_details = ?, emergency_contact = ?, medical_history = ? WHERE patient_id = ?;");
            stm.setObject(7, patient.getId());
            stm.setObject(1, patient.getName());
            stm.setObject(2, patient.getAge());
            stm.setObject(3, patient.getGender());
            stm.setObject(4, patient.getContactDetails());
            stm.setObject(5, patient.getEmergencyContact());
            stm.setObject(6, patient.getMedicalHistory());

            //  int i = stm.executeUpdate();

            System.out.println("UpdateCustomer Ok");
            return stm.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Patient searchCustomer(Integer id) {
        Connection connection = null;
        try {
            connection = DBConnection.getINSTANCE().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM patient WHERE patient_id=? ");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Patient patient = new Patient(
                        resultSet.getString(2),
                        resultSet.getInt(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7)

                );

                return patient;
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public Integer getNextId() {
        try {
            ResultSet resultSet = DBConnection.getINSTANCE().getConnection().createStatement().executeQuery(" SELECT IFNULL(MAX(patient_id), 0) + 1 AS next_id FROM patient");
            resultSet.next();

            return resultSet.getInt(1);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

}

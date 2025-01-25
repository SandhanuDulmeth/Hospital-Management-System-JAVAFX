package controller.patient;

import controller.CurdUtil.CrudUtil;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Patient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class PatientController implements PatientService {
    public static PatientController insance;

    private PatientController() {
    }

    public static PatientController getInstance() {
        return insance == null ? insance = new PatientController() : insance;

    }

    @Override
    public boolean addCustomer(Patient patient) {


        try {
            return CrudUtil.execute("INSERT INTO Patient VALUES(?,?,?,?,?,?,?)",
                    patient.getId(),
                    patient.getName(),
                    patient.getAge(),
                    patient.getGender(),
                    patient.getContactDetails(),
                    patient.getEmergencyContact(),
                    patient.getMedicalHistory()
            );

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean deleteCustomer(Integer id) {


        try {
            return CrudUtil.execute("DELETE FROM Patient WHERE patient_id = ?", id);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public ObservableList<Patient> getAll() {

        ObservableList<Patient> patientObservableList = FXCollections.observableArrayList();
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM Patient");

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

        try {
            return CrudUtil.execute("UPDATE patient SET name = ?, age = ?, gender = ?, contact_details = ?, emergency_contact = ?, medical_history = ? WHERE patient_id = ?;",
                    patient.getName(),
                    patient.getAge(),
                    patient.getGender(),
                    patient.getContactDetails(),
                    patient.getEmergencyContact(),
                    patient.getMedicalHistory(),
                    patient.getId()
            );

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Patient searchCustomer(Integer id) {

        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM patient WHERE patient_id=? ", id);


            if (resultSet.next()) {
                return new Patient(
                        resultSet.getString(2),
                        resultSet.getInt(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7)

                );
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public Integer getNextId() {
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT IFNULL(MAX(patient_id), 0) + 1 AS next_id FROM patient");

            resultSet.next();
            return resultSet.getInt(1);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

}

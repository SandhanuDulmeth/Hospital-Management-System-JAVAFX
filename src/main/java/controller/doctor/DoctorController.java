package controller.doctor;


import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Doctor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DoctorController implements DoctorService {
    public static DoctorController insance;

    private DoctorController() {
    }

    public static DoctorController getInstance() {
        return insance == null ? insance = new DoctorController() : insance;

    }

    @Override
    public boolean addCustomer(Doctor doctor) {

        Connection connection = null;
        try {
            connection = DBConnection.getINSTANCE().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Doctor VALUES(?,?,?,?,?,?)");
            preparedStatement.setInt(1, doctor.getId());
            preparedStatement.setString(2, doctor.getName());
            preparedStatement.setString(3, doctor.getAvailability());
            preparedStatement.setString(4, doctor.getAvailability());
            preparedStatement.setString(5, doctor.getQualifications());
            preparedStatement.setString(6, doctor.getContact_details());


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

            PreparedStatement stm = connection.prepareStatement("DELETE FROM Doctor WHERE doctor_id = ?");
            stm.setObject(1, id);

            return stm.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public ObservableList<Doctor> getAll() {

        ObservableList<Doctor> doctorObservableList = FXCollections.observableArrayList();
        try {
            ResultSet resultSet = DBConnection.getINSTANCE()
                    .getConnection().
                    createStatement()
                    .executeQuery("SELECT * FROM Doctor");

            while (resultSet.next()) {

                doctorObservableList.add(new Doctor(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6)

                ));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return doctorObservableList;
    }

    @Override
    public boolean UpdateCustomer(Doctor doctor) {
        Connection connection = null;
        try {
            connection = DBConnection.getINSTANCE().getConnection();

            PreparedStatement stm = connection.prepareStatement("UPDATE doctor SET name = ?, specialty = ?, availability = ?, qualifications = ?, contact_details = ? WHERE doctor_id = ?;");
            stm.setObject(6, doctor.getId());
            stm.setObject(1, doctor.getName());
            stm.setObject(2, doctor.getSpecialty());
            stm.setObject(3, doctor.getAvailability());
            stm.setObject(4, doctor.getQualifications());
            stm.setObject(5, doctor.getContact_details());

            System.out.println("UpdateCustomer Ok");
            return stm.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Doctor searchCustomer(Integer id) {
        Connection connection = null;
        try {
            connection = DBConnection.getINSTANCE().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM doctor WHERE doctor_id=? ");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Doctor doctor = new Doctor(
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6)

                );

                return doctor;
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public Integer getNextId() {
        try {
            ResultSet resultSet = DBConnection.getINSTANCE().getConnection().createStatement().executeQuery(" SELECT IFNULL(MAX(doctor_id), 0) + 1 AS next_id FROM doctor");
            resultSet.next();

            return resultSet.getInt(1);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

}

package controller.appointment;

import controller.appointment.AppointmentService;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AppointmentController implements AppointmentService {
    public static AppointmentController insance;

    private AppointmentController() {
    }

    public static AppointmentController getInstance() {
        return insance == null ? insance = new AppointmentController() : insance;

    }

    @Override
    public boolean addCustomer(Appointment appointment) {

        Connection connection = null;
        try {
            connection = DBConnection.getINSTANCE().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Appointment VALUES(?,?,?,?,?)");
            preparedStatement.setInt(1, appointment.getId());
            preparedStatement.setInt(2, appointment.getPId());
            preparedStatement.setInt(3, appointment.getDID());
            preparedStatement.setString(4, appointment.getDate());
            preparedStatement.setString(5, appointment.getTime());



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

            PreparedStatement stm = connection.prepareStatement("DELETE FROM Appointment WHERE appointment_id = ?");
            stm.setObject(1, id);

            return stm.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public ObservableList<Appointment> getAll() {

        ObservableList<Appointment> appointmentObservableList = FXCollections.observableArrayList();
        try {
            ResultSet resultSet = DBConnection.getINSTANCE()
                    .getConnection().
                    createStatement()
                    .executeQuery("SELECT * FROM Appointment");

            while (resultSet.next()) {

                appointmentObservableList.add(new Appointment(
                        resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getInt(3),
                        resultSet.getString(4),
                        resultSet.getString(5)


                ));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return appointmentObservableList;
    }

    @Override
    public boolean UpdateCustomer(Appointment appointment) {
        Connection connection = null;
        try {
            connection = DBConnection.getINSTANCE().getConnection();

            PreparedStatement stm = connection.prepareStatement("UPDATE Appointment SET patient_id = ?, doctor_id = ?, appointment_date = ?,time=? WHERE appointment_id = ?;");
            stm.setObject(5, appointment.getId());
            stm.setObject(1, appointment.getPId());
            stm.setObject(2, appointment.getDID());
            stm.setObject(3, appointment.getDate());
            stm.setObject(4, appointment.getTime());


            System.out.println("UpdateCustomer Ok");
            return stm.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Appointment searchCustomer(Integer id) {
        Connection connection = null;
        try {
            connection = DBConnection.getINSTANCE().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Appointments WHERE appointment_id=? ");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Appointment appointment = new Appointment(
                        resultSet.getInt(2),
                        resultSet.getInt(3),
                        resultSet.getString(4),
                        resultSet.getString(5)

                );

                return appointment;
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public Integer getNextId() {
        try {
            ResultSet resultSet = DBConnection.getINSTANCE().getConnection().createStatement().executeQuery(" SELECT IFNULL(MAX(appointment_id), 0) + 1 AS next_id FROM Appointment");
            resultSet.next();

            return resultSet.getInt(1);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

}

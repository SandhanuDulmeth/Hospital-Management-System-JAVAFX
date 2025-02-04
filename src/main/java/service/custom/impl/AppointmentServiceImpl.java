package service.custom.impl;

import util.CrudUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


import model.Appointment;
import model.Doctor;
import model.Patient;
import service.custom.AppointmentService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AppointmentServiceImpl implements AppointmentService {
    private static AppointmentServiceImpl instance;


    private AppointmentServiceImpl() {
    }

    public static AppointmentServiceImpl getInstance() {
        return (instance == null) ? (instance = new AppointmentServiceImpl()) : instance;
    }

    @Override
    public boolean addAppointment(Appointment appointment) {


        try {

            return CrudUtil.execute("INSERT INTO Appointment VALUES(?,?,?,?,?)",
                    appointment.getId(),
                    appointment.getPId(),
                    appointment.getDID(),
                    appointment.getDate(),
                    appointment.getTime()
            );

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean deleteAppointment(Integer id) {


        try {
            return CrudUtil.execute("DELETE FROM Appointment WHERE appointment_id = ?", id);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public ObservableList<Appointment> getAll() {

        ObservableList<Appointment> appointmentObservableList = FXCollections.observableArrayList();
        try {

            ResultSet resultSet = CrudUtil.execute("SELECT * FROM Appointment");

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
    public boolean updateAppointment(Appointment appointment) {

        try {
            return CrudUtil.execute("UPDATE Appointment SET patient_id = ?, doctor_id = ?, appointment_date = ?,time=? WHERE appointment_id = ?;",
                    appointment.getPId(),
                    appointment.getDID(),
                    appointment.getDate(),
                    appointment.getTime(),
                    appointment.getId()
            );

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Appointment searchAppointment(Integer id) {

        try {

            ResultSet resultSet = CrudUtil.execute("SELECT * FROM Appointment WHERE appointment_id=? ", id);
            if (resultSet.next()) {
                return new Appointment(
                        resultSet.getInt(2),
                        resultSet.getInt(3),
                        resultSet.getString(4),
                        resultSet.getString(5)

                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


    @Override
    public ArrayList<Patient> getPatientsID() {
        ArrayList<Patient> appointmentPatientsIDList = new ArrayList<>();
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT patient_id,name FROM PatientEntity");
            while (resultSet.next()) {
                appointmentPatientsIDList.add(new Patient(resultSet.getInt(1), resultSet.getString(2)));

            }
            return appointmentPatientsIDList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArrayList<Doctor> getDocID() {
        ArrayList<Doctor> appointmentDoctorIDList = new ArrayList<>();
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT doctor_id,name FROM doctor");
            while (resultSet.next()) {
                appointmentDoctorIDList.add(new Doctor(resultSet.getInt(1), resultSet.getString(2)));

            }
            return appointmentDoctorIDList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public Integer getNextId() {
        try {
            ResultSet resultSet= CrudUtil.execute("SELECT IFNULL(MAX(appointment_id), 0) + 1 AS next_id FROM Appointment");

            resultSet.next();
            return resultSet.getInt(1);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}

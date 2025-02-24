package dao.custom.impl;

import dao.custom.AppointmentDao;
import entity.AppointmentEntity;

import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AppointmentDaoImpl implements AppointmentDao {
    @Override
    public boolean save(AppointmentEntity appointmentEntity) throws SQLException {
        try {

            return CrudUtil.execute("INSERT INTO Appointment VALUES(?,?,?,?,?)",
                    appointmentEntity.getId(),
                    appointmentEntity.getPId(),
                    appointmentEntity.getDID(),
                    appointmentEntity.getDate(),
                    appointmentEntity.getTime()
            );

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(String id) throws SQLException {
        try {
            return CrudUtil.execute("DELETE FROM Appointment WHERE appointment_id = ?", id);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArrayList<AppointmentEntity> gettAll() {
        ArrayList<AppointmentEntity> appointmentArrayList =new ArrayList<>();
        try {

            ResultSet resultSet = CrudUtil.execute("SELECT * FROM Appointment");

            while (resultSet.next()) {

                appointmentArrayList.add(new AppointmentEntity(
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

        return appointmentArrayList;
    }

    @Override
    public boolean update(AppointmentEntity appointmentEntity) {
        try {
            return CrudUtil.execute("UPDATE Appointment SET patient_id = ?, doctor_id = ?, appointment_date = ?,time=? WHERE appointment_id = ?;",
                    appointmentEntity.getPId(),
                    appointmentEntity.getDID(),
                    appointmentEntity.getDate(),
                    appointmentEntity.getTime(),
                    appointmentEntity.getId()
            );

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public AppointmentEntity search(String id) {
        try {

            ResultSet resultSet = CrudUtil.execute("SELECT * FROM Appointment WHERE appointment_id=? ", id);
            if (resultSet.next()) {
                return new AppointmentEntity(
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
    public Integer getNextId() {
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT IFNULL(MAX(appointment_id), 0) + 1 AS next_id FROM Appointment");

            resultSet.next();
            return resultSet.getInt(1);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

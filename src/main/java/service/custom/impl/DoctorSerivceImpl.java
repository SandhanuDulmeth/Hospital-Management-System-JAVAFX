package service.custom.impl;

import Util.CrudUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Doctor;
import service.custom.DoctorService;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DoctorSerivceImpl implements DoctorService {
    public static DoctorSerivceImpl insance;

    private DoctorSerivceImpl() {
    }

    public static DoctorSerivceImpl getInstance() {
        return insance == null ? insance = new DoctorSerivceImpl() : insance;

    }

    @Override
    public boolean addDoctor(Doctor doctor) {


        try {
            return CrudUtil.execute("INSERT INTO Doctor VALUES(?,?,?,?,?,?)",
                    doctor.getId(),
                    doctor.getName(),
                    doctor.getSpecialty(),
                    doctor.getAvailability(),
                    doctor.getQualifications(),
                    doctor.getContact_details()
            );

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean deleteDoctor(Integer id) {


        try {
            return CrudUtil.execute("DELETE FROM Doctor WHERE doctor_id = ?", id);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public ObservableList<Doctor> getAll() {

        ObservableList<Doctor> doctorObservableList = FXCollections.observableArrayList();
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM Doctor");

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
    public boolean UpdateDoctor(Doctor doctor) {

        try {
            return CrudUtil.execute("UPDATE doctor SET name = ?, specialty = ?, availability = ?, qualifications = ?, contact_details = ? WHERE doctor_id = ?;",
                    doctor.getName(),
                    doctor.getSpecialty(),
                    doctor.getAvailability(),
                    doctor.getQualifications(),
                    doctor.getContact_details(),
                    doctor.getId()
            );

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Doctor searchDoctor(Integer id) {

        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM doctor WHERE doctor_id=? ", id);

            if (resultSet.next()) {
                return new Doctor(
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6)

                );
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public Integer getNextId() {
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT IFNULL (MAX(doctor_id), 0) + 1 AS next_id FROM doctor");

            resultSet.next();

            return resultSet.getInt(1);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}

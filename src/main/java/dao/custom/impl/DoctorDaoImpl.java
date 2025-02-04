package dao.custom.impl;

import dao.custom.DoctorDao;
import entity.DoctorEntity;
import model.Doctor;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DoctorDaoImpl implements DoctorDao {
    @Override
    public boolean save(DoctorEntity doctorEntity) throws SQLException {
        try {
            return CrudUtil.execute("INSERT INTO Doctor VALUES(?,?,?,?,?,?)",
                    doctorEntity.getId(),
                    doctorEntity.getName(),
                    doctorEntity.getSpecialty(),
                    doctorEntity.getAvailability(),
                    doctorEntity.getQualifications(),
                    doctorEntity.getContact_details()
            );

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(String id) throws SQLException {
        try {
            return CrudUtil.execute("DELETE FROM Doctor WHERE doctor_id = ?", id);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArrayList<DoctorEntity> gettAll() {
        ArrayList<DoctorEntity> doctorList = new ArrayList<>();
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM Doctor");

            while (resultSet.next()) {

                doctorList.add(new DoctorEntity(
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

        return doctorList;
    }

    @Override
    public boolean update(DoctorEntity doctorEntity) {
        try {
            return CrudUtil.execute("UPDATE doctor SET name = ?, specialty = ?, availability = ?, qualifications = ?, contact_details = ? WHERE doctor_id = ?;",
                    doctorEntity.getName(),
                    doctorEntity.getSpecialty(),
                    doctorEntity.getAvailability(),
                    doctorEntity.getQualifications(),
                    doctorEntity.getContact_details(),
                    doctorEntity.getId()
            );

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public DoctorEntity search(String id) {
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM doctor WHERE doctor_id=? ", id);

            if (resultSet.next()) {
                return new DoctorEntity(
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

    @Override
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

package dao.Custom.Impl;

import Util.CrudUtil;
import dao.Custom.PatientDao;
import entity.PatientEntity;

import java.sql.SQLException;

public class PatientDaoImpl implements PatientDao {
    @Override
    public boolean save(PatientEntity entity) {

        try {
            return CrudUtil.execute("INSERT INTO Patient VALUES(?,?,?,?,?,?,?)",
                    entity.getId(),
                    entity.getName(),
                    entity.getAge(),
                    entity.getGender(),
                    entity.getContactDetails(),
                    entity.getEmergencyContact(),
                    entity.getMedicalHistory()
            );

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean delete(String s) {
        return false;
    }
}

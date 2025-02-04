package dao.custom.impl;


import dao.custom.PatientDao;
import entity.PatientEntity;
import util.CrudUtil;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Logger;

public class PatientDaoImpl implements PatientDao {

    private static final Logger LOGGER = Logger.getLogger(PatientDaoImpl.class.getName());

    @Override
    public boolean save(PatientEntity patientEntity) {

        try {
            return CrudUtil.execute("INSERT INTO patient values(?,?,?,?,?,?,?) ", patientEntity.getId(), patientEntity.getName(), patientEntity.getAge(), patientEntity.getGender(), patientEntity.getContactDetails(), patientEntity.getEmergencyContact(), patientEntity.getMedicalHistory()

            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public boolean delete(String id) {

        try {
            return CrudUtil.execute("DELETE FROM patient WHERE patient_id = ?", id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public ArrayList<PatientEntity> gettAll() {

        ArrayList<PatientEntity> patientList = new ArrayList<>();
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM Patient");

            while (resultSet.next()) {

                patientList.add(new PatientEntity(
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

        return patientList;
    }

    @Override
    public boolean update(PatientEntity patientEntity) {
        try {
            return CrudUtil.execute("UPDATE patient SET name = ?, age = ?, gender = ?, contact_details = ?, emergency_contact = ?, medical_history = ? WHERE patient_id = ?;",
                    patientEntity.getName(),
                    patientEntity.getAge(),
                    patientEntity.getGender(),
                    patientEntity.getContactDetails(),
                    patientEntity.getEmergencyContact(),
                    patientEntity.getMedicalHistory(),
                    patientEntity.getId()
            );

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public PatientEntity search(String id) {
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM patient WHERE patient_id=? ", id);

            if (resultSet.next()) {
                return new PatientEntity(
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

    @Override
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
package dao.Custom.Impl;

import util.CrudUtil;
import dao.Custom.PatientDao;
import entity.PatientEntity;

import java.sql.SQLException;

public class PatientDaoImpl implements PatientDao {
    @Override
    public boolean save(PatientEntity patient) {


//        Session session = HibernateUtil.getSession();
//        session.beginTransaction();
//        session.persist(patient);
//        session.getTransaction().commit();
//        session.close();



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
    public boolean delete(String s) {
        return false;
    }
}

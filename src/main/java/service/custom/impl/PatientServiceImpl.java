package service.custom.impl;

import com.google.inject.Inject;
import entity.PatientEntity;
import model.Patient;


import util.DaoType;
import dao.custom.PatientDao;
import dao.DaoFactory;


import org.modelmapper.ModelMapper;
import service.custom.PatientService;


import java.sql.SQLException;
import java.util.ArrayList;

public class PatientServiceImpl implements PatientService {
    public static PatientServiceImpl insance;


    PatientDao patientDao = DaoFactory.getInstance().getDaoType(DaoType.PATIENT);


    private PatientServiceImpl() {
    }

    public static PatientServiceImpl getInstance() {
        return insance == null ? insance = new PatientServiceImpl() : insance;

    }

    @Override
    public boolean addPatient(Patient patient) throws SQLException {

        PatientEntity patientEntity = new ModelMapper().map(patient, PatientEntity.class);
        System.out.println(patientEntity.getId() + patientEntity.getName() + patientEntity.getGender());
        boolean save = patientDao.save(patientEntity);
        System.out.println(save);
        return save;
    }

    @Override
    public boolean deletePatient(Integer id) throws SQLException {

        return patientDao.delete(String.valueOf(id));

    }

    @Override
    public ArrayList<Patient> getAll() {
        ArrayList<Patient> patientArrayList = new ArrayList<>();
        patientDao.gettAll().forEach(patientEntity -> patientArrayList.add(new ModelMapper().map(patientEntity, Patient.class)));
        return patientArrayList;
    }

    @Override
    public boolean updatePatient(model.Patient patient) {

        return patientDao.update(new ModelMapper().map(patient, PatientEntity.class));
    }

    @Override
    public Patient searchPatient(Integer id) {
        return new ModelMapper().map(patientDao.search(String.valueOf(id)), Patient.class);
    }

    public Integer getNextId() {

        return patientDao.getNextId();

    }

    @Override
    public ArrayList<Patient> getPatientsID() {
        ArrayList<Patient> patientArrayList = new ArrayList<>();
        patientDao.getPatientsID().forEach(patientEntity -> patientArrayList.add(new ModelMapper().map(patientEntity, Patient.class)));
        return patientArrayList;
    }
}


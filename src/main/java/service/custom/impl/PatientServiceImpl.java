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
    public static PatientServiceImpl instance;


    PatientDao patientDao = DaoFactory.getInstance().getDaoType(DaoType.PATIENT);


    private PatientServiceImpl() {
    }

//    @Inject
//    public PatientServiceImpl(PatientServiceImpl instance) {
//        this.instance=instance;
//    }



    public static PatientServiceImpl getInstance() {
        return instance == null ? instance = new PatientServiceImpl() : instance;

    }

    @Override
    public boolean addPatient(Patient patient) throws SQLException {


        return patientDao.save(new ModelMapper().map(patient, PatientEntity.class));
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


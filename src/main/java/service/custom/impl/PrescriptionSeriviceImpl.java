package service.custom.impl;

import dao.DaoFactory;

import dao.custom.PrescriptionDao;
import entity.PrescriptionEntity;
import org.modelmapper.ModelMapper;

import model.Patient;
import model.Doctor;
import model.Prescription;
import service.custom.PrescriptionService;
import util.DaoType;


import java.sql.SQLException;
import java.util.ArrayList;

public class PrescriptionSeriviceImpl implements PrescriptionService {
    public static PrescriptionSeriviceImpl insance;

    PrescriptionDao prescriptionDao = DaoFactory.getInstance().getDaoType(DaoType.PRESCRIPTION);

    private PrescriptionSeriviceImpl() {
    }

    public static PrescriptionSeriviceImpl getInstance() {
        return insance == null ? insance = new PrescriptionSeriviceImpl() : insance;

    }

    @Override
    public boolean addPrescription(Prescription prescription) throws SQLException {

        return prescriptionDao.save(new ModelMapper().map(prescription, PrescriptionEntity.class));


    }

    @Override
    public boolean deletePrescription(Integer id) throws SQLException {

        return prescriptionDao.delete(String.valueOf(id));


    }

    @Override
    public ArrayList<Prescription> getAll() {
        ArrayList<Prescription> prescriptionList = new ArrayList<>();
        prescriptionDao.gettAll().forEach(prescriptionEntity -> prescriptionList.add(new ModelMapper().map(prescriptionEntity, Prescription.class)));
        return prescriptionList;
    }

    @Override
    public boolean updatePrescription(Prescription prescription) {

        return prescriptionDao.update(new ModelMapper().map(prescription, PrescriptionEntity.class));

    }

    @Override
    public Prescription searchPrescription(Integer id) {

        return new ModelMapper().map(prescriptionDao.search(String.valueOf(id)), Prescription.class);

    }


    @Override
    public ArrayList<Patient> getPatientsID() {
        return PatientServiceImpl.getInstance().getPatientsID();
    }

    @Override
    public ArrayList<Doctor> getDocID() {
        return DoctorSerivceImpl.getInstance().getDocID();
    }

    @Override
    public Integer getNextId() {
        return prescriptionDao.getNextId();
    }


}

package service.custom.impl;

import dao.DaoFactory;

import dao.custom.DoctorDao;
import entity.DoctorEntity;
import org.modelmapper.ModelMapper;
import model.Doctor;
import service.custom.DoctorService;
import util.DaoType;


import java.sql.SQLException;
import java.util.ArrayList;

public class DoctorSerivceImpl implements DoctorService {
    public static DoctorSerivceImpl insance;

    DoctorDao doctorDao = DaoFactory.getInstance().getDaoType(DaoType.DOCTOR);

    private DoctorSerivceImpl() {
    }

    public static DoctorSerivceImpl getInstance() {
        return insance == null ? insance = new DoctorSerivceImpl() : insance;

    }

    @Override
    public boolean addDoctor(Doctor doctor) throws SQLException {

        return doctorDao.save(new ModelMapper().map(doctor, DoctorEntity.class));

    }

    @Override
    public boolean deleteDoctor(Integer id) throws SQLException {

        return doctorDao.delete(String.valueOf(id));

    }

    @Override
    public ArrayList<Doctor> getAll() {
        ArrayList<Doctor> doctorList = new ArrayList<>();
        doctorDao.gettAll().forEach(doctorEntity -> doctorList.add(new ModelMapper().map(doctorEntity, Doctor.class)));
        return doctorList;
    }

    @Override
    public boolean UpdateDoctor(Doctor doctor) {

        return doctorDao.update(new ModelMapper().map(doctor, DoctorEntity.class));

    }

    @Override
    public Doctor searchDoctor(Integer id) {

        return new ModelMapper().map(doctorDao.search(String.valueOf(id)), Doctor.class);

    }

    public Integer getNextId() {

        return doctorDao.getNextId();

    }

    @Override
    public ArrayList<Doctor> getDocID() {
        ArrayList<Doctor> doctorArrayList = new ArrayList<>();
        doctorDao.getDocID().forEach(doctorEntity -> doctorArrayList.add(new ModelMapper().map(doctorEntity, Doctor.class)));
        return doctorArrayList;
    }
}

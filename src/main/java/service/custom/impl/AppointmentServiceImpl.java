package service.custom.impl;

import dao.DaoFactory;
import dao.custom.AppointmentDao;
import entity.AppointmentEntity;
import org.modelmapper.ModelMapper;

import model.Appointment;
import model.Doctor;
import model.Patient;
import service.custom.AppointmentService;
import util.DaoType;

import java.sql.SQLException;
import java.util.ArrayList;

public class AppointmentServiceImpl implements AppointmentService {
    private static AppointmentServiceImpl instance;

    AppointmentDao appointmentDao = DaoFactory.getInstance().getDaoType(DaoType.APPOINTMENT);

    private AppointmentServiceImpl() {
    }

    public static AppointmentServiceImpl getInstance() {
        return (instance == null) ? (instance = new AppointmentServiceImpl()) : instance;
    }

    @Override
    public boolean addAppointment(Appointment appointment) throws SQLException {

        return appointmentDao.save(new ModelMapper().map(appointment, AppointmentEntity.class));

    }

    @Override
    public boolean deleteAppointment(Integer id) throws SQLException {

        return appointmentDao.delete(String.valueOf(id));

    }

    @Override
    public ArrayList<Appointment> getAll() {
        ArrayList<Appointment> appointmentArrayList = new ArrayList<>();
        appointmentDao.gettAll().forEach(appointmentEntity -> appointmentArrayList.add(new ModelMapper().map(appointmentEntity, Appointment.class)));
        return appointmentArrayList;
    }

    @Override
    public boolean updateAppointment(Appointment appointment) {

        return appointmentDao.update(new ModelMapper().map(appointment, AppointmentEntity.class));

    }

    @Override
    public Appointment searchAppointment(Integer id) {

        return new ModelMapper().map(appointmentDao.search(String.valueOf(id)), Appointment.class);
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

        return appointmentDao.getNextId();

    }
}

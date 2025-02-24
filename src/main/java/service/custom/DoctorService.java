package service.custom;

import model.Doctor;
import service.SuperService;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DoctorService extends SuperService {
    boolean addDoctor(Doctor doctor) throws SQLException;

    boolean deleteDoctor(Integer id) throws SQLException;

    ArrayList<Doctor> getAll();

    boolean UpdateDoctor(Doctor doctor);

    Doctor searchDoctor(Integer id);

    Integer getNextId();

    ArrayList<Doctor> getDocID();


}

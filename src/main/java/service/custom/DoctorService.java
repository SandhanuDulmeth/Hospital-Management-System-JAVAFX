package service.custom;

import javafx.collections.ObservableList;
import model.Doctor;
import service.SuperService;

public interface DoctorService extends SuperService {
    boolean addDoctor(Doctor doctor);

    boolean deleteDoctor(Integer id);

    ObservableList<Doctor> getAll();

    boolean UpdateDoctor(Doctor doctor);

    Doctor searchDoctor(Integer id);

    Integer getNextId();
}

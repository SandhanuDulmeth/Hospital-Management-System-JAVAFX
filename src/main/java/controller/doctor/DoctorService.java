package controller.doctor;

import javafx.collections.ObservableList;
import model.Doctor;

public interface DoctorService {
    boolean addDoctor(Doctor doctor);

    boolean deleteDoctor(Integer id);

    ObservableList<Doctor> getAll();

    boolean UpdateDoctor(Doctor doctor);

    Doctor searchDoctor(Integer id);
}

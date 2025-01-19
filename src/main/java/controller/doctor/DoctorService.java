package controller.doctor;

import javafx.collections.ObservableList;
import model.Doctor;

public interface DoctorService {
    boolean addCustomer(Doctor doctor);

    boolean deleteCustomer(Integer id);

    ObservableList<Doctor> getAll();

    boolean UpdateCustomer(Doctor doctor);

    Doctor searchCustomer(Integer id);
}

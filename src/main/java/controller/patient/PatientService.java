package controller.patient;

import javafx.collections.ObservableList;
import model.Patient;

public interface PatientService {
    boolean addCustomer(Patient patient);

    boolean deleteCustomer(Integer id);

    ObservableList<Patient> getAll();

    boolean UpdateCustomer(Patient patient);

    Patient searchCustomer(Integer id);
}

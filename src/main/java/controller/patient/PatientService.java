package controller.patient;

import javafx.collections.ObservableList;
import model.Patient;

public interface PatientService {
    boolean addCustomer(Patient patient);

    boolean deleteCustomer(String id);

    ObservableList<Patient> getAll();

    boolean UpdateCustomer(Patient patient);

    Patient searchCustomer(String CusID);
}

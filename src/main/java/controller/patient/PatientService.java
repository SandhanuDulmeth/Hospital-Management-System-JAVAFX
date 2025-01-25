package controller.patient;

import javafx.collections.ObservableList;
import model.Patient;

public interface PatientService {
    boolean addPatient(Patient patient);

    boolean deletePatient(Integer id);

    ObservableList<Patient> getAll();

    boolean UpdatePatient(Patient patient);

    Patient searchPatient(Integer id);
}

package service.custom;

import javafx.collections.ObservableList;
import model.Patient;
import service.SuperService;

public interface PatientService  extends SuperService {
    boolean addPatient(Patient patient);

    boolean deletePatient(Integer id);

    ObservableList<Patient> getAll();

    boolean UpdatePatient(Patient patient);

    Patient searchPatient(Integer id);

    Integer getNextId();
}

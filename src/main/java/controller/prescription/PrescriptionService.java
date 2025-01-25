package controller.prescription;

import javafx.collections.ObservableList;
import model.Prescription;
import model.Doctor;
import model.Patient;

import java.util.ArrayList;

public interface PrescriptionService {
    boolean addPrescription(Prescription prescription);

    boolean deletePrescription(Integer id);

    ObservableList<Prescription> getAll();

    boolean UpdatePrescription(Prescription prescription);

    Prescription searchPrescription(Integer id);

    ArrayList<Patient> getPatientsID();
    ArrayList<Doctor> getDocID();
}

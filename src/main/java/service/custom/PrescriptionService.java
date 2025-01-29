package service.custom;

import javafx.collections.ObservableList;
import model.Doctor;
import model.Patient;
import model.Prescription;
import service.SuperService;

import java.util.ArrayList;

public interface PrescriptionService extends SuperService {
    boolean addPrescription(Prescription prescription);

    boolean deletePrescription(Integer id);

    ObservableList<Prescription> getAll();

    boolean UpdatePrescription(Prescription prescription);

    Prescription searchPrescription(Integer id);

    ArrayList<Patient> getPatientsID();

    ArrayList<Doctor> getDocID();

    Integer getNextId();
}

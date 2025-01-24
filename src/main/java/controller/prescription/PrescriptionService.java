package controller.prescription;

import javafx.collections.ObservableList;
import model.Prescription;
import model.Doctor;
import model.Patient;

import java.util.ArrayList;

public interface PrescriptionService {
    boolean addCustomer(Prescription prescription);

    boolean deleteCustomer(Integer id);

    ObservableList<Prescription> getAll();

    boolean UpdateCustomer(Prescription prescription);

    Prescription searchCustomer(Integer id);

   // ObservableList<Patient> getPatientsID();

    ArrayList<Patient> getPatientsID();
    ArrayList<Doctor> getDocID();
}

package service.custom;

import model.Doctor;
import model.Patient;
import model.Prescription;
import service.SuperService;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PrescriptionService extends SuperService {
    boolean addPrescription(Prescription prescription) throws SQLException;

    boolean deletePrescription(Integer id) throws SQLException;

    ArrayList<Prescription> getAll();

    boolean updatePrescription(Prescription prescription);

    Prescription searchPrescription(Integer id);

    ArrayList<Patient> getPatientsID();

    ArrayList<Doctor> getDocID();

    Integer getNextId();
}

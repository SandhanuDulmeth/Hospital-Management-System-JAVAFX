package service.custom;


import model.Patient;
import service.SuperService;

import java.sql.SQLException;
import java.util.ArrayList;


public interface PatientService  extends SuperService {
    boolean addPatient(Patient patient) throws SQLException;

    boolean deletePatient(Integer id) throws SQLException;

   ArrayList<Patient> getAll();

    boolean updatePatient(Patient patient);

    Patient searchPatient(Integer id);

    Integer getNextId();

    ArrayList<Patient>getPatientsID();
}

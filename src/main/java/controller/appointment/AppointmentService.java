package controller.appointment;

import javafx.collections.ObservableList;
import model.Appointment;
import model.Doctor;
import model.Patient;

import java.util.ArrayList;

public interface AppointmentService {
    boolean addCustomer(Appointment appointment);

    boolean deleteCustomer(Integer id);

    ObservableList<Appointment> getAll();

    boolean UpdateCustomer(Appointment appointment);

    Appointment searchCustomer(Integer id);

   // ObservableList<Patient> getPatientsID();

    ArrayList<Patient> getPatientsID();
    ArrayList<Doctor> getDocID();
}

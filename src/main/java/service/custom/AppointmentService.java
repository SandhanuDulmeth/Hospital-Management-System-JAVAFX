package service.custom;

import javafx.collections.ObservableList;
import model.Appointment;
import model.Doctor;
import model.Patient;
import service.SuperService;

import java.util.ArrayList;

public interface AppointmentService extends SuperService {
    boolean addAppointment(Appointment appointment);

    boolean deleteAppointment(Integer id);

    ObservableList<Appointment> getAll();

    boolean updateAppointment(Appointment appointment);

    Appointment searchAppointment(Integer id);

    ArrayList<Patient> getPatientsID();

    ArrayList<Doctor> getDocID();

    Integer getNextId();
}

package controller.appointment;

import javafx.collections.ObservableList;
import model.Appointment;

public interface AppointmentService {
    boolean addCustomer(Appointment appointment);

    boolean deleteCustomer(Integer id);

    ObservableList<Appointment> getAll();

    boolean UpdateCustomer(Appointment appointment);

    Appointment searchCustomer(Integer id);
}

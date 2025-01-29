package controller.billing;

import javafx.collections.ObservableList;
import model.Billing;
import model.Patient;

import java.util.ArrayList;

public interface BillingService {
    boolean addBilling(Billing billing);

    boolean deleteBilling(Integer id);

    ObservableList<Billing> getAll();

    boolean updateBilling(Billing billing);

    Billing searchBilling(Integer id);

    ArrayList<Patient> getPatientsID();


}

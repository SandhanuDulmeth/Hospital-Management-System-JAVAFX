package service.custom;

import javafx.collections.ObservableList;
import model.Billing;
import model.Patient;
import service.SuperService;

import java.util.ArrayList;

public interface BillingService extends SuperService {
    boolean addBilling(Billing billing);

    boolean deleteBilling(Integer id);

    ObservableList<Billing> getAll();

    boolean updateBilling(Billing billing);

    Billing searchBilling(Integer id);

    ArrayList<Patient> getPatientsID();

    Integer getNextId();
}

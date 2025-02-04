package service.custom;


import model.Billing;
import model.Patient;
import service.SuperService;

import java.sql.SQLException;
import java.util.ArrayList;

public interface BillingService extends SuperService {
    boolean addBilling(Billing billing) throws SQLException;

    boolean deleteBilling(Integer id) throws SQLException;

    ArrayList<Billing> getAll();

    boolean updateBilling(Billing billing);

    Billing searchBilling(Integer id);

    ArrayList<Patient> getPatientsID();

    Integer getNextId();
}

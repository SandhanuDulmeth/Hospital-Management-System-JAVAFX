package service.custom.impl;

import util.CrudUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Billing;
import model.Patient;
import service.custom.BillingService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BillingServiceImpl implements BillingService {
    private static BillingServiceImpl instance;

    private BillingServiceImpl() {
    }

    public static BillingServiceImpl getInstance() {
        return (instance == null) ? (instance = new BillingServiceImpl()) : instance;
    }

    @Override
    public boolean addBilling(Billing billing) {


        try {

            return CrudUtil.execute("INSERT INTO Billing VALUES(?,?,?,?,?)",
                    billing.getId(),
                    billing.getPId(),
                    billing.getTotalAmount(),
                    billing.getPaymentStatus(),
                    billing.getDate()
            );

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean deleteBilling(Integer id) {


        try {
            return CrudUtil.execute("DELETE FROM Billing WHERE bill_id = ?", id);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public ObservableList<Billing> getAll() {

        ObservableList<Billing> billingObservableList = FXCollections.observableArrayList();
        try {

            ResultSet resultSet = CrudUtil.execute("SELECT * FROM Billing");

            while (resultSet.next()) {

                billingObservableList.add(new Billing(
                        resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getDouble(3),
                        resultSet.getString(4),
                        resultSet.getString(5)


                ));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return billingObservableList;
    }

    @Override
    public boolean updateBilling(Billing billing) {

        try {
            return CrudUtil.execute("UPDATE Billing SET patient_id = ?, total_amount = ?, payment_status = ?, generated_date=? WHERE bill_id = ?;",
                    billing.getPId(),
                    billing.getTotalAmount(),
                    billing.getPaymentStatus(),
                    billing.getDate(),
                    billing.getId()
            );

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Billing searchBilling(Integer id) {

        try {

            ResultSet resultSet = CrudUtil.execute("SELECT * FROM Billing WHERE bill_id=? ", id);
            if (resultSet.next()) {
                return new Billing(
                        resultSet.getInt(2),
                        resultSet.getDouble(3),
                        resultSet.getString(4),
                        resultSet.getString(5)

                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


    @Override
    public ArrayList<Patient> getPatientsID() {
        ArrayList<Patient> billingPatientsIDList = new ArrayList<>();
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT patient_id,name FROM PatientEntity");
            while (resultSet.next()) {
                billingPatientsIDList.add(new Patient(resultSet.getInt(1), resultSet.getString(2)));

            }
            return billingPatientsIDList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    public Integer getNextId() {
        try {
            ResultSet resultSet= CrudUtil.execute("SELECT IFNULL(MAX(bill_id), 0) + 1 AS next_id FROM Billing");

            resultSet.next();
            return resultSet.getInt(1);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}

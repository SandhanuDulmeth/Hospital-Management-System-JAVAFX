package dao.custom.impl;

import dao.custom.BillingDao;
import entity.BillingEntity;
import model.Billing;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BillingDaoImpl implements BillingDao {
    @Override
    public boolean save(BillingEntity billingEntity) throws SQLException {
        try {

            return CrudUtil.execute("INSERT INTO Billing VALUES(?,?,?,?,?)",
                    billingEntity.getId(),
                    billingEntity.getPId(),
                    billingEntity.getTotalAmount(),
                    billingEntity.getPaymentStatus(),
                    billingEntity.getDate()
            );

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(String id) throws SQLException {
        try {
            return CrudUtil.execute("DELETE FROM Billing WHERE bill_id = ?", id);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public ArrayList<BillingEntity> gettAll() {
        ArrayList<BillingEntity> billingList =new ArrayList<>();
        try {

            ResultSet resultSet = CrudUtil.execute("SELECT * FROM Billing");

            while (resultSet.next()) {

                billingList.add(new BillingEntity(
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

        return billingList;
    }

    @Override
    public boolean update(BillingEntity billingEntity) {
        try {
            return CrudUtil.execute("UPDATE Billing SET patient_id = ?, total_amount = ?, payment_status = ?, generated_date=? WHERE bill_id = ?;",
                    billingEntity.getPId(),
                    billingEntity.getTotalAmount(),
                    billingEntity.getPaymentStatus(),
                    billingEntity.getDate(),
                    billingEntity.getId()
            );

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public BillingEntity search(String id) {
        try {

            ResultSet resultSet = CrudUtil.execute("SELECT * FROM Billing WHERE bill_id=? ", id);
            if (resultSet.next()) {
                return new BillingEntity(
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

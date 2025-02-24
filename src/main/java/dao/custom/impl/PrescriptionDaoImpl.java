package dao.custom.impl;

import dao.custom.PrescriptionDao;
import entity.PrescriptionEntity;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PrescriptionDaoImpl implements PrescriptionDao {
    @Override
    public boolean save(PrescriptionEntity prescriptionEntity) throws SQLException {
        try {
            return CrudUtil.execute("INSERT INTO Prescription VALUES(?,?,?,?,?,?)",
                    prescriptionEntity.getId(),
                    prescriptionEntity.getPId(),
                    prescriptionEntity.getDID(),
                    prescriptionEntity.getMedicine(),
                    prescriptionEntity.getDosage(),
                    prescriptionEntity.getDuration()
            );


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(String id) throws SQLException {
        try {
            return CrudUtil.execute("DELETE FROM Prescription WHERE prescription_id = ?", id);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArrayList<PrescriptionEntity> gettAll() {
        ArrayList<PrescriptionEntity> prescriptionList =new ArrayList<>();
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM Prescription");

            while (resultSet.next()) {

                prescriptionList.add(new PrescriptionEntity(
                        resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getInt(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6)


                ));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return prescriptionList;
    }

    @Override
    public boolean update(PrescriptionEntity prescriptionEntity) {
        try {
            return CrudUtil.execute("UPDATE Prescription SET patient_id = ?, doctor_id = ?, medicine = ?, dosage= ?, duration= ? WHERE prescription_id = ?;",
                    prescriptionEntity.getPId(),
                    prescriptionEntity.getDID(),
                    prescriptionEntity.getMedicine(),
                    prescriptionEntity.getDosage(),
                    prescriptionEntity.getDuration(),
                    prescriptionEntity.getId()
            );

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public PrescriptionEntity search(String id) {

        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM Prescription WHERE prescription_id=? ", id);

            if (resultSet.next()) {
                return new PrescriptionEntity(
                        resultSet.getInt(2),
                        resultSet.getInt(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6)

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
            ResultSet resultSet = CrudUtil.execute("SELECT IFNULL(MAX(prescription_id), 0) + 1 AS next_id FROM Prescription");
            resultSet.next();
            return resultSet.getInt(1);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

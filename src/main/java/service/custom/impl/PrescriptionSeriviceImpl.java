package service.custom.impl;

import util.CrudUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Doctor;
import model.Patient;
import model.Prescription;
import service.custom.PrescriptionService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PrescriptionSeriviceImpl implements PrescriptionService {
    public static PrescriptionSeriviceImpl insance;

    private PrescriptionSeriviceImpl() {
    }

    public static PrescriptionSeriviceImpl getInstance() {
        return insance == null ? insance = new PrescriptionSeriviceImpl() : insance;

    }

    @Override
    public boolean addPrescription(Prescription prescription) {


        try {
            return CrudUtil.execute("INSERT INTO Prescription VALUES(?,?,?,?,?,?)",
                    prescription.getId(),
                    prescription.getPId(),
                    prescription.getDID(),
                    prescription.getMedicine(),
                    prescription.getDosage(),
                    prescription.getDuration()
            );


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean deletePrescription(Integer id) {

        try {
            return CrudUtil.execute("DELETE FROM Prescription WHERE prescription_id = ?", id);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public ObservableList<Prescription> getAll() {

        ObservableList<Prescription> prescriptionObservableList = FXCollections.observableArrayList();
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM Prescription");

            while (resultSet.next()) {

                prescriptionObservableList.add(new Prescription(
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

        return prescriptionObservableList;
    }

    @Override
    public boolean UpdatePrescription(Prescription prescription) {

        try {
            return CrudUtil.execute("UPDATE Prescription SET patient_id = ?, doctor_id = ?, medicine = ?, dosage= ?, duration= ? WHERE prescription_id = ?;",
                    prescription.getPId(),
                    prescription.getDID(),
                    prescription.getMedicine(),
                    prescription.getDosage(),
                    prescription.getDuration(),
                    prescription.getId()
            );

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Prescription searchPrescription(Integer id) {

        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM Prescription WHERE prescription_id=? ", id);

            if (resultSet.next()) {
                return new Prescription(
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
    public ArrayList<Patient> getPatientsID() {
        ArrayList<Patient> prescriptionPatientsIDList = new ArrayList<>();
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT patient_id,name FROM Patient");


            while (resultSet.next()) {
                prescriptionPatientsIDList.add(new Patient(resultSet.getInt(1), resultSet.getString(2)));
                System.out.println(prescriptionPatientsIDList);
            }

            return prescriptionPatientsIDList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArrayList<Doctor> getDocID() {
        ArrayList<Doctor> prescriptionDoctorIDList = new ArrayList<>();
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT doctor_id,name FROM doctor");

            while (resultSet.next()) {
                prescriptionDoctorIDList.add(new Doctor(resultSet.getInt(1), resultSet.getString(2)));
            }
            return prescriptionDoctorIDList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


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

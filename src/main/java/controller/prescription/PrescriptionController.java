package controller.prescription;

import controller.prescription.PrescriptionService;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Prescription;
import model.Doctor;
import model.Patient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PrescriptionController implements PrescriptionService {
    public static PrescriptionController insance;

    private PrescriptionController() {
    }

    public static PrescriptionController getInstance() {
        return insance == null ? insance = new PrescriptionController() : insance;

    }

    @Override
    public boolean addCustomer(Prescription prescription) {

        Connection connection = null;
        try {
            connection = DBConnection.getINSTANCE().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Prescription VALUES(?,?,?,?,?,?)");
            preparedStatement.setInt(1, prescription.getId());
            preparedStatement.setInt(2, prescription.getPId());
            preparedStatement.setInt(3, prescription.getDID());
            preparedStatement.setString(4, prescription.getMedicine());
            preparedStatement.setString(5, prescription.getDosage());
            preparedStatement.setString(6, prescription.getDuration());


            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean deleteCustomer(Integer id) {

        Connection connection = null;

        try {
            connection = DBConnection.getINSTANCE().getConnection();

            PreparedStatement stm = connection.prepareStatement("DELETE FROM Prescription WHERE prescription_id = ?");
            stm.setObject(1, id);

            return stm.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public ObservableList<Prescription> getAll() {

        ObservableList<Prescription> prescriptionObservableList = FXCollections.observableArrayList();
        try {
            ResultSet resultSet = DBConnection.getINSTANCE()
                    .getConnection().
                    createStatement()
                    .executeQuery("SELECT * FROM Prescription");

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
    public boolean UpdateCustomer(Prescription prescription) {
        Connection connection = null;
        try {
            connection = DBConnection.getINSTANCE().getConnection();

            PreparedStatement stm = connection.prepareStatement("UPDATE Prescription SET patient_id = ?, doctor_id = ?, medicine = ?, dosage= ?, duration= ? WHERE prescription_id = ?;");
            stm.setObject(6, prescription.getId());
            stm.setObject(1, prescription.getPId());
            stm.setObject(2, prescription.getDID());
            stm.setObject(3, prescription.getMedicine());
            stm.setObject(4, prescription.getDosage());
            stm.setObject(5, prescription.getDosage());


            System.out.println("UpdatePrescription Ok");
            return stm.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Prescription searchCustomer(Integer id) {
        Connection connection = null;
        try {
            connection = DBConnection.getINSTANCE().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Prescription WHERE prescription_id=? ");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Prescription prescription = new Prescription(
                        resultSet.getInt(2),
                        resultSet.getInt(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6)

                );

                return prescription;
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
            ResultSet resultSet = DBConnection.getINSTANCE().getConnection().createStatement().executeQuery("SELECT patient_id,name FROM Patient");

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
            ResultSet resultSet = DBConnection.getINSTANCE().getConnection().createStatement().executeQuery("SELECT doctor_id,name FROM doctor");

            while (resultSet.next()) {
                prescriptionDoctorIDList.add(new Doctor(resultSet.getInt(1), resultSet.getString(2)));
                System.out.println(prescriptionDoctorIDList);
            }

            return prescriptionDoctorIDList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public Integer getNextId() {
        try {
            ResultSet resultSet = DBConnection.getINSTANCE().getConnection().createStatement().executeQuery(" SELECT IFNULL(MAX(prescription_id), 0) + 1 AS next_id FROM Prescription");
            resultSet.next();

            return resultSet.getInt(1);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

}

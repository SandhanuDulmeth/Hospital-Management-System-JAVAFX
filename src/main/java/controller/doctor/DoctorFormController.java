package controller.doctor;


import controller.report.ReportController;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import util.ServiceType;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import model.Doctor;
import service.ServiceFactory;
import service.custom.DoctorService;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DoctorFormController implements Initializable {


    public TableView tblDoctor;
    public JFXTextField TxtId11;
    public JFXTextField TxtName11;
    public TableColumn colID;
    public TableColumn colName;
    public TableColumn colAge;
    public TableColumn colGender;
    public TableColumn colContactDetails;
    public TableColumn colEmergencyContact;
    public JFXTextField TxtContactDetails11;
    public JFXTextField TxtContactDetails;
    public JFXTextField TxtContactDetails1;

    public JFXTextField TxtQualifications;
    public JFXTextField TxtSpecialty;
    public JFXTextField TxtAvailability;
    public JFXTextField TxtAvailability1;
    public JFXTextField TxtSpecialty1;
    public JFXTextField TxtQualifications1;
    public JFXTextField TxtQualifications11;
    public JFXTextField TxtAvailability11;
    public JFXTextField TxtSpecialty11;
    public TableColumn colSpecialty;
    public TableColumn colAvailability;
    public TableColumn colQualifications;
    public Label CountLabelID;

    @FXML
    private JFXTextField TxtId;

    @FXML
    private JFXTextField TxtId1;

    @FXML
    private JFXTextField TxtName;

    @FXML
    private JFXTextField TxtName1;

    private final DoctorService doctorService = ServiceFactory.getInstance().getServiceType(ServiceType.DOCTOR);

    @FXML
    public void btnAddOnAction(ActionEvent event) throws SQLException {

        if (!(TxtId.getText().isEmpty())) {
            Doctor doctor = new Doctor(
                    Integer.valueOf(TxtId.getText()),
                    TxtName.getText(),
                    TxtSpecialty.getText(),
                    TxtAvailability.getText(),
                    TxtQualifications.getText(),
                    TxtContactDetails.getText());
            if (doctorService.addDoctor(doctor)) {
                new Alert(Alert.AlertType.INFORMATION, "Added").show();
                clearAddForm();
                loadTable();
                setNextId();
            } else {
                new Alert(Alert.AlertType.ERROR, "Not Added").show();
            }

        } else {
            new Alert(Alert.AlertType.ERROR, "Please Enter A ID").show();
        }

    }

    @FXML
    public void btnClearOnAction(ActionEvent event) {
        clearAddForm();
    }

    public void clearAddForm() {
        TxtName.clear();
        TxtSpecialty.clear();
        TxtAvailability.clear();
        TxtQualifications.clear();
        TxtContactDetails.clear();

    }

    public void setNextId() {
        TxtId.setText(String.valueOf(doctorService.getNextId()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setNextId();



        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colSpecialty.setCellValueFactory(new PropertyValueFactory<>("specialty"));
        colAvailability.setCellValueFactory(new PropertyValueFactory<>("availability"));
        colQualifications.setCellValueFactory(new PropertyValueFactory<>("qualifications"));
        colContactDetails.setCellValueFactory(new PropertyValueFactory<>("contact_details"));

        loadTable();
        clearAddForm();

    }

    public void btnSearchRemoveOnAction(ActionEvent actionEvent) throws SQLException {


        if (doctorService.deleteDoctor(Integer.valueOf(TxtId1.getText())))
            new Alert(Alert.AlertType.INFORMATION, "Removed " + TxtId1.getText()).show();
        else new Alert(Alert.AlertType.INFORMATION, "Not Removed " + TxtId1.getText()).show();


        clearRemoveForm();

        setNextId();
        loadTable();


    }

    public void clearRemoveForm() {
        TxtName1.clear();
        TxtSpecialty1.clear();
        TxtAvailability1.clear();
        TxtQualifications1.clear();
        TxtContactDetails1.clear();

    }

    public void OnSreachKeyReleased(KeyEvent keyEvent) {

        Doctor doctor = doctorService.searchDoctor(Integer.valueOf("0" + TxtId1.getText()));

        if (null != doctor) {

            TxtName1.setText(doctor.getName());
            TxtSpecialty1.setText(doctor.getSpecialty());
            TxtAvailability1.setText(doctor.getAvailability());
            TxtQualifications1.setText(doctor.getQualifications());
            TxtContactDetails1.setText(doctor.getContact_details());

        } else {
            clearRemoveForm();

        }
    }


    private void loadTable() {
        tblDoctor.getItems().clear();
        ObservableList<Doctor> doctorObservableList = FXCollections.observableArrayList();
        doctorService.getAll().forEach(doctor -> doctorObservableList.add(doctor));
        tblDoctor.setItems(doctorObservableList);
    }

    public void btnSearchUpdateOnAction(ActionEvent actionEvent) {

        if (doctorService.UpdateDoctor(new Doctor(
                Integer.valueOf(TxtId11.getText()),
                TxtName11.getText(),
                TxtSpecialty11.getText(),
                TxtAvailability11.getText(),
                TxtQualifications11.getText(),
                TxtContactDetails11.getText()
        ))) {
            new Alert(Alert.AlertType.INFORMATION, "Updated ").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Not Updated ").show();
        }

        loadTable();
    }

    public void OnSreachUpdateKeyReleased(KeyEvent keyEvent) {
        Doctor doctor = doctorService.searchDoctor(Integer.valueOf("0" + TxtId11.getText()));

        if (null != doctor) {

            TxtName11.setText(doctor.getName());
            TxtSpecialty11.setText(doctor.getSpecialty());
            TxtAvailability11.setText(doctor.getAvailability());
            TxtQualifications11.setText(doctor.getQualifications());
            TxtContactDetails11.setText(doctor.getContact_details());

        } else {
            TxtName11.clear();
            TxtSpecialty11.clear();
            TxtAvailability11.clear();
            TxtQualifications11.clear();
            TxtContactDetails11.clear();

        }
    }














}

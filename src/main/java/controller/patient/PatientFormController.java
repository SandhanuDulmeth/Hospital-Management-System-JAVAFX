package controller.patient;

import javafx.collections.ObservableList;
import util.ServiceType;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import model.Patient;
import service.ServiceFactory;
import service.custom.PatientService;


import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class PatientFormController implements Initializable {


    public TableView tblPatient;

    public JFXTextField TxtId11;
    public JFXTextField TxtName11;

    public TableColumn colID;
    public TableColumn colName;
    public TableColumn colAge;
    public TableColumn colGender;
    public TableColumn colContactDetails;
    public TableColumn colEmergencyContact;
    public TableColumn colMedicalHistory;
    public JFXTextField TxtContactDetails11;
    public JFXTextField TxtEmegencyContact11;
    public JFXTextField TxtMedicalHistory11;
    public JFXTextField TxtAge11;
    public JFXTextField TxtContactDetails;
    public JFXTextField TxtAge;
    public JFXTextField TxtEmegencyContact;
    public JFXTextField TxtMedicalHistory;
    public JFXTextField TxtGender1;
    public JFXTextField TxtMedicalHistory1;
    public JFXTextField TxtEmegencyContact1;

    public JFXTextField TxtContactDetails1;
    public JFXTextField TxtAge1;
    public JFXComboBox GenderComboBox;
    public JFXComboBox GenderComboBox11;
    public JFXTextField TxtGender11;

    @FXML
    private JFXTextField TxtId;

    @FXML
    private JFXTextField TxtId1;

    @FXML
    private JFXTextField TxtName;

    @FXML
    private JFXTextField TxtName1;


private final PatientService patientService=ServiceFactory.getInstance().getServiceType(ServiceType.PATIENT);

    @FXML
    public void btnAddOnAction(ActionEvent event) throws SQLException {

        if (!(TxtId.getText().isEmpty())) {
            Patient patient = new Patient(
                    Integer.valueOf(TxtId.getText()),
                    TxtName.getText(),
                    Integer.valueOf(TxtAge.getText()),
                    (String) GenderComboBox.getValue(),
                    TxtContactDetails.getText(),
                    TxtEmegencyContact.getText(),
                    TxtMedicalHistory.getText());
            if (patientService.addPatient(patient)) {
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
        TxtAge.clear();
        TxtContactDetails.clear();
        TxtEmegencyContact.clear();
        TxtMedicalHistory.clear();

    }

    public void setNextId() {
        TxtId.setText(String.valueOf(patientService.getNextId()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setNextId();
        GenderComboBox.setItems(FXCollections.observableArrayList("Male", "Female", "Other"));
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colContactDetails.setCellValueFactory(new PropertyValueFactory<>("contactDetails"));
        colEmergencyContact.setCellValueFactory(new PropertyValueFactory<>("emergencyContact"));
        colMedicalHistory.setCellValueFactory(new PropertyValueFactory<>("medicalHistory"));

        loadTable();
        clearAddForm();


    }

    public void btnSearchRemoveOnAction(ActionEvent actionEvent) throws SQLException {


        if (patientService.deletePatient(Integer.valueOf(TxtId1.getText())))
            new Alert(Alert.AlertType.INFORMATION, "Removed " + TxtId1.getText()).show();
        else new Alert(Alert.AlertType.INFORMATION, "Not Removed " + TxtId1.getText()).show();


        clearRemoveForm();

        setNextId();
        loadTable();


    }

    public void clearRemoveForm() {
        TxtName1.clear();
        TxtAge1.clear();
        TxtGender1.clear();
        TxtContactDetails1.clear();
        TxtEmegencyContact1.clear();
        TxtMedicalHistory1.clear();

    }

    public void OnSreachKeyReleased(KeyEvent keyEvent) {

        Patient patient = patientService.searchPatient(Integer.valueOf("0" + TxtId1.getText()));

        if (null != patient) {

            TxtName1.setText(patient.getName());
            TxtAge1.setText(String.valueOf(patient.getAge()));
            TxtGender1.setText(patient.getGender());
            TxtContactDetails1.setText(patient.getContactDetails());
            TxtEmegencyContact1.setText(patient.getEmergencyContact());
            TxtMedicalHistory1.setText(patient.getMedicalHistory());

        } else {
            clearRemoveForm();

        }
    }


    private void loadTable() {
        tblPatient.getItems().clear();
       ObservableList<Patient> observableList= FXCollections.observableArrayList();

        patientService.getAll().forEach(patient -> observableList.add(patient));

        tblPatient.setItems(observableList);
    }

    public void btnSearchUpdateOnAction(ActionEvent actionEvent) {

        if (patientService.updatePatient(new Patient(
                Integer.valueOf(TxtId11.getText()),
                TxtName11.getText(),
                Integer.valueOf(TxtAge11.getText()),
TxtGender11.getText(),
//(String) GenderComboBox11.getValue(),
                TxtContactDetails11.getText(),
                TxtEmegencyContact11.getText(),
                TxtMedicalHistory11.getText()
        ))) {
            new Alert(Alert.AlertType.INFORMATION, "Updated ").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Not Updated ").show();
        }

        loadTable();
    }

    public void OnSreachUpdateKeyReleased(KeyEvent keyEvent) {
        Patient patient = patientService.searchPatient(Integer.valueOf("0" + TxtId11.getText()));

        if (null != patient) {

            TxtName11.setText(patient.getName());
            TxtAge11.setText(String.valueOf(patient.getAge()));
           // (patient.getGender()

            //GenderComboBox11.getSelectionModel().select(2);
            TxtGender11.setText(patient.getGender());
            TxtContactDetails11.setText(patient.getContactDetails());
            TxtEmegencyContact11.setText(patient.getEmergencyContact());
            TxtMedicalHistory11.setText(patient.getMedicalHistory());

        } else {
            TxtName11.clear();
            TxtAge11.clear();
            TxtContactDetails11.clear();
            TxtEmegencyContact11.clear();
            TxtMedicalHistory11.clear();

        }
    }
}

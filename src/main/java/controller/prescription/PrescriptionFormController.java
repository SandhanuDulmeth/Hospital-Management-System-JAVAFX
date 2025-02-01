package controller.prescription;

import util.ServiceType;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import model.Prescription;
import model.Doctor;
import model.Patient;
import service.ServiceFactory;
import service.custom.PrescriptionService;

import java.net.URL;
import java.util.ResourceBundle;

public class PrescriptionFormController implements Initializable {


    public TableView tblPrescription;

    public JFXTextField TxtId11;

    public JFXTextField TxtDId1;
    public JFXTextField TxtPId1;
    public JFXTextField TxtPId11;
    public JFXTextField TxtTime11;
    public JFXTextField TxtTime1;
    public JFXTextField TxtDId11;
    public JFXTextField TxtDate11;
    public JFXComboBox PIdComboBox;
    public JFXComboBox DIdComboBox;
    public javafx.scene.control.DatePicker DatePicker;
    public TableColumn colPrescriptionID;
    public TableColumn colPatientId;
    public TableColumn colDoctorId;


    public JFXTextField TxtDosage;
    public JFXTextField TxtDuration;
    public JFXTextField TxtMedicine;
    public JFXTextField TxtMedicine1;
    public JFXTextField TxtDosage1;
    public JFXTextField TxtDuration1;
    public JFXTextField TxtDosage11;
    public JFXTextField TxtMedicine11;
    public JFXTextField TxtDuration11;
    public TableColumn colMedicine;
    public TableColumn colDosage;
    public TableColumn colDuration;


    @FXML
    private JFXTextField TxtId;

    @FXML
    private JFXTextField TxtId1;

    @FXML
    private JFXTextField TxtName;

    @FXML
    private JFXTextField TxtName1;

    private final PrescriptionService prescriptionService= ServiceFactory.getInstance().getServiceType(ServiceType.PRESCRIPTION);

    @FXML
    public void btnAddOnAction(ActionEvent event) {

        if (!(TxtId.getText().isEmpty())) {
            Prescription prescription = new Prescription(Integer.valueOf(TxtId.getText()), getId((String) PIdComboBox.getValue()), getId((String) DIdComboBox.getValue()), TxtMedicine.getText(), TxtDosage.getText(), TxtDuration.getText());

            if (prescriptionService.addPrescription(prescription)) {
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

    public static int getId(String input) {

        String[] parts = input.split("[ -]");

        return Integer.parseInt(parts[1]);
    }

    @FXML
    public void btnClearOnAction(ActionEvent event) {
        clearAddForm();
    }

    public void clearAddForm() {

        TxtMedicine.clear();
        TxtDosage.clear();
        TxtDuration.clear();

    }

    public void setNextId() {
        TxtId.setText(String.valueOf(prescriptionService.getNextId()));
    }

    @Override
    public void initialize(URL url, ResourceBundle prescriptionBundle) {
        setNextId();


        ObservableList<Object> objectsPatient = FXCollections.observableArrayList();
        for (Patient patient : prescriptionService.getPatientsID()) {
            objectsPatient.add("ID-" + patient.getId() + " Name-" + patient.getName());
        }
        PIdComboBox.setItems(objectsPatient);


        ObservableList<Object> objectsDoc = FXCollections.observableArrayList();
        for (Doctor doctor : prescriptionService.getDocID()) {
            objectsDoc.add("ID-" + doctor.getId() + " Name-" + doctor.getName());
        }
        DIdComboBox.setItems(objectsDoc);
        colPrescriptionID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPatientId.setCellValueFactory(new PropertyValueFactory<>("pId"));
        colDoctorId.setCellValueFactory(new PropertyValueFactory<>("dID"));
        colMedicine.setCellValueFactory(new PropertyValueFactory<>("medicine"));
        colDosage.setCellValueFactory(new PropertyValueFactory<>("dosage"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));

        loadTable();
        clearAddForm();

    }

    public void btnSearchRemoveOnAction(ActionEvent actionEvent) {


        if (prescriptionService.deletePrescription(Integer.valueOf(TxtId1.getText())))
            new Alert(Alert.AlertType.INFORMATION, "Removed " + TxtId1.getText()).show();
        else new Alert(Alert.AlertType.INFORMATION, "Not Removed " + TxtId1.getText()).show();


        clearRemoveForm();


        setNextId();
        loadTable();


    }
    public void clearRemoveForm(){
        TxtPId1.clear();
        TxtDId1.clear();
        TxtMedicine1.clear();
        TxtDosage1.clear();
        TxtDuration1.clear();

    }
    public void OnSreachKeyReleased(KeyEvent keyEvent) {

        Prescription prescription = prescriptionService.searchPrescription(Integer.valueOf("0" + TxtId1.getText()));

        if (null != prescription) {
            TxtPId1.setText(String.valueOf(prescription.getPId()));
            TxtDId1.setText(String.valueOf(prescription.getDID()));
            TxtMedicine1.setText(prescription.getMedicine());
            TxtDosage1.setText(prescription.getDosage());
            TxtDuration1.setText(prescription.getDuration());


        } else {
            clearRemoveForm();

        }
    }


    private void loadTable() {
        tblPrescription.getItems().clear();

        tblPrescription.setItems(prescriptionService.getAll());
    }

    public void btnSearchUpdateOnAction(ActionEvent actionEvent) {

        if (prescriptionService.UpdatePrescription(new Prescription(Integer.valueOf(TxtId11.getText()), Integer.valueOf(TxtPId11.getText()), Integer.valueOf(TxtDId11.getText()), TxtMedicine11.getText(), TxtDosage11.getText(), TxtDuration11.getText()


        ))) {
            new Alert(Alert.AlertType.INFORMATION, "Updated ").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Not Updated ").show();
        }

        loadTable();
    }

    public void OnSreachUpdateKeyReleased(KeyEvent keyEvent) {
        Prescription prescription = prescriptionService.searchPrescription(Integer.valueOf("0" + TxtId11.getText()));

        if (null != prescription) {

            TxtPId11.setText(String.valueOf(prescription.getPId()));
            TxtDId11.setText(String.valueOf(prescription.getDID()));
            TxtMedicine11.setText(prescription.getMedicine());
            TxtDosage11.setText(prescription.getDosage());
            TxtDuration11.setText(prescription.getDuration());

        } else {
            TxtPId11.clear();
            TxtDId11.clear();
            TxtMedicine11.clear();
            TxtDosage11.clear();
            TxtDuration11.clear();


        }
    }
}

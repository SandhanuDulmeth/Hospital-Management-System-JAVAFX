package controller.appointment;

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

import model.Appointment;
import model.Doctor;
import model.Patient;

import java.net.URL;
import java.util.ResourceBundle;

public class AppointmentFormController implements Initializable {


    public TableView tblAppointment;
    public JFXTextField TxtId11;
    public JFXTextField TxtTime;
    public JFXTextField TxtDate1;
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
    public TableColumn colAppointmentID;
    public TableColumn colPatientId;
    public TableColumn colDoctorId;
    public TableColumn colAppointmentDate;
    public TableColumn colTime;


    @FXML
    private JFXTextField TxtId;

    @FXML
    private JFXTextField TxtId1;

    @FXML
    private JFXTextField TxtName;

    @FXML
    private JFXTextField TxtName1;

    @FXML
    public void btnAddOnAction(ActionEvent event) {

        if (!(TxtId.getText().isEmpty())) {
            Appointment appointment = new Appointment(
                    Integer.valueOf(TxtId.getText()),
                    getId((String) PIdComboBox.getValue()) ,
                    getId((String)  DIdComboBox.getValue()),
                   // Integer.valueOf(TxtPId.getText()),
                    //Integer.valueOf(TxtDId.getText()),
                    String.valueOf(DatePicker.getValue()),

                    TxtTime.getText()
            );
            System.out.println( getId((String) PIdComboBox.getValue()) +"><"+getId((String)  DIdComboBox.getValue()));
            System.out.println(DatePicker.getValue());
            System.out.println();
            if (AppointmentController.getInstance().addAppointment(appointment)) {
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


        TxtTime.clear();

    }

    public void setNextId() {
        TxtId.setText(String.valueOf(AppointmentController.getInstance().getNextId()));
    }

    @Override
    public void initialize(URL url, ResourceBundle appointmentBundle) {
        setNextId();


        ObservableList<Object> objectsPatient = FXCollections.observableArrayList();
        for (Patient patient : AppointmentController.getInstance().getPatientsID()) {
            objectsPatient.add("ID-"+patient.getId()+" Name-"+patient.getName());
        }
        PIdComboBox.setItems(objectsPatient);


        ObservableList<Object> objectsDoc = FXCollections.observableArrayList();
        for (Doctor doctor : AppointmentController.getInstance().getDocID()) {
            objectsDoc.add("ID-"+doctor.getId()+" Name-"+doctor.getName());
        }
        DIdComboBox.setItems(objectsDoc);

        colAppointmentID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPatientId.setCellValueFactory(new PropertyValueFactory<>("pId"));
        colDoctorId.setCellValueFactory(new PropertyValueFactory<>("dID"));
        colAppointmentDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));

        loadTable();
        clearAddForm();

    }

    public void btnSearchRemoveOnAction(ActionEvent actionEvent) {


        if (AppointmentController.getInstance().deleteAppointment(Integer.valueOf(TxtId1.getText())))
            new Alert(Alert.AlertType.INFORMATION, "Removed " + TxtId1.getText()).show();
        else new Alert(Alert.AlertType.INFORMATION, "Not Removed " + TxtId1.getText()).show();


        TxtPId1.clear();
        TxtDId1.clear();
        TxtDate1.clear();
        TxtTime1.clear();


        setNextId();
        loadTable();


    }

    public void OnSreachKeyReleased(KeyEvent keyEvent) {

        Appointment appointment = AppointmentController.getInstance().searchAppointment(Integer.valueOf("0" + TxtId1.getText()));

        if (null != appointment) {
            TxtPId1.setText(String.valueOf(appointment.getPId()));
            TxtDId1.setText(String.valueOf(appointment.getDID()));
            TxtDate1.setText(appointment.getDate());
            TxtTime1.setText(String.valueOf(appointment.getTime()));

        } else {
            TxtPId1.clear();
            TxtDId1.clear();
            TxtDate1.clear();
            TxtTime1.clear();

        }
    }


    private void loadTable() {
        tblAppointment.getItems().clear();

        tblAppointment.setItems(AppointmentController.getInstance().getAll());
    }

    public void btnSearchUpdateOnAction(ActionEvent actionEvent) {

        if (AppointmentController.getInstance().updateAppointment(new Appointment(
                Integer.valueOf(TxtId11.getText()),
                Integer.valueOf(TxtPId11.getText()),
                Integer.valueOf(TxtDId11.getText()),
                TxtDate11.getText(),
                TxtTime11.getText()


        ))) {
            new Alert(Alert.AlertType.INFORMATION, "Updated ").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Not Updated ").show();
        }

        loadTable();
    }

    public void OnSreachUpdateKeyReleased(KeyEvent keyEvent) {
        Appointment appointment = AppointmentController.getInstance().searchAppointment(Integer.valueOf("0" + TxtId11.getText()));

        if (null != appointment) {

            TxtPId11.setText(String.valueOf(appointment.getPId()));
            TxtDId11.setText(String.valueOf(appointment.getDID()));
            TxtDate11.setText(appointment.getDate());
            TxtTime11.setText(String.valueOf(appointment.getTime()));

        } else {
            TxtPId11.clear();
            TxtDId11.clear();
            TxtDate11.clear();
            TxtTime11.clear();


        }
    }
}

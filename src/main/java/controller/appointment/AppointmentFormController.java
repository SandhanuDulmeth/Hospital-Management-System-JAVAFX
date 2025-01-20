package controller.appointment;

import com.jfoenix.controls.JFXTextField;
import controller.appointment.AppointmentController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import model.Appointment;

import java.net.URL;
import java.util.ResourceBundle;

public class AppointmentFormController implements Initializable {


    public TableView tblAppointment;

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
    public JFXTextField TxtType;
    public JFXTextField TxtAllocatedTo;
    public JFXTextField TxtStatus;
    public JFXTextField TxtStatus1;
    public JFXTextField TxtAllocatedTo1;
    public JFXTextField TxtType1;
    public JFXTextField TxtType11;
    public JFXTextField TxtAllocatedTo11;
    public JFXTextField TxtStatus11;
    public JFXTextField TxtPId;
    public JFXTextField TxtTime;
    public JFXTextField TxtDId;
    public JFXTextField TxtDate;
    public JFXTextField TxtDate1;
    public JFXTextField TxtDId1;
    public JFXTextField TxtPId1;
    public JFXTextField TxtPId11;
    public JFXTextField TxtTime11;
    public JFXTextField TxtTime1;
    public JFXTextField TxtDId11;
    public JFXTextField TxtDate11;

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
                    Integer.valueOf(TxtPId.getText()),
                    Integer.valueOf(TxtDId.getText()),
                    TxtDate.getText(),
                    TxtTime.getText()
            );

            if (AppointmentController.getInstance().addCustomer(appointment)) {
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
        TxtPId.clear();
        TxtDId.clear();
        TxtDate.clear();
        TxtTime.clear();

    }

    public void setNextId() {
        TxtId.setText(String.valueOf(AppointmentController.getInstance().getNextId()));
    }

    @Override
    public void initialize(URL url, ResourceBundle appointmentBundle) {
        setNextId();
//        private Integer id;
//        private Integer pId;
//        private Integer dID;
//        private String date;
//        private String time;

        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("pId"));
        colSpecialty.setCellValueFactory(new PropertyValueFactory<>("dID"));
        colAvailability.setCellValueFactory(new PropertyValueFactory<>("date"));
        colQualifications.setCellValueFactory(new PropertyValueFactory<>("time"));

        loadTable();
        clearAddForm();

    }

    public void btnSearchRemoveOnAction(ActionEvent actionEvent) {


        if (AppointmentController.getInstance().deleteCustomer(Integer.valueOf(TxtId1.getText())))
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

        Appointment appointment = AppointmentController.getInstance().searchCustomer(Integer.valueOf("0" + TxtId1.getText()));

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

        if (AppointmentController.getInstance().UpdateCustomer(new Appointment(
                Integer.valueOf(TxtId11.getText()),
                Integer.valueOf(TxtPId1.getText()),
                Integer.valueOf(TxtDId11.getText()),
                TxtName11.getText(),
                TxtDate11.getText()

        ))) {
            new Alert(Alert.AlertType.INFORMATION, "Updated ").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Not Updated ").show();
        }

        loadTable();
    }

    public void OnSreachUpdateKeyReleased(KeyEvent keyEvent) {
        Appointment appointment = AppointmentController.getInstance().searchCustomer(Integer.valueOf("0" + TxtId11.getText()));

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

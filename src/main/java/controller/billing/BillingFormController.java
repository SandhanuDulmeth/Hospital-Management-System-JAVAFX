package controller.billing;

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
import model.Billing;
import model.Patient;
import service.ServiceFactory;
import service.custom.BillingService;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class BillingFormController implements Initializable {


    public TableView tblBilling;
    public JFXTextField TxtId11;

    public JFXTextField TxtDate1;
    public JFXTextField TxtPId1;
    public JFXTextField TxtPId11;
    public JFXTextField TxtDate11;
    public JFXComboBox PIdComboBox;

    public javafx.scene.control.DatePicker DatePicker;

    public TableColumn colPatientId;

    public JFXTextField TxtTotalAmount;
    public JFXComboBox PaymentStatusComboBox;
    public JFXTextField TxtPaymentStatus1;
    public JFXTextField TxtTotalAmount1;
    public JFXTextField TxtPaymentStatus11;
    public JFXTextField TxtTotalAmount11;
    public TableColumn colID;
    public TableColumn colTotalAmount;
    public TableColumn colPaymentStatus;
    public TableColumn colGeneratedDate;


    @FXML
    private JFXTextField TxtId;

    @FXML
    private JFXTextField TxtId1;


    private final BillingService billingService = ServiceFactory.getInstance().getServiceType(ServiceType.BILLING);

    @FXML
    public void btnAddOnAction(ActionEvent event) throws SQLException {

        if (!(TxtId.getText().isEmpty())) {
            Billing billing = new Billing(
                    Integer.valueOf(TxtId.getText()),
                    getId((String) PIdComboBox.getValue()),
                    Double.valueOf(TxtTotalAmount.getText()),
                    (String) PaymentStatusComboBox.getValue(),
                    String.valueOf(DatePicker.getValue())
            );

            if (billingService.addBilling(billing)) {
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
        TxtTotalAmount.clear();

    }

    public void setNextId() {
        TxtId.setText(String.valueOf(billingService.getNextId()));
    }

    @Override
    public void initialize(URL url, ResourceBundle billingBundle) {
        setNextId();

        PaymentStatusComboBox.setItems(FXCollections.observableArrayList("Paid", "Unpaid"));

        ObservableList<Object> objectsPatient = FXCollections.observableArrayList();
        for (Patient patient : billingService.getPatientsID()) {
            objectsPatient.add("ID-" + patient.getId() + " Name-" + patient.getName());
        }
        PIdComboBox.setItems(objectsPatient);


        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPatientId.setCellValueFactory(new PropertyValueFactory<>("pId"));
        colTotalAmount.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
        colPaymentStatus.setCellValueFactory(new PropertyValueFactory<>("paymentStatus"));
        colGeneratedDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        loadTable();
        clearAddForm();

    }

    public void btnSearchRemoveOnAction(ActionEvent actionEvent) throws SQLException {


        if (billingService.deleteBilling(Integer.valueOf(TxtId1.getText())))
            new Alert(Alert.AlertType.INFORMATION, "Removed " + TxtId1.getText()).show();
        else new Alert(Alert.AlertType.INFORMATION, "Not Removed " + TxtId1.getText()).show();

        clearRemoveForm();

        setNextId();
        loadTable();


    }

    public void clearRemoveForm() {
        TxtPId1.clear();
        TxtTotalAmount1.clear();
        TxtPaymentStatus1.clear();
        TxtDate1.clear();

    }

    public void OnSreachKeyReleased(KeyEvent keyEvent) {

        Billing billing = billingService.searchBilling(Integer.valueOf("0" + TxtId1.getText()));

        if (null != billing) {
            TxtPId1.setText(String.valueOf(billing.getPId()));
            TxtTotalAmount1.setText(String.valueOf(billing.getTotalAmount()));
            TxtPaymentStatus1.setText(billing.getPaymentStatus());
            TxtDate1.setText(String.valueOf(billing.getDate()));

        } else {
            clearRemoveForm();

        }
    }


    private void loadTable() {
        tblBilling.getItems().clear();
        ObservableList<Billing> billingObservableList = FXCollections.observableArrayList();
        billingService.getAll().forEach(billing -> billingObservableList.add(billing));
        tblBilling.setItems(billingObservableList);
    }

    public void btnSearchUpdateOnAction(ActionEvent actionEvent) {

        if (billingService.updateBilling(new Billing(
                Integer.valueOf(TxtId11.getText()),
                Integer.valueOf(TxtPId11.getText()),
                Double.valueOf(TxtTotalAmount11.getText()),
                TxtPaymentStatus11.getText(),
                TxtDate11.getText()


        ))) {
            new Alert(Alert.AlertType.INFORMATION, "Updated ").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Not Updated ").show();
        }

        loadTable();
    }

    public void OnSreachUpdateKeyReleased(KeyEvent keyEvent) {
        Billing billing = billingService.searchBilling(Integer.valueOf("0" + TxtId11.getText()));

        if (null != billing) {

            TxtPId11.setText(String.valueOf(billing.getPId()));
            TxtTotalAmount11.setText(String.valueOf(billing.getTotalAmount()));
            TxtPaymentStatus11.setText(billing.getPaymentStatus());
            TxtDate11.setText(String.valueOf(billing.getDate()));

        } else {
            TxtPId11.clear();
            TxtTotalAmount11.clear();
            TxtPaymentStatus11.clear();
            TxtDate11.clear();

        }
    }
}

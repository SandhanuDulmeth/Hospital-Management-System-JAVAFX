package controller.patient;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class PatientFormController implements Initializable {

    public JFXTextField TxtTitle1;

    public JFXTextField TxtDate1;
    public TableView tblPatient;

    public JFXTextField TxtId11;
    public JFXTextField TxtName11;
    public JFXTextField TxtAddress11;
    public JFXTextField TxtSalary11;
    public JFXTextField TxtCity11;
    public JFXTextField TxtProvince11;
    public JFXTextField TxtPostalCode11;
    public JFXTextField TxtTitle11;
    public JFXTextField TxtDate11;
    public TableColumn colID;
    public TableColumn colName;
    public TableColumn colAge;
    public TableColumn colGender;
    public TableColumn colContactDetails;
    public TableColumn colEmergencyContact;
    public TableColumn colMedicalHistory;
    @FXML
    private JFXComboBox ComboBoxTitle;

    @FXML
    private JFXComboBox ComboBoxTitle1;

    @FXML
    private DatePicker DatePickerDOB;

    @FXML
    private DatePicker DatePickerDOB1;

    @FXML
    private JFXTextField TxtAddress;

    @FXML
    private JFXTextField TxtAddress1;

    @FXML
    private JFXTextField TxtCity;

    @FXML
    private JFXTextField TxtCity1;

    @FXML
    private JFXTextField TxtId;

    @FXML
    private JFXTextField TxtId1;

    @FXML
    private JFXTextField TxtName;

    @FXML
    private JFXTextField TxtName1;

    @FXML
    private JFXTextField TxtPostalCode;

    @FXML
    private JFXTextField TxtPostalCode1;

    @FXML
    private JFXTextField TxtProvince;

    @FXML
    private JFXTextField TxtProvince1;

    @FXML
    private JFXTextField TxtSalary;

    @FXML
    private JFXTextField TxtSalary1;


    @FXML
    public void btnAddOnAction(ActionEvent event) {


//        if (ComboBoxTitle.getValue() != null) {
//           // PatientService customerService= ServiceFactory.getInstance().getServiceType(ServiceType.CUSTOMER);
//            Patient patient = new Patient(TxtId.getText(),
//                    (String) ComboBoxTitle.getValue(),
//                    TxtName.getText(),
//                    Date.valueOf(DatePickerDOB.getValue()),
//                    Double.parseDouble(TxtSalary.getText()),
//                    TxtAddress.getText(), TxtCity.getText(),
//                    TxtProvince.getText(),
//                    TxtPostalCode.getText());
//            //if (CustomerController.getInstance().addCustomer()) {
//            if(PatientController.getInstance().addCustomer(patient)){
//                new Alert(Alert.AlertType.INFORMATION, "Added").show();
//                clearAddForm();
//                loadTable();
//            } else {
//                new Alert(Alert.AlertType.ERROR, "Not Added").show();
//            }
//
//        } else {
//            new Alert(Alert.AlertType.ERROR, "Please Select a Title").show();
//        }

    }

    @FXML
    public void btnClearOnAction(ActionEvent event) {
        clearAddForm();
    }

    public void clearAddForm() {
//        Connection connection = null;
//        try {
//            connection = DBConnection.getINSTANCE().getConnection();
//            PreparedStatement preparedStatement = connection.prepareStatement("SELECT CustID FROM customer ORDER BY CustID DESC LIMIT 1");
//            ResultSet resultSet = preparedStatement.executeQuery();
//            resultSet.next();
//
//            String nextIndex = String.format("C%03d", Integer.parseInt(resultSet.getString(1).split("[C]")[1]) + 1);
//            TxtId.setText(nextIndex);
//
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        ComboBoxTitle.setValue(null);
//        TxtName.setText(null);
//        TxtAddress.setText(null);
//        DatePickerDOB.setValue(null);
//        TxtSalary.setText(null);
//        TxtAddress.setText(null);
//        TxtCity.setText(null);
//        TxtProvince.setText(null);
//        TxtPostalCode.setText(null);

    }
//    private Integer id;
//    private String name;
//    private Integer age;
//    private String gender;
//    private String contactDetails;
//    private String emergencyContact ;
//    private String medicalHistory;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colContactDetails.setCellValueFactory(new PropertyValueFactory<>("contactDetails"));
        colEmergencyContact.setCellValueFactory(new PropertyValueFactory<>("emergencyContact"));
        colMedicalHistory.setCellValueFactory(new PropertyValueFactory<>("medicalHistory"));

        loadTable();
        //clearAddForm();
        //ComboBoxTitle.setItems(FXCollections.observableArrayList("Mr.", "Mrs.", "Miss", "Ms"));

    }

    public void btnSearchRemoveOnAction(ActionEvent actionEvent) {


//        if (PatientController.getInstance().deleteCustomer(TxtId1.getText()))
//            new Alert(Alert.AlertType.INFORMATION, "Removed " + TxtId1.getText()).show();
//        else new Alert(Alert.AlertType.INFORMATION, "Not Removed " + TxtId1.getText()).show();
//
//
//        TxtId1.setText(null);
//        TxtTitle1.setText(null);
//        TxtName1.setText(null);
//        TxtDate1.setText(null);
//        TxtSalary1.setText(null);
//        TxtAddress1.setText(null);
//        TxtCity1.setText(null);
//        TxtPostalCode1.setText(null);
//        TxtProvince1.setText(null);
//
//        clearAddForm();
//        loadTable();


    }

    public void OnSreachKeyReleased(KeyEvent keyEvent) {

//        Patient patient = PatientController.getInstance().searchCustomer(TxtId1.getText());
//
//        if (null != patient) {
//            TxtTitle1.setText(patient.getCustTitle());
//            TxtName1.setText(patient.getCustName());
//            TxtDate1.setText(String.valueOf(patient.getDOB()));
//            TxtSalary1.setText(String.valueOf(patient.getSalary()));
//            TxtAddress1.setText(patient.getCustAddress());
//            TxtCity1.setText(patient.getCity());
//            TxtProvince1.setText(patient.getProvince());
//            TxtPostalCode1.setText(patient.getPostalCode());
//        }else{
//            TxtTitle1.setText(null);
//            TxtName1.setText(null);
//            TxtDate1.setText(null);
//            TxtSalary1.setText(null);
//            TxtAddress1.setText(null);
//            TxtCity1.setText(null);
//            TxtProvince1.setText(null);
//            TxtPostalCode1.setText(null);
//
//        }
    }

    private void loadTable() {
        tblPatient.getItems().clear();

        tblPatient.setItems(PatientController.getInstance().getAll());
    }

    public void btnSearchUpdateOnAction(ActionEvent actionEvent) {

//        if (PatientController.getInstance().UpdateCustomer(new Patient(
//                TxtId11.getText(),
//                TxtTitle11.getText(),
//                TxtName11.getText(),
//                Date.valueOf(TxtDate11.getText()) ,
//                Double.parseDouble(TxtSalary11.getText()),
//                TxtAddress11.getText(), TxtCity11.getText(),
//                TxtProvince11.getText(),
//                TxtPostalCode11.getText()
//        ))) {
//            new Alert(Alert.AlertType.INFORMATION, "Updated " ).show();
//        } else {
//            new Alert(Alert.AlertType.ERROR, "Not Updated " ).show();
//        }
//
//        loadTable();
    }

    public void OnSreachUpdateKeyReleased(KeyEvent keyEvent) {
//        Patient patient = PatientController.getInstance().searchCustomer(TxtId11.getText());
//
//        if (null != patient) {
//            TxtTitle11.setText(patient.getCustTitle());
//            TxtName11.setText(patient.getCustName());
//            TxtDate11.setText(String.valueOf(patient.getDOB()));
//            TxtSalary11.setText(String.valueOf(patient.getSalary()));
//            TxtAddress11.setText(patient.getCustAddress());
//            TxtCity11.setText(patient.getCity());
//            TxtProvince11.setText(patient.getProvince());
//            TxtPostalCode11.setText(patient.getPostalCode());
//        }else{
//            TxtTitle11.setText(null);
//            TxtName11.setText(null);
//            TxtDate11.setText(null);
//            TxtSalary11.setText(null);
//            TxtAddress11.setText(null);
//            TxtCity11.setText(null);
//            TxtProvince11.setText(null);
//            TxtPostalCode11.setText(null);
//
//        }
    }
}

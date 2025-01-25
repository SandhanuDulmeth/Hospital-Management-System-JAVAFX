package controller.resource;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import model.Resource;

import java.net.URL;
import java.util.ResourceBundle;

public class ResourceFormController implements Initializable {


    public TableView tblResource;

    public JFXTextField TxtId11;
    public JFXTextField TxtName11;

    public TableColumn colID;
    public TableColumn colName;

    public JFXTextField TxtType;
    public JFXTextField TxtAllocatedTo;
    public JFXTextField TxtStatus;
    public JFXTextField TxtStatus1;
    public JFXTextField TxtAllocatedTo1;
    public JFXTextField TxtType1;
    public JFXTextField TxtType11;
    public JFXTextField TxtAllocatedTo11;
    public JFXTextField TxtStatus11;
    public TableColumn colType;
    public TableColumn colStatus;
    public TableColumn colAllocatedTo;

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
            Resource resource = new Resource(Integer.valueOf(TxtId.getText()), TxtType.getText(), TxtName.getText(), TxtStatus.getText(), Integer.valueOf(TxtAllocatedTo.getText()));

            if (controller.resource.ResourceController.getInstance().addResource(resource)) {
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
        TxtType.clear();
        TxtName.clear();
        TxtStatus.clear();
        TxtAllocatedTo.clear();

    }

    public void setNextId() {
        TxtId.setText(String.valueOf(controller.resource.ResourceController.getInstance().getNextId()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setNextId();

        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colAllocatedTo.setCellValueFactory(new PropertyValueFactory<>("allocatedTo"));

        loadTable();
        clearAddForm();

    }

    public void btnSearchRemoveOnAction(ActionEvent actionEvent) {


        if (controller.resource.ResourceController.getInstance().deleteResource(Integer.valueOf(TxtId1.getText())))
            new Alert(Alert.AlertType.INFORMATION, "Removed " + TxtId1.getText()).show();
        else new Alert(Alert.AlertType.INFORMATION, "Not Removed " + TxtId1.getText()).show();


        TxtType1.clear();
        TxtName1.clear();
        TxtStatus1.clear();
        TxtAllocatedTo1.clear();


        setNextId();
        loadTable();


    }

    public void OnSreachKeyReleased(KeyEvent keyEvent) {

        Resource resource = controller.resource.ResourceController.getInstance().searchResource(Integer.valueOf("0" + TxtId1.getText()));

        if (null != resource) {
            TxtType1.setText(resource.getType());
            TxtName1.setText(resource.getName());
            TxtStatus1.setText(resource.getStatus());
            TxtAllocatedTo1.setText(String.valueOf(resource.getAllocatedTo()));

        } else {
            TxtType1.clear();
            TxtName1.clear();
            TxtStatus1.clear();
            TxtAllocatedTo1.clear();

        }
    }


    private void loadTable() {
        tblResource.getItems().clear();

        tblResource.setItems(controller.resource.ResourceController.getInstance().getAll());
    }

    public void btnSearchUpdateOnAction(ActionEvent actionEvent) {

        if (controller.resource.ResourceController.getInstance().UpdateResource(new Resource(
                Integer.valueOf(TxtId11.getText()),
                TxtType11.getText(),
                TxtName11.getText(),
                TxtStatus11.getText(),
               Integer.valueOf(TxtAllocatedTo11.getText())
                ))) {
            new Alert(Alert.AlertType.INFORMATION, "Updated ").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Not Updated ").show();
        }

        loadTable();
    }

    public void OnSreachUpdateKeyReleased(KeyEvent keyEvent) {
        Resource resource = ResourceController.getInstance().searchResource(Integer.valueOf("0" + TxtId11.getText()));

        if (null != resource) {

            TxtType11.setText(resource.getType());
            TxtName11.setText(resource.getName());
            TxtStatus11.setText(resource.getStatus());
            TxtAllocatedTo11.setText(String.valueOf(resource.getAllocatedTo()));

        } else {
            TxtType11.clear();
            TxtName11.clear();
            TxtStatus11.clear();
            TxtAllocatedTo11.clear();

        }
    }
}

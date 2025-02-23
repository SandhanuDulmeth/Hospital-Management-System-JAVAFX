package controller.dashBoard;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import util.StageUtils;

import java.io.IOException;


public class DashBoardController {

    public static AnchorPane DsashBoardAnchorPaneID;


    private void loadFXMLAndShowStage(String fxmlPath, String title, ActionEvent event, double width, double height) {
        Stage loadingStage = createLoadingStage();
        loadingStage.show();

        Thread loadThread = new Thread(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
                Parent root = loader.load();

                Platform.runLater(() -> {
                    Stage stage = new Stage();
                    StageUtils.configureDecoratedStage(stage, root, title, width, height);
                    stage.initOwner(((Node) event.getSource()).getScene().getWindow());
                    stage.show();
                    loadingStage.close();
                });
            } catch (IOException e) {
                Platform.runLater(() -> {
                    loadingStage.close();
                    showErrorDialog("Error Loading " + title, e.getMessage());
                });
            }
        });

        loadThread.setDaemon(true);
        loadThread.start();
    }

    private Stage createLoadingStage() {
        Stage loadingStage = new Stage();
        ProgressIndicator progress = new ProgressIndicator();
        StackPane root = new StackPane(progress);
        root.setStyle("-fx-background-color: rgba(255, 255, 255, 0.7);");
        Scene scene = new Scene(root, 100, 100);
        loadingStage.initStyle(StageStyle.TRANSPARENT);
        loadingStage.setScene(scene);
        return loadingStage;
    }

    private void showErrorDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Example button handlers with specific dimensions
    public void btnPatientOnAction(ActionEvent actionEvent) {
        loadFXMLAndShowStage("/view/Patient.fxml", "Patient Management", actionEvent, 1000, 700);
    }

    public void btnDoctorOnAction(ActionEvent actionEvent) {
        loadFXMLAndShowStage("/view/Doctor.fxml", "Doctor Management", actionEvent, 1000, 700);
    }

    public void btnResourceOnAction(ActionEvent actionEvent) {
        loadFXMLAndShowStage("/view/Resource.fxml", "Resource Management", actionEvent, 1000, 700);
    }

    public void btnAppointmentOnAction(ActionEvent actionEvent) {
        loadFXMLAndShowStage("/view/Appointment.fxml", "Appointment Management", actionEvent, 1000, 700);
    }

    public void btnPrescriptionOnAction(ActionEvent actionEvent) {
        loadFXMLAndShowStage("/view/Prescription.fxml", "Prescription Management", actionEvent, 1000, 700);
    }

    public void btnBillingOnAction(ActionEvent actionEvent) {
        loadFXMLAndShowStage("/view/Billing.fxml", "Billing Management", actionEvent, 1000, 700);
    }



    public void btnReportOnAction(ActionEvent actionEvent) {
        loadFXMLAndShowStage("/view/Report.fxml", "Report Management", actionEvent, 1000, 700);
    }
}


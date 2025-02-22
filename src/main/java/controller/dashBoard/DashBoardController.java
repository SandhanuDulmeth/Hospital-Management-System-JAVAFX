package controller.dashBoard;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class DashBoardController {

    // Reusable method to load FXML and open a new stage with a loading animation.
    private void loadFXMLAndShowStage(String fxmlPath, String title, ActionEvent event) {
        // Create a temporary stage with a loading animation (ProgressIndicator)
        Stage loadingStage = new Stage();
        ProgressIndicator progressIndicator = new ProgressIndicator();
        StackPane loadingRoot = new StackPane(progressIndicator);
        Scene loadingScene = new Scene(loadingRoot, 100, 100);
        loadingStage.setScene(loadingScene);
        loadingStage.setTitle("Loading " + title + "...");
        loadingStage.show();

        // Create a new thread to load the FXML in the background
        Thread loadThread = new Thread(() -> {
            try {
                // Load the FXML file in the background thread
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
                Parent root = loader.load();

                // Update the UI on the JavaFX Application Thread
                Platform.runLater(() -> {
                    Stage stage = new Stage();
                    stage.setTitle(title);
                    stage.setScene(new Scene(root));
                    // Set the owner to the current window
                    Window currentWindow = ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                    stage.initOwner(currentWindow);
                    stage.show();
                    loadingStage.close();
                });
            } catch (IOException e) {
                // If loading fails, close the loading stage and show an error dialog
                Platform.runLater(() -> {
                    e.printStackTrace();
                    loadingStage.close();
                    showErrorDialog("Failed to load " + title, e.getMessage());
                });
            }
        });

        loadThread.setDaemon(true);
        loadThread.start();
    }

    // Method to show an error dialog
    private void showErrorDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Button action methods
    public void btnPatientOnAction(ActionEvent actionEvent) {
        loadFXMLAndShowStage("/view/Patient.fxml", "Patient Management", actionEvent);
    }

    public void btnDoctorOnAction(ActionEvent actionEvent) {
        loadFXMLAndShowStage("/view/Doctor.fxml", "Doctor Management", actionEvent);
    }

    public void btnResourceOnAction(ActionEvent actionEvent) {
        loadFXMLAndShowStage("/view/Resource.fxml", "Resource Management", actionEvent);
    }

    public void btnAppointmentOnAction(ActionEvent actionEvent) {
        loadFXMLAndShowStage("/view/Appointment.fxml", "Appointment Management", actionEvent);
    }

    public void btnPrescriptionOnAction(ActionEvent actionEvent) {
        loadFXMLAndShowStage("/view/Prescription.fxml", "Prescription Management", actionEvent);
    }

    public void btnBillingOnAction(ActionEvent actionEvent) {
        loadFXMLAndShowStage("/view/Billing.fxml", "Billing Management", actionEvent);
    }



    public void btnReportOnAction(ActionEvent actionEvent) {
        loadFXMLAndShowStage("/view/Report.fxml", "Report Management", actionEvent);
    }
}


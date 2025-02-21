package controller.report;

import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import service.ServiceFactory;
import service.custom.DoctorService;
import util.ServiceType;

import java.net.URL;
import java.util.ResourceBundle;

public class ReportController implements Initializable {

    public Label MaxDoctorId;
    private final DoctorService doctorService = ServiceFactory.getInstance().getServiceType(ServiceType.DOCTOR);
    public CheckBox DoctorCheckBox;
    public JFXTextField lblStartId;
    public JFXTextField lblEndId;

    public static void generateReportWithLoading(String reportPath, String fileName, String query) {

        Stage loadingStage = new Stage();
        ProgressIndicator progressIndicator = new ProgressIndicator();
        StackPane loadingRoot = new StackPane(progressIndicator);
        Scene loadingScene = new Scene(loadingRoot, 100, 100);
        loadingStage.setScene(loadingScene);
        loadingStage.setTitle("Generating Report...");
        loadingStage.show();


        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {

                    JasperDesign report = JRXmlLoader.load(reportPath);
                    JRDesignQuery newQuery = new JRDesignQuery();
                    newQuery.setText(query);
                    report.setQuery(newQuery);
                    JasperReport jasperReport = JasperCompileManager.compileReport(report);


                    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DBConnection.getInstance().getConnection());


                    JasperExportManager.exportReportToPdfFile(jasperPrint, fileName);


                    Platform.runLater(() -> {
                        JasperViewer.viewReport(jasperPrint, false);
                        new Alert(Alert.AlertType.INFORMATION, "Successfully Generated!").show();
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    Platform.runLater(() -> {
                        new Alert(Alert.AlertType.ERROR, "Report Generation Failed!").show();
                    });
                    throw e;
                }
                return null;
            }
        };


        task.setOnSucceeded(e -> loadingStage.close());
        task.setOnFailed(e -> loadingStage.close());


        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MaxDoctorId.setText("Enter ID Between 1 and " + (doctorService.getNextId() - 1));

    }


    public void lblGetReportsOnAction(ActionEvent actionEvent) {

        if (DoctorCheckBox.isSelected()) {

            String reportPath = "src/main/resources/report/Simple_Blue2.jrxml";
            String SQL ="SELECT * FROM doctor WHERE doctor_id BETWEEN "+lblStartId.getText()+" AND "+lblEndId.getText();
            ReportController.generateReportWithLoading(reportPath, "Doctor.pdf", SQL);
        }
    }
}

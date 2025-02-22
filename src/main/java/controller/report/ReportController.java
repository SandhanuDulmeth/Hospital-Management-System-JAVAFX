package controller.report;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dao.DaoFactory;
import dao.custom.PatientDao;
import db.DBConnection;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import service.ServiceFactory;
import service.custom.AppointmentService;
import service.custom.DoctorService;
import service.custom.PatientService;
import service.custom.ResourceService;
import util.DaoType;
import util.ServiceType;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.util.Duration;

import javax.swing.event.SwingPropertyChangeSupport;
import java.net.URL;
import java.util.ResourceBundle;

public class ReportController implements Initializable {

    public Label MaxDoctorId;

    public CheckBox DoctorCheckBox;
    public JFXTextField lblStartId;
    public JFXTextField lblEndId;
    public JFXCheckBox PatientCheckBox;
    public Label MaxPatient;
    public Button btnGetreport;
    public PieChart pieChart;
    private final DoctorService doctorService = ServiceFactory.getInstance().getServiceType(ServiceType.DOCTOR);
    private final PatientService patientService = ServiceFactory.getInstance().getServiceType(ServiceType.PATIENT);
    private final ResourceService resourceService = ServiceFactory.getInstance().getServiceType(ServiceType.RESOURCE);
    private final AppointmentService appointmentService= ServiceFactory.getInstance().getServiceType(ServiceType.APPOINTMENT);
    public JFXCheckBox ResourceCheckBox;
    public JFXComboBox ComboBox;
    public Label lblOFMaxSize;
    public JFXCheckBox AppointmentCheckBox;

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


    public void lblGetReportsOnAction(ActionEvent actionEvent) {
        String SQL =null;
        if (lblStartId.getText().equals("") || lblEndId.getText().equals("")){
            new Alert(Alert.AlertType.ERROR,"Select Start and End").show();
            return;
        }
        if(ComboBox.getValue() ==null){
            new Alert(Alert.AlertType.ERROR,"Select the User").show();
            return;
        }
        if (ComboBox.getValue().equals("Doctor")) {

             SQL = "SELECT * FROM doctor WHERE doctor_id BETWEEN " + lblStartId.getText() + " AND " + lblEndId.getText();
            ReportController.generateReportWithLoading("src/main/resources/report/Doctor.jrxml", "Doctor.pdf", SQL);
        }
        if (ComboBox.getValue().equals("Patient")) {
            SQL = "SELECT * FROM patient WHERE patient_id BETWEEN " + lblStartId.getText() + " AND " + lblEndId.getText();
            ReportController.generateReportWithLoading("src/main/resources/report/Patient.jrxml", "Patient.pdf", SQL);
        }
        if (ComboBox.getValue().equals("Appointment")){
            SQL = "SELECT * FROM appointment WHERE appointment_id BETWEEN " + lblStartId.getText() + " AND " + lblEndId.getText();
            ReportController.generateReportWithLoading("src/main/resources/report/Appointment.jrxml", "Appointment.pdf", SQL);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MaxDoctorId.setText("Enter ID Between 1 and " + (doctorService.getNextId() - 1));
        pieCharts();
        ComboBox.setItems(FXCollections.observableArrayList("Doctor", "Patient", "Resource","Appointment"));
    }

    public void pieCharts() {
        // Clear the existing pie chart data
        pieChart.getData().clear();

        ObservableList<PieChart.Data> objects = FXCollections.observableArrayList();

        if (DoctorCheckBox.isSelected()) {
            objects.add(new PieChart.Data("Doctor", doctorService.getNextId() - 1));
        }
        if (PatientCheckBox.isSelected()) {
            objects.add(new PieChart.Data("Patient", patientService.getNextId() - 1));
        }
        if (ResourceCheckBox.isSelected()){
            objects.add(new PieChart.Data("Resource", resourceService.getNextId() - 1));
        }


        ObservableList<PieChart.Data> pieChartData = objects;

        pieChartData.forEach(data ->
                data.nameProperty().bind(
                        Bindings.concat(
                                "Name: ", data.getName(), ", Amount: ", data.pieValueProperty()
                        )
                )
        );

        pieChart.getData().addAll(pieChartData);
    }


    public void getPieChartReport(ActionEvent actionEvent) {
    }

    public void btnDoctorCheckBox(ActionEvent actionEvent) {
        pieCharts();
    }

    public void btnPatientCheckBox(ActionEvent actionEvent) {
        pieCharts();
    }

    public void btnResourceCheckBox(ActionEvent actionEvent) {
        pieCharts();
    }


    public void ComboBoxonAction(ActionEvent actionEvent) {
        if(ComboBox.getValue() ==null){
          lblOFMaxSize.setText(null);
        }
        if (ComboBox.getValue().equals("Doctor")) {

            lblOFMaxSize.setText("OF :"+(doctorService.getNextId()-1));
        }else if (ComboBox.getValue().equals("Patient")) {
           lblOFMaxSize.setText("OF :"+(patientService.getNextId()-1));
        }else if (ComboBox.getValue().equals("Appointment")){
          lblOFMaxSize.setText("OF :"+(appointmentService.getNextId()-1));
        }else if (ComboBox.getValue().equals("Resource")){
            lblOFMaxSize.setText("OF :"+(resourceService.getNextId()-1));
        }else{
            lblOFMaxSize.setText(null);
        }

    }


}



package controller.report;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Attachments;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import java.io.IOException;

import com.sendgrid.helpers.mail.objects.Personalization;
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

import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import service.ServiceFactory;
import service.custom.*;

import util.ServiceType;

import javafx.scene.control.Button;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.*;

public class ReportController implements Initializable {


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
    private final AppointmentService appointmentService = ServiceFactory.getInstance().getServiceType(ServiceType.APPOINTMENT);
    private final BillingService billingService = ServiceFactory.getInstance().getServiceType(ServiceType.BILLING);
    private final PrescriptionService prescriptionService = ServiceFactory.getInstance().getServiceType(ServiceType.PRESCRIPTION);
    public JFXCheckBox ResourceCheckBox;
    public JFXComboBox ComboBox;
    public Label lblOFMaxSize;
    public JFXTextField sendReportEmailTextField;
    public JFXButton btnsendReports;

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
        String SQL = null;
        if (lblStartId.getText().equals("") || lblEndId.getText().equals("")) {
            new Alert(Alert.AlertType.ERROR, "Select Start and End").show();
            return;
        }
        if (ComboBox.getValue() == null) {
            new Alert(Alert.AlertType.ERROR, "Select the User").show();
            return;
        }
        if (ComboBox.getValue().equals("Doctor")) {

            SQL = "SELECT * FROM doctor WHERE doctor_id BETWEEN " + lblStartId.getText() + " AND " + lblEndId.getText();
            ReportController.generateReportWithLoading("src/main/resources/report/Doctor.jrxml", "src/main/resources/reportPDF/Doctor.pdf", SQL);
        }
        if (ComboBox.getValue().equals("Patient")) {
            SQL = "SELECT * FROM patient WHERE patient_id BETWEEN " + lblStartId.getText() + " AND " + lblEndId.getText();
            ReportController.generateReportWithLoading("src/main/resources/report/Patient.jrxml", "src/main/resources/reportPDF/Patient.pdf", SQL);
        }
        if (ComboBox.getValue().equals("Appointment")) {
            SQL = "SELECT * FROM appointment WHERE appointment_id BETWEEN " + lblStartId.getText() + " AND " + lblEndId.getText();
            ReportController.generateReportWithLoading("src/main/resources/report/Appointment.jrxml", "src/main/resources/reportPDF/Appointment.pdf", SQL);
        }
        if (ComboBox.getValue().equals("Resource")) {
            SQL = "SELECT * FROM Resources WHERE resource_id BETWEEN " + lblStartId.getText() + " AND " + lblEndId.getText();
            ReportController.generateReportWithLoading("src/main/resources/report/Resources.jrxml", "src/main/resources/reportPDF/Resources.pdf", SQL);
        }
        if (ComboBox.getValue().equals("Billing")) {
            SQL = "SELECT * FROM Billing WHERE bill_id BETWEEN " + lblStartId.getText() + " AND " + lblEndId.getText();
            ReportController.generateReportWithLoading("src/main/resources/report/Billing.jrxml", "src/main/resources/reportPDF/Billing.pdf", SQL);
        }
        if (ComboBox.getValue().equals("Prescription")) {
            SQL = "SELECT * FROM Prescription WHERE prescription_id BETWEEN " + lblStartId.getText() + " AND " + lblEndId.getText();
            ReportController.generateReportWithLoading("src/main/resources/report/Prescription.jrxml", "src/main/resources/reportPDF/Prescription.pdf", SQL);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        pieCharts();
        ComboBox.setItems(FXCollections.observableArrayList("Doctor", "Patient", "Resource", "Appointment", "Billing", "Prescription"));
    }

    public void pieCharts() {

        pieChart.getData().clear();

        ObservableList<PieChart.Data> objects = FXCollections.observableArrayList();

        if (DoctorCheckBox.isSelected()) {
            objects.add(new PieChart.Data("Doctor", doctorService.getNextId() - 1));

        }
        if (PatientCheckBox.isSelected()) {
            objects.add(new PieChart.Data("Patient", patientService.getNextId() - 1));
        }
        if (ResourceCheckBox.isSelected()) {
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
        if (ComboBox.getValue() == null) {
            lblOFMaxSize.setText(null);
        }
        if (ComboBox.getValue().equals("Doctor")) {

            lblOFMaxSize.setText("OF :" + (doctorService.getNextId() - 1));
        } else if (ComboBox.getValue().equals("Patient")) {
            lblOFMaxSize.setText("OF :" + (patientService.getNextId() - 1));
        } else if (ComboBox.getValue().equals("Appointment")) {
            lblOFMaxSize.setText("OF :" + (appointmentService.getNextId() - 1));
        } else if (ComboBox.getValue().equals("Resource")) {
            lblOFMaxSize.setText("OF :" + (resourceService.getNextId() - 1));
        } else if (ComboBox.getValue().equals("Billing")) {
            lblOFMaxSize.setText("OF :" + (billingService.getNextId() - 1));
        } else if (ComboBox.getValue().equals("Prescription")) {
            lblOFMaxSize.setText("OF :" + (prescriptionService.getNextId() - 1));
        } else {
            lblOFMaxSize.setText(null);
        }

    }


    public void getPieChartReportOnAction(ActionEvent actionEvent) throws JRException, SQLException {
        String reportPath = "src/main/resources/report/Pie_chart.jrxml";
        List<String> queryParts = new ArrayList<>();

        if (DoctorCheckBox.isSelected()) {
            queryParts.add("SELECT 'Doctors' AS category, COUNT(*) AS total FROM doctor");
        }
        if (PatientCheckBox.isSelected()) {
            queryParts.add("SELECT 'Patients' AS category, COUNT(*) AS total FROM patient");
        }
        if (ResourceCheckBox.isSelected()) {
            queryParts.add("SELECT 'Resources' AS category, COUNT(*) AS total FROM resources");
        }

        if (queryParts.isEmpty()) {
            System.out.println("Please select at least one category to generate the report.");
            return;
        }

        String SQLForChart = String.join(" UNION ALL ", queryParts);


        ReportController.generateReportWithLoading(reportPath, "Chart.pdf", SQLForChart);
    }




    public void btnsendReportsOnAction(ActionEvent actionEvent) {
        if (ComboBox.getValue() == null) {
            new Alert(Alert.AlertType.ERROR, "Select the User").show();
            return;
        }
        if (sendReportEmailTextField.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Enter the Sender Email").show();
            return;
        }


        String apiKey = "Replace with your actual SendGrid API Key";
        String templateId = "d-fd8150babfaa4f6d937df091e148e078";
        String fromEmail = "bruno12mendis740cj30x@gmail.com";
        String toEmail = sendReportEmailTextField.getText();

        String memberName = (String) ComboBox.getValue();
        if (memberName.isEmpty()) memberName = "User";

        Email from = new Email(fromEmail);
        Email to = new Email(toEmail);
        Mail mail = new Mail();
        mail.setFrom(from);
        mail.setTemplateId(templateId);

        Personalization personalization = new Personalization();
        personalization.addTo(to);
        personalization.addDynamicTemplateData("UserName", memberName);
        personalization.addDynamicTemplateData("SenderEmail", toEmail);
        mail.addPersonalization(personalization);


        String filePath = "";
        String selectedReport = (String) ComboBox.getValue();
        switch (selectedReport) {
            case "Doctor":
                filePath = "src/main/resources/reportPDF/Doctor.pdf";
                break;
            case "Patient":
                filePath = "src/main/resources/reportPDF/Patient.pdf";
                break;
            case "Appointment":
                filePath = "src/main/resources/reportPDF/Appointment.pdf";
                break;
            case "Resource":
                filePath = "src/main/resources/reportPDF/Resources.pdf";
                break;
            case "Billing":
                filePath = "src/main/resources/reportPDF/Billing.pdf";
                break;
            case "Prescription":
                filePath = "src/main/resources/reportPDF/Prescription.pdf";
                break;
            default:
                new Alert(Alert.AlertType.ERROR, "Invalid report type selected").show();
                return;
        }

        try {

            byte[] fileData = Files.readAllBytes(Paths.get(filePath));
            String encodedFile = Base64.getEncoder().encodeToString(fileData);

            Attachments attachment = new Attachments();
            attachment.setContent(encodedFile);
            attachment.setType("application/pdf");
            attachment.setFilename("report.pdf");
            attachment.setDisposition("attachment");
            mail.addAttachments(attachment);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Error attaching file: " + e.getMessage()).show();
            return;
        }


        SendGrid sg = new SendGrid(apiKey);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);

            if (response.getStatusCode() >= 200 && response.getStatusCode() < 300) {
                new Alert(Alert.AlertType.INFORMATION, "Report sent successfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to send report. Response: " + response.getBody()).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error sending email: " + e.getMessage()).show();
        }

    }
}



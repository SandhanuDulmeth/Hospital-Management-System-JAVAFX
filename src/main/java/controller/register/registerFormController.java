package controller.register;

import util.ServiceType;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.Users;
import org.jasypt.util.text.BasicTextEncryptor;
import service.ServiceFactory;
import service.custom.RegisterService;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class registerFormController implements Initializable {

    public JFXTextField txtId;
    public JFXTextField txtName;
    public JFXTextField txtEmail;
    public JFXTextField txtPassword;
    public JFXButton btnAdd;
    public JFXButton GetOTPID;
    public JFXTextField TextOTPId;
    public String OTPFinal = "";

    private final RegisterService registerService = ServiceFactory.getInstance().getServiceType(ServiceType.REGISTER);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnAdd.setDisable(true);
        int i = registerService.geLastUserId();
        txtId.setText("New ID : " + i);
    }

    public void btnAddOnAction(ActionEvent actionEvent) {
        if (isFieldEmpty(txtName, "Name") || isFieldEmpty(txtEmail, "Email") || isFieldEmpty(txtPassword, "Password")) {
            return;
        }
        if (!TextOTPId.getText().equals(OTPFinal)) {
            new Alert(Alert.AlertType.ERROR, "OTP is Wrong").show();
            return;
        }

        String key = "12345";
        BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();
        basicTextEncryptor.setPassword(key);
        String encrypt = basicTextEncryptor.encrypt(txtPassword.getText());

        Boolean b = registerService.addUser(new Users(txtName.getText(), txtEmail.getText(), encrypt));
        if (b) {
            new Alert(Alert.AlertType.INFORMATION, "Added").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Not Added").show();
            return;
        }
        Stage stageClose = (Stage) btnAdd.getScene().getWindow();
        try {
            stageClose.setScene(new Scene(FXMLLoader.load(getClass().getResource("../../view/Login_form.fxml"))));
            stageClose.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnBackOnAction(ActionEvent actionEvent) {
        Stage stageClose = (Stage) btnAdd.getScene().getWindow();
        try {
            stageClose.setScene(new Scene(FXMLLoader.load(getClass().getResource("../../view/Login_form.fxml"))));
            stageClose.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isFieldEmpty(JFXTextField field, String fieldName) {
        if (field == null || field.getText().trim().isEmpty()) {
            showAlert("Error", fieldName + " cannot be empty.");
            return true;
        }
        return false;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void btnGetOTPOnAction(ActionEvent actionEvent) {
        String recipientEmail = txtEmail.getText();
        sendEmailWithLoading(recipientEmail);
    }

    private void sendEmailWithLoading(String recipientEmail) {

        Stage loadingStage = new Stage();
        ProgressIndicator progressIndicator = new ProgressIndicator();
        StackPane loadingRoot = new StackPane(progressIndicator);
        Scene loadingScene = new Scene(loadingRoot, 100, 100);
        loadingStage.setScene(loadingScene);
        loadingStage.setTitle("Sending Email...");
        loadingStage.show();


        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Properties properties = new Properties();
                    properties.put("mail.smtp.auth", "true");
                    properties.put("mail.smtp.starttls.enable", "true");
                    properties.put("mail.smtp.host", "smtp.gmail.com");
                    properties.put("mail.smtp.port", "587");

                    String myEmail = "bruno12mendis740cj30x@gmail.com";
                    String password = "yrkd xity mqmv hktk";

                    Session session = Session.getInstance(properties, new Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(myEmail, password);
                        }
                    });

                    OTPFinal = generateOTP();
                    Message message = prepareMessage(session, myEmail, recipientEmail, OTPFinal);
                    if (message != null) {
                        Transport.send(message);
                        Platform.runLater(() -> {
                            new Alert(Alert.AlertType.INFORMATION, "Send Email Successfully").show();
                            btnAdd.setDisable(false);
                        });
                    } else {
                        Platform.runLater(() -> {
                            new Alert(Alert.AlertType.ERROR, "Please Try Again").show();
                        });
                        OTPFinal = "";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Platform.runLater(() -> {
                        new Alert(Alert.AlertType.ERROR, "Email Sending Failed!").show();
                    });
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

    private Message prepareMessage(Session session, String myEmail, String recipientEmail, String OTP) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myEmail));
            message.setRecipients(Message.RecipientType.TO, new InternetAddress[]{
                    new InternetAddress(recipientEmail)
            });
            message.setSubject("Messages");
            message.setText(OTP);
            return message;
        } catch (Exception e) {
            Logger.getLogger(registerFormController.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    private String generateOTP() {
        String otp = "";
        Random rand = new Random();
        for (int i = 0; i < 6; i++) {
            otp += rand.nextInt(10);
        }
        return otp;
    }
}

package controller.Login;

import controller.ForgotPassword.ForgetPasswordController;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXButton;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import model.Users;
import org.jasypt.util.text.BasicTextEncryptor;
import service.ServiceFactory;
import service.custom.LoginService;
import util.ServiceType;
import util.StageUtils;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class LoginFormController implements Initializable {

    public Button btnLogin;
    public JFXTextField txtEmail;
    public JFXTextField txtPassword;
    public JFXButton btnAdd;
    public Label lblDate;
    public Label LblTime;
    public String OTPFinal = "";

    private final LoginService loginService = ServiceFactory.getInstance().getServiceType(ServiceType.LOGIN);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        if (lblDate != null) {
            lblDate.setText(f.format(date));
        }
        startLiveTime(() -> {
            String liveTime = LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss a"));
            if (LblTime != null) {
                LblTime.setText(liveTime);
            }
        });
    }

    public void btnLoginOnAction(ActionEvent actionEvent) throws IOException {
        if (isFieldEmpty(txtEmail, "Email") || isFieldEmpty(txtPassword, "Password")) {
            return;
        }
        ArrayList<Users> allUser = loginService.getUser(txtEmail.getText());

        for (Users user : allUser) {
            String key = "12345";
            BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();
            basicTextEncryptor.setPassword(key);
            String decrypt = basicTextEncryptor.decrypt(user.getPassword());
            if (txtPassword.getText().equals(decrypt)) {
                new Alert(Alert.AlertType.INFORMATION, "Correct").show();
                Stage stageClose = (Stage) btnLogin.getScene().getWindow();

                try {
                    Node dashboardForm = FXMLLoader.load(getClass().getResource("/view/DashBoard.fxml"));
                    dashboardForm.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, #f5f7fa, #124087);");

                    Stage dashboardStage = new Stage();
                    StageUtils.configureDecoratedStage(dashboardStage, dashboardForm, "Dashboard", 600, 580);
                    stageClose.close();
                    dashboardStage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return;
            }
        }
        new Alert(Alert.AlertType.INFORMATION, "Wrong").show();
    }

    public void btnAddOnAction(ActionEvent actionEvent) {
        Stage stageClose = (Stage) btnLogin.getScene().getWindow();
        try {
            stageClose.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/Register_form.fxml"))));
            stageClose.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void startLiveTime(Runnable timeUpdateCallback) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> timeUpdateCallback.run()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    // This method is triggered when the user clicks "Forgot Password"
    public void ForgotPasswordOnAction(ActionEvent actionEvent) {
        if (isFieldEmpty(txtEmail, "Email")) {
            return;
        }
        String recipientEmail = txtEmail.getText();
        if (!isValidEmail(recipientEmail)) {
            new Alert(Alert.AlertType.ERROR, "Invalid Email Address!").show();
            return;
        }
        // Send OTP email asynchronously
        sendEmailWithLoading(recipientEmail, result -> {
            if (result) {
                Platform.runLater(() -> {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ForgetPassword.fxml"));
                        Parent root = loader.load();
                        // Retrieve the controller for the forgot password view.
                        ForgetPasswordController fpController = loader.getController();
                        // Pass the generated OTP and email to the new controller.
                        fpController.initData(OTPFinal, txtEmail.getText());
                        Stage stageF = new Stage();
                        stageF.setScene(new Scene(root));
                        stageF.setTitle("Forgot Password");
                        stageF.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        });
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

    /**
     * Asynchronously sends an email while showing a loading animation.
     * The callback is invoked with true if the email was sent successfully, false otherwise.
     */
    private void sendEmailWithLoading(String recipientEmail, Consumer<Boolean> callback) {
        Stage loadingStage = new Stage();
        javafx.scene.control.ProgressIndicator progressIndicator = new javafx.scene.control.ProgressIndicator();
        StackPane loadingRoot = new StackPane(progressIndicator);
        Scene loadingScene = new Scene(loadingRoot, 120, 120);
        loadingStage.setScene(loadingScene);
        loadingStage.setTitle("Sending Email...");
        loadingStage.show();

        Task<Boolean> emailTask = new Task<Boolean>() {
            @Override
            protected Boolean call() {
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
                        });
                        return true;
                    } else {
                        Platform.runLater(() -> {
                            new Alert(Alert.AlertType.ERROR, "Please Try Again").show();
                        });
                        OTPFinal = "";
                        return false;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Platform.runLater(() -> {
                        new Alert(Alert.AlertType.ERROR, "Email Sending Failed!").show();
                    });
                    return false;
                }
            }
        };

        emailTask.setOnSucceeded(event -> {
            loadingStage.close();
            callback.accept(emailTask.getValue());
        });
        emailTask.setOnFailed(event -> {
            loadingStage.close();
            callback.accept(false);
        });

        Thread thread = new Thread(emailTask);
        thread.setDaemon(true);
        thread.start();
    }

    private Message prepareMessage(Session session, String myEmail, String recipientEmail, String OTP) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Password Reset OTP");
            message.setText("Your OTP is: " + OTP);
            return message;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String generateOTP() {
        StringBuilder otp = new StringBuilder();
        Random rand = new Random();
        for (int i = 0; i < 6; i++) {
            otp.append(rand.nextInt(10));
        }
        return otp.toString();
    }

    // Simple regex-based email validator.
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(emailRegex);
    }
}

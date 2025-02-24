package controller.ForgotPassword;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.jasypt.util.text.BasicTextEncryptor;
import service.ServiceFactory;
import service.custom.LoginService;
import util.ServiceType;

import java.net.URL;
import java.util.ResourceBundle;

public class ForgetPasswordController implements Initializable {

    public JFXButton AddnewPasswordId;
    @FXML
    private TextField txtOTP;

    @FXML
    private PasswordField txtNewPassword;

    @FXML
    private Button btnSetNewPassword;

    private String expectedOTP;
    private String email;


    private final LoginService loginService = ServiceFactory.getInstance().getServiceType(ServiceType.LOGIN);


    public void initData(String otp, String email) {

        this.expectedOTP = otp;
        this.email = email;
    }

    @FXML
    public void handleSetNewPassword(ActionEvent event) {
        String enteredOTP = txtOTP.getText();
        if (enteredOTP == null || enteredOTP.trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please enter the OTP.").show();
            return;
        }
        if (!expectedOTP.equals(enteredOTP)) {
            new Alert(Alert.AlertType.ERROR, "Invalid OTP!").show();
            return;
        }

        txtNewPassword.setDisable(false);
        AddnewPasswordId.setDisable(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtNewPassword.setDisable(true);
        AddnewPasswordId.setDisable(true);
    }

    public void AddnewPasswordOnAction(ActionEvent actionEvent) {
        String newPassword = txtNewPassword.getText();
        if (newPassword == null || newPassword.trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "New password cannot be empty!").show();
            return;
        }

        String key = "12345";
        BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();
        basicTextEncryptor.setPassword(key);
        String encryptedPassword = basicTextEncryptor.encrypt(newPassword);

        boolean success = loginService.isInsertNewPassword(email, encryptedPassword);
        if (success) {
            new Alert(Alert.AlertType.INFORMATION, "Password updated successfully!").show();

            btnSetNewPassword.getScene().getWindow().hide();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to update password. Please try again.").show();
        }
    }


}

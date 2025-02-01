package controller.Login;

import util.ServiceType;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Users;
import org.jasypt.util.text.BasicTextEncryptor;
import service.ServiceFactory;
import service.custom.LoginService;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class LoginFormController implements Initializable {

    public Button btnLogin;
    public JFXTextField txtEmail;
    public JFXTextField txtPassword;
    public JFXButton btnAdd;
    public Label lblDate;
    public Label LblTime;

    private final LoginService loginService = ServiceFactory.getInstance().getServiceType(ServiceType.LOGIN);

    public void btnLoginOnAction(ActionEvent actionEvent) throws IOException {


        List<Users> allUser = loginService.getUser(txtEmail.getText());


        for (Users user : allUser) {
            String key = "12345";
            BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();
            basicTextEncryptor.setPassword(key);
            String decrypt = basicTextEncryptor.decrypt(user.getPassword());
            if (txtPassword.getText().equals(decrypt)) {
                new Alert(Alert.AlertType.INFORMATION, "Correct").show();
                Stage stageClose = (Stage) btnLogin.getScene().getWindow();


                try {
                    stageClose.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/DashBoard.fxml"))));
                    stageClose.show();
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(f.format(date));
        startLiveTime(() -> {
            String liveTime = LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss a"));
            LblTime.setText(liveTime);
        });
    }

    public void startLiveTime(Runnable timeUpdateCallback) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a");

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            String currentTime = LocalTime.now().format(timeFormatter);
            timeUpdateCallback.run();
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

}

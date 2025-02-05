package controller.register;

import util.ServiceType;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.Users;
import org.jasypt.util.text.BasicTextEncryptor;
import service.ServiceFactory;
import service.custom.RegisterService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class registerFormController implements Initializable {

    public JFXTextField txtId;
    public JFXTextField txtName;
    public JFXTextField txtEmail;
    public JFXTextField txtPassword;
    public JFXButton btnAdd;

    private final RegisterService registerService= ServiceFactory.getInstance().getServiceType(ServiceType.REGISTER);

    public void btnAddOnAction(ActionEvent actionEvent) {
        String key="12345";
        BasicTextEncryptor basicTextEncryptor=new BasicTextEncryptor();
        basicTextEncryptor.setPassword(key);
        String encrypt = basicTextEncryptor.encrypt(txtPassword.getText());

        Boolean b = registerService.addUser(new Users(txtName.getText(), txtEmail.getText(), encrypt));
        if (b){
            new Alert(Alert.AlertType.INFORMATION,"Added");
        }else{
            new Alert(Alert.AlertType.ERROR,"Now Added");
            return;
        }
        Stage stageClose =(Stage) btnAdd.getScene().getWindow() ;

        try {
            stageClose.setScene(new Scene(FXMLLoader.load(getClass().getResource("../../view/Login_form.fxml"))));
            stageClose.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int i = registerService.geLastUserId();
        txtId.setText("New ID : "+i);
    }
}

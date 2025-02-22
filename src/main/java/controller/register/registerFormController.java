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

    private final RegisterService registerService= ServiceFactory.getInstance().getServiceType(ServiceType.REGISTER);
    public JFXButton GetOTPID;

    public void btnAddOnAction(ActionEvent actionEvent) {
        if (isFieldEmpty(txtName, "Name") || isFieldEmpty(txtEmail, "Email") || isFieldEmpty(txtPassword, "Password")) {
            return;
        }

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

    public void btnBackOnAction(ActionEvent actionEvent) {
        Stage stageClose =(Stage) btnAdd.getScene().getWindow() ;
        try {
            stageClose.setScene(new Scene(FXMLLoader.load(getClass().getResource("../../view/Login_form.fxml"))));
            stageClose.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


//

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

    public void btnGetOTPOnAction(ActionEvent actionEvent) throws MessagingException {
        String recipientEmail=txtEmail.getText();
        sendEmail(recipientEmail);
    }

    private void sendEmail(String recipientEmail) throws MessagingException {
        Properties properties = new Properties();

        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        String myEmail = "bruno12mendis740cj30x@gmail.com";
        String password = "yrkd xity mqmv hktk";


        Session session=Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myEmail,password);
            }
        });
        Message message=prepareMessage(session,myEmail,recipientEmail,generateOTP());
        if (message!=null){
            new Alert(Alert.AlertType.INFORMATION,"Send Email Successfully").show();
        }else{
            new Alert(Alert.AlertType.ERROR,"Please Try Again").show();
        }
        Transport.send(message);
    }

    private Message prepareMessage(Session session, String myEmail, String recipientEmail, String OTP) {
        try{
    Message message=new MimeMessage(session);
    message.setFrom(new InternetAddress(myEmail));
    message.setRecipients(Message.RecipientType.TO,new InternetAddress[]{
            new InternetAddress(recipientEmail)
    });
    message.setSubject("Messages");
    message.setText(OTP);
    return message;
        }catch (Exception e){
            Logger.getLogger(registerFormController.class.getName()).log(Level.SEVERE,null,e);
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

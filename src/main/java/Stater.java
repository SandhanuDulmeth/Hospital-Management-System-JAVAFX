import javafx.application.Application;
import javafx.fxml.FXMLLoader;

import javafx.scene.Node;

import javafx.stage.Stage;
import javafx.stage.StageStyle;

import util.StageUtils;


public class Stater extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        stage.initStyle(StageStyle.TRANSPARENT);

        Node loginForm = FXMLLoader.load(getClass().getResource("view/Login_form.fxml"));
        loginForm.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, #f5f7fa, #124087);");

        StageUtils.configureDecoratedStage(stage, loginForm, "Login", 800, 500);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
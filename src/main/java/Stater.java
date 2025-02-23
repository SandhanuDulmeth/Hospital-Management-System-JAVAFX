import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.effect.DropShadow;

import java.awt.*;

public class Stater extends Application {
    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage stage) throws Exception {
        // Remove default window decorations and make the stage transparent
        stage.initStyle(StageStyle.TRANSPARENT);

        // Create custom title bar
        HBox titleBar = new HBox(5);
        titleBar.setPadding(new Insets(5));
        titleBar.setStyle("-fx-background-color: linear-gradient(to right, #34495e, #2c3e50);");

        // Custom buttons
        Button minimizeButton = new Button("🗕");
        minimizeButton.setOnAction(e -> stage.setIconified(true));
        minimizeButton.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");

        Button maximizeButton = new Button("🗖");
        maximizeButton.setOnAction(e -> stage.setMaximized(!stage.isMaximized()));
        maximizeButton.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");

        Button closeButton = new Button("✕");
        closeButton.setOnAction(e -> stage.close());
        closeButton.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");

        titleBar.getChildren().addAll(minimizeButton, maximizeButton, closeButton);

        // Implement dragging for the title bar
        titleBar.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        titleBar.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });

        // Load the FXML content
        Node loginForm = FXMLLoader.load(getClass().getResource("view/Login_form.fxml"));
        loginForm.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, #f5f7fa, #124087);");

        // Create a root BorderPane and add title bar and content
        BorderPane root = new BorderPane();
        root.setTop(titleBar);
        root.setCenter(loginForm);

        // Create a Pane with rounded corners
        Pane roundedPane = new Pane();
        roundedPane.setStyle("-fx-background-color: white; -fx-background-radius: 20; -fx-border-radius: 20;");
        roundedPane.setPadding(new Insets(10));
        roundedPane.getChildren().add(root);

        // Apply a clip to the Pane to ensure rounded corners
        Rectangle clip = new Rectangle();
        clip.setArcWidth(20);
        clip.setArcHeight(20);
        roundedPane.setClip(clip);

        // Bind the clip size to the Pane size
        clip.widthProperty().bind(roundedPane.widthProperty());
        clip.heightProperty().bind(roundedPane.heightProperty());

        // Add a drop shadow effect
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(10);
        dropShadow.setOffsetX(0);
        dropShadow.setOffsetY(0);
        dropShadow.setColor(Color.color(0, 0, 0, 0.5));
        roundedPane.setEffect(dropShadow);

        // Create a Scene with a transparent fill
        Scene scene = new Scene(roundedPane, 800, 500);
        scene.setFill(Color.TRANSPARENT);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}


package util;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.geometry.Insets;

public class StageUtils {

    public static void configureDecoratedStage(Stage stage, Node content, String title, double width, double height) {
        // Create custom title bar
        HBox titleBar = createTitleBar(stage);

        // Setup BorderPane
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(titleBar);
        borderPane.setCenter(content);

        // Create rounded pane with shadow
        Pane roundedPane = createRoundedPane(borderPane);

        // Scene setup
        Scene scene = new Scene(roundedPane, width, height);
        scene.setFill(Color.TRANSPARENT);
        scene.getStylesheets().add("/css/styles.css"); // Optional CSS

        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.setTitle(title);
    }

    private static HBox createTitleBar(Stage stage) {
        HBox titleBar = new HBox(5);
        titleBar.setPadding(new Insets(5));
        titleBar.setStyle("-fx-background-color: linear-gradient(to right, #34495e, #2c3e50);");


        Button minimizeButton = createControlButton3("🔴");
        minimizeButton.setOnAction(e -> stage.setIconified(true));

        Button closeButton = createControlButton1("🔴");
        closeButton.setOnAction(e -> stage.close());

        titleBar.getChildren().addAll(minimizeButton, closeButton);


        final double[] xOffset = new double[1];
        final double[] yOffset = new double[1];

        titleBar.setOnMousePressed(event -> {
            xOffset[0] = event.getSceneX();
            yOffset[0] = event.getSceneY();
        });

        titleBar.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset[0]);
            stage.setY(event.getScreenY() - yOffset[0]);
        });

        return titleBar;
    }


    private static Button createControlButton1(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: transparent; -fx-text-fill: red; -fx-font-size: 15px;");
        return button;
    }

    private static Button createControlButton3(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: transparent; -fx-text-fill: green; -fx-font-size: 15px;");
        return button;
    }

    private static Pane createRoundedPane(BorderPane borderPane) {
        Pane roundedPane = new Pane();
        roundedPane.setStyle("-fx-background-color: white; -fx-background-radius: 20; -fx-border-radius: 20;");
        roundedPane.setPadding(new Insets(10));
        roundedPane.getChildren().add(borderPane);


        Rectangle clip = new Rectangle();
        clip.setArcWidth(20);
        clip.setArcHeight(20);
        roundedPane.setClip(clip);
        clip.widthProperty().bind(roundedPane.widthProperty());
        clip.heightProperty().bind(roundedPane.heightProperty());


        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(10);
        dropShadow.setOffsetX(0);
        dropShadow.setOffsetY(0);
        dropShadow.setColor(Color.color(0, 0, 0, 0.5));
        roundedPane.setEffect(dropShadow);

        return roundedPane;
    }
}
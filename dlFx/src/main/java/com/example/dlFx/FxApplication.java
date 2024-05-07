package com.example.dlFx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class FxApplication extends Application {

    private Stage primaryStage;

    private double x = 0;
    private double y = 0;

    @Override
    public void start(Stage primaryStage) throws IOException {

        this.primaryStage = primaryStage;
        showFXMLDocument();
    }

    public void showFXMLDocument() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
            Scene scene = new Scene(root);

            // Этот и следующий методы позволяют перемещать окно приложения на экране
            root.setOnMousePressed((MouseEvent event) -> {

                x = event.getSceneX();
                y = event.getSceneY();
            });

            root.setOnMouseDragged((MouseEvent event) -> {

                primaryStage.setX(event.getScreenX() - x);
                primaryStage.setY(event.getScreenY() - y);
            });
// --------------------------------------------------------------------------------------

            primaryStage.setResizable(false);
            primaryStage.initStyle(StageStyle.TRANSPARENT);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showFXMLDocument2() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument2.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();

            // Этот и следующий методы позволяют перемещать окно приложения на экране
            root.setOnMousePressed((MouseEvent event) -> {

                x = event.getSceneX();
                y = event.getSceneY();
            });

            root.setOnMouseDragged((MouseEvent event) -> {

                stage.setX(event.getScreenX() - x);
                stage.setY(event.getScreenY() - y);
            });
// --------------------------------------------------------------------------------------

            stage.setResizable(false);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        launch();
    }
}

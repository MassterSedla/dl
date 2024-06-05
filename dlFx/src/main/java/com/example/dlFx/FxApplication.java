package com.example.dlFx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.Getter;

import java.io.IOException;

/**
 * Главный класс JavaFX приложения.
 */
public class FxApplication extends Application {

    private Stage primaryStage;
    @Getter
    private static FXMLLoader fxmlLoader;

    private double x = 0;
    private double y = 0;

    /**
     * Запускает JavaFX приложение и показывает начальную сцену.
     *
     * @param primaryStage первичная сцена.
     * @throws IOException если происходит ошибка ввода-вывода.
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        showFXMLDocument();
    }

    /**
     * Показывает начальную сцену.
     */
    public void showFXMLDocument() {
        try {
            fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("FXMLDocument.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);

            // Позволяет перемещать окно приложения на экране
            root.setOnMousePressed((MouseEvent event) -> {
                x = event.getSceneX();
                y = event.getSceneY();
            });

            root.setOnMouseDragged((MouseEvent event) -> {
                primaryStage.setX(event.getScreenX() - x);
                primaryStage.setY(event.getScreenY() - y);
            });

            // Применяем стили для закругленных углов
            scene.getStylesheets().add(getClass().getResource("designLogin.css").toExternalForm());

            primaryStage.setResizable(false);
            primaryStage.initStyle(StageStyle.TRANSPARENT);  // Настраиваем окно без рамки
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Показывает вторую сцену.
     *
     * @param stage сцена, на которой будет отображена вторая форма.
     */
    public void showFXMLDocument2(Stage stage) {
        try {
            fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("FXMLDocument2.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);

            // Позволяет перемещать окно приложения на экране
            root.setOnMousePressed((MouseEvent event) -> {
                x = event.getSceneX();
                y = event.getSceneY();
            });

            root.setOnMouseDragged((MouseEvent event) -> {
                stage.setX(event.getScreenX() - x);
                stage.setY(event.getScreenY() - y);
            });

            stage.setResizable(false);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Точка входа в приложение.
     *
     * @param args аргументы командной строки.
     */
    public static void main(String[] args) {
        launch();
    }
}

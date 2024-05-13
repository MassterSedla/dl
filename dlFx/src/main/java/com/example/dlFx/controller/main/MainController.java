package com.example.dlFx.controller.main;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
    private static final String FXML_PACKAGE = "/com/example/dlFx/";

    public String getFxmlPackage() {
        return FXML_PACKAGE;
    }

    public void nextPage(Button button, String pageLocation) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(FXML_PACKAGE + pageLocation));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) button.getScene().getWindow();
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}

package com.example.dlFx.controller;

import com.example.dlFx.FxApplication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class FXMLDocumentController2 implements Initializable {

    @FXML
    private AnchorPane dashboard_form;

    @FXML
    private Button dashboard_close_btn;

    @FXML
    private Button dashboard_minimize_btn;

    @FXML
    private Button dashboard_logOut_btn;

    @FXML
    private ChoiceBox<String> choiceBox_roomNumber;

    @FXML
    private ChoiceBox<String> choiceBox_switch;

    private String[] rooms = {"E.1.015.1", "F.1.001.1", "B.2.044"};

    // Закрыть окно
    public void dashboard_close() {
        System.exit(0);
    }

    // Свернуть окно
    public void dashboard_minimize() {
        Stage stage = (Stage)dashboard_form.getScene().getWindow();
        stage.setIconified(true);
    }

    // Назад на страницу авторизации
    @FXML
    private void switchToFXMLDocument() {

        dashboard_logOut_btn.getScene().getWindow().hide();
        FxApplication fxApplication = new FxApplication();
        fxApplication.showFXMLDocument();
    }

    private void handleSelectionRoomNumber(String val) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        choiceBox_roomNumber.getItems().addAll(rooms);
        choiceBox_roomNumber.valueProperty().addListener((obs, oldVal, newVal) -> handleSelectionRoomNumber(newVal));
    }
}

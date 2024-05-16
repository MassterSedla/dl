package com.example.dlFx.controller;

import com.example.dlFx.dto.MainPageDto;
import com.example.dlFx.httpRequests.HttpRequests;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.SneakyThrows;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
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
    private Button vacate_occupy_btn;

    @FXML
    private ChoiceBox<String> choiceBox_roomNumber;

    @FXML
    private ChoiceBox<String> choiceBox_switch;

    private String[] rooms = {"E.1.015.1", "F.1.001.1", "B.2.044"};

    private FXMLLoader fxmlLoader;

    // Закрыть окно
    public void dashboard_close() {
        System.exit(0);
    }

    // Свернуть окно
    public void dashboard_minimize() {
        Stage stage = (Stage)dashboard_form.getScene().getWindow();
        stage.setIconified(true);
    }

//     Назад на страницу авторизации
    @FXML
    private void switchToFXMLDocument() {

        dashboard_logOut_btn.getScene().getWindow().hide();
        //FxApplication fxApplication = new FxApplication();
        //fxApplication.showFXMLDocument();
    }

    @FXML
    public void vacate_occupy_btnClicked() throws IOException {

        fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/com/example/dlFx/DialogPane.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);

        // Создание DialogPane
        DialogPane dialogPane = new DialogPane();

        // Создание нового окна
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);

        // Установка DialogPane в качестве содержимого окна
        dialogStage.setScene(scene);
        dialogStage.show();


        //stage.setResizable(false);
        //stage.centerOnScreen();
    }


    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        JsonNode node = HttpRequests.GetRequest("api/page");
        MainPageDto mainPageDto = new ObjectMapper().treeToValue(node, MainPageDto.class);
        mainPageDto.getList().replaceAll(s -> s.replace(",", "  f"));
        choiceBox_roomNumber.getItems().setAll(mainPageDto.getList());
        choiceBox_roomNumber.valueProperty().addListener((obs, oldVal, newVal) -> handleSelectionBuilding(newVal));
    }

    @SneakyThrows
    private void handleSelectionBuilding(String val) {
        JsonNode node = HttpRequests.GetRequest("api/page/" + val.replace("  f", "/"));
        MainPageDto mainPageDto = new ObjectMapper().treeToValue(node, MainPageDto.class);
        choiceBox_switch.getItems().clear();
        choiceBox_switch.getItems().setAll(mainPageDto.getList());
        choiceBox_roomNumber.valueProperty().addListener((obs, oldVal, newVal) -> handleSelectionRoomNumber(newVal));
    }

    private void handleSelectionRoomNumber(String newVal) {

    }
}

package com.example.dlFx.controller;

import com.example.dlFx.FxApplication;
import com.example.dlFx.dto.MainPageDto;
import com.example.dlFx.httpRequests.HttpRequests;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import lombok.SneakyThrows;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

public class DialogPaneController implements Initializable  {

    @FXML
    private ChoiceBox<String> choiceBox_equipmentType;

    @FXML
    private ChoiceBox<String> choiceBox_equipment;

    @FXML
    private Button button_apply;

    private final String url = "api/dialogPage";


    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        JsonNode node = HttpRequests.GetRequest(this.url);
        MainPageDto mainPageDto = new ObjectMapper().treeToValue(node, MainPageDto.class);
        choiceBox_equipmentType.getItems().setAll(mainPageDto.getList());
        choiceBox_equipmentType.valueProperty().addListener((obs, oldVal, newVal) -> handleSelectionType(newVal));
        choiceBox_equipment.valueProperty().addListener((obs, oldVal, newVal) -> handleSelectionEquipment(newVal));
    }

    @SneakyThrows
    private void handleSelectionType(String newVal) {
        choiceBox_equipment.getItems().clear();
        if (newVal != null) {
            JsonNode node = HttpRequests.GetRequest(url + "/" + newVal);
            MainPageDto mainPageDto = new ObjectMapper().treeToValue(node, MainPageDto.class);
            choiceBox_equipment.getItems().setAll(mainPageDto.getList());
        }
    }

    private void handleSelectionEquipment(String newVal) {
        if (newVal != null) {
            button_apply.setOnAction(event -> {
                try {
                    applyAction(newVal);
                } catch (IOException | URISyntaxException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });

        }
    }

    private void applyAction(String newVal) throws IOException, URISyntaxException, InterruptedException {
        JsonNode node = HttpRequests.GetRequest(url + "/" + choiceBox_equipmentType.getValue() + "/" + newVal);
        MainPageDto mainPageDto = new ObjectMapper().treeToValue(node, MainPageDto.class);
        int equipmentId = Integer.parseInt(mainPageDto.getList().get(0));
        FXMLDocumentController2 controller2 = FxApplication.getFxmlLoader().getController();
        controller2.getFunctionHandleSelectionSwitchNumber(equipmentId);
        Stage stage = (Stage) button_apply.getScene().getWindow();
        stage.close();
    }

}

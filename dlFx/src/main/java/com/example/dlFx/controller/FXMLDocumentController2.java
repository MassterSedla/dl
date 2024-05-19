package com.example.dlFx.controller;

import com.example.dlFx.dto.MainPageDto;
import com.example.dlFx.httpRequests.HttpRequests;
import com.example.dlFx.model.EquipmentWithPort;
import com.example.dlFx.model.Switch;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;

import java.io.IOException;
import java.io.PipedOutputStream;
import java.net.URL;
import java.util.ResourceBundle;

import static de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon.FORT_AWESOME;

public class FXMLDocumentController2 implements Initializable {


    @FXML
    public Label label_occupiedPorts;

    @FXML
    public Label label_availablePorts;

    @FXML
    public Label label_trafficLoad;

    @FXML
    public Label label_powerLoad;

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
    private ChoiceBox<String> choiceBox_building;

    @FXML
    private ChoiceBox<String> choiceBox_roomNumber;

    @FXML
    private ChoiceBox<String> choiceBox_switch;

    @FXML
    private TableView<EquipmentWithPort> switchTable;

    @FXML
    private TableColumn<EquipmentWithPort, Integer> tableColumn_port;

    @FXML
    private TableColumn<EquipmentWithPort, String> tableColumn_type;

    @FXML
    private TableColumn<EquipmentWithPort, Integer> tableColumn_trafficLoad;

    @FXML
    private TableColumn<EquipmentWithPort, Integer> tableColumn_powerLoad;

    @FXML
    private TableColumn<EquipmentWithPort, String> tableColumn_comment;

    @FXML
    private AnchorPane anchorPane_ports;


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
        choiceBox_building.getItems().setAll(mainPageDto.getList());
        choiceBox_building.valueProperty().addListener((obs, oldVal, newVal) -> handleSelectionBuilding(newVal));
        choiceBox_roomNumber.valueProperty().addListener((obs, oldVal, newVal) -> handleSelectionRoomNumber(choiceBox_building.getValue(), newVal));
        choiceBox_switch.valueProperty().addListener((obs, oldVal, switchNumber) ->
                handleSelectionSwitchNumber(choiceBox_building.getValue(), choiceBox_roomNumber.getValue(), switchNumber));
    }

    @SneakyThrows
    private void handleSelectionBuilding(String val) {
        choiceBox_roomNumber.getItems().clear();
        if (val != null) {
            JsonNode node = HttpRequests.GetRequest("api/page/" + val);
            MainPageDto mainPageDto = new ObjectMapper().treeToValue(node, MainPageDto.class);
            choiceBox_roomNumber.getItems().setAll(mainPageDto.getList());
        }
    }

    @SneakyThrows
    private void handleSelectionRoomNumber(String val, String newVal) {
        choiceBox_switch.getItems().clear();
        if (newVal != null) {
            JsonNode node = HttpRequests.GetRequest("api/page/" + val + "/" + newVal);
            MainPageDto mainPageDto = new ObjectMapper().treeToValue(node, MainPageDto.class);
            choiceBox_switch.getItems().setAll(mainPageDto.getList());
        }
    }

    @SneakyThrows
    private void handleSelectionSwitchNumber(String val, String newVal, String switchNumber) {
        switchTable.getItems().clear();
        label_availablePorts.setText("");
        label_occupiedPorts.setText("");
        label_trafficLoad.setText("");
        label_powerLoad.setText("");
        if (switchNumber != null) {
            JsonNode node = HttpRequests.GetRequest("api/page/" + val + "/" + newVal + "/" + switchNumber);
            Switch aSwitch = new ObjectMapper().treeToValue(node, Switch.class);

            int countPort = aSwitch.getNumberOfPort();
            Button[] buttons = new Button[countPort];
            Label[] labels = new Label[countPort];
            int k = 0;
            int distance = 43;
            for (int i = 0; i < countPort / 12; i++) {
                for (int j = 0; j < 12; j++) {
                    Button button = new Button();
                    button.setLayoutX(15.0 + j * 65);
                    button.setLayoutY(14.0 + i * 80);
                    button.setPrefWidth(65.0);
                    button.setPrefHeight(39.0);
                    button.setMnemonicParsing(false);
                    button.setGraphic(new FontAwesomeIconView(FORT_AWESOME, String.valueOf(30)));
                    Label label = new Label();
                    if (k == 10) {
                        distance = 40;
                    }
                    label.setLayoutX(distance + j * 65);
                    label.setLayoutY(53.0 + i * 80);
                    label.setFont(new Font("Ayuthaya", 14.0));
                    label.setText(String.valueOf(k + 1));
                    button.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            System.out.println(label.getText());
                        }
                    });
                    buttons[k] = button;
                    labels[k] = label;
                    k++;
                }
            }
            anchorPane_ports.getChildren().addAll(buttons);
            anchorPane_ports.getChildren().addAll(labels);

            tableColumn_port.setCellValueFactory(new PropertyValueFactory<>("port"));
            tableColumn_type.setCellValueFactory(new PropertyValueFactory<>("type"));
            tableColumn_trafficLoad.setCellValueFactory(new PropertyValueFactory<>("equipmentTrafficLoad"));
            tableColumn_powerLoad.setCellValueFactory(new PropertyValueFactory<>("equipmentPowerLoad"));
            tableColumn_comment.setCellValueFactory(new PropertyValueFactory<>("comment"));
            switchTable.getItems().addAll(aSwitch.getEquipments());

            label_availablePorts.setText(aSwitch.getAvailablePorts());
            label_occupiedPorts.setText(aSwitch.getOccupiedPorts());
            label_trafficLoad.setText(aSwitch.getTrafficLoad());
            label_powerLoad.setText(aSwitch.getPowerLoad());
        }
    }
}

package com.example.dlFx.controller;

import com.example.dlFx.dto.CommentDto;
import com.example.dlFx.dto.MainPageDto;
import com.example.dlFx.httpRequests.HttpRequests;
import com.example.dlFx.model.EquipmentWithPort;
import com.example.dlFx.model.Switch;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import lombok.Getter;
import lombok.SneakyThrows;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon.FORT_AWESOME;

public class FXMLDocumentController2 implements Initializable {
    @FXML
    private Label label_occupiedPorts;

    @FXML
    private Label label_availablePorts;

    @FXML
    private Label label_trafficLoad;

    @FXML
    private Label label_powerLoad;

    @FXML
    private AnchorPane dashboard_form;

    @FXML
    private AnchorPane dashboard_form_greeting;

    @FXML
    private AnchorPane dashboard_form_info;

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

    @FXML
    private ScrollPane scrollPane_ports;

    private int selectedPort;
    private Long selectedSwitchId;

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

    // Назад на страницу авторизации
    @FXML
    private void switchToFXMLDocument() {

        dashboard_logOut_btn.getScene().getWindow().hide();
        //FxApplication fxApplication = new FxApplication();
        //fxApplication.showFXMLDocument();
    }

    @FXML
    public void openDialogPage() throws IOException {
        fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/com/example/dlFx/DialogPane.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.setScene(scene);
        dialogStage.show();
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
        cleanInformationAboutSwitch();
        if (switchNumber != null) {
            fillingInformationAboutSwitch("api/page/" + val + "/" + newVal + "/" + switchNumber);
        }
    }

    @SneakyThrows
    private void fillingInformationAboutSwitch(String url) {
        JsonNode node = HttpRequests.GetRequest(url);
        Switch aSwitch = new ObjectMapper().treeToValue(node, Switch.class);
        List<Integer> portList = aSwitch.getEquipments().stream().filter(e -> e.getId() != -1)
                .mapToInt(EquipmentWithPort::getPort).boxed().toList();
        int countPort = aSwitch.getNumberOfPort();
        Button[] buttons = new Button[countPort];
        Label[] labels = new Label[countPort];
        int k = 0;
        int distance = 37;
        for (int i = 0; i < countPort / 12; i++) {
            for (int j = 0; j < 12; j++) {
                Button button = new Button();
                button.setLayoutX(10 + j * 64);
                button.setLayoutY(14.0 + i * 80);
                button.setPrefWidth(63.0);
                button.setPrefHeight(39.0);
                button.setMnemonicParsing(false);
                button.setGraphic(new FontAwesomeIconView(FORT_AWESOME, String.valueOf(30)));
                Label label = new Label();
                if (k == 10) {
                    distance = 34;
                }
                label.setLayoutX(distance + j * 64);
                label.setLayoutY(53.0 + i * 80);
                label.setFont(new Font("Ayuthaya", 14.0));
                label.setText(String.valueOf(k + 1));
                ContextMenu contextMenu = new ContextMenu();
                MenuItem menuItem3 = new MenuItem("Comment");
                menuItem3.setOnAction(event -> makeComment(aSwitch.getId(), Integer.parseInt(label.getText()), url));
                if (portList.contains(k + 1)) {
                    MenuItem menuItem1 = new MenuItem("Show");
                    MenuItem menuItem2 = new MenuItem("Release");
                    contextMenu.getItems().addAll(menuItem1, menuItem2, menuItem3);
                    menuItem1.setOnAction(event -> selectCellByValue(Integer.parseInt(label.getText())));
                    menuItem2.setOnAction(event -> releasePort(aSwitch.getId(), Integer.parseInt(label.getText()), url));
                    button.getStyleClass().add("port-btn-occupied");
                } else {
                    MenuItem menuItem4 = new MenuItem("Occupy");
                    contextMenu.getItems().addAll(menuItem3, menuItem4);
                    menuItem4.setOnAction(event -> {
                        try {
                            selectedPort = Integer.parseInt(label.getText());
                            selectedSwitchId = aSwitch.getId();
                            openDialogPage();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                    button.getStyleClass().add("port-btn-available");
                }
                button.setOnAction(event -> {
                    if (!contextMenu.isShowing()) {
                        contextMenu.show(button, button.getScene().getWindow().getX() + 236.5 + button.getLayoutX(),
                                button.getScene().getWindow().getY() + 228.5 + button.getLayoutY() + button.getHeight());
                    } else {
                        contextMenu.hide();
                    }
                });
                buttons[k] = button;
                labels[k] = label;
                k++;
            }
        }
        scrollPane_ports.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        anchorPane_ports.getChildren().addAll(buttons);
        anchorPane_ports.getChildren().addAll(labels);

        label_availablePorts.setText(aSwitch.getAvailablePorts());
        label_occupiedPorts.setText(aSwitch.getOccupiedPorts());
        label_trafficLoad.setText(aSwitch.getTrafficLoad());
        label_powerLoad.setText(aSwitch.getPowerLoad());
        aSwitch.clean();

        tableColumn_port.setCellValueFactory(new PropertyValueFactory<>("port"));
        tableColumn_port.setSortType(TableColumn.SortType.ASCENDING);
        tableColumn_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        tableColumn_trafficLoad.setCellValueFactory(new PropertyValueFactory<>("equipmentTrafficLoad"));
        tableColumn_powerLoad.setCellValueFactory(new PropertyValueFactory<>("equipmentPowerLoad"));
        tableColumn_comment.setCellValueFactory(new PropertyValueFactory<>("comment"));
        switchTable.getItems().addAll(aSwitch.getEquipments());
        switchTable.getSortOrder().add(tableColumn_port);

        dashboard_form_greeting.setVisible(false);
        dashboard_form_info.setVisible(true);


    }

    private void cleanInformationAboutSwitch() {
        switchTable.getItems().clear();
        anchorPane_ports.getChildren().clear();
        label_availablePorts.setText("");
        label_occupiedPorts.setText("");
        label_trafficLoad.setText("");
        label_powerLoad.setText("");
        dashboard_form_info.setVisible(false);
        dashboard_form_greeting.setVisible(true);
    }

    private void selectCellByValue(int value) {
        for (EquipmentWithPort item : switchTable.getItems()) {
            if (tableColumn_port.getCellData(item) == value) {
                switchTable.getSelectionModel().select(item);
                switchTable.scrollTo(item);
                break;
            }
        }
    }

    @SneakyThrows
    private void releasePort(Long switchId, int port, String url) {
        HttpRequests.DeleteRequest("api/page/" + switchId + "/" + port);
        cleanInformationAboutSwitch();
        fillingInformationAboutSwitch(url);
    }

    public void getFunctionHandleSelectionSwitchNumber(int equipmentId) throws IOException, URISyntaxException, InterruptedException {
        HttpRequests.PostRequest(
                new MainPageDto(List.of(String.valueOf(equipmentId),
                        String.valueOf(selectedSwitchId), String.valueOf(selectedPort))),
                "api/occupyPort");
        handleSelectionSwitchNumber(choiceBox_building.getValue(),
                choiceBox_roomNumber.getValue(), choiceBox_switch.getValue());
    }

    @SneakyThrows
    public void makeComment(Long id, int port, String url) {
        HttpRequests.PostRequest(new CommentDto(id, port, "whdihqewuf"), "api/makeComment");
        cleanInformationAboutSwitch();
        fillingInformationAboutSwitch(url);
    }
}

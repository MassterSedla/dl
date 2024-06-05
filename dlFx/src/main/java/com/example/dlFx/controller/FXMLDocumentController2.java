package com.example.dlFx.controller;

import com.example.dlFx.dto.CommentDto;
import com.example.dlFx.dto.EquipmentDto;
import com.example.dlFx.dto.MainPageDto;
import com.example.dlFx.httpRequests.HttpRequests;
import com.example.dlFx.model.EquipmentWithPort;
import com.example.dlFx.model.Switch;
import com.example.dlFx.model.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import static de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon.FORT_AWESOME;

/**
 * Контроллер для управления основной функциональностью приложения.
 */

public class FXMLDocumentController2 implements Initializable {
    @FXML
    public Label label_ipError;

    @FXML
    public Label label_macError;

    @FXML
    public AnchorPane anchorPane_comment;

    @FXML
    public AnchorPane anchorPane_occupyPort;

    @FXML
    private DialogPane dialogPane;

    @FXML
    private ChoiceBox<String> choiceBox_equipmentType;

    @FXML
    private ChoiceBox<String> choiceBox_equipmentCompany;

    @FXML
    private ChoiceBox<String> choiceBox_equipmentModel;

    @FXML
    private Button button_apply;

    @FXML
    private Button button_cancel;

    @FXML
    private Button button_apply2;

    @FXML
    private Button button_cancel2;

    @FXML
    private Button button_delete;

    @FXML
    private TextArea text_comment;

    @FXML
    private TextField mac;

    @FXML
    private TextField ip;

    @FXML
    private Label label_username;

    @FXML
    private Label label_email;

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

    // Загрузка изображения из ресурсов
    private final Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/dlFx/ethernet.png")));

    private int selectedPort;
    private Long selectedSwitchId;
    private boolean[] flagToApply;
    private FXMLLoader fxmlLoader;

    /**
     * Закрывает окно.
     */
    public void dashboard_close() {
        System.exit(0);
    }

    /**
     * Сворачивает окно.
     */
    public void dashboard_minimize() {
        Stage stage = (Stage)dashboard_form.getScene().getWindow();
        stage.setIconified(true);
    }

    /**
     * Метод инициализации контроллера, вызывается автоматически при создании экземпляра контроллера.
     *
     * @param url URL для инициализации.
     * @param resourceBundle ресурсный бандл для инициализации.
     */
    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Получение информации о текущем пользователе
        JsonNode userNode = HttpRequests.GetRequest("user/info");
        User user = new ObjectMapper().treeToValue(userNode, User.class);
        label_username.setText(user.getName());
        label_email.setText(user.getEmail());

        // Получение списка зданий
        JsonNode node = HttpRequests.GetRequest("api/page");
        MainPageDto mainPageDto = new ObjectMapper().treeToValue(node, MainPageDto.class);
        choiceBox_building.getItems().setAll(mainPageDto.getList());

        // Добавление слушателей изменений выбора для ChoiceBox'ов
        choiceBox_building.valueProperty().addListener((obs, oldVal, newVal) -> handleSelectionBuilding(newVal));
        choiceBox_roomNumber.valueProperty().addListener((obs, oldVal, newVal) -> handleSelectionRoomNumber(choiceBox_building.getValue(), newVal));
        choiceBox_switch.valueProperty().addListener((obs, oldVal, switchNumber) ->
                handleSelectionSwitchNumber(choiceBox_building.getValue(), choiceBox_roomNumber.getValue(), switchNumber));

        // Инициализация флагов для проверки ввода
        flagToApply = new boolean[3];
        setPatterns();
    }

    /**
     * Устанавливает паттерны для проверки ввода IP и MAC адресов добавляемого оборудования.
     */
    private void setPatterns() {
        // Паттерн для проверки корректности IP адресов
        Pattern pattern = Pattern.compile("(192\\.168|10\\.0)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)");
        TextFormatter<String> formatter = new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            // Проверка введенного IP адреса с помощью паттерна
            flagToApply[0] = pattern.matcher(newText).matches();
            // Показать или скрыть ошибку валидации IP адреса
            label_ipError.setVisible(!flagToApply[0]);
            // Проверка флагов для активации кнопки "Применить"
            checkApplyFlag();
            return change;
        });
        ip.setTextFormatter(formatter);

        // Паттерн для проверки корректности MAC адресов
        Pattern pattern2 = Pattern.compile("([0-9a-f]{2}:){5}([0-9a-f]{2})");
        formatter = new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            // Проверка введенного MAC адреса с помощью паттерна
            flagToApply[1] = pattern2.matcher(newText).matches();
            // Показать или скрыть ошибку валидации MAC адреса
            label_macError.setVisible(!flagToApply[1]);
            // Проверка флагов для активации кнопки "Применить"
            checkApplyFlag();
            return change;
        });
        mac.setTextFormatter(formatter);
    }


    /**
     * Проверяет флаги валидации ввода и устанавливает состояние кнопки "Применить".
     * Если все флаги установлены в true, кнопка становится активной, иначе - неактивной.
     */
    private void checkApplyFlag() {
        button_apply.setDisable(!(flagToApply[0] && flagToApply[1] && flagToApply[2]));
    }


    /**
     * Обрабатывает выбор здания из выпадающего списка и обновляет список комнат.
     *
     * @param val выбранное здание.
     */
    @SneakyThrows
    private void handleSelectionBuilding(String val) {
        // Очистка списка комнат
        choiceBox_roomNumber.getItems().clear();

        // Проверка, что значение не null
        if (val != null) {
            // Отправка GET-запроса для получения списка комнат в выбранном здании
            JsonNode node = HttpRequests.GetRequest("api/page/" + val);
            // Преобразование ответа в объект MainPageDto
            MainPageDto mainPageDto = new ObjectMapper().treeToValue(node, MainPageDto.class);
            // Установка списка комнат в ChoiceBox
            choiceBox_roomNumber.getItems().setAll(mainPageDto.getList());
        }
    }


    /**
     * Обрабатывает выбор комнаты из выпадающего списка и обновляет список коммутаторов.
     *
     * @param val выбранное здание.
     * @param newVal выбранная комната.
     */
    @SneakyThrows
    private void handleSelectionRoomNumber(String val, String newVal) {
        // Очистка списка коммутаторов
        choiceBox_switch.getItems().clear();

        // Проверка, что значение newVal не null
        if (newVal != null) {
            // Отправка GET-запроса для получения списка коммутаторов в выбранной комнате
            JsonNode node = HttpRequests.GetRequest("api/page/" + val + "/" + newVal);
            // Преобразование ответа в объект MainPageDto
            MainPageDto mainPageDto = new ObjectMapper().treeToValue(node, MainPageDto.class);
            // Установка списка коммутаторов в ChoiceBox
            choiceBox_switch.getItems().setAll(mainPageDto.getList());
        }
    }


    /**
     * Обрабатывает выбор коммутатора из выпадающего списка и обновляет информацию о нем.
     *
     * @param val выбранное здание.
     * @param newVal выбранная комната.
     * @param switchNumber выбранный коммутатор.
     */
    @SneakyThrows
    private void handleSelectionSwitchNumber(String val, String newVal, String switchNumber) {
        // Очистка текущей информации о коммутаторе
        cleanInformationAboutSwitch();

        // Проверка, что значение switchNumber не null
        if (switchNumber != null) {
            // Заполнение информации о выбранном коммутаторе
            fillingInformationAboutSwitch("api/page/" + val + "/" + newVal + "/" + switchNumber);
        }
    }


    /**
     * Заполняет информацию о коммутаторе, включая таблицу с содержагием информацию о портах,
     * кнопки портов, их состояния и соответствующие действия.
     *
     * @param url URL для запроса информации о коммутаторе.
     */
    @SneakyThrows
    private void fillingInformationAboutSwitch(String url) {
        // Отправка GET-запроса для получения информации о коммутаторе
        JsonNode node = HttpRequests.GetRequest(url);
        Switch aSwitch = new ObjectMapper().treeToValue(node, Switch.class);

        // Получение списка портов, занятых оборудованием
        List<Integer> portList = aSwitch.getEquipments().stream()
                .filter(e -> e.getId() != -1)
                .mapToInt(EquipmentWithPort::getPort)
                .boxed()
                .toList();

        int countPort = aSwitch.getNumberOfPort();
        Button[] buttons = new Button[countPort];
        Label[] labels = new Label[countPort];

        int k = 0;
        int distance = 37;

        // Установка кнопок портов, их номеров и действий при нажатии
        for (int i = 0; i < countPort / 12; i++) {
            for (int j = 0; j < 12; j++) {
                Button button = new Button();
                button.setLayoutX(10 + j * 64);
                button.setLayoutY(14.0 + i * 80);
                button.setPrefWidth(63.0);
                button.setPrefHeight(39.0);
                button.setMnemonicParsing(false);

                // Создание ImageView с загруженным изображением
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(30);
                imageView.setFitHeight(30);
                button.setGraphic(imageView);

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
                menuItem3.setOnAction(event -> openDialogPageComment(aSwitch.getId(), Integer.parseInt(label.getText()), url));

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
                        selectedPort = Integer.parseInt(label.getText());
                        selectedSwitchId = aSwitch.getId();
                        openDialogPageOccupyPort();
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

        // Заполнение информации о коммутаторе
        label_availablePorts.setText(aSwitch.getAvailablePorts());
        label_occupiedPorts.setText(aSwitch.getOccupiedPorts());
        label_trafficLoad.setText(aSwitch.getTrafficLoad());
        label_powerLoad.setText(aSwitch.getPowerLoad());
        aSwitch.clean();

        // Заполнение таблицы с информацией о портах
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

    /**
     * Очищает информацию о коммутаторе и обновляет интерфейс.
     */
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

    /**
     * Выбирает ячейку в таблице по значению порта и прокручивает таблицу до этой ячейки.
     *
     * @param value значение порта, по которому нужно выбрать ячейку.
     */
    private void selectCellByValue(int value) {
        for (EquipmentWithPort item : switchTable.getItems()) {
            if (tableColumn_port.getCellData(item) == value) {
                switchTable.getSelectionModel().select(item);
                switchTable.scrollTo(item);
                break;
            }
        }
    }

    /**
     * Освобождает указанный порт на коммутаторе и обновляет информацию о коммутаторе.
     *
     * @param switchId идентификатор коммутатора.
     * @param port номер порта, который нужно освободить.
     * @param url URL для повторного запроса информации о коммутаторе.
     */
    @SneakyThrows
    private void releasePort(Long switchId, int port, String url) {
        // Отправка DELETE-запроса для освобождения порта
        HttpRequests.DeleteRequest("api/page/" + switchId + "/" + port);

        // Очистка текущей информации о коммутаторе
        cleanInformationAboutSwitch();

        // Повторное заполнение информации о коммутаторе
        fillingInformationAboutSwitch(url);
    }


    /**
     * Открывает окно для добавления или редактирования комментария к порту коммутатора.
     *
     * @param id идентификатор коммутатора.
     * @param port номер порта, к которому добавляется комментарий.
     * @param url URL для обновления информации о коммутаторе.
     */
    @SneakyThrows
    public void openDialogPageComment(Long id, int port, String url) {
        // Отображение диалогового окна и панели комментариев
        dialogPane.setVisible(true);
        anchorPane_comment.setVisible(true);

        // Отправка GET-запроса для получения текущего комментария к порту
        JsonNode node = HttpRequests.GetRequest("api/comment/" + id + "/" + port);
        MainPageDto mainPageDto = new ObjectMapper().treeToValue(node, MainPageDto.class);
        String comment = mainPageDto.getList().get(0);

        // Проверка, есть ли текущий комментарий
        if (comment == null)  {
            button_delete.setVisible(false);
            text_comment.setPromptText("");
        } else {
            button_delete.setVisible(true);
            text_comment.setPromptText("Текущий коментарий: " + comment);
            button_delete.setOnAction(event -> makeComment(id, port, "", url));
        }

        // Обработчик для кнопки "Отменить"
        button_cancel2.setOnAction(event -> closeCommentPane());

        // Обработчик для кнопки "Применить"
        button_apply2.setOnAction(event -> {
            if (text_comment.getText().isEmpty()) {
                text_comment.setText(text_comment.getPromptText().replace("Текущий коментарий: ", ""));
            }
            makeComment(id, port, text_comment.getText(), url);
        });
    }

    /**
     * Закрывает диалоговое окно комментариев и очищает текстовое поле для комментариев.
     */
    private void closeCommentPane() {
        dialogPane.setVisible(false);
        anchorPane_comment.setVisible(false);
        text_comment.clear();
    }

    /**
     * Добавляет или обновляет комментарий к порту коммутатора и обновляет информацию о коммутаторе.
     *
     * @param id идентификатор коммутатора.
     * @param port номер порта, к которому добавляется комментарий.
     * @param comment текст комментария.
     * @param url URL для повторного запроса информации о коммутаторе.
     */
    @SneakyThrows
    public void makeComment(Long id, int port, String comment, String url) {
        // Отправка POST-запроса для добавления или обновления комментария
        HttpRequests.PostRequest(new CommentDto(id, port, comment), "api/makeComment");
        // Закрытие панели комментариев
        closeCommentPane();
        // Очистка текущей информации о коммутаторе
        cleanInformationAboutSwitch();
        // Повторное заполнение информации о коммутаторе
        fillingInformationAboutSwitch(url);
    }

    /**
     * Открывает диалоговое окно для занятия порта и загружает данные для выбора оборудования.
     *
     */
    @SneakyThrows
    public void openDialogPageOccupyPort() {
        // Отображение диалогового окна и панели для занятия порта
        dialogPane.setVisible(true);
        anchorPane_occupyPort.setVisible(true);

        // Отправка GET-запроса для получения списка типов оборудования
        JsonNode node = HttpRequests.GetRequest("api/dialogPage");
        MainPageDto mainPageDto = new ObjectMapper().treeToValue(node, MainPageDto.class);

        // Установка обработчиков для кнопок отмены и выбора
        button_cancel.setOnAction(event -> {
            dialogPane.setVisible(false);
            anchorPane_occupyPort.setVisible(false);
            ip.clear();
            mac.clear();
        });
        choiceBox_equipmentType.getItems().setAll(mainPageDto.getList());
        choiceBox_equipmentType.valueProperty().addListener((obs, oldVal, newVal) -> handleSelectionType(newVal));
        choiceBox_equipmentCompany.valueProperty().addListener((obs, oldVal, newVal) -> handleSelectionCompany(newVal));
        choiceBox_equipmentModel.valueProperty().addListener((obs, oldVal, newVal) -> handleSelectionModel(newVal));
    }

    /**
     * Обрабатывает выбор типа оборудования и обновляет список компаний.
     *
     * @param newVal выбранный тип оборудования.
     */
    @SneakyThrows
    private void handleSelectionType(String newVal) {
        choiceBox_equipmentCompany.getItems().clear();
        if (newVal != null) {
            // Отправка GET-запроса для получения списка компаний по выбранному типу оборудования
            JsonNode node = HttpRequests.GetRequest("api/dialogPage/" + newVal);
            MainPageDto mainPageDto = new ObjectMapper().treeToValue(node, MainPageDto.class);
            choiceBox_equipmentCompany.getItems().setAll(mainPageDto.getList());
        }
    }

    /**
     * Обрабатывает выбор компании оборудования и обновляет список моделей.
     *
     * @param newVal выбранная компания.
     */
    @SneakyThrows
    private void handleSelectionCompany(String newVal) {
        choiceBox_equipmentModel.getItems().clear();
        flagToApply[2] = false;
        if (newVal != null) {
            // Отправка GET-запроса для получения списка моделей по выбранному типу и компании оборудования
            JsonNode node = HttpRequests.GetRequest("api/dialogPage/" + choiceBox_equipmentType.getValue() + "/" + newVal);
            MainPageDto mainPageDto = new ObjectMapper().treeToValue(node, MainPageDto.class);
            choiceBox_equipmentModel.getItems().setAll(mainPageDto.getList());
        }
    }

    /**
     * Обрабатывает выбор модели оборудования и активирует кнопку "Применить".
     *
     * @param newVal выбранная модель оборудования.
     */
    private void handleSelectionModel(String newVal) {
        if (newVal != null) {
            flagToApply[2] = true;
            checkApplyFlag();
            button_apply.setOnAction(event -> applyAction(newVal));
        }
    }

    /**
     * Обрабатывает действие "Применить" для занятия порта выбранным оборудованием.
     *
     * @param newVal выбранная модель оборудования.
     */
    @SneakyThrows
    private void applyAction(String newVal) {
        // Отправка POST-запроса для занятия порта выбранным оборудованием
        HttpRequests.PostRequest(
                new EquipmentDto(selectedSwitchId, selectedPort, ip.getText(), mac.getText()),
                "api/occupyPort/" + choiceBox_equipmentType.getValue() +
                        "/" + choiceBox_equipmentCompany.getValue() + "/" + newVal);
        // Закрытие диалогового окна и панели для занятия порта
        dialogPane.setVisible(false);
        anchorPane_occupyPort.setVisible(false);
        ip.clear();
        mac.clear();
        // Обновление информации о коммутаторе
        handleSelectionSwitchNumber(choiceBox_building.getValue(),
                choiceBox_roomNumber.getValue(), choiceBox_switch.getValue());
    }

}

package com.example.dlFx.controller;

import com.example.dlFx.FxApplication;
import com.example.dlFx.dto.AuthorizedUserDto;
import com.example.dlFx.httpRequests.HttpRequests;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Контроллер для управления авторизацией в приложении.
 */
public class FXMLDocumentController {

    @FXML
    private AnchorPane signIn_form;
    @FXML
    private Button signIn_close;
    @FXML
    private Button signIn_minimize;
    @FXML
    private Button signIn_logIn_btn;
    @FXML
    private PasswordField signIn_password;
    @FXML
    private TextField signIn_username;
    @FXML
    private Label label_error;

    /**
     * Закрывает окно авторизации.
     */
    public void signIn_close() {
        System.exit(0);
    }

    /**
     * Сворачивает окно авторизации.
     */
    public void signIn_minimize() {
        Stage stage = (Stage) signIn_form.getScene().getWindow();
        stage.setIconified(true);
    }

    /**
     * Обрабатывает попытку входа пользователя, проверяет учетные данные и меняет сцену при успешной авторизации.
     *
     * @throws IOException          если происходит ошибка ввода-вывода.
     * @throws URISyntaxException   если URI имеет неверный синтаксис.
     * @throws InterruptedException если запрос прерван.
     */
    @FXML
    private void switchToFXMLDocument2() throws IOException, URISyntaxException, InterruptedException {
        // Создание DTO с данными пользователя
        AuthorizedUserDto authorizedUserDto = new AuthorizedUserDto(signIn_username.getText(), signIn_password.getText());

        // Проверка заполнения полей
        if (signIn_username.getText().isEmpty() || signIn_password.getText().isEmpty()) {
            label_error.setText("Fill in all the fields!");
            label_error.setVisible(true);
        } else {
            // Отправка запроса на аутентификацию
            String uri = "login";
            String response = HttpRequests.AuthRequest(authorizedUserDto, uri);

            // Проверка ответа на запрос
            if (response.contains(HttpRequests.AUTH_EXCEPTION)) {
                label_error.setText(HttpRequests.AUTH_EXCEPTION);
                label_error.setVisible(true);
                signIn_username.setText("");
                signIn_password.setText("");
            } else {
                // Смена сцены при успешной авторизации
                Stage stage = (Stage) signIn_logIn_btn.getScene().getWindow();
                new FxApplication().showFXMLDocument2(stage);
            }
        }
    }
}

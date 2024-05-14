package com.example.dlFx.controller;

import com.example.dlFx.FxApplication;
import com.example.dlFx.dto.AuthorizedUserDto;
import com.example.dlFx.httpRequests.HttpRequests;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

public class FXMLDocumentController implements Initializable {

    // на экране входа нужно добавить поле для отображения ошибки входа строка 103 поле label

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


    // Закрыть окно авторизации
    public void signIn_close() {
        System.exit(0);
    }

    // Свернуть окно авторизации
    public void signIn_minimize() {
        Stage stage = (Stage)signIn_form.getScene().getWindow();
        stage.setIconified(true);
    }

    // Смена между окнами авторизации и регистрации
//    public void switchForm(ActionEvent event) {
//        if (event.getSource() == signIn_createAccount) {
//            signIn_form.setVisible(false);
//            signUp_form.setVisible(true);
//        } else if (event.getSource() == signUp_alreadyHaveAccount) {
//            signUp_form.setVisible(false);
//            signIn_form.setVisible(true);
//        }
//    }

    // Авторизация, вход и смена сцены
    @FXML
    private void switchToFXMLDocument2() throws IOException, URISyntaxException, InterruptedException {
        AuthorizedUserDto authorizedUserDto = new AuthorizedUserDto(signIn_username.getText(), signIn_password.getText());
        String uri = "login";
        String response = HttpRequests.AuthRequest(authorizedUserDto, uri);
        if (response.contains(HttpRequests.AUTH_EXCEPTION)) {
//            label.setText(HttpRequests.AUTH_EXCEPTION);
            signIn_username.setText("");
            signIn_password.setText("");
        } else {
            HttpRequests.setTOKEN(response);
            Stage stage = (Stage) signIn_logIn_btn.getScene().getWindow();
            new FxApplication().showFXMLDocument2(stage);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //label.setText("This is a second controller")
    }
}

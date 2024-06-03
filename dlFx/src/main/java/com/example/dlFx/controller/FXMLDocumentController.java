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

public class FXMLDocumentController implements Initializable {

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


    // Закрыть окно авторизации
    public void signIn_close() {
        System.exit(0);
    }

    // Свернуть окно авторизации
    public void signIn_minimize() {
        Stage stage = (Stage)signIn_form.getScene().getWindow();
        stage.setIconified(true);
    }

    // Авторизация, вход и смена сцены
    @FXML
    private void switchToFXMLDocument2() throws IOException, URISyntaxException, InterruptedException {
        AuthorizedUserDto authorizedUserDto = new AuthorizedUserDto(signIn_username.getText(), signIn_password.getText());
        if (signIn_username.getText().isEmpty() || signIn_password.getText().isEmpty()) {
            label_error.setText("Fill in all the fields!");
            label_error.setVisible(true);
        } else {
            String uri = "login";
            String response = HttpRequests.AuthRequest(authorizedUserDto, uri);
            if (response.contains(HttpRequests.AUTH_EXCEPTION)) {
                label_error.setText(HttpRequests.AUTH_EXCEPTION);
                label_error.setVisible(true);
                signIn_username.setText("");
                signIn_password.setText("");
            } else {
                HttpRequests.setTOKEN(response);
                Stage stage = (Stage) signIn_logIn_btn.getScene().getWindow();
                new FxApplication().showFXMLDocument2(stage);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

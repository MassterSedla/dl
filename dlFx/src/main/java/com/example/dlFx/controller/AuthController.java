package com.example.dlFx.controller;

import com.example.dlFx.controller.main.MainController;
import com.example.dlFx.httpRequests.HttpRequests;
import com.example.dlFx.dto.AuthorizedUserDto;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

public class AuthController extends MainController implements Initializable {
    @FXML
    private Label label;
    @FXML
    private Button button;
    @FXML
    private Button button2;
    @FXML
    private TextField login;
    @FXML
    private TextField password;

    @FXML
    public void next() throws IOException {
//        nextPage(button, "Main page", getFxmlPackage() + "MainPageController.fxml");
    }

    @FXML
    public void sendRequest() throws IOException, URISyntaxException, InterruptedException {
        AuthorizedUserDto authorizedUserDto = new AuthorizedUserDto(login.getText(), password.getText());
        String uri = "login";
        String response = HttpRequests.AuthRequest(authorizedUserDto, uri);
        if (response.contains(HttpRequests.AUTH_EXCEPTION)) {
            label.setText(HttpRequests.AUTH_EXCEPTION);
            login.setText("");
            password.setText("");
        } else {
            HttpRequests.setTOKEN(response);
//            nextPage(button, "Main page", getFxmlPackage() + "MainPageController.fxml");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        label.setText("This is a second controller");
    }
}

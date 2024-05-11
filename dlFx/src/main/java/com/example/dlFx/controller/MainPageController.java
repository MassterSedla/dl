package com.example.dlFx.controller;

import com.example.dlFx.controller.main.MainController;
import com.example.dlFx.httpRequests.HttpRequests;
import com.example.dlFx.model.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import java.util.ResourceBundle;

public class MainPageController extends MainController implements Initializable {
    @FXML
    private Label label;
    @FXML
    private Button button;
    //eknfieuwfuhwehfewf
    @FXML
    private Button button2;

    @FXML
    public void next() throws IOException {
        nextPage(button, "AuthController page", getFxmlPackage() + "AuthController.fxml");
    }

    @FXML
    public void sendRequest() throws URISyntaxException, IOException, InterruptedException {
        String uri = "api/page/1";
        JsonNode rootNode = HttpRequests.GetRequest(uri);
        ObjectMapper objectMapper = new ObjectMapper();
        ((ObjectNode) rootNode).remove("enabled");
        ((ObjectNode) rootNode).remove("authorities");
        User user = objectMapper.treeToValue(rootNode, User.class);
        user.setPassword("****");
        System.out.println(user);
        label.setText(user.toString());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        label.setText("This is a first controller");
    }
}

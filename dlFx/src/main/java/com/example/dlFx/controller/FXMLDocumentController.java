package com.example.dlFx.controller;

import com.example.dlFx.FxApplication;
import com.example.dlFx.controller.main.MainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FXMLDocumentController extends MainController implements Initializable {

    @FXML
    private AnchorPane signIn_form;
    @FXML
    private Button signIn_close;
    @FXML
    private Button signIn_minimize;
    @FXML
    private Button signIn_logIn_btn;
    @FXML
    private Hyperlink signIn_createAccount;
    @FXML
    private PasswordField signIn_password;
    @FXML
    private TextField signIn_username;


    @FXML
    private AnchorPane signUp_form;
    @FXML
    private Button signUp_close;
    @FXML
    private Button signUp_minimize;
    @FXML
    private Button signUp_signUp_btn;
    @FXML
    private Hyperlink signUp_alreadyHaveAccount;
    @FXML
    private TextField signUp_email;
    @FXML
    private PasswordField signUp_password;
    @FXML
    private TextField signUp_username;


    // Закрыть окно авторизации
    public void signIn_close() {
        System.exit(0);
    }

    // Свернуть окно авторизации
    public void signIn_minimize() {
        Stage stage = (Stage)signIn_form.getScene().getWindow();
        stage.setIconified(true);
    }

    // Закрыть окно регистрации
    public void signUp_close() {
        System.exit(0);
    }

    // Свернуть окно регистрации
    public void signUp_minimize() {
        Stage stage = (Stage)signUp_form.getScene().getWindow();
        stage.setIconified(true);
    }

    // Смена между окнами авторизации и регистрации
    public void switchForm(ActionEvent event) {
        if (event.getSource() == signIn_createAccount) {
            signIn_form.setVisible(false);
            signUp_form.setVisible(true);
        } else if (event.getSource() == signUp_alreadyHaveAccount) {
            signUp_form.setVisible(false);
            signIn_form.setVisible(true);
        }
    }

    // Авторизация, вход и смена сцены
    @FXML
   private void switchToFXMLDocument2() {

        signIn_logIn_btn.getScene().getWindow().hide();
        FxApplication fxApplication = new FxApplication();
        fxApplication.showFXMLDocument2();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //label.setText("This is a second controller")
    }
}

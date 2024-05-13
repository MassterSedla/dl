package com.example.dlFx.controller;

import com.example.dlFx.FxApplication;
import com.example.dlFx.controller.main.MainController;
import com.example.dlFx.dto.AuthorizedUserDto;
import com.example.dlFx.httpRequests.HttpRequests;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

public class FXMLDocumentController extends MainController implements Initializable {

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


    private double x = 0;
    private double y = 0;

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
   private void switchToFXMLDocument2() throws IOException, URISyntaxException, InterruptedException {
        AuthorizedUserDto authorizedUserDto = new AuthorizedUserDto(signIn_username.getText(), signIn_password.getText());
        String uri = "login";
        String response = HttpRequests.AuthRequest(new AuthorizedUserDto("user", "user"), uri);
        if (response.contains(HttpRequests.AUTH_EXCEPTION)) {
//            label.setText(HttpRequests.AUTH_EXCEPTION);
            signIn_username.setText("");
            signIn_password.setText("");
        } else {
            HttpRequests.setTOKEN(response);
            Stage stage = (Stage) signIn_logIn_btn.getScene().getWindow();
            showFXMLDocument2(stage);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //label.setText("This is a second controller")
    }

    public void showFXMLDocument2(Stage stage) {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/example/dlFx/FXMLDocument2.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
//            stage = new Stage();

            // Этот и следующий методы позволяют перемещать окно приложения на экране
            root.setOnMousePressed((MouseEvent event) -> {

                x = event.getSceneX();
                y = event.getSceneY();
            });

            root.setOnMouseDragged((MouseEvent event) -> {

                stage.setX(event.getScreenX() - x);
                stage.setY(event.getScreenY() - y);
            });
// --------------------------------------------------------------------------------------

            stage.setResizable(false);
//            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

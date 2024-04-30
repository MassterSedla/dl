module com.example.dlFx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.example.dlSpring;
    requires com.fasterxml.jackson.databind;

    opens com.example.dlFx to javafx.fxml;
    opens com.example.dlFx.controller to javafx.fxml;

    exports com.example.dlFx;
    exports com.example.dlFx.controller;
}
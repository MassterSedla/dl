module com.example.dlFx {
        requires javafx.controls;
        requires javafx.fxml;
        requires java.net.http;
        requires com.fasterxml.jackson.databind;
        requires static lombok;

        opens com.example.dlFx to javafx.fxml;
        opens com.example.dlFx.controller to javafx.fxml;
        opens com.example.dlFx.dto to com.fasterxml.jackson.databind;
        opens com.example.dlFx.model to com.fasterxml.jackson.databind;

        exports com.example.dlFx;
        exports com.example.dlFx.controller;
        exports com.example.dlFx.dto;
        exports com.example.dlFx.model;
        }
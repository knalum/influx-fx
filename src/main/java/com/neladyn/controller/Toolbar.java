package com.neladyn.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.stream.Collectors;

public class Toolbar extends Main {

    private static final String CONNECTED = "Connected";
    private static final String DISCONNECTED = "Disconnected";

    @FXML
    TextField serverField;

    @FXML
    ChoiceBox databaseChoiceBox;

    @FXML
    Label connectionStatusLabel;

    public Toolbar(){
    }

    public void initialize(){
        serverField.setText(influxService.getServer());
        serverField.textProperty().addListener((observable, oldValue, newValue) -> super.influxService.setServer(newValue));

        serverField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            boolean connected = super.influxService.isConnected();
            if( connected ){
                connectionStatusLabel.setText(CONNECTED);
                connectionStatusLabel.setTextFill(Color.GREEN);

                populateDatabaseChoiceBox();
            }
            else{
                connectionStatusLabel.setText(DISCONNECTED);
                connectionStatusLabel.setTextFill(Color.RED);
            }
        });

        databaseChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            influxService.setDbName((String) newValue);
            Platform.runLater(()->{
                ListController.updateList();
            });
        });


    }

    private void populateDatabaseChoiceBox() {
        List<String> names = influxService.getDatabaseNames();
        List<String> filtered = names.stream().filter(n -> !n.equals("_internal")).collect(Collectors.toList());
        databaseChoiceBox.getItems().setAll(filtered);
    }
}

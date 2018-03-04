package com.neladyn.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;

public class Menu extends Main{
    @FXML
    MenuItem quitItem;

    @FXML
    MenuItem aboutItem;

    public void initialize(){
        quitItem.setOnAction(event -> Platform.exit());
    }

    @FXML
    public void openDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("About influx-fx");
        alert.setContentText("Find readme at https://github.com/knalum");
        alert.show();
    }
}

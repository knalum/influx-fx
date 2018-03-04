package com.neladyn.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.util.List;

public class ListController extends Main {
    @FXML
    ListView dataList;


    public ListController() {

    }

    public void initialize() {
        dataList.setItems(super.items);

        dataList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedName.set((String) observable.getValue());
        });
    }

    public static void updateList() {
        List<String> measurements = influxService.getMeasurements();
        items.clear();
        items.addAll(measurements);
    }
}

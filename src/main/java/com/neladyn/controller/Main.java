package com.neladyn.controller;

import com.neladyn.service.InfluxService;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.Observable;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    static InfluxService influxService = new InfluxService("http://localhost:8086");


    static ObservableList<String> items = FXCollections.observableList(new LinkedList<>());

    static StringProperty selectedName = new SimpleStringProperty();

    public Main() { }

    public void initialize() { }


}

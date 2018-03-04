package com.neladyn.controller;

import com.neladyn.domain.Measurement;
import com.neladyn.domain.Series;
import com.neladyn.service.InfluxService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

import java.util.List;

public class OtherController extends Main{
    @FXML
    TextField nameField;

    @FXML
    TextField valueField;

    @FXML
    TextField timestampField;

    @FXML
    StackPane stackPane;

    public void initialize(){
        // Chart init
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        //creating the chart
        xAxis.setTickLabelsVisible(false);
        xAxis.setOpacity(0);

        final LineChart<Number, Number> lineChart =
                new LineChart<>(xAxis, yAxis);
        lineChart.setAnimated(false);

        lineChart.setCreateSymbols(false);
        lineChart.getStyleClass().add("thick-chart");
        //

        stackPane.getChildren().add(lineChart);

        super.selectedName.addListener((observable, oldValue, name) -> {
            nameField.setText(name);

            Measurement meas = influxService.getMeasurementValue(name);
            timestampField.setText(String.valueOf(meas.getTimestamp()));
            valueField.setText(String.valueOf(meas.getValue()));
            XYChart.Series series = getSeries(name).getSeries();
            lineChart.getData().clear();
            lineChart.getData().add(series);

            lineChart.getXAxis().setTickLabelsVisible(false);
            lineChart.getXAxis().setOpacity(0);
        });
    }

    public Series getSeries(String name) {
        XYChart.Series series = new XYChart.Series();
        series.setName("My portfolio");

        //series.getData().add(new XYChart.Data(1, 23));
        List<Measurement> measurements = influxService.getMeasurementArray(name);
        for (int i = 0; i < measurements.size(); i++) {
            series.getData().add(new XYChart.Data<>(i, measurements.get(i).getValue()));
        }

        return new Series(measurements.get(0).getTimestamp(), measurements.get(measurements.size() - 1).getTimestamp(), series);
    }
}

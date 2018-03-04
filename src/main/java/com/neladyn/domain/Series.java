package com.neladyn.domain;

import javafx.scene.chart.XYChart;

public class Series {
    private long from;
    private long to;
    private XYChart.Series series;

    public Series(long from, long to, XYChart.Series series) {
        this.from = from;
        this.to = to;
        this.series = series;
    }

    public long getFrom() {
        return from;
    }

    public long getTo() {
        return to;
    }

    public XYChart.Series getSeries() {
        return series;
    }
}

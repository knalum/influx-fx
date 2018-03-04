package com.neladyn.domain;

public class Measurement {
    long timestamp;
    double value;

    public Measurement(long timestamp, double value) {
        this.timestamp = timestamp;
        this.value = value;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public double getValue() {
        return value;
    }
}

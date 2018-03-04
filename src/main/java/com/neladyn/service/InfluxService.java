package com.neladyn.service;

import com.neladyn.domain.Measurement;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class InfluxService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InfluxService.class);

    private String server; // http://localhost:8086
    private String dbName; // Plant

    public InfluxService(String server) {
        this.server = server;
    }

    public List<String> getDatabaseNames() {
        OkHttpClient client = new OkHttpClient();
        List<String> names = new LinkedList<>();

        String query = null;
        try {
            query = URLEncoder.encode("SHOW DATABASES ", "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        try {
            Request req = new Request.Builder()
                    .url(server + "/query?db=" + dbName + "&epoch=ms&q=" + query)
                    .build();
            Response res = null;
            res = client.newCall(req).execute();
            if (res.isSuccessful()) {

                JSONObject obj = new JSONObject(res.body().string());
                JSONArray values = obj.getJSONArray("results").getJSONObject(0).getJSONArray("series").getJSONObject(0).getJSONArray("values");
                for (int i = 0; i < values.length(); i++) {
                    names.add(values.getJSONArray(i).getString(0));
                }
            }
        } catch (Exception e) { }

        return names;
    }

    public List<Measurement> getMeasurementArray(String name) {
        List<Measurement> measurements = new LinkedList<>();
        OkHttpClient client = new OkHttpClient();

        String query = null;
        try {
            query = URLEncoder.encode("select * from " + name, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Request req = new Request.Builder()
                .url(server + "/query?db=" + dbName + "&epoch=ms&q=" + query)
                .build();

        Response res = null;
        try {
            res = client.newCall(req).execute();
            JSONObject obj = new JSONObject(res.body().string());

            JSONArray values = obj.getJSONArray("results").getJSONObject(0).getJSONArray("series").getJSONObject(0).getJSONArray("values");
            for (int i = 0; i < values.length(); i++) {
                long timestamp = values.getJSONArray(i).getLong(0);
                double value = values.getJSONArray(i).getDouble(1);

                measurements.add(new Measurement(timestamp, value));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return measurements;
    }

    public Measurement getMeasurementValue(String name) {
        OkHttpClient client = new OkHttpClient();

        String query = null;
        try {
            query = URLEncoder.encode("select last(value) from " + name, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Request req = new Request.Builder()
                .url(server + "/query?db=" + dbName + "&epoch=ms&q=" + query)
                .build();

        Response res = null;
        try {
            res = client.newCall(req).execute();
            JSONObject obj = new JSONObject(res.body().string());

            JSONArray values = obj.getJSONArray("results").getJSONObject(0).getJSONArray("series").getJSONObject(0).getJSONArray("values");
            long timestamp = values.getJSONArray(0).getLong(0);
            double value = values.getJSONArray(0).getDouble(1);
            return new Measurement(timestamp, value);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<String> getMeasurements() {
        List<String> measurements = new ArrayList<>();
        OkHttpClient client = new OkHttpClient();

        String query = null;
        try {
            query = URLEncoder.encode("SHOW MEASUREMENTS", "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            Request req = new Request.Builder()
                    .url(server + "/query?db=" + dbName + "&q=" + query)
                    .build();

            Response res = client.newCall(req).execute();
            JSONObject obj = new JSONObject(res.body().string());
            JSONArray values = obj.getJSONArray("results").getJSONObject(0).getJSONArray("series").getJSONObject(0).getJSONArray("values");


            for (int i = 0; i < values.length(); i++) {
                measurements.add(values.getJSONArray(i).getString(0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return measurements;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public boolean isConnected() {
        OkHttpClient client = new OkHttpClient();

        String query = null;
        try {
            query = URLEncoder.encode("SHOW DATABASES", "utf-8");
            Request req = new Request.Builder()
                    .url(server + "/query?q=" + query)
                    .build();
            client.newCall(req).execute();


        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info("Not connected");
            return false;
        }

        LOGGER.info("Connected");
        return true;
    }
}

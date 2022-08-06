package com.example.smarthouse01;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Switch extends AsyncTask<Void, Void, String> {

    public void Switch_on() throws IOException {
        URL url = new URL("https://api.thingspeak.com/update?api_key=X0RS687S9D0ZGW2X&field6=1");
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        InputStream inputStream = httpURLConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
    }

    public void Switch_off() throws IOException {
        URL url = new URL("https://api.thingspeak.com/update?api_key=X0RS687S9D0ZGW2X&field6=0");
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        InputStream inputStream = httpURLConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
    }

    @Override
    protected String doInBackground(Void... voids) {
        return null;
    }
}

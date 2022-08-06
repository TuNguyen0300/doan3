/*
package com.example.smarthouse01;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    TextView tv_nhietdo, tv_doam, tv_doamdat, tv_cdanhsang;
    Switch sw_den, sw_bom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_nhietdo = findViewById(R.id.id_nhietdo);
        tv_doam = findViewById(R.id.id_doam);
        tv_doamdat = findViewById(R.id.id_doamdat);
        tv_cdanhsang = findViewById(R.id.id_anhsang);
        sw_den = findViewById(R.id.id_sw_den);
        sw_bom = findViewById(R.id.id_sw_maybom);

        String url = "https://api.thingspeak.com/channels/1807783/feeds.json?api_key=8OZMCJQAFRS1U32M";
        set_thongso(url);
        set_thongso_denbom(url);
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                set_thongso_denbom(url);
//            }
//        }, 1000);
        refresh_data();

        sw_den.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    //Toast.makeText(MainActivity.this, "Đèn đã được bật", Toast.LENGTH_SHORT).show();
                    if (sw_bom.isChecked()==true){
                        update_data(tv_nhietdo.getText().toString(), tv_doam.getText().toString(), tv_doamdat.getText().toString(), tv_cdanhsang.getText().toString(), "1", "1");
                    } else update_data(tv_nhietdo.getText().toString(), tv_doam.getText().toString(), tv_doamdat.getText().toString(), tv_cdanhsang.getText().toString(), "0", "1");
                } else {
                    //Toast.makeText(MainActivity.this, "Đèn đã được tắt", Toast.LENGTH_SHORT).show();
                    if (sw_bom.isChecked()==true){
                        update_data(tv_nhietdo.getText().toString(), tv_doam.getText().toString(), tv_doamdat.getText().toString(), tv_cdanhsang.getText().toString(), "1", "0");
                    } else update_data(tv_nhietdo.getText().toString(), tv_doam.getText().toString(), tv_doamdat.getText().toString(), tv_cdanhsang.getText().toString(), "0", "0");
                }
            }
        });
//        sw_bom.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b) {
//                    Toast.makeText(MainActivity.this, "Máy bơm đã được bật", Toast.LENGTH_SHORT).show();
//                    if (sw_den.isChecked()){
//                        update_data(tv_nhietdo.getText().toString(), tv_doam.getText().toString(), tv_doamdat.getText().toString(), tv_cdanhsang.getText().toString(), "1", "1");
//
//                    } else update_data(tv_nhietdo.getText().toString(), tv_doam.getText().toString(), tv_doamdat.getText().toString(), tv_cdanhsang.getText().toString(), "1", "0");
//                } else {
//                    Toast.makeText(MainActivity.this, "Máy bơm đã được tắt", Toast.LENGTH_SHORT).show();
//                    if (sw_den.isChecked()){
//                        update_data(tv_nhietdo.getText().toString(), tv_doam.getText().toString(), tv_doamdat.getText().toString(), tv_cdanhsang.getText().toString(), "0", "1");
//
//                    } else update_data(tv_nhietdo.getText().toString(), tv_doam.getText().toString(), tv_doamdat.getText().toString(), tv_cdanhsang.getText().toString(), "0", "0");
//                }
//            }
//        });
        //update_den_bom("1", "1");
    }
    void set_thongso(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("feeds");
                            JSONObject jsonObject = jsonArray.getJSONObject(jsonArray.length() - 1);
                            //Toast.makeText(MainActivity.this, jsonObject.toString(), Toast.LENGTH_SHORT).show();
                            String nhietdo = jsonObject.getString("field1");
                            String doam = jsonObject.getString("field2");
                            String cdanhsang = jsonObject.getString("field3");
                            String doamdat = jsonObject.getString("field4");
                            tv_nhietdo.setText(nhietdo);
                            tv_doam.setText(doam);
                            tv_doamdat.setText(doamdat);
                            tv_cdanhsang.setText(cdanhsang);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);
    }
    void set_thongso_denbom(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("feeds");
                            JSONObject jsonObject = jsonArray.getJSONObject(jsonArray.length() - 1);
                            //Toast.makeText(MainActivity.this, jsonObject.toString(), Toast.LENGTH_SHORT).show();
                            String den = jsonObject.getString("field5");
                            String maybom = jsonObject.getString("field6");
                            if (den.compareTo("1")==0) sw_den.setChecked(true); else sw_den.setChecked(false);
                            if (maybom.compareTo("1")==0) sw_bom.setChecked(true); else sw_bom.setChecked(false);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);
    }
    void refresh_data(){
        String url = "https://api.thingspeak.com/channels/1807783/feeds.json?api_key=8OZMCJQAFRS1U32M";
        final Handler handler = new Handler();
        Runnable refresh = new Runnable() {
            @Override
            public void run() {
                // data request
                set_thongso(url);
                handler.postDelayed(this, 1000);
            }
        };
        handler.postDelayed(refresh, 1000);
    }
    void update_data(String nhietdo, String doam, String doamdat, String cdanhsang, String maybom, String den){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        Uri builtUri = Uri.parse("https://api.thingspeak.com/update?")
                .buildUpon().appendQueryParameter("api_key", "K5WBCHD6FEU4BYIR")
                .appendQueryParameter("field1", nhietdo)
                .appendQueryParameter("field2", doam)
                .appendQueryParameter("field3", cdanhsang)
                .appendQueryParameter("field4", doamdat)
                .appendQueryParameter("field5", den)
                .appendQueryParameter("field6", maybom)
                .build();
        String url = builtUri.toString();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
    }
//    void get_data(String url){
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            JSONArray jsonArray = response.getJSONArray("feeds");
//                            JSONObject jsonObject = jsonArray.getJSONObject(jsonArray.length() - 1);
//                            //Toast.makeText(MainActivity.this, jsonObject.toString(), Toast.LENGTH_SHORT).show();
//                            String nhietdo = jsonObject.getString("field1");
//                            String doam = jsonObject.getString("field2");
//                            String cdanhsang = jsonObject.getString("field3");
//                            String doamdat = jsonObject.getString("field4");
//                            String den = jsonObject.getString("field5");
//                            String maybom = jsonObject.getString("field6");
//                            update_data(nhietdo, doam, doamdat, cdanhsang, maybom, den);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//        requestQueue.add(jsonObjectRequest);
//    }
}*/
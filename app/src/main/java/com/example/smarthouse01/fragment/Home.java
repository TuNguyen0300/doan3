package com.example.smarthouse01.fragment;

import static android.app.NotificationManager.IMPORTANCE_DEFAULT;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.smarthouse01.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Home extends Fragment {

    Switch SWgara, SWgate, SWfan;
    TextView txtHumi, txtTemp, txtNotice, txtFanCtrl;
    SeekBar SBLight;
    String position, i, j, k, l, rainValue, fan;
    ImageView Img_menu;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f_home, container, false);

        SWgate = view.findViewById(R.id.Switch_gate);
        SWgara = view.findViewById(R.id.Switch_gara);
        txtHumi = view.findViewById(R.id.txt_Hum);
        txtTemp = view.findViewById(R.id.txt_Temp);
        txtNotice = view.findViewById(R.id.txt_notice);
        SBLight = view.findViewById(R.id.SB_liv);
        SWfan = view.findViewById(R.id.Switch_fan);
        txtFanCtrl = view.findViewById(R.id.txt_fan_control);
        Img_menu = view.findViewById(R.id.menu_popup);
//
        SBLight.setMax(4);

        SWgate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    if (SWgara.isChecked() == true) {
                        update_data(txtTemp.getText().toString(), txtHumi.getText().toString(), position, rainValue, "1.0", "1.0", k, l);
                        i = "1.0";
                        j = "1.0";
                    } else {
                        update_data(txtTemp.getText().toString(), txtHumi.getText().toString(), position, rainValue, "1.0", "0.0", k, l);
                        i = "1.0";
                        j = "0.0";
                    }
                } else {
                    if (SWgara.isChecked() == true) {
                        update_data(txtTemp.getText().toString(), txtHumi.getText().toString(), position, rainValue, "0.0", "1.0", k, l);
                        i = "0.0";
                        j = "1.0";
                    } else {
                        update_data(txtTemp.getText().toString(), txtHumi.getText().toString(), position, rainValue, "0.0", "0.0", k, l);
                        i = "0.0";
                        j = "0.0";
                    }
                }
            }
        });

        SWgara.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    if (SWgate.isChecked() == true) {
                        update_data(txtTemp.getText().toString(), txtHumi.getText().toString(), position, rainValue, "1.0", "1.0", k, l);
                        i = "1.0";
                        j = "1.0";
                    } else {
                        update_data(txtTemp.getText().toString(), txtHumi.getText().toString(), position, rainValue, "0.0", "1.0", k, l);
                        i = "0.0";
                        j = "1.0";
                    }
                } else {
                    if (SWgate.isChecked() == true) {
                        update_data(txtTemp.getText().toString(), txtHumi.getText().toString(), position, rainValue, "1.0", "0.0", k, l);
                        i = "1.0";
                        j = "0.0";
                    } else {
                        update_data(txtTemp.getText().toString(), txtHumi.getText().toString(), position, rainValue, "0.0", "0.0", k, l);
                        i = "0.0";
                        j = "0.0";
                    }
                }
            }
        });

        SBLight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int posi, boolean b) {
                position = String.valueOf(posi);

                update_data(txtTemp.getText().toString(), txtHumi.getText().toString(), position, rainValue, i, j, k, l);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        get_Data();

        Img_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Show_menu();
            }
        });

        return view;
    }

    private void get_Data() {
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        String url = "https://api.thingspeak.com/channels/1807511/feeds.json?api_key=Z9G3P3R9IL3SATEV";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("feeds");
                            JSONObject jsonObject = jsonArray.getJSONObject(jsonArray.length() - 1);
                            String Temp = jsonObject.getString("field1");
                            String Humi = jsonObject.getString("field2");
                            String Light = jsonObject.getString("field3");
                            position = Light;
                            String Rain_value = jsonObject.getString("field4");
                            String Gara = jsonObject.getString("field6");
                            String Gate = jsonObject.getString("field5");
                            String Fan = jsonObject.getString("field7");

                            //
                            txtTemp.setText(Temp);
                            ////////////////////////////
                            txtHumi.setText(Humi);
                            float hum = Float.parseFloat(Humi);
                            float a = 50;
                            if (hum < a) {
                                txtNotice.setText(" Below standard of humidity! Need to be irrigated");
                                txtNotice.setTextColor(Color.parseColor("#D82D2D"));
                            } else {
                                txtNotice.setText(" In allowed standard of humidity");
                                txtNotice.setTextColor(Color.parseColor("#39AC31"));
                            }
                            ////////////////////////////
                            rainValue = Rain_value;
                            float rain = Float.parseFloat(Rain_value);
                            float b = 20;
                            if (rain > b) {
                                notification();
                            }
                            ////////////////////////////
                            if (Gara.compareTo("1.0") == 0) {
                                SWgara.setChecked(true);
                            } else {
                                SWgara.setChecked(false);
                            }
                            ////////////////////////////
                            if (Gate.compareTo("1.0") == 0) {
                                SWgate.setChecked(true);
                            } else SWgate.setChecked(false);

                            ////////////////////////////
                            if (Fan.compareTo("1.0") == 0) {
                                SWfan.setChecked(true);
                            } else SWfan.setChecked(false);

                            ////////////////////////////
                            int po = (int) Float.parseFloat(Light);
                            SBLight.setProgress(po);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "ERROR", Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(request);
    }

    private void get_Data_Fan() {
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        String url = "https://api.thingspeak.com/channels/1807511/feeds.json?api_key=Z9G3P3R9IL3SATEV";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("feeds");
                            JSONObject jsonObject = jsonArray.getJSONObject(jsonArray.length() - 1);
                            String Fan = jsonObject.getString("field7");

                            fan = Fan;

                            if (Fan.compareTo("1.0") == 0) {
                                SWfan.setChecked(true);
                            } else SWfan.setChecked(false);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "ERROR", Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(request);
    }

    public void update_data(String Temp, String Humi, String Light, String Rain_Value, String Gate, String Gara, String Fan, String Option_level) {

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        Uri builtUri = Uri.parse("https://api.thingspeak.com/update?")
                .buildUpon().appendQueryParameter("api_key", "X0RS687S9D0ZGW2X")
                .appendQueryParameter("field1", Temp)
                .appendQueryParameter("field2", Humi)
                .appendQueryParameter("field3", Light)
                .appendQueryParameter("field4", Rain_Value)
                .appendQueryParameter("field5", Gate)
                .appendQueryParameter("field6", Gara)
                .appendQueryParameter("field7", Fan)
                .appendQueryParameter("field8", Option_level)
                .build();
        String url = builtUri.toString();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
    }

    public void Show_menu() {
        PopupMenu popupMenu = new PopupMenu(getActivity(), Img_menu);
        popupMenu.getMenuInflater().inflate(R.menu.menu_fan, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menu_Auto:
                        txtFanCtrl.setText("  automatically");
                        get_Data_Fan();
                        update_data(txtTemp.getText().toString(), txtHumi.getText().toString(), position, rainValue, i, j, fan, "1.0");
                        l = "1.0";
                        break;
                    case R.id.menu_Man:
                        txtFanCtrl.setText("  manually");
                        update_data(txtTemp.getText().toString(), txtHumi.getText().toString(), position, rainValue, i, j, "0.0", "0.0");
                        SWfan.setChecked(false);
                        SWfan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                                if (isChecked) {
                                    if (SWgate.isChecked() == true) {
                                        if (SWgara.isChecked() == true) {
                                            update_data(txtTemp.getText().toString(), txtHumi.getText().toString(), position, rainValue, "1.0", "1.0", "1.0", "0.0");
                                            i = "1.0";
                                            j = "1.0";
                                            k = "1.0";
                                            l = "0.0";
                                        } else {
                                            update_data(txtTemp.getText().toString(), txtHumi.getText().toString(), position, rainValue, "1.0", "0.0", "1.0", "0.0");
                                            i = "1.0";
                                            j = "0.0";
                                            k = "1.0";
                                            l = "0.0";
                                        }
                                    } else {
                                        if (SWgara.isChecked() == true) {
                                            update_data(txtTemp.getText().toString(), txtHumi.getText().toString(), position, rainValue, "0.0", "1.0", "1.0", "0.0");
                                            i = "0.0";
                                            j = "1.0";
                                            k = "1.0";
                                            l = "0.0";
                                        } else {
                                            update_data(txtTemp.getText().toString(), txtHumi.getText().toString(), position, rainValue, "0.0", "0.0", "0.0", "0.0");
                                            i = "0.0";
                                            j = "0.0";
                                            k = "1.0";
                                            l = "0.0";
                                        }
                                    }

                                } else {
                                    if (SWgate.isChecked() == true) {
                                        if (SWgara.isChecked() == true) {
                                            update_data(txtTemp.getText().toString(), txtHumi.getText().toString(), position, rainValue, "1.0", "1.0", "0.0", "0.0");
                                            i = "1.0";
                                            j = "1.0";
                                            k = "0.0";
                                            l = "0.0";
                                        } else {
                                            update_data(txtTemp.getText().toString(), txtHumi.getText().toString(), position, rainValue, "1.0", "0.0", "0.0", "0.0");
                                            i = "1.0";
                                            j = "0.0";
                                            k = "0.0";
                                            l = "0.0";
                                        }
                                    } else {
                                        if (SWgara.isChecked() == true) {
                                            update_data(txtTemp.getText().toString(), txtHumi.getText().toString(), position, rainValue, "0.0", "1.0", "0.0", "0.0");
                                            i = "0.0";
                                            j = "1.0";
                                            k = "0.0";
                                            l = "0.0";
                                        } else {
                                            update_data(txtTemp.getText().toString(), txtHumi.getText().toString(), position, rainValue, "0.0", "0.0", "0.0", "0.0");
                                            i = "0.0";
                                            j = "0.0";
                                            k = "0.0";
                                            l = "0.0";
                                        }
                                    }
                                }
                            }
                        });
                        break;
                }
                return false;
            }
        });
        popupMenu.show();
    }

    public void notification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("My Notification", "My Notification", importance);
            NotificationManager notificationManager = getActivity().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

            NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity(), "My Notification")
                    .setSmallIcon(R.drawable.notifcation_icon)
                    .setContentTitle("Warning")
                    .setContentText("Mưa to rồi! Mưa to rồi! Mau... Mau... Mau vào thôi.....")
                    .setAutoCancel(true)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getActivity());

            notificationManager.notify(1, builder.build());
        }
}

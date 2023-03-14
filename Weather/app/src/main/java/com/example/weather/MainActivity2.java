package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {
    List<weather> weatherList;
    weather_adapter adapter;
    ListView list_item;
    private String apikey = "9e143d24f3bd8b853ec0ae97b59e1ce6";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        list_item = (ListView) findViewById(R.id.list_item);
        Intent intent = getIntent();
        String city = intent.getStringExtra("city");
        weatherList = new ArrayList<>();
        adapter = new weather_adapter(MainActivity2.this, R.layout.item_weather, weatherList);
        list_item.setAdapter(adapter);
        getjsonnextday(city);
    }

    private void getjsonnextday(String city) {
        String url = "https://api.openweathermap.org/data/2.5/forecast?q="+city+"&cnt=7&appid="+apikey+"&units=metric";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray list = response.getJSONArray("list");
                            for(int i=0;i< list.length(); i++){
                                JSONObject item = list.getJSONObject(i);
                                String sngay = item.getString("dt");        // get ngay
                                long lngay = Long.parseLong(sngay);
                                SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, yyyy-MM-dd HH:mm:ss");
                                Date date = new Date(lngay*1000);
                                String currentTime = dateFormat.format(date);       // formmat ngay

                                JSONObject main = item.getJSONObject("main");
                                String min = main.getString("temp_min");             // nhiet do min
                                String max = main.getString("temp_max");             //nhiet do max

                                JSONArray weather = item.getJSONArray("weather");
                                JSONObject weather_item = weather.getJSONObject(0);
                                String mota = weather_item.getString("description");  // get description (trang thai)
                                String icon = weather_item.getString("icon");           // get icon name
                                String urlIcon = "https://openweathermap.org/img/wn/"+icon+".png";   // set icon

                                weatherList.add(new weather(currentTime, mota, urlIcon, min+"°C", max+"°C" ));
                            }
                            adapter.notifyDataSetChanged();


                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        Toast.makeText(MainActivity2.this, ""+response.toString(), Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity2.this, "Khong co du lieu cho thanh pho", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }
}
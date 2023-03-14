package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AsyncRequestQueue;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    static final String AIP_KEY = "9e143d24f3bd8b853ec0ae97b59e1ce6";
    String city= "hanoi";
    EditText edttimkiem;
    Button bttimkiem, btxemngaytieptheo;
    TextView txtthanhpho, txtnhietdo, txttrangthai, txtdatnuoc, txtdoam, txtmay, txtgio, txtngay;
    ImageView imgv;
    //AsyncRequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapping();
        bttimkiem.setOnClickListener(this);
        btxemngaytieptheo.setOnClickListener(this);
        getjsonweather(city);
    }

    public void getjsonweather(String city){
        String url = "https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid="+AIP_KEY+"&units=metric";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray weatheArray = response.getJSONArray("weather");
                            JSONObject weatherObj = weatheArray.getJSONObject(0);
                            String icon = weatherObj.getString("icon");  //icon
                            String urlIcon = "https://openweathermap.org/img/wn/"+icon+".png";
                            Picasso.get().load(urlIcon).into(imgv);

                            String thanhpho = response.getString("name");       //thanh pho

                            JSONObject tempsys = response.getJSONObject("sys");  // get obj sys
                            String quocgia = tempsys.getString("country");       //quoc gia

                            String trangthai = weatherObj.getString("main");    //trangthai

                            JSONObject main = response.getJSONObject("main");   //get obj main
                            String temp = main.getString("temp");               //nhiet do

                            String doam = main.getString("humidity");           //do am (humidity)

                            JSONObject wind = response.getJSONObject("wind");   //get obj wind
                            String tocdogio = wind.getString("speed");          //toc do gio

                            JSONObject clouds = response.getJSONObject("clouds");//get obj cloud
                            String may = clouds.getString("all");               //may

                            String sngay = response.getString("dt");
                            long lngay = Long.parseLong(sngay);
                            SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, yyyy-MM-dd HH:mm:ss");
                            Date date = new Date(lngay*1000);
                            String currentTime = dateFormat.format(date);               //ngay gio cap nhat

                            txtthanhpho.setText(" Thanh pho"+thanhpho);
                            txtdatnuoc.setText(" Quoc gia"+quocgia);
                            txttrangthai.setText(trangthai);
                            txtnhietdo.setText(temp+"°C");
                            txtdoam.setText(doam+"%");
                            txtmay.setText(may+"%");
                            txtgio.setText(tocdogio+"m/s");
                            txtngay.setText(currentTime);

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        //Toast.makeText(MainActivity.this, ""+response.toString(), Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Khong co du lieu cho thanh pho"+city, Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);


//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        log.d("ketqua", response);
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                    }
//                }
//        );

    }
    private void mapping(){
        edttimkiem =(EditText) findViewById(R.id.edttimkiem);
        bttimkiem = (Button) findViewById(R.id.bttimkiem);
        btxemngaytieptheo = (Button) findViewById(R.id.btxemngaytieptheo);
        txtnhietdo = (TextView) findViewById(R.id.txtnhietdo);
        txttrangthai = (TextView) findViewById(R.id.txttrangthai);
        txtdatnuoc = (TextView) findViewById(R.id.txtdatnuoc);
        txtthanhpho = (TextView) findViewById(R.id.txtthanhpho);
        txtdoam = (TextView) findViewById(R.id.txtdoam);
        txtmay = (TextView) findViewById(R.id.txtmay);
        txtgio = (TextView) findViewById(R.id.txtgio);
        txtngay = (TextView) findViewById(R.id.txtngay);
        imgv = (ImageView) findViewById(R.id.imgv);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bttimkiem:
                city = edttimkiem.getText().toString().trim();

                getjsonweather(city);
                break;
            case R.id.btxemngaytieptheo:
                Intent intent= new Intent(MainActivity.this, MainActivity2.class);
                intent.putExtra("city", city);
                startActivity(intent);

                break;
        }
    }
}
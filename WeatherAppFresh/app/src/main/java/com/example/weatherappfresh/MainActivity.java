package com.example.weatherappfresh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements Jason,MediaPlayerAudio {
    ImageView imageViewLogo;
    ImageView imageViewBackground;
    AutoCompleteTextView autoCompleteTextView;
    TextView textView;
    Switch aSwitch;
    String output="";

    private final String url = "https://api.openweathermap.org/data/2.5/weather";
    private final String appid = "749be5e398b9999251741b6656b05b32";
    DecimalFormat df = new DecimalFormat("#.##");
    double tempu;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //acess widgets inside app:
        imageViewLogo = findViewById(R.id.imageViewLogo);
        imageViewBackground = findViewById(R.id.imageViewBackground);
        autoCompleteTextView = findViewById(R.id.autoCompleteTextView);
        textView = findViewById(R.id.textView);
        aSwitch = findViewById(R.id.switch1);


        //For auto-complete textview suggestions :
        String[] places = {"bhagalpur", "delhi", "punjab", "pune", "gujrat", "new York", "Mumbai", "Washington Dc", "Dubai", "Siliguri", "kolkata", "London", "Tokyo", "Paris", "los angeles", "gangtok", "patna", "lucknow", "britain"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, places);
        autoCompleteTextView.setAdapter(arrayAdapter); //yaha tak sugestion to milega lekin after typing 2 char
        autoCompleteTextView.setThreshold(1);//suggestion starts after typing 1 char

        getSupportActionBar().hide();
    }





    public void searchResult(View view){

            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(autoCompleteTextView.getWindowToken(), 0);
            gettingData();

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    gettingData(tempu);
                    //textView.setText(output);
                    //Toast.makeText(getBaseContext(), "working on status", Toast.LENGTH_SHORT).show();
                } else {
                    gettingData();
                    //searchResult();
                    //Toast.makeText(getBaseContext(), "off status", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void gettingData() {
            String tempUrl = "";
            String city = autoCompleteTextView.getText().toString();
            if (city == "") {
                Toast.makeText(getApplicationContext(), "City name cannot be empty", Toast.LENGTH_SHORT).show();
            } else {
                tempUrl = url + "?q=" + city + "&appid=" + appid;
            }
            StringRequest stringRequest = new StringRequest(Request.Method.POST, tempUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    String output = "";
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        JSONArray jsonArray = jsonResponse.getJSONArray("weather");
                        JSONObject jsonObjectWeather = jsonArray.getJSONObject(0);
                        String description = jsonObjectWeather.getString("description");
                        String main = jsonObjectWeather.getString("main");

                        JSONObject jsonObjectMain = jsonResponse.getJSONObject("main");
                       // double temp = jsonObjectMain.getDouble("temp") - 273.15;
                        double feels_like = jsonObjectMain.getDouble("feels_like") - 273.15;
                        int humidity = jsonObjectMain.getInt("humidity");

                        JSONObject jsonObjectSys = jsonResponse.getJSONObject("sys");
                        String countryName = jsonObjectSys.getString("country");
                        String cityName = jsonResponse.getString("name");

                        switch (main){
                            case "Rain":
                                mediaPlayerRain();
                            case "Thunder" :
                                mediaPlayerThunder();
                            case "Clouds" :
                                mediaPlayerSea();
                            case "Snow" :
                                mediaPlayerWinter();
                        }

                        output += "Current Weather of " + cityName + " (" + countryName + ")"
                                + "\n Main: " + main
                                + "\n Description: " + description
                                + "\n Humidity: " + humidity + "%";
                        textView.setBackgroundColor(947066);
                        textView.setText(output);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, error -> Toast.makeText(getApplicationContext(), "Could not get weather", Toast.LENGTH_SHORT).show());
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }

    @Override
    public void gettingData(double temperature) {
            String tempUrl = "";
            String city = autoCompleteTextView.getText().toString();
            if (city.equals("")) {
                Toast.makeText(getApplicationContext(), "City name cannot be empty", Toast.LENGTH_SHORT).show();
            } else {
                tempUrl = url + "?q=" + city + "&appid=" + appid;
            }
            StringRequest stringRequest = new StringRequest(Request.Method.POST, tempUrl, response -> {
                String output = "";
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray jsonArray = jsonResponse.getJSONArray("weather");
                    JSONObject jsonObjectWeather = jsonArray.getJSONObject(0);
                    String description = jsonObjectWeather.getString("description");
                    String main = jsonObjectWeather.getString("main");

                    JSONObject jsonObjectMain = jsonResponse.getJSONObject("main");
                    double temperature1 = jsonObjectMain.getDouble("temp") - 273.15;
                    temperature1 = tempu;
                    double feels_like = jsonObjectMain.getDouble("feels_like") - 273.15;
                    int humidity = jsonObjectMain.getInt("humidity");

                    JSONObject jsonObjectSys = jsonResponse.getJSONObject("sys");
                    String countryName = jsonObjectSys.getString("country");
                    String cityName = jsonResponse.getString("name");

                    Log.i("temp :", String.valueOf(tempu));
                    Log.i("temp :", String.valueOf(temperature1));

                    output += "Current Weather of " + cityName + " (" + countryName + ")"
                            + "\n Main: " + main
                            + "\n Description: " + description
                            + "\n Feels Like: " + df.format(feels_like)
                            + "\n Humidity: " + humidity + "%"
                            + "\n Temperature: " + df.format(tempu) + "°C";

                    switch (main){
                        case "Rain":
                            mediaPlayerRain();
                        case "Thunder" :
                            mediaPlayerThunder();
                        case "Clouds" :
                            mediaPlayerSea();
                        case "Snow" :
                            mediaPlayerWinter();
                    }

                    textView.setBackgroundColor(947066);
                    textView.setText(output);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, error -> Toast.makeText(getApplicationContext(), "Could not get weather", Toast.LENGTH_SHORT).show());
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }


    @Override
    public void mediaPlayerRain() {
        MediaPlayer mediaPlayerRain = MediaPlayer.create(this,R.raw.rain);
        mediaPlayerRain.start();
    }

    @Override
    public void mediaPlayerWinter() {
        MediaPlayer mediaPlayerWinter = MediaPlayer.create(this,R.raw.winter);
        mediaPlayerWinter.start();
    }

    @Override
    public void mediaPlayerThunder() {
        MediaPlayer mediaPlayerThunder = MediaPlayer.create(this,R.raw.thunder);
        mediaPlayerThunder.start();
    }

    @Override
    public void mediaPlayerSea() {
        MediaPlayer mediaPlayerSea = MediaPlayer.create(this,R.raw.sea);
        mediaPlayerSea.start();
    }
}


 interface MediaPlayerAudio{
    void mediaPlayerRain();
    void mediaPlayerWinter();
    void mediaPlayerThunder();
    void mediaPlayerSea();
 }

 interface Jason {

    void gettingData();
    void gettingData(double temperature);

 }



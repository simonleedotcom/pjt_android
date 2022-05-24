package com.test.boiler;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class SubActivity extends AppCompatActivity {

    Button btn_back;
    Button btn_on;
    Button btn_off;
    Button btn_set;

    String status_on;
    String temperature_on;
    String status_off;
    String temperature_off;

    static List<Status> data;

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        Intent intent = getIntent();

        btn_back = findViewById(R.id.btn_back);
        btn_on = findViewById(R.id.btn_on);
        btn_off = findViewById(R.id.btn_off);
        btn_set = findViewById(R.id.btn_set);


        //======================================================================================

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String page = "http://10.10.141.56:8080/android/statusList";

                try {
                    URL url = new URL(page);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    StringBuilder stringBuilder = new StringBuilder();

                    if(conn != null){

                        conn.setConnectTimeout(10000);
                        conn.setRequestMethod("GET");
                        conn.setUseCaches(false);
                        if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){

                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

                            while(true){
                                String line = bufferedReader.readLine();
                                if(line == null) break;
                                stringBuilder.append(line + "\n");
                            }
                            bufferedReader.close();
                        }
                        conn.disconnect();
                    }

                    Gson gson = new Gson();

                    Type type = new TypeToken<List<Status>>() {}.getType();
                    data = gson.fromJson(String.valueOf(stringBuilder),type);

                    status_on = data.get(0).getStatus();
                    temperature_on = String.valueOf(data.get(0).getTemperature());
                    status_off = data.get(1).getStatus();
                    temperature_off = String.valueOf(data.get(1).getTemperature());

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } //End of run()

        }); //End of Thread
        thread.start();

        //======================================================================================

        String status = intent.getStringExtra("status");
        String temperature = intent.getStringExtra("temperature");

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SubActivity.this, MainActivity.class);
                intent.putExtra("status_ch", status);
                intent.putExtra("temperature_ch",temperature);
                startActivity(intent);

            }
        });

        btn_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SubActivity.this, SubActivity2.class);
                intent.putExtra("status_ch", status);
                intent.putExtra("temperature_ch",temperature);
                startActivity(intent);
            }
        });


        btn_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SubActivity.this, MainActivity.class);
                intent.putExtra("status_ch", status_on);
                intent.putExtra("temperature_ch",temperature_on);
                startActivity(intent);
            }
        });


        btn_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SubActivity.this, MainActivity.class);
                intent.putExtra("status_ch", status_off);
                intent.putExtra("temperature_ch",temperature_off);
                startActivity(intent);
            }
        });
    }
}




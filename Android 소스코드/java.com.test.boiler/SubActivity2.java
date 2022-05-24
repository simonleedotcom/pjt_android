package com.test.boiler;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SubActivity2 extends AppCompatActivity {

    Button btn_back2;
    Button btn_UpOn;
    Button btn_UpOff;
    EditText et_id;
    String Update;
    TextView id_test;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub2);

        btn_back2 = findViewById(R.id.btn_back2);
        btn_UpOn = findViewById(R.id.btn_test);
        btn_UpOff = findViewById(R.id.btn_test2);
        et_id = findViewById(R.id.et_id);
        id_test = findViewById(R.id.id_test);

        Intent intent = getIntent();

        String status = intent.getStringExtra("status_ch");
        String temperature = intent.getStringExtra("temperature_ch");

        btn_UpOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Update = "http://10.10.141.56:8080/android/updateONTemp/" + et_id.getText().toString();
                new UpdateDB().updateDB(Update);
                id_test.setText(et_id.getText().toString());
            }
        });

        btn_UpOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Update = "http://10.10.141.56:8080/android/updateOFFTemp/" + et_id.getText().toString();
                new UpdateDB().updateDB(Update);
                id_test.setText(et_id.getText().toString());
            }
        });

        btn_back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SubActivity2.this, SubActivity.class);
                intent.putExtra("status",status);
                intent.putExtra("temperature",et_id.getText().toString());
                startActivity(intent);

            }
        });
    }
}


class UpdateDB {

    public void updateDB(String str) {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String page = str;

                try {
                    URL url = new URL(page);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    StringBuilder stringBuilder = new StringBuilder();

                    if(conn != null){

                        conn.setConnectTimeout(10000);
                        conn.setRequestMethod("GET");
                        conn.setUseCaches(false);
                        new InputStreamReader(conn.getInputStream(), "UTF-8");

                        conn.disconnect();
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } //End of run()

        }); //End of Thread
        thread.start();
    }

}
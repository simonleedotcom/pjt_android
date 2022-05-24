package com.test.boiler;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    private TextView tv_status;
    private TextView tv_temp;
    Button btn_menu;

    String status;
    String temperature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();

        tv_status = findViewById(R.id.tv_status);
        tv_temp = findViewById(R.id.tv_temp);
        btn_menu = findViewById(R.id.btn_menu);

        status = intent.getStringExtra("status_ch");
        temperature = intent.getStringExtra("temperature_ch");

        tv_status.setText(status);
        tv_temp.setText(temperature);


        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SubActivity.class);
                intent.putExtra("status",status);
                intent.putExtra("temperature",temperature);
                startActivity(intent);
            }
        });

    }
}
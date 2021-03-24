package com.example.pizzaneck;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageButton;
import android.content.Intent;

import android.os.Bundle;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    LinearLayout realtime_btn, gallery_btn, stretching_btn, byDate_Btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        realtime_btn = (LinearLayout)findViewById(R.id.linearLayout_realtime);
        gallery_btn = (LinearLayout)findViewById(R.id.linearLayout_gallery);
        stretching_btn = (LinearLayout)findViewById(R.id.linearLayout_stretching);
        byDate_Btn = (LinearLayout)findViewById(R.id.linearLayout_byDate);

        stretching_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Stretching.class);
                startActivity(intent);
            }
        });

        realtime_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Realtime.class);
                startActivity(intent);
            }
        });

        gallery_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Gallery.class);
                startActivity(intent);
            }
        });

        byDate_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Graph.class);
                startActivity(intent);
            }
        });

        ImageButton setting_btn = (ImageButton)findViewById(R.id.setting_btn);
        setting_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Setting.class);
                startActivity(intent);
            }
        });
    }
}
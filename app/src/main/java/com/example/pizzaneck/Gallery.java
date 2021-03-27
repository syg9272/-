package com.example.pizzaneck;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class Gallery extends AppCompatActivity {

    LinearLayout album1_btn, album2_btn, album3_btn, album4_btn, album5_btn, album6_btn, album7_btn, album8_btn, album9_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery);

        ImageButton setting_btn_ = (ImageButton)findViewById(R.id.setting_btn);
        setting_btn_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Gallery.this, Setting.class);
                startActivity(intent);
            }
        });

        album1_btn = (LinearLayout)findViewById(R.id.album1);
        album2_btn = (LinearLayout)findViewById(R.id.album2);
        album3_btn = (LinearLayout)findViewById(R.id.album3);
        album4_btn = (LinearLayout)findViewById(R.id.album4);
        album5_btn = (LinearLayout)findViewById(R.id.album5);
        album6_btn = (LinearLayout)findViewById(R.id.album6);
        album7_btn = (LinearLayout)findViewById(R.id.album7);
        album8_btn = (LinearLayout)findViewById(R.id.album8);
        album9_btn = (LinearLayout)findViewById(R.id.album9);

        album1_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Gallery.this, Photo.class);
                startActivity(intent);
            }
        });

        album2_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Gallery.this, Photo.class);
                startActivity(intent);
            }
        });

        album3_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Gallery.this, Photo.class);
                startActivity(intent);
            }
        });

        album4_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Gallery.this, Photo.class);
                startActivity(intent);
            }
        });

        album5_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Gallery.this, Photo.class);
                startActivity(intent);
            }
        });

        album6_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Gallery.this, Photo.class);
                startActivity(intent);
            }
        });

        album7_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Gallery.this, Photo.class);
                startActivity(intent);
            }
        });

        album8_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Gallery.this, Photo.class);
                startActivity(intent);
            }
        });

        album9_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Gallery.this, Photo.class);
                startActivity(intent);
            }
        });
    }
}

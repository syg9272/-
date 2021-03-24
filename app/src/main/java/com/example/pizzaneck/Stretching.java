package com.example.pizzaneck;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageButton;

public class Stretching extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stretching);

        ImageButton setting_btn_ = (ImageButton)findViewById(R.id.setting_btn);
        setting_btn_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Stretching.this, Setting.class);
                startActivity(intent);
            }
        });
    }
}

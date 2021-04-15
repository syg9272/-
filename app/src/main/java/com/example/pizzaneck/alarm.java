package com.example.pizzaneck;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;

import com.google.android.material.navigation.NavigationView;

public class alarm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);    //기존 title 지우기
        actionBar.setDisplayHomeAsUpEnabled(true);//뒤로가기 버튼 생성.

        RadioButton sound1 = (RadioButton) findViewById(R.id.sound1);
        sound1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer player = MediaPlayer.create(alarm.this, R.raw.alien);
                player.start();
            }
        });

        RadioButton sound2 = (RadioButton) findViewById(R.id.sound2);
        sound2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer player = MediaPlayer.create(alarm.this, R.raw.beep_once);
                player.start();
            }
        });

        RadioButton sound3 = (RadioButton) findViewById(R.id.sound3);
        sound3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer player = MediaPlayer.create(alarm.this, R.raw.chaos);
                player.start();
            }
        });

        RadioButton sound4 = (RadioButton) findViewById(R.id.sound4);
        sound4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer player = MediaPlayer.create(alarm.this, R.raw.luna);
                player.start();
            }
        });

        RadioButton sound5 = (RadioButton) findViewById(R.id.sound5);
        sound5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer player = MediaPlayer.create(alarm.this, R.raw.milky_way);
                player.start();
            }
        });

        RadioButton sound6 = (RadioButton) findViewById(R.id.sound6);
        sound6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer player = MediaPlayer.create(alarm.this, R.raw.orion);
                player.start();
            }
        });

        RadioButton sound7 = (RadioButton) findViewById(R.id.sound7);
        sound7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer player = MediaPlayer.create(alarm.this, R.raw.prism);
                player.start();
            }
        });

        RadioButton sound8 = (RadioButton) findViewById(R.id.sound8);
        sound8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer player = MediaPlayer.create(alarm.this, R.raw.signal);
                player.start();
            }
        });

        RadioButton sound9 = (RadioButton) findViewById(R.id.sound9);
        sound9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer player = MediaPlayer.create(alarm.this, R.raw.voyager);
                player.start();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item ){
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}


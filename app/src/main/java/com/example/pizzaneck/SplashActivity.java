package com.example.pizzaneck;

import android.os.Bundle;
import android.content.Intent;
import android.app.Activity;

public class SplashActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        //setContentContentView는 하지 않습니다.
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
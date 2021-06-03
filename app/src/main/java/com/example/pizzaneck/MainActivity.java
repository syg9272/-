package com.example.pizzaneck;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.widget.ImageButton;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import org.w3c.dom.Text;
import java.text.SimpleDateFormat;
import java.util.Date;
public class MainActivity extends AppCompatActivity {

    private long backPressedTime;   //뒤로가기 누른 시간. 뒤로가기 두번으로 종료 위한 변수
    LinearLayout realtime_btn, gallery_btn, stretching_btn, byDate_Btn;
    TextView today, warning, posture, useage;

    private RealtimeDBHelper r_dbHelper;
    private SQLiteDatabase db;
    private SharedPreferences appData;
    private RadioGroup radioGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        realtime_btn = (LinearLayout)findViewById(R.id.linearLayout_realtime);
        gallery_btn = (LinearLayout)findViewById(R.id.linearLayout_gallery);
        stretching_btn = (LinearLayout)findViewById(R.id.linearLayout_stretching);
        byDate_Btn = (LinearLayout)findViewById(R.id.linearLayout_byDate);

        r_dbHelper = new RealtimeDBHelper(MainActivity.this, "Realtime.db", null, 1);
        db = r_dbHelper.getWritableDatabase();
        r_dbHelper.onCreate(db);

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
                Intent intent = new Intent(MainActivity.this, ClassifierActivity.class);
                startActivity(intent);
            }
        });
        gallery_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Setting.class);
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




        appData = getSharedPreferences("appData", MODE_PRIVATE);
        appData.getString("MODE_SETTING","");
        String DARK = appData.getString("MODE_SETTING", "");
        switch (DARK){
            case "LIGHT":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case "DARK":
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            case "DEFAULT":
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                }
                // 안드로이드 10 미만
                else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY);
                }
                break;
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        //오늘 날짜 출력
        today = (TextView)findViewById(R.id.textView_today);
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat format = new SimpleDateFormat("yyyy - MM - dd");
        String date_str = format.format(date);
        today.setText(date_str);

        //총 사용시간 출력
        useage = findViewById(R.id.textView_useage_time);
        int totalTime = r_dbHelper.getTotalTime();

        if(totalTime >= 60){    // 1분 이상
            int minutes;
            int seconds;
            if(totalTime >= 3600){  //1시간 이상
                int hours = totalTime / 3600;
                minutes = totalTime % 3600;
                seconds = minutes % 60;
                minutes = minutes / 60;
                useage.setText(Integer.toString(hours) + "시간 " + Integer.toString(minutes) + "분 " + Integer.toString(seconds) + "초");
            }
            minutes = totalTime / 60;
            seconds = totalTime % 60;
            useage.setText(Integer.toString(minutes) + "분 " + Integer.toString(seconds) + "초");
        } else{
            useage.setText(Integer.toString(totalTime) + "초");
        }


        //경고횟수 출력
        warning = (TextView)findViewById(R.id.warning_number);
        int count = r_dbHelper.warningCount();
        warning.setText(Integer.toString(count) + "회");

        //오늘의 자세 점수 출력
        posture = (TextView)findViewById(R.id.textView_point);
        if(count < 3) {
            posture.setText("EXCELLENT");
            posture.setTextColor(Color.parseColor("#0F9616"));
        } else if(count >= 3 && count < 6) {
            posture.setText("GREAT");
            posture.setTextColor(Color.parseColor("#7FA82F"));
            posture.setTextSize(45);
        } else if(count >= 6 && count < 9) {
            posture.setText("GOOD");
            posture.setTextColor(Color.parseColor("#C4C423"));
            posture.setTextSize(45);
        } else if(count >= 9 && count < 12) {
            posture.setText("SOSO");
            posture.setTextColor(Color.parseColor("#C47C4B"));
            posture.setTextSize(45);
        } else {
            posture.setText("BAD");
            posture.setTextColor(Color.parseColor("#C1432F"));
            posture.setTextSize(45);
        }
    }

    //뒤로가기 눌렀을때 호출. 뒤로가기 두번으로 앱 종료 위함
    @Override
    public void onBackPressed(){
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;
        if(0 <= intervalTime && 2000 >= intervalTime){
            super.onBackPressed();
        } else{
            backPressedTime = tempTime;
            Toast.makeText(this, "한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }
    }
}
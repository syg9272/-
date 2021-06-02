package com.example.pizzaneck;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.SharedPreferences;
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
    TextView today, warning, posture;

    private RealtimeDBHelper r_dbHelper;
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


        //오늘 날짜 출력
        today = (TextView)findViewById(R.id.textView_today);
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat format = new SimpleDateFormat("yyyy - MM - dd");
        String date_str = format.format(date);
        today.setText(date_str);

        //경고횟수 출력
        warning = (TextView)findViewById(R.id.warning_number);
        int count = r_dbHelper.warningCount();
        warning.setText(Integer.toString(count) + "회");

        //오늘의 자세 점수 출력


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
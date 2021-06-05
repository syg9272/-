package com.example.pizzaneck;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.util.Locale;

public class Graph extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navView;
    Toolbar toolbar;
    TextView today, week, first, second, third, fourth, fifth, sixth, seventh, warning_count, evaluation, bad_percent, comparison;
    TextView time[] = new TextView[7];

    private RealtimeDBHelper g_dbHelper;
    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph);
        setToolbar();

        //realtimeDBHelper.java 데이터 불러옴
        g_dbHelper = new RealtimeDBHelper(Graph.this, "Realtime.db", null, 1);

        //디비생성
        db = g_dbHelper.getWritableDatabase();
        g_dbHelper.onCreate(db);
        db.close();

        BarChart barChart = findViewById(R.id.graph_total);

        //일주일 전 날짜 - 오늘 날짜 (0000.00.00 - 0000.00.00)
        //일주일 전 날짜
        week = (TextView)findViewById(R.id.graph_week);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,-6);
        Date date = calendar.getTime();
        SimpleDateFormat format01 = new SimpleDateFormat("yyyy.MM.dd");
        String date_str = format01.format(date);
        week.setText(date_str);

        //오늘 날짜
        today = (TextView)findViewById(R.id.graph_today);
        long now = System.currentTimeMillis();
        Date day = new Date(now);
        SimpleDateFormat format02 = new SimpleDateFormat("yyyy.MM.dd");
        String day_str = format02.format(day);
        today.setText(day_str);

        //자세히보기 내용 >> 일주일 전부터 오늘까지의 날짜 (형식 ex.) 2021.06.03 (목)
        //D-6
        first = (TextView)findViewById(R.id.graph_first_date);
        Calendar calendar_first = Calendar.getInstance();
        calendar_first.add(Calendar.DAY_OF_MONTH,-6);
        Date date_first = calendar_first.getTime();
        SimpleDateFormat format_first = new SimpleDateFormat("yyyy.MM.dd (EE)", Locale.KOREAN);
        String date_first_str = format_first.format(date_first);
        first.setText(date_first_str);

        //D-5
        second = (TextView)findViewById(R.id.graph_second_date);
        Calendar calendar_second = Calendar.getInstance();
        calendar_second.add(Calendar.DAY_OF_MONTH,-5);
        Date date_second = calendar_second.getTime();
        SimpleDateFormat format_second = new SimpleDateFormat("yyyy.MM.dd (EE)", Locale.KOREAN);
        String date_second_str = format_second.format(date_second);
        second.setText(date_second_str);

        //D-4
        third = (TextView)findViewById(R.id.graph_third_date);
        Calendar calendar_third = Calendar.getInstance();
        calendar_third.add(Calendar.DAY_OF_MONTH,-4);
        Date date_third = calendar_third.getTime();
        SimpleDateFormat format_third = new SimpleDateFormat("yyyy.MM.dd (EE)", Locale.KOREAN);
        String date_third_str = format_third.format(date_third);
        third.setText(date_third_str);

        //D-3
        fourth = (TextView)findViewById(R.id.graph_fourth_date);
        Calendar calendar_fourth = Calendar.getInstance();
        calendar_fourth.add(Calendar.DAY_OF_MONTH,-3);
        Date date_fourth = calendar_fourth.getTime();
        SimpleDateFormat format_fourth = new SimpleDateFormat("yyyy.MM.dd (EE)", Locale.KOREAN);
        String date_fourth_str = format_fourth.format(date_fourth);
        fourth.setText(date_fourth_str);

        //D-2
        fifth = (TextView)findViewById(R.id.graph_fifth_date);
        Calendar calendar_fifth = Calendar.getInstance();
        calendar_fifth.add(Calendar.DAY_OF_MONTH,-2);
        Date date_fifth = calendar_fifth.getTime();
        SimpleDateFormat format_fifth = new SimpleDateFormat("yyyy.MM.dd (EE)", Locale.KOREAN);
        String date_fifth_str = format_fifth.format(date_fifth);
        fifth.setText(date_fifth_str);

        //D-1
        sixth = (TextView)findViewById(R.id.graph_sixth_date);
        Calendar calendar_sixth = Calendar.getInstance();
        calendar_sixth.add(Calendar.DAY_OF_MONTH,-1);
        Date date_sixth = calendar_sixth.getTime();
        SimpleDateFormat format_sixth = new SimpleDateFormat("yyyy.MM.dd (EE)", Locale.KOREAN);
        String date_sixth_str = format_sixth.format(date_sixth);
        sixth.setText(date_sixth_str);

        //D-DAY
        seventh = (TextView)findViewById(R.id.graph_seventh_date);
        long now_seventh = System.currentTimeMillis();
        Date day_seventh = new Date(now_seventh);
        SimpleDateFormat format_seventh = new SimpleDateFormat("yyyy.MM.dd (EE)", Locale.KOREAN);
        String day_seventh_str = format_seventh.format(day_seventh);
        seventh.setText(day_seventh_str);


        SimpleDateFormat day1P = new SimpleDateFormat("yyyy-MM-dd");
        String day1 = day1P.format(date_first);
        SimpleDateFormat day2P = new SimpleDateFormat("yyyy-MM-dd");
        String day2 = day2P.format(date_second);
        SimpleDateFormat day3P = new SimpleDateFormat("yyyy-MM-dd");
        String day3 = day3P.format(date_third);
        SimpleDateFormat day4P = new SimpleDateFormat("yyyy-MM-dd");
        String day4 = day4P.format(date_fourth);
        SimpleDateFormat day5P = new SimpleDateFormat("yyyy-MM-dd");
        String day5 = day5P.format(date_fifth);
        SimpleDateFormat day6P = new SimpleDateFormat("yyyy-MM-dd");
        String day6 = day6P.format(date_sixth);
        SimpleDateFormat today_pattern = new SimpleDateFormat("yyyy-MM-dd");
        String today_format = today_pattern.format(day_seventh);

        // 자세히보기 내용 >> 나쁜자세 지속시간 (형식 ex.) 1 min 20 sec
        time[0] = (TextView)findViewById(R.id.first_date_txt);
        time[1] = (TextView)findViewById(R.id.second_date_txt);
        time[2] = (TextView)findViewById(R.id.third_date_txt);
        time[3] = (TextView)findViewById(R.id.fourth_date_txt);
        time[4] = (TextView)findViewById(R.id.fifth_date_txt);
        time[5] = (TextView)findViewById(R.id.sixth_date_txt);
        time[6] = (TextView)findViewById(R.id.seventh_date_txt);

        db = g_dbHelper.getReadableDatabase();
        ArrayList<BarEntry> visitors = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT forward_head_posture_time.seq, using_time.date, " +
                "forward_head_posture_time.time, using_time.time " +
                "FROM forward_head_posture_time INNER JOIN using_time " +
                "ON forward_head_posture_time.date = using_time.date " +
                "WHERE forward_head_posture_time.date " +
                "BETWEEN '" + day1 + "' AND '" + today_format + "'", null);

        // 그래프에 값 넣어줌 (좋은 자세 지속 시간, 나쁜 자세 지속 시간)
        int good_time;
        int using_time_total = 0;
        int good_time_total = 0;
        int bad_time_total = 0;

        ArrayList<String> date_string = new ArrayList<>();
        date_string.add(day1);
        date_string.add(day2);
        date_string.add(day3);
        date_string.add(day4);
        date_string.add(day5);
        date_string.add(day6);
        date_string.add(today_format);

        //cursor 의 데이터를 담아둘 int 배열 0번째가 bad_time, 1번째가 using_time
        int[][] DB_Data = new int[7][2];

        while(cursor.moveToNext()) {
            //bad_time을 날짜에 맞춰서 넣어줌
            DB_Data[date_string.indexOf(cursor.getString(1))][0] += Integer.parseInt(cursor.getString(2));
            //using_time을 날짜에 맞춰서 넣어줌
            if (DB_Data[date_string.indexOf(cursor.getString(1))][1] == 0) {
                DB_Data[date_string.indexOf(cursor.getString(1))][1] = Integer.parseInt(cursor.getString(3));
            }
        }

        int x = 17;
        for(int i=0; i<7; i++){
            //good time 입력
            visitors.add(new BarEntry(x,DB_Data[i][1]-DB_Data[i][0]));
            x++;
            //bad time 입력
            visitors.add(new BarEntry(x,DB_Data[i][0]));
            x+=2;

            if(DB_Data[i][0] >= 60){    // 1분 이상
                int minutes;
                int seconds;
                if(DB_Data[i][0] >= 3600){  //1시간 이상
                    int hours = DB_Data[i][0] / 3600;
                    minutes = DB_Data[i][0] % 3600;
                    seconds = minutes % 60;
                    minutes = minutes / 60;
                    time[i].setText(Integer.toString(hours) + "hr " + Integer.toString(minutes) + "min " + Integer.toString(seconds) + "sec");
                }
                minutes = DB_Data[i][0] / 60;
                seconds = DB_Data[i][0] % 60;
                time[i].setText(Integer.toString(minutes) + "min " + Integer.toString(seconds) + "sec");
            } else {
                time[i].setText(DB_Data[i][0] + "sec");
            }
            bad_time_total += DB_Data[i][0];
            good_time_total += DB_Data[i][1] - DB_Data[i][0];
        }
        db.close();

        //그래프 세팅
        BarDataSet barDataSet = new BarDataSet(visitors, "자세 지속시간");

        Legend legend = barChart.getLegend(); //범례 설정
        legend.setEnabled(false); //범례 비활성화
        barChart.animateY(5000); //화면 시작 시 애니메이트 설정

        //그래프 바 색상, 값 텍스트 설정
        barDataSet.setColors(getResources().getColor(R.color.graph_good),getResources().getColor(R.color.graph_bad));
        barDataSet.setValueTextColor(getResources().getColor(R.color.graph_value));
        barDataSet.setValueTextSize(0f);
        BarData barData = new BarData(barDataSet);

        barChart.getDescription().setEnabled(false); //차트 설명 비활성화
        barChart.setFitBars(true); //차트 비율 활성화
        barChart.setData(barData);
        barChart.getAxisLeft().setLabelCount(7, false);
        barChart.setTouchEnabled(false); //차트 터치 막기

        //X축 속성
        XAxis xAxis = barChart.getXAxis();
        xAxis.setCenterAxisLabels(true); //x축 레이블이 그룹 중앙에 오도록 설정
        xAxis.setTextSize(12);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); //x축 하단에 표시
        xAxis.setTextColor(getResources().getColor(R.color.main_color)); //X축 레이블 색상
        xAxis.enableGridDashedLine(0, 24, 0); //그리드 라인 설정
        xAxis.setEnabled(false); //x축 값 비활성화

        //Y축 속성
        YAxis yLAxis = barChart.getAxisLeft();
        yLAxis.setTextColor(getResources().getColor(R.color.main_color)); //Y축 레이블 색상
        yLAxis.setGridColor(getResources().getColor(R.color.main_color)); //Y축 그리드 색상
        yLAxis.setDrawLabels(true);
        yLAxis.setDrawZeroLine(true);
        yLAxis.setDrawZeroLine(true);

        //오른쪽 y축 레이블 삭제
        YAxis yRAxis = barChart.getAxisRight();
        yRAxis.setDrawLabels(false);
        yRAxis.setDrawAxisLine(false);
        yRAxis.setDrawGridLines(false);

        //이번주 당신의 자세는
        //총 경고 횟수(3단위)의 값이 커질 때마다 등급 하락
        evaluation = (TextView)findViewById(R.id.graph_evaluation);
        int sum = g_dbHelper.warning_Total_Count_week();
        if(sum < 21){
            evaluation.setText("Excellent");
            evaluation.setTextColor(getResources().getColor(R.color.graph_excellent_color));
        }
        else if(sum >= 21 && sum < 42){
            evaluation.setText("Great");
            evaluation.setTextColor(getResources().getColor(R.color.graph_great_color));
        }
        else if(sum >= 42 && sum < 63){
            evaluation.setText("Good");
            evaluation.setTextColor(getResources().getColor(R.color.graph_good_color));
        }
        else if(sum >= 63 && sum < 84){
            evaluation.setText("So so");
            evaluation.setTextColor(getResources().getColor(R.color.graph_soso_color));
        }
        else {
            evaluation.setText("Bad");
            evaluation.setTextColor(getResources().getColor(R.color.graph_bad_color));
        }

        //총 알림 횟수는
        warning_count = (TextView)findViewById(R.id.graph_count);
        warning_count.setText(sum + "회");

        //자세히보기 버튼
        btn1 = (Button) findViewById(R.id.graph_detail);

        flag = true;
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag == true){
                    btn1.setText("요약하기 ▲");
                    changeView ( 0 );
                    flag = false;

                }
                else {
                    btn1.setText("자세히보기 ▼");
                    changeView ( 1 );
                    flag = true;
                }
            }
        });

        //나쁜 자세 비율은
        double percent = ((double)bad_time_total/((double)good_time_total + (double)bad_time_total)) * 100.0;

        String result = String.format("%.2f", percent);
        bad_percent = (TextView)findViewById(R.id.percent);
        bad_percent.setText(result + "%");

        //저번주에 비해
        comparison = (TextView)findViewById(R.id.grade_comparison);
        int sum_last_week = g_dbHelper.warning_Total_Count_last_week();
        int grade_result = sum_last_week - sum;

        if(sum_last_week == 0){
            comparison.setText("이런, 데이터가 없어요..");
            comparison.setTextColor(getResources().getColor(R.color.OnBackground));
        }
        else if(grade_result > 9){
            ImageView imageView = findViewById(R.id.graph_image_Excellent);
            comparison.setText("아주 좋아졌어요  ");
            imageView.setVisibility(View.VISIBLE);
            comparison.setTextColor(getResources().getColor(R.color.graph_excellent_color));
        }
        else if(grade_result <= 9 && grade_result > 0){
            ImageView imageView = findViewById(R.id.graph_image_Great);
            comparison.setText("좋아지고 있네요  ");
            imageView.setVisibility(View.VISIBLE);
            comparison.setTextColor(getResources().getColor(R.color.graph_great_color));
        }
        else if(grade_result == 0){
            ImageView imageView = findViewById(R.id.graph_image_Good);
            comparison.setText("똑같아요  ");
            imageView.setVisibility(View.VISIBLE);
            comparison.setTextColor(getResources().getColor(R.color.graph_good_color));
        }
        else if(grade_result >= -9 && grade_result < 0){
            ImageView imageView = findViewById(R.id.graph_image_Soso);
            comparison.setText("조금 더 노력하세요  ");
            imageView.setVisibility(View.VISIBLE);
            comparison.setTextColor(getResources().getColor(R.color.graph_soso_color));
        }
        else {
            ImageView imageView = findViewById(R.id.graph_image_Bad);
            comparison.setText("노력이 필요합니다  ");
            imageView.setVisibility(View.VISIBLE);
            comparison.setTextColor(getResources().getColor(R.color.graph_bad_color));
        }
    }

    //자세히보기 버튼 클릭시 이벤트 처리
    public Button btn1;
    public boolean flag;

    private void changeView(int index){
        TableLayout tableLayout = findViewById(R.id.graph_table);
        switch (index) {
            case 0:
                tableLayout.setVisibility(View.VISIBLE);
                break;
            case 1:
                tableLayout.setVisibility(View.GONE);
                break;
        }
    }

    /* 툴바 및 툴바기능 설정 함수.
     * onCreate 에서 호출
     * 클래스 내 DrawerLayout drawerLayout; NavigationView navView; Toolbar toolbar; 선언 필요
     */
    protected void setToolbar(){
        //툴바 설정
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);    //기존 title 지우기
        actionBar.setDisplayHomeAsUpEnabled(true);      //뒤로가기 버튼 생성. 이 버튼을 메뉴바 버튼으로 사용
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu); //뒤로가기 버튼 아이콘 -> 메뉴 아이콘 변경


        drawerLayout = findViewById(R.id.drawer_layout);
        navView = findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();

                int id = menuItem.getItemId();

                if(id == R.id.home){
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    //액티비티 스택제거 -> 메인에서는 뒤로가기 누르면 이전 액티비티로 가지 않고 종료됨.
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                else if(id == R.id.realtime){
                    Intent intent = new Intent(getApplicationContext(), ClassifierActivity.class);
                    startActivity(intent);
                }
                else if(id == R.id.stretching){
                    Intent intent = new Intent(getApplicationContext(), Stretching.class);
                    startActivity(intent);
                }
                else if(id == R.id.graph){
                    Intent intent = new Intent(getApplicationContext(), Graph.class);
                    startActivity(intent);
                }

                menuItem.setChecked(false);
                return true;
            }
        });
    }

    //툴바 우측에 버튼 생성 (설정버튼)
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_option_btn, menu);
        return true;
    }

    //툴바에 버튼 클릭 시
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            //네비게이션드로어
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            //설정버튼
            case R.id.setting:
                Intent intent = new Intent(getApplicationContext(), Setting.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    //네비게이션 열려있을때 뒤로가기로 버튼 닫기
    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }
}
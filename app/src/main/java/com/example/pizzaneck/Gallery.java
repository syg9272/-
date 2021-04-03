package com.example.pizzaneck;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.google.android.material.navigation.NavigationView;

public class Gallery extends AppCompatActivity {

    LinearLayout album1_btn, album2_btn, album3_btn, album4_btn, album5_btn, album6_btn, album7_btn, album8_btn, album9_btn;

    DrawerLayout drawerLayout;
    NavigationView navView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery);
        setToolbar();



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
    /* 툴바 및 툴바기능 설정 함수.
     * onCreate에서 호출
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
                    Intent intent = new Intent(getApplicationContext(), Realtime.class);
                    startActivity(intent);
                }
                else if(id == R.id.gallery){
                    Intent intent = new Intent(getApplicationContext(), Gallery.class);
                    startActivity(intent);

                }
                else if(id == R.id.stretching){
                    Intent intent = new Intent(getApplicationContext(), Stretching.class);
                    startActivity(intent);
                }
                else if(id == R.id.graph){
                    Intent intent = new Intent(getApplicationContext(), Gallery.class);
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

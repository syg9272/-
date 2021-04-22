package com.example.pizzaneck;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.widget.Toast;
import android.widget.LinearLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.graphics.Color;
import android.graphics.Paint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class Checklist extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navView;
    Toolbar toolbar;

    //캘린더//
    /*연/월 텍스트뷰*/
    private TextView tvDate;

    private TextView check;

    /*그리드뷰 어댑터 */
    private GridAdapter gridAdapter;

    /* 일 저장 할 리스트 */
    private ArrayList<String> dayList;

    /*그리드뷰 */
    private GridView gridView;

    /* 캘린더 변수 */
    private Calendar mCal;

    private int click;

    private long btnPressTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checklist);
        setToolbar();

        //디비
        DBHelper helper;
        SQLiteDatabase db;
        helper = new DBHelper(Checklist.this, "newdb.db", null, 1);
        db = helper.getWritableDatabase();
        helper.onCreate(db);

        //캘린더//
        tvDate = (TextView)findViewById(R.id.tv_date);
        gridView = (GridView)findViewById(R.id.gridview);

        // 오늘에 날짜를 세팅 해준다.
        long now = System.currentTimeMillis();
        final Date date = new Date(now);
        //연,월,일을 따로 저장
        final SimpleDateFormat curYearFormat = new SimpleDateFormat("yyyy", Locale.KOREA);
        final SimpleDateFormat curMonthFormat = new SimpleDateFormat("MM", Locale.KOREA);
        final SimpleDateFormat curDayFormat = new SimpleDateFormat("dd", Locale.KOREA);

        //현재 날짜 텍스트뷰에 뿌려줌
        tvDate.setText(curYearFormat.format(date) + "년   " + curMonthFormat.format(date) + " 월");

        //gridview 요일 표시
        dayList = new ArrayList<String>();
        dayList.add("Sun");
        dayList.add("Mon");
        dayList.add("Tue");
        dayList.add("Wed");
        dayList.add("Thu");
        dayList.add("Fri");
        dayList.add("Sat");

        mCal = Calendar.getInstance();

        //이번달 1일 무슨요일인지 판단 mCal.set(Year,Month,Day)
        mCal.set(Integer.parseInt(curYearFormat.format(date)), Integer.parseInt(curMonthFormat.format(date)) - 1, 1);
        int dayNum = mCal.get(Calendar.DAY_OF_WEEK);
        //1일 - 요일 매칭 시키기 위해 공백 add
        for (int i = 1; i < dayNum; i++) {
            dayList.add("");
        }
        setCalendarDate(mCal.get(Calendar.MONTH) + 1);

        gridAdapter = new GridAdapter(getApplicationContext(), dayList);
        gridView.setAdapter(gridAdapter);

        click = 0;

        gridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                check = (TextView) gridView.getChildAt(position).findViewById(R.id.tv_item_gridview);
                switch(click) {
                    case 0:
                        click++;
                        if(check.getCurrentTextColor() == 0xff808080 ||
                                check.getCurrentTextColor() == 0xffff0000 ||
                                check.getCurrentTextColor() == 0xff005FFF ||
                                check.getCurrentTextColor() == 0xff093A7A) {
                            if (position < 7 || (dayList.get(position)).equals("")) {
                            } else {
                                Toast.makeText(getApplicationContext(), "" + dayList.get(position) + "일에 체크되었습니다😊", Toast.LENGTH_SHORT).show();
                                check.setTextColor(getResources().getColor(R.color._000000));
                                gridView.getChildAt(position).setBackgroundColor(Color.parseColor("#afeeee"));
                                break;
                            }
                        }else {
                            if (position < 7 || (dayList.get(position)).equals("")) {
                            } else {
                                if (System.currentTimeMillis() > btnPressTime + 1000) {
                                    btnPressTime = System.currentTimeMillis();
                                    Toast.makeText(getApplicationContext(), "한번 더 터치하면 체크 해제됩니다.",
                                            Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                if (System.currentTimeMillis() <= btnPressTime + 1000) {
                                    Toast.makeText(getApplicationContext(), "" + dayList.get(position) + "일에 체크 해제되었습니다😥", Toast.LENGTH_SHORT).show();
                                    if (position % 7 == 0) {
                                        check.setTextColor(getResources().getColor(R.color.RED));
                                    } else if (position % 7 == 6) {
                                        check.setTextColor(getResources().getColor(R.color.BLUE));
                                    }else {
                                        check.setTextColor(getResources().getColor(R.color.color_day));
                                    }
                                    //해당 날짜 텍스트 컬러,배경 변경
                                    mCal = Calendar.getInstance();
                                    //오늘 day 가져옴
                                    Integer today = mCal.get(Calendar.DAY_OF_MONTH);
                                    String sToday = String.valueOf(today);
                                    if (sToday.equals(dayList.get(position))) { //오늘 day 텍스트 컬러 변경
                                        check.setTextColor(getResources().getColor(R.color.main));
                                    }
                                    gridView.getChildAt(position).setBackgroundColor(Color.parseColor("#00000000"));
                                }
                            }
                        }
                        break;
                    case 1:
                        click = 0;
                        if(check.getCurrentTextColor() == 0xff000000) {
                            if (position < 7 || (dayList.get(position)).equals("")) {
                            } else {
                                if (System.currentTimeMillis() > btnPressTime + 1000) {
                                    btnPressTime = System.currentTimeMillis();
                                    Toast.makeText(getApplicationContext(), "한번 더 터치하면 체크 해제됩니다.",
                                            Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                if (System.currentTimeMillis() <= btnPressTime + 1000) {
                                    Toast.makeText(getApplicationContext(), "" + dayList.get(position) + "일에 체크 해제되었습니다😥", Toast.LENGTH_SHORT).show();
                                    if (position % 7 == 0) {
                                        check.setTextColor(getResources().getColor(R.color.RED));
                                    } else if (position % 7 == 6) {
                                        check.setTextColor(getResources().getColor(R.color.BLUE));
                                    }else {
                                        check.setTextColor(getResources().getColor(R.color.color_day));
                                    }
                                    //해당 날짜 텍스트 컬러,배경 변경
                                    mCal = Calendar.getInstance();
                                    //오늘 day 가져옴
                                    Integer today = mCal.get(Calendar.DAY_OF_MONTH);
                                    String sToday = String.valueOf(today);
                                    if (sToday.equals(dayList.get(position))) { //오늘 day 텍스트 컬러 변경
                                        check.setTextColor(getResources().getColor(R.color.main));
                                    }
                                    gridView.getChildAt(position).setBackgroundColor(Color.parseColor("#00000000"));
                                }
                                break;
                            }
                        }else{
                            if (position < 7 || (dayList.get(position)).equals("")) {
                            } else {
                                Toast.makeText(getApplicationContext(), "" + dayList.get(position) + "일에 체크되었습니다😊", Toast.LENGTH_SHORT).show();
                                check.setTextColor(getResources().getColor(R.color._000000));
                                gridView.getChildAt(position).setBackgroundColor(Color.parseColor("#afeeee"));
                            }
                        }
                        break;
                    default:
                        click = 0;
                        break;
                }



            }
        });
    }

    /**
     * 해당 월에 표시할 일 수 구함
     *
     * @param month
     */
    private void setCalendarDate(int month) {
        mCal.set(Calendar.MONTH, month - 1);

        for (int i = 0; i < mCal.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
            dayList.add("" + (i + 1));
        }
    }

    /**
     * 그리드뷰 어댑터
     *
     */
    private class GridAdapter extends BaseAdapter {

        private final List<String> list;

        private final LayoutInflater inflater;

        /**
         * 생성자
         *
         * @param context
         * @param list
         */
        public GridAdapter(Context context, List<String> list) {
            this.list = list;
            this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public String getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_calendar_gridview, parent, false);
                holder = new ViewHolder();

                holder.tvItemGridView = (TextView)convertView.findViewById(R.id.tv_item_gridview);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder)convertView.getTag();
            }
            holder.tvItemGridView.setText("" + getItem(position));

            if (position % 7 == 0) {
                holder.tvItemGridView.setTextColor(getResources().getColor(R.color.RED));
            } else if (position % 7 == 6) {
                holder.tvItemGridView.setTextColor(getResources().getColor(R.color.BLUE));
            }

            //해당 날짜 텍스트 컬러,배경 변경
            mCal = Calendar.getInstance();
            //오늘 day 가져옴
            Integer today = mCal.get(Calendar.DAY_OF_MONTH);
            String sToday = String.valueOf(today);
            if (sToday.equals(getItem(position))) { //오늘 day 텍스트 컬러 변경
                holder.tvItemGridView.setPaintFlags(holder.tvItemGridView.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
                holder.tvItemGridView.setTextColor(getResources().getColor(R.color.main));

            }



            return convertView;
        }
    }

    private class ViewHolder {
        TextView tvItemGridView;
    }

    /*그리드 뷰 클릭이벤트*/


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

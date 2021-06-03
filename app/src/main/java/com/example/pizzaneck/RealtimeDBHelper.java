package com.example.pizzaneck;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.lang.String;

public class RealtimeDBHelper extends SQLiteOpenHelper {

    public RealtimeDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //테이블 생성
        // 거북목 자세 시간 테이블 (순번, 날짜, 지속시간)
        String sql = "CREATE TABLE if not exists forward_head_posture_time ("
                + "seq INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "date DATE NOT NULL,"
                + "time INTEGER NOT NULL);";
        db.execSQL(sql);

        sql = "CREATE TABLE if not exists using_time("
                + "date DATE PRIMARY KEY,"
                + "time INTEGER NOT NULL);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE if exists forward_head_posture_time";
        db.execSQL(sql);
        sql = "DROP TABLE IF EXISTS using_time";
        db.execSQL(sql);
        onCreate(db);
    }

    // 나쁜 자세 지속시간 insert
    void insertTime(SQLiteDatabase db, long time){
        ContentValues contentValues = new ContentValues();

        //오늘 날짜
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        contentValues.put("date", format.format(date));
        contentValues.put("time", time);
        long result = db.insert("forward_head_posture_time", null, contentValues);

        if(result == -1){
            Log.d("##Database## ", "Insert Failed");
        }
        else{
            Log.d("##Database## ", "Insert Success");
        }
    }

    // 오늘 경고횟수 조회
    public int warningCount() {
        int count = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM forward_head_posture_time WHERE date=(SELECT date('now', 'localtime'))", null);
        count = cursor.getCount();
        db.close();
        return count;
    }

    // 이번주 총 알림 횟수
    public int warning_Total_Count_week() {

        //이번주 첫 날
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,-6);
        Date date = calendar.getTime();
        SimpleDateFormat week_ago_pattern = new SimpleDateFormat("yyyy-MM-dd");
        String week_ago_format = week_ago_pattern.format(date);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM forward_head_posture_time " +
                "WHERE date " +
                "BETWEEN '" + week_ago_format + "' AND '" + date + "'", null);

        int count = 0;
        int sum = 0;
        while(cursor.moveToNext()){
            count = cursor.getCount();
            sum += count;
        }

        db.close();
        return sum;
    }

    //저번주 총 알림 횟수
    public int warning_Total_Count_last_week() {

        //저번주 첫 날
        Calendar calendar_last_week_first = Calendar.getInstance();
        calendar_last_week_first.add(Calendar.DAY_OF_MONTH,-13);
        Date date_last_week_first = calendar_last_week_first.getTime();
        SimpleDateFormat last_week_first_pattern = new SimpleDateFormat("yyyy-MM-dd");
        String last_week_first_format = last_week_first_pattern.format(date_last_week_first);

        //저번주 마지막 날
        Calendar calendar_last_week_seventh = Calendar.getInstance();
        calendar_last_week_seventh.add(Calendar.DAY_OF_MONTH,-7);
        Date date_last_week_seventh = calendar_last_week_seventh.getTime();
        SimpleDateFormat last_week_seventh_pattern = new SimpleDateFormat("yyyy-MM-dd");
        String last_week_seventh_format = last_week_seventh_pattern.format(date_last_week_seventh);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM forward_head_posture_time " +
                "WHERE date " +
                "BETWEEN '" + last_week_first_format + "' AND '" + last_week_seventh_format + "'", null);

        int count = 0;
        int sum = 0;
        while(cursor.moveToNext()){
            count = cursor.getCount();
            sum += count;
        }

        db.close();
        return sum;
    }

    // 오늘 총 사용시간 삽입
    public void insertUsingTime(int time){
        int table_time; //테이블에 들어있던 시간
        ContentValues contentValues = new ContentValues();

        //오늘 날짜
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT time FROM using_time WHERE date=(SELECT date('now', 'localtime'))", null);

        if(cursor.getCount() == 0) {   // 오늘 날짜가 테이블에 없으면 insert
            contentValues.put("date", format.format(date));
            contentValues.put("time", time);
            long result = db.insert("using_time", null, contentValues);

            if(result == -1){
                Log.d("##TOTAL TIME INSERT ", "Failed");
            }
            else{
                Log.d("##TOTAL TIME INSERT ", "Success");
            }
        }
        else {  // 오늘 날짜가 테이블에 있으면 더해서 update
            cursor.moveToFirst();
            table_time = cursor.getInt(0);
            time += table_time;
            Log.d("time", Integer.toString(time));
            Log.d("table_time", Integer.toString(table_time));
            String query = "UPDATE using_time SET time=" + time + " WHERE date= '" + format.format(date) + "'";
            Log.d("QUERY", query);
            try{
                db.execSQL(query);
                Log.d("##TOTAL TIME UPDATE", "SUCCESS");
            } catch (Exception e){
                Log.d("##TOTAL TIME UPDATE", "FAILED");
                e.printStackTrace();
            }
        }
        cursor.close();
        db.close();
    }

    //오늘 사용시간 조회
    public int getTotalTime(){
        int result = 0;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT time FROM using_time WHERE date=(SELECT date('now', 'localtime'))", null);

        if(cursor.getCount() > 0){
            result = cursor.getInt(0);

        }
        cursor.close();
        db.close();
        Log.d("getTotalTime",Integer.toString(result));
        return 0;
    }
}
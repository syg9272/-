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
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //mytable자리 본인이 지정한 테이블 명으로 수정
        String sql = "DROP TABLE if exists forward_head_posture_time";

        db.execSQL(sql);
        onCreate(db);
    }

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

    public int warningCount() {
        int count = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT count(*) FROM forward_head_posture_time WHERE date=(SELECT date('now', 'localtime'))", null);
        count = cursor.getCount();
        db.close();
        return count;
    }
}

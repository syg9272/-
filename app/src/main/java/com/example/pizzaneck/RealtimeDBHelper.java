package com.example.pizzaneck;

import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;

public class RealtimeDBHelper extends SQLiteOpenHelper {
    public RealtimeDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //테이블 생성
        //mytable에 테이블 명 넣고 txt에 넣고 싶은 컬럼 넣고 타입도 text대신 맞는 걸로 넣으세용
        String sql = "CREATE TABLE if not exists mytable ("
                + "_id integer primary key autoincrement,"
                + "txt text);";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //mytable자리 본인이 지정한 테이블 명으로 수정
        String sql = "DROP TABLE if exists mytable";

        db.execSQL(sql);
        onCreate(db);
    }
}

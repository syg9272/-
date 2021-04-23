package com.example.pizzaneck;

import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;

public class ChecklistDBHelper extends SQLiteOpenHelper {
    public ChecklistDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE if not exists checklist ("
                + "_id integer primary key autoincrement,"
                + "position INTEGER);";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE if exists mytable";

        db.execSQL(sql);
        onCreate(db);
    }
}

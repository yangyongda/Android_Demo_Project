package com.fjsd.yyd.contentproviderdemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2017/4/14 0014.
 */

public class DBOpenHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "person.db"; //数据库名称
    private static final int DATABASE_VERSION = 1;//数据库版本

    public DBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //创建数据表
        sqLiteDatabase.execSQL("CREATE TABLE person (_id integer primary key autoincrement, name varchar(20), age varchar(10))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS person");
        onCreate(sqLiteDatabase);
    }
}

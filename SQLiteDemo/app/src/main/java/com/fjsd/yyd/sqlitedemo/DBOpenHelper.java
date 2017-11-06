package com.fjsd.yyd.sqlitedemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2017/11/5 0005.
 */

public class DBOpenHelper extends SQLiteOpenHelper {
    //版本
    private static final int DATABASE_VERSION = 1;
    //数据库名
    private static final String DATABASE_NAME = "myTest.db";
    //表名
    public static final String TABLE_NAME = "Orders";

    public DBOpenHelper(Context context) {
        //创建数据库
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //创建表
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table if not exists " + TABLE_NAME + " (Id integer primary key, Book text, Price integer, Author text)";
        sqLiteDatabase.execSQL(sql);
    }
    //更新数据库版本
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }
}

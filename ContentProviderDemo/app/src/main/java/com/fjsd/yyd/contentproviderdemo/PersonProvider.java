package com.fjsd.yyd.contentproviderdemo;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by Administrator on 2017/4/14 0014.
 */

public class PersonProvider extends ContentProvider {

    private DBOpenHelper dbOpenHelper;
    private static final UriMatcher MATCHER = new UriMatcher(
            UriMatcher.NO_MATCH);
    private static final int PERSONS = 1;
    private static final int PERSON = 2;
    static {
        //添加URI，其他应用通过这个URI来访问该应用的数据
        MATCHER.addURI("com.fjsd.yyd.personProvider", "person", PERSONS);
        MATCHER.addURI("com.fjsd.yyd.personProvider", "person/#", PERSON);
    }
    @Override
    public boolean onCreate() {
        dbOpenHelper = new DBOpenHelper(this.getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        switch (MATCHER.match(uri)) {   //匹配查询的URI
            case PERSONS:
                return db.query("person", strings, s, strings1,
                        null, null, s1);

            case PERSON:
                long id = ContentUris.parseId(uri);
                String where = "_id=" + id;
                if (s != null && !"".equals(s)) {
                    where = s + " and " + where;
                }
                return db.query("person", strings, where, strings1, null,
                        null, s1);

            default:
                throw new IllegalArgumentException("Unkwon Uri:" + uri.toString());
        }
    }

    //返回数据的MIME类型
    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (MATCHER.match(uri)) {
            case PERSONS:
                return "vnd.android.cursor.dir/person";

            case PERSON:
                return "vnd.android.cursor.item/person";

            default:
                throw new IllegalArgumentException("Unkwon Uri:" + uri.toString());
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        switch (MATCHER.match(uri)) {
            case PERSONS:
                // 特别说一下第二个参数是当name字段为空时，将自动插入一个NULL。
                long rowid = db.insert("person", "name", contentValues);
                Uri insertUri = ContentUris.withAppendedId(uri, rowid);// 得到代表新增记录的Uri
                this.getContext().getContentResolver().notifyChange(uri, null);
                return insertUri;

            default:
                throw new IllegalArgumentException("Unkwon Uri:" + uri.toString());
        }
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        int count = 0;
        switch (MATCHER.match(uri)) {
            case PERSONS:
                count = db.delete("person", s, strings);
                return count;

            case PERSON:
                long id = ContentUris.parseId(uri);
                String where = "_id=" + id;
                if (s != null && !"".equals(s)) {
                    where = s + " and " + where;
                }
                count = db.delete("person", where, strings);
                return count;

            default:
                throw new IllegalArgumentException("Unkwon Uri:" + uri.toString());
        }
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        int count = 0;
        switch (MATCHER.match(uri)) {
            case PERSONS:
                count = db.update("person", contentValues, selection, selectionArgs);
                return count;
            case PERSON:
                long id = ContentUris.parseId(uri);
                String where = "_id=" + id;
                if (selection != null && !"".equals(selection)) {
                    where = selection + " and " + where;
                }
                count = db.update("person", contentValues, where, selectionArgs);
                return count;
            default:
                throw new IllegalArgumentException("Unkwon Uri:" + uri.toString());
        }
    }
}

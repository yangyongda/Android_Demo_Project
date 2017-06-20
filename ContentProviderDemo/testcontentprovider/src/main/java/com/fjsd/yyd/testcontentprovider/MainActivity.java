package com.fjsd.yyd.testcontentprovider;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
/*
* 创建ContentResolver对象，并调用对应的方法进行增删改查。
* */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button add;
    private Button find;
    private RecyclerView recyclerView;
    private ContentResolver contentResolver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contentResolver = getContentResolver();

        add = (Button)findViewById(R.id.add);
        find = (Button)findViewById(R.id.find);
        recyclerView = (RecyclerView)findViewById(R.id.recycleView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        add.setOnClickListener(this);
        find.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add:
                Uri insertUri = Uri.parse("content://com.fjsd.yyd.personProvider/person");
                ContentValues values = new ContentValues();
                values.put("name", "wangkuifeng");
                values.put("age", 23);
                Uri uri = contentResolver.insert(insertUri, values);
                Toast.makeText(MainActivity.this, "添加完成", Toast.LENGTH_SHORT).show();
                break;
            case R.id.find:
                List<Person> persons = new ArrayList<>();
                Uri selectUri = Uri.parse("content://com.fjsd.yyd.personProvider/person");
                Cursor cursor=contentResolver.query(selectUri, null, null, null, null);
                while(cursor.moveToNext()){
                    Person person=new Person();
                    int id=cursor.getInt(cursor.getColumnIndex("_id"));
                    String name=cursor.getString(cursor.getColumnIndex("name"));
                    String age=cursor.getString(cursor.getColumnIndex("age"));
                    person.set_id(id);
                    person.setName(name);
                    person.setAge(age);
                    persons.add(person);
                }
                recyclerView.setAdapter(new NormalRecyclerViewAdapter(this,persons));
                break;
        }
    }
}

package com.fjsd.yyd.sqlitedemo;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
/*
* 1.创建一个继承在SQLiteOpenHelper的类，并重写onCreate()和onUpgrade()方法
* 2.写一个工具类，包含insert，update, delete, query等方法
* 3.利用工具类来进行数据库操作
*
* 注意：SQLiteOpenHelper主要用来创建数据库和获取数据库对象，以便进行操作。
* */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText mId;
    private EditText mBook;
    private EditText mPrice;
    private EditText mAuthor;
    private TextView mContent;
    private Button mInsert;
    private Button mUpdate;
    private Button mDetele;
    private Button mQuery;
    private DBUtil mDBUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        mDBUtil = new DBUtil(this);
    }

    public void initView(){
        mId = (EditText) findViewById(R.id.id);
        mBook = (EditText)findViewById(R.id.book);
        mPrice = (EditText)findViewById(R.id.price);
        mAuthor = (EditText)findViewById(R.id.author);
        mContent = (TextView)findViewById(R.id.content);
        mInsert = (Button)findViewById(R.id.insert);
        mDetele = (Button)findViewById(R.id.delete);
        mUpdate = (Button)findViewById(R.id.update);
        mQuery = (Button)findViewById(R.id.query);

        mInsert.setOnClickListener(this);
        mUpdate.setOnClickListener(this);
        mDetele.setOnClickListener(this);
        mQuery.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.insert:
                mDBUtil.insert(Integer.parseInt(mId.getText().toString()), mBook.getText().toString(),
                        Integer.parseInt(mPrice.getText().toString()), mAuthor.getText().toString() );
                Toast.makeText(this, "插入数据成功",Toast.LENGTH_SHORT).show();
                break;
            case R.id.update:
                mDBUtil.update(Integer.parseInt(mId.getText().toString()), mBook.getText().toString(),
                        Integer.parseInt(mPrice.getText().toString()), mAuthor.getText().toString() );
                Toast.makeText(this, "更新数据成功",Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete:
                mDBUtil.delete(Integer.parseInt(mId.getText().toString()));
                Toast.makeText(this, "删除数据成功",Toast.LENGTH_SHORT).show();
                break;
            case R.id.query:
                Cursor mCursor = mDBUtil.Query();
                StringBuilder result = new StringBuilder();
                if(mCursor != null && mCursor.moveToFirst()){
                    do{
                        int Id = mCursor.getInt(mCursor.getColumnIndex("Id"));
                        String book = mCursor.getString(mCursor.getColumnIndex("Book"));
                        int price = mCursor.getInt(mCursor.getColumnIndex("Price"));
                        String author = mCursor.getString(mCursor.getColumnIndex("Author"));
                        result.append(Id).append(" ").append(book).append(" ").append(price).append(" ").append(author).append("\n");
                    }while(mCursor.moveToNext());
                    mContent.setText(result);
                }
                break;
        }
    }
}

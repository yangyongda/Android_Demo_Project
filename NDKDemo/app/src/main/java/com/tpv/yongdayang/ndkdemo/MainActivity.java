package com.tpv.yongdayang.ndkdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText firstNum;
    private EditText secondNum;
    private Button result;
    private JniUtil jniUtil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        jniUtil = new JniUtil();

        initUI();


    }

    private void initUI() {
        firstNum = (EditText)findViewById(R.id.firstNum);
        secondNum = (EditText)findViewById(R.id.secondNum);
        result = (Button)findViewById(R.id.result);

        result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a = firstNum.getText().toString();
                String b = secondNum.getText().toString();

                if(!TextUtils.isEmpty(a) && !TextUtils.isEmpty(b)){
                    int result = jniUtil.add(Integer.parseInt(a) ,Integer.parseInt(b));
                    Toast.makeText(MainActivity.this, "result:  "+ result, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

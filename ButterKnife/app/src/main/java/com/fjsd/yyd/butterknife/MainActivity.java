package com.fjsd.yyd.butterknife;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.login)
    Button login;
    @BindView(R.id.cancel)
    Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //光标要在布局文件名上右键才能显示Generate ButterKnife Injections
        setContentView(R.layout.activity_main);
        //在setContentView之后才能bind
        ButterKnife.bind(this);


    }

    @OnClick({R.id.login, R.id.cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                title.setText("Login .......!");
                break;
            case R.id.cancel:
                title.setText("Cancel .......!");
                break;
        }
    }
}

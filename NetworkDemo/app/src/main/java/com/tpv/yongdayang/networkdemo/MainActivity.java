package com.tpv.yongdayang.networkdemo;

import android.net.wifi.ScanResult;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.tpv.yongdayang.networkdemo.adapter.WifiAdapter;
import com.tpv.yongdayang.networkdemo.util.WifiUtil;

import java.util.List;
//WIFI Demo
/*
* Wifi笔记：
* 1、WifiLock:屏幕关闭后一段时间wifi是否关闭。WifiLock开启后屏幕关闭后wifi会一直连接着。
* 2、ScanResult重要属性:
*       BSSID：接入点的地址，一般为MAC地址
*       SSID：网络的名字
*       Capabilities：网络接入的性能
*       Frequency：当前WIFI设备附近热点的频率(MHz)
*       Level：所发现的WIFI网络信号强度
* */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button mCheckNetworkStatus;
    private Button mOpen;
    private Button mClose;
    private Button mScan;
    private RecyclerView mWifiList;
    private WifiUtil mWifiUtil;
    private List<ScanResult> mScanList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitView();
    }

    private void InitView(){
        mCheckNetworkStatus = (Button)findViewById(R.id.checkNetworkStatus);
        mOpen = (Button)findViewById(R.id.open);
        mClose = (Button)findViewById(R.id.close);
        mScan = (Button)findViewById(R.id.scan);
        mWifiList = (RecyclerView)findViewById(R.id.networkList);
        mWifiUtil = WifiUtil.getInstance(MainActivity.this);

        mCheckNetworkStatus.setOnClickListener(this);
        mOpen.setOnClickListener(this);
        mClose.setOnClickListener(this);
        mScan.setOnClickListener(this);

        mWifiList.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.open:
                mWifiUtil.openWifi(MainActivity.this);
                break;
            case R.id.close:
                mWifiUtil.closeWifi(MainActivity.this);
                break;
            case R.id.scan:
                mWifiUtil.startScan(MainActivity.this);
                mScanList = mWifiUtil.getWifiList();
                //Log.v("YANG--",mScanList.size()+"");
                mWifiList.setAdapter(new WifiAdapter(MainActivity.this,mScanList));
                break;
            case R.id.checkNetworkStatus:
                mWifiUtil.checkState(MainActivity.this);
                break;
        }
    }
}

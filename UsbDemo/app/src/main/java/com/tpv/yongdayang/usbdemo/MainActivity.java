package com.tpv.yongdayang.usbdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
/*
* USB设备广播
* Android提供的API都是作为host
* 注意：USB鼠标和键盘暂时无法识别
*
* */
public class MainActivity extends AppCompatActivity {

    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (UsbManager.ACTION_USB_DEVICE_ATTACHED.equals(action)) {
                Toast.makeText(MainActivity.this, "识别到USB设备", Toast.LENGTH_SHORT).show();
            } else if (UsbManager.ACTION_USB_DEVICE_DETACHED.equals(action)) {
                Toast.makeText(MainActivity.this, "USB设备被移除", Toast.LENGTH_SHORT).show();
            }else if(action.equals("android.hardware.usb.action.USB_STATE")){  //这个主要针对OTG(micro USB)
                if (intent.getExtras().getBoolean("connected")){
                    // usb 插入
                    Toast.makeText(context, "插入", Toast.LENGTH_SHORT).show();
                }else{
                    //   usb 拔出
                    Toast.makeText(context, "拔出", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter usbFilter = new IntentFilter();
        usbFilter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);
        usbFilter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);
        usbFilter.addAction("android.hardware.usb.action.USB_STATE"); //隐藏的ACTION
        registerReceiver(mUsbReceiver, usbFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mUsbReceiver);
    }
}

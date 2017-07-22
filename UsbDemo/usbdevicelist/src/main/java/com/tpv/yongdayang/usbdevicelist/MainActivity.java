package com.tpv.yongdayang.usbdevicelist;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Iterator;
/*
*列出所有usb设备
* */
public class MainActivity extends AppCompatActivity {

    private UsbManager mUsbManager;
    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (UsbManager.ACTION_USB_DEVICE_ATTACHED.equals(action)) {
                HashMap<String, UsbDevice> deviceHashMap = mUsbManager.getDeviceList();
                Iterator<UsbDevice> iterator = deviceHashMap.values().iterator();
                while (iterator.hasNext()) {
                    UsbDevice device = iterator.next();
            /*
            有些API是21的才能使用
            Toast.makeText(this,"\ndevice name: "+device.getDeviceName()+"\ndevice product name:"
                    +device.getProductName()+"\nvendor id:"+device.getVendorId()+
                    "\ndevice serial: "+device.getSerialNumber(),Toast.LENGTH_SHORT).show();
             */
                    Toast.makeText(MainActivity.this, "\ndevice name: " + device.getDeviceName() + "\nvendor id:" + device.getVendorId(), Toast.LENGTH_SHORT).show();
                }
            } else if (UsbManager.ACTION_USB_DEVICE_DETACHED.equals(action)) {
                Toast.makeText(MainActivity.this, "USB设备被移除", Toast.LENGTH_SHORT).show();
            }else if(action.equals("android.hardware.usb.action.USB_STATE")){  //这个主要针对OTG(micro USB)
                if (intent.getExtras().getBoolean("connected")){
                        HashMap<String, UsbDevice> deviceHashMap = mUsbManager.getDeviceList();
                        Iterator<UsbDevice> iterator = deviceHashMap.values().iterator();
                        while (iterator.hasNext()) {
                            UsbDevice device = iterator.next();
                            Toast.makeText(MainActivity.this, "\ndevice name: " + device.getDeviceName() + "\nvendor id:" + device.getVendorId(), Toast.LENGTH_SHORT).show();
                        }
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

        mUsbManager = (UsbManager) getSystemService(Context.USB_SERVICE);

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

package com.tpv.yongdayang.usbcommunication;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbConstants;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Iterator;
/*
* Usb通信
*该工程没有实际的通信，只是open 设备，并没有通信
* */
public class MainActivity extends AppCompatActivity {

    public static final String ACTION_DEVICE_PERMISSION = "com.yongda.USB_PERMISSION";
    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ACTION_DEVICE_PERMISSION.equals(action)) {
                synchronized (this) {
                    UsbDevice device = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        if (device != null) {
                            Toast.makeText(MainActivity.this,"usb EXTRA_PERMISSION_GRANTED",Toast.LENGTH_SHORT).show();
                            initCommunication(device);
                        }
                    } else {
                        Toast.makeText(MainActivity.this,"usb EXTRA_PERMISSION_GRANTED null!!!",Toast.LENGTH_SHORT).show();
                    }
                }
            }else if(UsbManager.ACTION_USB_DEVICE_ATTACHED.equals(action)){
                HashMap<String, UsbDevice> deviceHashMap = mUsbManager.getDeviceList();
                Iterator<UsbDevice> iterator = deviceHashMap.values().iterator();
                while (iterator.hasNext()) {
                    UsbDevice device = iterator.next();
                    //无权限则请求权限
                    if(!mUsbManager.hasPermission(device)){
                        mUsbManager.requestPermission(device,mPermissionIntent);
                    }

                }
            }
        }
    };

    private PendingIntent mPermissionIntent;
    private UsbManager mUsbManager;
    private UsbEndpoint mUsbEndpointIn;
    private UsbEndpoint mUsbEndpointOut;
    private UsbInterface mUsbInterface;
    private UsbDeviceConnection mUsbDeviceConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取UsbManager
        mUsbManager = (UsbManager)getSystemService(Context.USB_SERVICE);
        //注册Usb插上和访问Usb Device广播
        mPermissionIntent = PendingIntent.getBroadcast(this,0,new Intent(ACTION_DEVICE_PERMISSION),0);
        IntentFilter permissionFilter = new IntentFilter(ACTION_DEVICE_PERMISSION);
        permissionFilter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);
        registerReceiver(mUsbReceiver,permissionFilter);
    }


    private void initCommunication(UsbDevice device) {

        if(2385 == device.getVendorId()) {
            int interfaceCount = device.getInterfaceCount();
            Log.v("YANG","interfaceCount = "+interfaceCount);
            for (int interfaceIndex = 0; interfaceIndex < interfaceCount; interfaceIndex++) {
                UsbInterface usbInterface = device.getInterface(interfaceIndex); //获取UsbInterface

                Log.v("YANG","endPointCount = "+usbInterface.getEndpointCount());
                for (int i = 0; i < usbInterface.getEndpointCount(); i++) {
                    UsbEndpoint ep = usbInterface.getEndpoint(i);
                    if (ep.getType() == UsbConstants.USB_ENDPOINT_XFER_BULK) {
                        if (ep.getDirection() == UsbConstants.USB_DIR_OUT) {
                            mUsbEndpointOut = ep; //输出Endpoint
                        } else {
                            mUsbEndpointIn = ep; //输入Endpoint
                        }
                    }
                }

                if ((null == mUsbEndpointIn) || (null == mUsbEndpointOut)) {
                    mUsbEndpointIn = null;
                    mUsbEndpointOut = null;
                    mUsbInterface = null;
                } else {
                    mUsbInterface = usbInterface;
                    mUsbDeviceConnection = mUsbManager.openDevice(device);
                    Log.v("YANG","device already open");
                    break;
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mUsbReceiver);
    }
}

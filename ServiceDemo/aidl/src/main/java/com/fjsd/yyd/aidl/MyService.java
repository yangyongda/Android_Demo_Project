package com.fjsd.yyd.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

/**
 * Created by Administrator on 2017/6/18 0018.
 */

public class MyService extends Service {

    IMyAidlInterface.Stub mBinder = new IMyAidlInterface.Stub() {
        @Override
        public int plus(int a, int b) throws RemoteException {
            return a + b;
        }

        @Override
        public String toUpperCase(String str) throws RemoteException {
            if(str != null){
                return str.toUpperCase();
            }
            return null;
        }
    };
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}

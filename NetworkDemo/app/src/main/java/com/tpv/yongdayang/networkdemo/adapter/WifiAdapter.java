package com.tpv.yongdayang.networkdemo.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tpv.yongdayang.networkdemo.R;
import com.tpv.yongdayang.networkdemo.util.WifiUtil;

import java.util.List;

/**
 * Created by YongDa.Yang on 2017/7/9.
 */

public class WifiAdapter extends RecyclerView.Adapter<WifiAdapter.WIFIViewHolder> {

    private List<ScanResult> mWifiList;
    private Context mContext;

    public WifiAdapter(Context context, List<ScanResult> WifiList){
        mContext = context;
        mWifiList = WifiList;
    }

    @Override
    public WIFIViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //加载布局
        return new WIFIViewHolder(LayoutInflater.from(mContext).inflate(R.layout.wifi_item_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(WIFIViewHolder holder, int position) {
        final ScanResult scanResult = mWifiList.get(position);
        int level= WifiManager.calculateSignalLevel(scanResult.level,5);
        holder.WifiName.setText(scanResult.SSID); //设置WIFI名字
        if(scanResult.capabilities.contains("WEP")||scanResult.capabilities.contains("PSK")|| scanResult.capabilities.contains("EAP"))
            holder.Signal_Level.setImageResource(R.drawable.wifi_signal_lock);
        else
            holder.Signal_Level.setImageResource(R.drawable.wifi_signal_open);
        holder.Signal_Level.setImageLevel(level);

        holder.ListItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(mContext,"click",Toast.LENGTH_SHORT).show();
                final WifiUtil mWifiUtil = WifiUtil.getInstance(mContext);
                AlertDialog.Builder alert=new AlertDialog.Builder(mContext,R.style.AlertDialogCustom);
                alert.setTitle(scanResult.SSID);
                alert.setMessage("输入密码");
                final EditText et_password=new EditText(mContext);
                final SharedPreferences preferences=mContext.getSharedPreferences("wifi_password",Context.MODE_PRIVATE);
                et_password.setText(preferences.getString(scanResult.SSID, ""));
                alert.setView(et_password);
                alert.setPositiveButton("连接", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String pw = et_password.getText().toString();
                        if(null == pw  || pw.length() < 8){
                            Toast.makeText(mContext, "密码至少8位", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        SharedPreferences.Editor editor=preferences.edit();
                        editor.putString(scanResult.SSID, pw);
                        editor.commit();
                        mWifiUtil.addNetwork(mWifiUtil.CreateWifiInfo(scanResult.SSID, et_password.getText().toString(), 3));
                    }
                });
                alert.setNegativeButton("取消", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.create();
                alert.show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return mWifiList.size();
    }

    public static class WIFIViewHolder extends RecyclerView.ViewHolder {
        public TextView WifiName;
        public ImageView Signal_Level;
        public RelativeLayout ListItem;
        public WIFIViewHolder(View itemView) {
            super(itemView);
            WifiName = (TextView)itemView.findViewById(R.id.wifiName);
            Signal_Level = (ImageView)itemView.findViewById(R.id.wifi_level);
            ListItem = (RelativeLayout)itemView.findViewById(R.id.list_item) ;
        }
    }
}

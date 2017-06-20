package com.fjsd.yyd.glide;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/26 0026.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ImageViewHolder> {
    private Context mContext;
    private ArrayList<String> mItems;
    public RecyclerAdapter(Context context,ArrayList<String> items){
        mContext = context;
        mItems = items;
    }
    @Override
    public RecyclerAdapter.ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ImageViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ImageViewHolder holder, int position) {
        final String url = mItems.get(position);
        Log.e("tag","============onBindViewHolder url: "+url);
        Glide.with(mContext)
                .load(url)   //支持远程图片，本地图片文件，图片资源，多媒体数据库的uri
                //.placeholder(R.mipmap.ic_launcher)  //占位符，未加载时显示的图片
                //.error(R.mipmap.ic_launcher)    //加载失败显示的图片
                //.crossFade()                  //淡入淡出动画,默认激活  另一个方法：crossFade(int duration)
                //.dontAnimate()                //取消淡入淡出效果
                //.override(600, 200)           //显示到ImageView之前重新改变图片大小
                //.centerCrop()                 //缩放图像让它填充到ImageView界限内并且裁剪额外的部分
                //.fitCenter()                  //缩放图像让图像都测量出来等于或小于ImageView的边界范围
                .diskCacheStrategy(DiskCacheStrategy.RESULT)//缓存最终的图像，即降低分辨率后的（或者是转换后的）
                .into(holder.mImageView);//在哪个imageview中显示

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder{
        ImageView mImageView;
        public ImageViewHolder(View view){
            super(view);
            mImageView = (ImageView)view.findViewById(R.id.image);
        }
    }

}

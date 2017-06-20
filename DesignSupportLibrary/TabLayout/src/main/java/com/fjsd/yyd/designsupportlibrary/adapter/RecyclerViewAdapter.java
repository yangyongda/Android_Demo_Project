package com.fjsd.yyd.designsupportlibrary.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fjsd.yyd.designsupportlibrary.R;


import java.util.List;

/**
 * Created by Administrator on 2016/12/1 0001.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {

    private Context mContext;
    private List<String> datas;
    private LayoutInflater mLayoutInflater;

    public RecyclerViewAdapter(Context mContext,List<String> datas) {
        this.mContext = mContext;
        this.mLayoutInflater = LayoutInflater.from(mContext);
        this.datas = datas;
    }

//创建ViewHolder
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(mLayoutInflater.inflate(R.layout.item_layout,parent,false));
    }
//设置控件数据
    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.mTextView.setText(datas.get(position));
    }
//返回item数量
    @Override
    public int getItemCount() {
        return (datas.size() != 0)?datas.size():0;
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView mTextView;
        public RecyclerViewHolder(View view){
            super(view);
            mTextView = (TextView)view.findViewById(R.id.textView);
        }
    }
}

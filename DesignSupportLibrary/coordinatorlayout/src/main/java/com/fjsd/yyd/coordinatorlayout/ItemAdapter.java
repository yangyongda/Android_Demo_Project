package com.fjsd.yyd.coordinatorlayout;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/12/7 0007.
 */
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    private Context mContext;
    public ItemAdapter(Context context){
        mContext = context;
    }
    private String[] datas = {
            "item0","item0","item0","item0","item0","item0","item0","item0",
            "item0","item0","item0","item0","item0","item0","item0","item0",
            "item0","item0","item0","item0","item0","item0","item0","item0",
            "item0","item0","item0","item0","item0","item0","item0","item0",
            "item0","item0","item0","item0","item0","item0","item0","item0",
    };
    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.mTextView.setText(datas[position]);
    }

    @Override
    public int getItemCount() {
        return datas.length;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView mTextView;
        public ItemViewHolder(View view){
            super(view);
            mTextView = (TextView) view.findViewById(R.id.textView);
        }
    }
}

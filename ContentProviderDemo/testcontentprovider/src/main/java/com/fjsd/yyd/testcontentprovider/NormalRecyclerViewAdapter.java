package com.fjsd.yyd.testcontentprovider;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2016/11/21 0021.
 */
public class NormalRecyclerViewAdapter extends RecyclerView.Adapter<NormalRecyclerViewAdapter.NormalTextViewHolder> {
    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private List<Person> mPersons;

    public NormalRecyclerViewAdapter(Context context, List<Person> persons) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mPersons = persons;
    }

/*
* 返回一个自定义的ViewHolder,生成为每个Item inflater出一个View
* */
    @Override
    public NormalTextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NormalTextViewHolder(mLayoutInflater.inflate(R.layout.item_text, parent, false));
    }
/*
*  填充onCreateViewHolder方法返回的holder中的控件
* */
    @Override
    public void onBindViewHolder(NormalTextViewHolder holder, int position) {
        if(mPersons.size() != 0) {
            holder._id.setText(mPersons.get(position).get_id()+"");
            holder.name.setText(mPersons.get(position).getName());
            holder.age.setText(mPersons.get(position).getAge());
        }
    }

/*
* 总共有多少个条目
* */
    @Override
    public int getItemCount() {
        return mPersons == null ? 0 : mPersons.size();
    }

    public static class NormalTextViewHolder extends RecyclerView.ViewHolder{
        TextView _id;
        TextView name;
        TextView age;
        public NormalTextViewHolder(View view) {
            super(view);  //一定要添加这行
            _id = (TextView) view.findViewById(R.id._id);
            name = (TextView)view.findViewById(R.id.name);
            age = (TextView)view.findViewById(R.id.age);
        }
    }
}

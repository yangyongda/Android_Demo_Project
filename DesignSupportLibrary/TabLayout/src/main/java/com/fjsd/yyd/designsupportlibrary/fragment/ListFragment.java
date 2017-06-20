package com.fjsd.yyd.designsupportlibrary.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fjsd.yyd.designsupportlibrary.R;
import com.fjsd.yyd.designsupportlibrary.adapter.RecyclerViewAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/1 0001.
 */
public class ListFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private ArrayList<String> datas = new ArrayList<>();
/*
* 创建该Fragment的视图
* */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View list_Layout  = inflater.inflate(R.layout.list_fragment,container,false);
        mRecyclerView = (RecyclerView) list_Layout.findViewById(R.id.recycler_view);
        return list_Layout;
    }
/*
* 当Activity的onCreate方法返回时调用
* */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        datas.add("item1");
        datas.add("item2");
        datas.add("item3");
        datas.add("item4");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
        mRecyclerView.setAdapter(new RecyclerViewAdapter(getActivity(),datas));
    }
}

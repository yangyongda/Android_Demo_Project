package com.fjsd.yyd.designsupportlibrary.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.fjsd.yyd.designsupportlibrary.R;
import com.fjsd.yyd.designsupportlibrary.adapter.FragmentAdapter;
import com.fjsd.yyd.designsupportlibrary.fragment.ListFragment;

import java.util.ArrayList;
import java.util.List;
/*
* TabLayout一般是和ViewPager一起使用
* 每个ViewPager上都是一个View或Fragment
* */
public class MainActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);   //支持ToolBar
        initViewPager();
    }

    private void initViewPager() {
        mTabLayout = (TabLayout) findViewById(R.id.tab_FindFragment_title);
        mViewPager = (ViewPager)findViewById(R.id.vp_FindFragment_pager);
        List<String> titles = new ArrayList<>();
        titles.add("精选");
        titles.add("体育");
        titles.add("巴萨");
        titles.add("购物");
        titles.add("明星");
        titles.add("视频");
        titles.add("健康");
        titles.add("励志");
        titles.add("图文");

        //在TabLayout上添加各个Tab
        for(int i=0;i<titles.size();i++){
            mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(i)));
        }
        //创建所有的Fragment
        List<Fragment> fragments = new ArrayList<>();
        for(int i=0;i<titles.size();i++){
            fragments.add(new ListFragment());
        }
        FragmentAdapter mFragmentAdapteradapter =
                new FragmentAdapter(getSupportFragmentManager(), fragments, titles);
        //给ViewPager设置适配器
        mViewPager.setAdapter(mFragmentAdapteradapter);
        //将TabLayout和ViewPager关联起来。
        mTabLayout.setupWithViewPager(mViewPager);
    }
}

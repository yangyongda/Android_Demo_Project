package com.fjsd.yyd.coordinatorlayout;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
/*
* CoordinatorLayout是一个增强型的FrameLayout
*
* */
public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private CollapsingToolbarLayout toolbarLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    /******************************************************************
        // CoordinatorLayout和FloatingActionView的配合使用
        //FloatingActionButton默认使用FloatingActionButton.Behavior

        setContentView(R.layout.activity_main);
        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Snackbar.make(view,"FAB",Snackbar.LENGTH_LONG)
                        .setAction("cancel", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //这里的单击事件代表点击消除Action后的响应事件

                            }
                        }).show();
            }
        });
    ***************************************************************************/

        /*********************************************************************
         * CoordinatorLayout与AppBarLayout配合使用
         * RecyclerView使用app:layout_behavior="@string/appbar_scrolling_view_behavior"

        setContentView(R.layout.appbar_layout);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        //设置ToolBar，并设置为ActionBar
        toolbar.setTitleTextColor(Color.WHITE); //设置文本颜色
        toolbar.setTitle("CoordinatorLayout");
        setSupportActionBar(toolbar);
        //RecyclerView设置布局管理器
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
         //设置Adapter
        recyclerView.setAdapter(new ItemAdapter(MainActivity.this));
         ************************************************************************/

        // CoordinatorLayout与AppBarLayout、CollapsingToolbarLayout配合使用
        // RecyclerView使用app:layout_behavior="@string/appbar_scrolling_view_behavior"
        //出现title显示不出来，可能是bug
        //将titleEnabled设置为false就显示title了，无语了
        setContentView(R.layout.collapsingtoolbar_layout);
        toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);

        setSupportActionBar(toolbar);

        toolbarLayout.setTitleEnabled(false); //设置为false才能显示，坑爹啊
        toolbarLayout.setCollapsedTitleTextColor(Color.WHITE); //设置文本颜色
        toolbarLayout.setExpandedTitleColor(Color.GREEN);
        toolbarLayout.setTitle("CoordinatorLayout");   //显示不出来，bug？

        //RecyclerView设置布局管理器
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        //设置Adapter
        recyclerView.setAdapter(new ItemAdapter(MainActivity.this));

    }
}

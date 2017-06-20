package com.fjsd.yyd.glide;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

import com.fjsd.yyd.glide.bean.GetInfo;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
/*
* 还存在一个问题：当上滑时又重新获取网络图片
* */
public class MainActivity extends AppCompatActivity {
    private OkHttpClient mClient;
    private RecyclerView mRecyclerView;
    private ArrayList<String> mUrls;
    private int index;
    private RecyclerAdapter adapter;
    private int size;
    private int[] position = new int[2];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUrls = new ArrayList<>();
        mClient = new OkHttpClient();
        mRecyclerView = (RecyclerView)findViewById(R.id.recycleView);
        loadData("1");
        //瀑布式布局
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        adapter = new RecyclerAdapter(this,mUrls);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if(isVisBottom(recyclerView)) {
                    Log.e("tag", "============scroll to end");
                    index += 1;
                    loadData(Integer.toString(index));
                }
            }
        });


    }

    public void loadData(String index){
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://gank.io/api/data/%E7%A6%8F%E5%88%A9/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(mClient)
                .build();
        ImageAPI imageAPI = retrofit.create(ImageAPI.class);
        imageAPI.getInfo(index).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GetInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GetInfo value) {
                        for(int i = 0; i< value.getResults().size(); i++){
                            mUrls.add(value.getResults().get(i).getUrl());
                        }
                        adapter.notifyItemRangeChanged(size,size+value.getResults().size());
                        size += value.getResults().size();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public boolean isVisBottom(RecyclerView recyclerView){
        StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
        //屏幕中最后一个可见子项的position
        int[] lastVisibleItemPosition = layoutManager.findLastCompletelyVisibleItemPositions(position);
        //当前屏幕所看到的子项个数
        int visibleItemCount = layoutManager.getChildCount();
        //当前RecyclerView的所有子项个数
        int totalItemCount = layoutManager.getItemCount();

        if(visibleItemCount > 0 && (lastVisibleItemPosition[0] + lastVisibleItemPosition[1]) > totalItemCount - 1){
            return true;
        }else {
            return false;
        }
    }

}

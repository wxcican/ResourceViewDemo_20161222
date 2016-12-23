package com.fuicuiedu.idedemo.resourceviewdemo_20161222.democ;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ProgressBar;

import com.fuicuiedu.idedemo.resourceviewdemo_20161222.R;
import com.fuicuiedu.idedemo.resourceviewdemo_20161222.SimpleAdapter;
import com.mugen.Mugen;
import com.mugen.MugenCallbacks;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DemoCActivity extends AppCompatActivity
        implements SwipeRefreshLayout.OnRefreshListener,
        MugenCallbacks{

    @BindView(R.id.recyclerview)RecyclerView recyclerView;
    @BindView(R.id.swiperefreshLayout)SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.progressbar)ProgressBar progressBar;
    SimpleAdapter simpleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_c);
        ButterKnife.bind(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        simpleAdapter = new SimpleAdapter();
        recyclerView.setAdapter(simpleAdapter);

        //下拉刷新
        swipeRefreshLayout.setOnRefreshListener(this);

        //上拉加载(mugen)
        Mugen.with(recyclerView,this).start();
    }

    @Override
    public void onRefresh() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //睡3秒，模拟网络刷新
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                simpleAdapter.clear();
                for (int i = 0; i < 20; i++) {
                    simpleAdapter.addItem("我是新刷新到的第" + i +"条数据");
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        simpleAdapter.notifyDataSetChanged();
                        //刷新完成，关闭progressbar
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    @Override
    public void onLoadMore() {
        //显示加载布局
        progressBar.setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < 10; i++) {
                    simpleAdapter.addItem("我是新加载到的第" + i + "条数据");
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        simpleAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }
        }).start();
    }

    @Override
    public boolean isLoading() {
        return progressBar.getVisibility() == View.VISIBLE;
    }

    @Override
    public boolean hasLoadedAllItems() {
        return false;
    }
}

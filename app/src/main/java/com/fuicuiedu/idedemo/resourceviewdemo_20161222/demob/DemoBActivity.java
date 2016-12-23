package com.fuicuiedu.idedemo.resourceviewdemo_20161222.demob;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;

import com.fuicuiedu.idedemo.resourceviewdemo_20161222.R;
import com.fuicuiedu.idedemo.resourceviewdemo_20161222.SimpleAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DemoBActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.recyclerview)RecyclerView recyclerView;
    @BindView(R.id.swiperefreshLayout)SwipeRefreshLayout swipeRefreshLayout;
    private SimpleAdapter simpleAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_b);
        ButterKnife.bind(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        simpleAdapter = new SimpleAdapter();
        recyclerView.setAdapter(simpleAdapter);

        //下拉刷新监听
        swipeRefreshLayout.setOnRefreshListener(this);
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
}

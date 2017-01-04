package com.fuicuiedu.idedemo.resourceviewdemo_20161222.demod;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.fuicuiedu.idedemo.resourceviewdemo_20161222.R;
import com.fuicuiedu.idedemo.resourceviewdemo_20161222.SimpleAdapter;
import com.mugen.Mugen;
import com.mugen.MugenCallbacks;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.media.CamcorderProfile.get;


// 带下拉刷新和上拉加载的自定义列表视图,完成数据加载和数据适配显示的业务流程
// 列表视图 -> RecyclerView 实现
// 下拉刷新 -> SwipeRefreshLayout 实现
// 上拉加载 -> Mugen + ProgressBar 实现

public abstract class BaseResourceView<Model,ItemView extends BaseItemView<Model>> extends FrameLayout implements SwipeRefreshLayout.OnRefreshListener,MugenCallbacks{
    public BaseResourceView(Context context) {
        this(context,null);
    }

    public BaseResourceView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BaseResourceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @BindView(R.id.recyclerview)RecyclerView recyclerview;
    @BindView(R.id.swiperefreshLayout)SwipeRefreshLayout swiperefreshLayout;
    @BindView(R.id.progressbar)ProgressBar progressbar;

    private SimpleAdapter adapter;

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.base_resource_view,this,true);
        ButterKnife.bind(this);

        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new SimpleAdapter();
        recyclerview.setAdapter(adapter);

        //下拉刷新
        swiperefreshLayout.setOnRefreshListener(this);
        //上拉加载
        Mugen.with(recyclerview,this).start();
    }

    public void autoRefresh(){
        swiperefreshLayout.setRefreshing(true);
        onRefresh();
    }

    //请求数据
//    protected abstract 数据 queryData(int limit,int skip);
    //单次请求多少条数据
    protected abstract int getLimit();

    protected abstract ItemView creatItemView();

    private int skip = 0;

    @Override
    public void onRefresh() {
//        数据 = queryData(getLimit(),0);
//        if (数据请求成功){
//            skip = 数据.size();
//        }
    }

    //------------------------mugen 监听 start ----------------------

    @Override
    public void onLoadMore() {
//        数据 = queryData(getLimit(),skip);
//        if (数据加载成功){
//            skip = skip + 数据.size;
//        }
    }

    @Override
    public boolean isLoading() {
        return progressbar.getVisibility() == View.VISIBLE;
    }

    @Override
    public boolean hasLoadedAllItems() {
        return false;
    }

    //------------------------mugen 监听 end ----------------------

    protected class ModelAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        private ArrayList<Model> dataSet = new ArrayList<>();

        public void clear(){
            dataSet.clear();
            notifyDataSetChanged();
        }

        public void addData(ArrayList<Model> data){
            dataSet.addAll(data);
            notifyDataSetChanged();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ItemView itemView = creatItemView();
            return new RecyclerView.ViewHolder(itemView) {
            };
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            Model model = dataSet.get(position);
            ItemView itemView = (ItemView) holder.itemView;
            itemView.bindModel(model);
        }

        @Override
        public int getItemCount() {
            return dataSet.size();
        }
    }
}

package com.fuicuiedu.idedemo.resourceviewdemo_20161222;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/12/22 0022.
 */

public class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.DemoViewHolder>{

    private ArrayList<String> datas = new ArrayList<>();

    public void addItem(String data){
        datas.add(data);
    }

    public void clear(){
        datas.clear();
    }

    //创建ViewHolder
    @Override
    public DemoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_text,parent,false);
        return new DemoViewHolder(view);
    }

    //数据绑定，操作
    @Override
    public void onBindViewHolder(DemoViewHolder holder, int position) {
        String data = datas.get(position);
        holder.textView.setText(data);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    static class DemoViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.textView)TextView textView;

        public DemoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}

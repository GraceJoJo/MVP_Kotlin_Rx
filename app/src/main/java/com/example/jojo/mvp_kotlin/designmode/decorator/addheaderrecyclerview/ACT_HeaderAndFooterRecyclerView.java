package com.example.jojo.mvp_kotlin.designmode.decorator.addheaderrecyclerview;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jojo.mvp_kotlin.R;
import com.example.jojo.mvp_kotlin.base.BaseActivity;
import com.example.jojo.mvp_kotlin.ioc.ContentView;
import com.example.jojo.mvp_kotlin.ioc.ViewById;

/**
 * Created by JoJo on 2018/7/25.
 * wechat:18510829974
 * description:
 */
@ContentView(R.layout.header_footer_recyclerview)
public class ACT_HeaderAndFooterRecyclerView extends BaseActivity {
    @ViewById(R.id.recyclerview)
    RecyclerView recyclerView;

    @Override
    public void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        RealAdapter realAdapter = new RealAdapter();
//        recyclerView.setAdapter(realAdapter);
        //装饰者模式给RecyclerView添加头部和底部-不采用继承的情况下，扩展对象的功能
        RealAdapter realAdapter = new RealAdapter();
        WrapRecyclerViewAdapter mWrapAdapter = new WrapRecyclerViewAdapter(realAdapter);
        View headerView = LayoutInflater.from(ACT_HeaderAndFooterRecyclerView.this).inflate(R.layout.header_view, recyclerView, false);
        mWrapAdapter.addHeaderView(headerView);
        mWrapAdapter.addHeaderView(headerView);
        recyclerView.setAdapter(mWrapAdapter);
    }

    class RealAdapter extends RecyclerView.Adapter<RealAdapter.MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            //inflate(R.layout.item_view, null)显示就只能显示wrap. inflate(R.layout.item_view, parent,false)这样才能match显示
            View itemview = LayoutInflater.from(ACT_HeaderAndFooterRecyclerView.this).inflate(R.layout.item_view, parent, false);
            return new MyViewHolder(itemview);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.tv.setText("text=" + position);
        }

        @Override
        public int getItemCount() {
            return 30;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tv;

            public MyViewHolder(View itemView) {
                super(itemView);
                tv = itemView.findViewById(R.id.tv);
            }
        }
    }


}

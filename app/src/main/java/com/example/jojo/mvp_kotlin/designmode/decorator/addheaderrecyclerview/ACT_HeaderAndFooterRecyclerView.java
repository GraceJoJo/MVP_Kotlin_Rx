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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JoJo on 2018/7/25.
 * wechat:18510829974
 * description:
 */
@ContentView(R.layout.header_footer_recyclerview)
public class ACT_HeaderAndFooterRecyclerView extends BaseActivity {
    @ViewById(R.id.recyclerview)
    WrapRecyclerView recyclerView;
    private List<Integer> mData = new ArrayList<>();

    @Override
    public void initView() {

        for (int i = 0; i < 30; i++) {
            mData.add(i);
        }


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        RealAdapter realAdapter = new RealAdapter();
//        recyclerView.setAdapter(realAdapter);
        /**
         *  装饰者模式给RecyclerView添加头部和底部-不采用继承的情况下，扩展对象的功能
         */
//        RealAdapter realAdapter = new RealAdapter();
//        WrapRecyclerViewAdapter mWrapAdapter = new WrapRecyclerViewAdapter(realAdapter);
//        View headerView = LayoutInflater.from(ACT_HeaderAndFooterRecyclerView.this).inflate(R.layout.header_view, recyclerView, false);
//        View footview = LayoutInflater.from(ACT_HeaderAndFooterRecyclerView.this).inflate(R.layout.footer_view, recyclerView, false);
//        mWrapAdapter.addHeaderView(headerView);
//        mWrapAdapter.addFooterView(footview);
//        recyclerView.setAdapter(mWrapAdapter);
//        mWrapAdapter.setOnItemClickListener((holder, position) -> {
//            Toast.makeText(ACT_HeaderAndFooterRecyclerView.this, "position=" + position, Toast.LENGTH_SHORT).show();
//            if (position < mData.size()) {
//                mData.remove(position - 1);
//                realAdapter.notifyDataSetChanged();
//            }
//        });
        /**
         * 将添加headview和footerview封装到RecyclerView中
         */
        RealAdapter realAdapter = new RealAdapter();
        View headerView = LayoutInflater.from(ACT_HeaderAndFooterRecyclerView.this).inflate(R.layout.header_view, recyclerView, false);
        View footview = LayoutInflater.from(ACT_HeaderAndFooterRecyclerView.this).inflate(R.layout.footer_view, recyclerView, false);
        recyclerView.setAdapter(realAdapter);
        recyclerView.addHeaderView(headerView);
        recyclerView.addFooterView(footview);
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
            holder.tv.setText("text=" + mData.get(position));
        }

        @Override
        public int getItemCount() {
            return mData.size();
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

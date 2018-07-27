package com.example.jojo.mvp_kotlin.designmode.decorator.addheaderrecyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by JoJo on 2018/7/27.
 * wechat:18510829974
 * description:支持添加头部和尾部的RecyclerView
 * 使用装饰者模式，拓展recyclerViewAdapter的功能，使其。
 */

public class WrapRecyclerView extends RecyclerView {

    private WrapRecyclerViewAdapter mWrapAdapter;

    public WrapRecyclerView(Context context) {
        super(context);
    }

    public WrapRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WrapRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        mWrapAdapter= new WrapRecyclerViewAdapter(adapter);
        super.setAdapter(mWrapAdapter);
    }

    /**
     * 添加头部
     *
     * @param view
     */
    public void addHeaderView(View view) {
        if(mWrapAdapter==null) {
            return;
        }
        mWrapAdapter.addHeaderView(view);
    }

    public void removeHeaderView(View view) {
        if(mWrapAdapter==null) {
            return;
        }
        mWrapAdapter.removeHeaderView(view);
    }

    /**
     * 添加底部
     *
     * @param view
     */
    public void addFooterView(View view) {
        if(mWrapAdapter==null) {
            return;
        }
        mWrapAdapter.addFooterView(view);
    }

    public void removeFooterView(View view) {
        if(mWrapAdapter==null) {
            return;
        }
        mWrapAdapter.removeFooterView(view);
    }
}

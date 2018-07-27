package com.example.jojo.mvp_kotlin.designmode.decorator.addheaderrecyclerview;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JoJo on 2018/7/26.
 * wechat:18510829974
 * description:
 */

public class WrapRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private RecyclerView.Adapter mRealAdapter;
    public List<View> mHeaderViews = new ArrayList<>();
    public List<View> mFooterViews = new ArrayList<>();

    public WrapRecyclerViewAdapter(RecyclerView.Adapter realAdapter) {
        this.mRealAdapter = realAdapter;
        mRealAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //根据位置，判断类型，headerview/中间的view/footerview。返回ViewHolder

        //viewType即是对应的位置
        int position = viewType;

        int numHeaders = getHeadersCount();
        if (position < numHeaders) {
            return createHeaderViewViewHolder(mHeaderViews.get(position));
        }

        // Adapter
        final int adjPosition = position - numHeaders;
        int adapterCount = 0;
        if (mRealAdapter != null) {
            //中间的view
            adapterCount = mRealAdapter.getItemCount();
            if (adjPosition < adapterCount) {
                // 直接传 adjPosition ,会不兼容万能适配多布局条目
                return mRealAdapter.createViewHolder(parent, mRealAdapter.getItemViewType(adjPosition));
            }
        }

        // Footer (off-limits positions will throw an IndexOutOfBoundsException)
        return createFooterViewViewHolder(mFooterViews.get(adjPosition - adapterCount));
    }


    /**
     * headerview的ViewHolder
     *
     * @param view
     * @return
     */
    private RecyclerView.ViewHolder createHeaderViewViewHolder(View view) {
        return new RecyclerView.ViewHolder(view) {
        };
    }

    /**
     * footerview的ViewHolder
     *
     * @param view
     * @return
     */
    private RecyclerView.ViewHolder createFooterViewViewHolder(View view) {
        return new RecyclerView.ViewHolder(view) {
        };
    }

    private int getHeadersCount() {
        return mHeaderViews.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        holder.itemView.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onItemClick(holder, position);
            }
        });


        // 头部和底部是都不需要做处理的，只要 mRealAdapter 要去做处理
        int numHeaders = getHeadersCount();
        if (position < numHeaders) {
            return;
        }

        // Adapter
        final int adjPosition = position - numHeaders;
        int adapterCount = 0;
        if (mRealAdapter != null) {
            //中间的view
            adapterCount = mRealAdapter.getItemCount();
            Log.e("TAG", "adjPosition=" + adjPosition + "-----adapterCount=" + adapterCount);
            if (adjPosition < adapterCount) {
                mRealAdapter.onBindViewHolder(holder, adjPosition);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mHeaderViews.size() + mFooterViews.size() + mRealAdapter.getItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        //将位置，作为recyclerview的类型返回
        return position;
    }

    /**
     * 添加头部
     *
     * @param view
     */
    public void addHeaderView(View view) {
        if (!mHeaderViews.contains(view)) {
            mHeaderViews.add(view);
            notifyDataSetChanged();
        }

    }

    public void removeHeaderView(View view) {
        if (mHeaderViews.contains(view)) {
            mHeaderViews.remove(view);
            notifyDataSetChanged();
        }
    }

    /**
     * 添加底部
     *
     * @param view
     */
    public void addFooterView(View view) {
        if (!mFooterViews.contains(view)) {
            mFooterViews.add(view);
            notifyDataSetChanged();
        }
    }

    public void removeFooterView(View view) {
        if (mHeaderViews.contains(view)) {
            mHeaderViews.remove(view);
            notifyDataSetChanged();
        }
    }

    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(RecyclerView.ViewHolder holder, int position);
    }

    public void setOnItemClickListener(OnItemClickListener mListener) {
        this.mListener = mListener;
    }
}

package com.example.jojo.mvp_kotlin.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by zhoujuan on 2018/11/2.
 */

public class LoadMoreScrollView extends ScrollView {
    public LoadMoreScrollView(Context context) {
        super(context);
    }

    public LoadMoreScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LoadMoreScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        if (scrollY > 0 && onScrollToBottomListener != null) {
            onScrollToBottomListener.onScrollBottomListener(clampedY);
        }
    }

    public void setOnScrollToBottomLintener(OnScrollToBottomListener listener) {
        onScrollToBottomListener = listener;
    }

    private OnScrollToBottomListener onScrollToBottomListener;

    public interface OnScrollToBottomListener {
        void onScrollBottomListener(boolean isBottom);

    }
}
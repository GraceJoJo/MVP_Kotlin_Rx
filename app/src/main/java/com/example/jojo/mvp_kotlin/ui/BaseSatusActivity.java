package com.example.jojo.mvp_kotlin.ui;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

/**
 * Created by JoJo on 2018/6/1.
 * wechat:18510829974
 * description:
 */

public abstract class BaseSatusActivity  extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayout());

        initView();
        initData();
    }

    // 设置布局
    abstract int setLayout();

    // 初始化组件
    protected abstract void initView();

    // 初始化数据
    protected abstract void initData();

    public <T extends View> T bindView(int id){
        return (T)findViewById(id);
    }

    /**
     * 设置状态栏颜色
     * @param activity
     * @param color
     */
    public static void setColor(Activity activity,int color){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            // 设置状态栏透明
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 生成一个状态栏大小的矩形
            View StatusView = createStatusView(activity,color);
            // 添加statusView到布局中
            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
            decorView.addView(StatusView);
            // 设置根布局的参数
            ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content))
                    .getChildAt(0);
            rootView.setFitsSystemWindows(true);
            rootView.setClipToPadding(true);
        }
    }

    /**
     * 生成一个和状态栏大小相同的矩形条
     * @param activity 需要设置的activity
     * @param color 状态栏的颜色值
     * @return 状态栏矩形条
     */
    private static View createStatusView(Activity activity, int color) {
        // 获得状态栏的高度
        int resourceId = activity.getResources()
                .getIdentifier("status_bar_height","dimen","android");
        int statusBarHeight = activity.getResources()
                .getDimensionPixelSize(resourceId);

        // 绘制一个和状态栏一样高度的矩形
        View statusView = new View(activity);
        LinearLayout.LayoutParams params = new LinearLayout
                .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,statusBarHeight);
        statusView.setLayoutParams(params);
        statusView.setBackgroundColor(color);

        return statusView;
    }

    /***********************我是分割线***************************/

    /**
     * 适用于图片作为背景的界面，此时需要图片填充到状态栏
     * @param activity 需要设置的activity
     */
    public static void setTranslucent(Activity activity){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            // 设置状态栏透明
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 设置根布局的参数
            ViewGroup rootView = (ViewGroup)((ViewGroup)activity
                    .findViewById(android.R.id.content)).getChildAt(0);
            rootView.setFitsSystemWindows(true);
            rootView.setClipToPadding(true);
        }
    }
    /**
     * set status bar translucency
     *
     * @param on
     */
    protected void setTranslucentStatus(boolean on) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            if (on) {
                winParams.flags |= bits;
            } else {
                winParams.flags &= ~bits;
            }
            win.setAttributes(winParams);
        }
    }

}

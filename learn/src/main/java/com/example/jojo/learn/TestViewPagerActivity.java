package com.example.jojo.learn;

import android.graphics.Paint;
import android.graphics.PathMeasure;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.jojo.learn.customview.MyViewPager;
import com.example.jojo.learn.customview.ScrollBallView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JoJo on 2018/8/9.
 * wechat:18510829974
 * description:
 */

public class TestViewPagerActivity extends AppCompatActivity {
    ImageView ball;
    private float[] mCurrentPosition = new float[2];
    private PathMeasure pathMeasure;
    private RelativeLayout.LayoutParams layoutParams;
    private int ballRadius = 30;
    private ScrollBallView triangleview;
    private double scaleRate = 30;
    private Paint paint;
    private MyViewPager
            myviewpager;
    private LinearLayout llPointList;
    private List<Integer> mData = new ArrayList<>();
    private LinearLayout.LayoutParams params;
    private View viewDot;
    private int dotDistance = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_viewpager);


        myviewpager = findViewById(R.id.myviewpager);
        viewDot = findViewById(R.id.view_dot);
        llPointList = findViewById(R.id.ll_point_list);

        initCirclePoint();

        myviewpager.setOnPageScrollListener(new MyViewPager.OnPageScrollListener() {
            @Override
            public void onPageScrolled(float offsetPercent, int position) {
                //效果二：滑动页面过程中小圆点跟随移动
                //offsetPercent:0-0.5-1-1.5-...
                float leftMargin = offsetPercent * dotDistance;
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) viewDot.getLayoutParams();
                params.leftMargin = (int) leftMargin; //滑动后更新距离
//                Elog.e("Offset", "params.leftMargin=" + params.leftMargin);
                viewDot.setLayoutParams(params);
            }

            @Override
            public void onPageSelected(int position) {
                Log.e("TAG", "position=" + position);
//                float leftMargin = position * dotDistance;
//                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) viewDot.getLayoutParams();
//                params.leftMargin = (int) leftMargin; //滑动后更新距离
////                Elog.e("Offset", "params.leftMargin=" + params.leftMargin);
//                viewDot.setLayoutParams(params);

            }
        });

    }

    private void initCirclePoint() {
        for (int i = 0; i < 4; i++) {
            mData.add(i);
        }
        for (int i = 0; i < mData.size(); i++) {
            View point = new View(this);
            point.setBackgroundResource(R.drawable.bg_point_selector);
            params = new LinearLayout.LayoutParams(20, 20);
            if (i != 0) {
                params.leftMargin = 10;
            }
            point.setEnabled(false);
            point.setLayoutParams(params);
            llPointList.addView(point);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}

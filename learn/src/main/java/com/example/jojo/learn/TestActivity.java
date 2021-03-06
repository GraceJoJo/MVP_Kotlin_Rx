package com.example.jojo.learn;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.jojo.learn.customview.ScrollBallView;

/**
 * Created by JoJo on 2018/8/9.
 * wechat:18510829974
 * description:
 */

public class TestActivity extends AppCompatActivity {
    ImageView ball;
    private float[] mCurrentPosition = new float[2];
    private PathMeasure pathMeasure;
    private RelativeLayout.LayoutParams layoutParams;
    private int ballRadius = 30;
    private ScrollBallView triangleview;
    private double scaleRate = 30;
    private Paint paint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        ball = findViewById(R.id.ball);
        triangleview = findViewById(R.id.triangleview);

        layoutParams = new RelativeLayout.LayoutParams(ballRadius * 2, ballRadius * 2);
        ball.setLayoutParams(layoutParams);
        startRotate();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void startRotate() {
        Canvas canvas = new Canvas();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        //4.执行绘制动作(绘制一个路径)
        paint.setStyle(Paint.Style.STROKE);//必须要设置
        paint.setStrokeWidth(2);

        Path path = new Path();//通过此对象绘制一个轨迹
        path.moveTo(0, 400);//起始点
        path.lineTo(200, 0);//终止点

//        path.moveTo(200 + 80, 0);//起始点
//        path.lineTo(400, 400);
//        path.setLastPoint(200, 0);
        path.lineTo(400, 400);
//        path.close();//形成闭环
        //pathMeasure用来计算显示坐标
        pathMeasure = new PathMeasure(path, true);

        //属性动画加载
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, pathMeasure.getLength() - 400);

        //设置动画时长
        valueAnimator.setDuration(2000);
        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);

        //加入差值器
        valueAnimator.setInterpolator(new LinearInterpolator());

        //设置无限次循环
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);

        //添加监听
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //获取当前位置
                float value = (float) animation.getAnimatedValue();
                //boolean getPosTan(float distance, float[] pos, float[] tan) ：
                //传入一个距离distance(0<=distance<=getLength())，然后会计算当前距
                // 离的坐标点和切线，pos会自动填充上坐标
                pathMeasure.getPosTan(value, mCurrentPosition, null);
                //打印当前坐标
//                Log.e("xy", mCurrentPosition[0] + "    " + mCurrentPosition[1]);
                if (mCurrentPosition[0] < 200) {
                    layoutParams.width = (int) ((400f / (400 - mCurrentPosition[0] - scaleRate)) * ballRadius * 2);
                    layoutParams.height = (int) ((400f / (400 - mCurrentPosition[0] - scaleRate)) * ballRadius * 2);
//                    layoutParams.width = (int) (((200 + mCurrentPosition[0]) / 200f) * ballRadius * 2 * scaleRate);
//                    layoutParams.height = (int) (((200 + mCurrentPosition[0]) / 200f) * ballRadius * 2 * scaleRate);
                    ball.setLayoutParams(layoutParams);
                } else {
                    layoutParams.width = (int) ((400f / (mCurrentPosition[0] - scaleRate)) * ballRadius * 2);
                    layoutParams.height = (int) ((400f / (mCurrentPosition[0] - scaleRate)) * ballRadius * 2);
                    ball.setLayoutParams(layoutParams);
                }

                //设置视图坐标
                ball.setX(mCurrentPosition[0]);
                ball.setY(mCurrentPosition[1]);
            }
        });

        valueAnimator.start();
    }
}

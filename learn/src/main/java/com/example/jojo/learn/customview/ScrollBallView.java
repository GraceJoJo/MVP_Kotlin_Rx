package com.example.jojo.learn.customview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.jojo.learn.R;

/**
 * Created by JoJo on 2018/8/9.
 * wechat:18510829974
 * description:
 */

public class ScrollBallView extends View {

    private Context mContext;
    private Paint paint;
    private View ball;
    private PathMeasure pathMeasure;
    private int ballRadius = 30;
    private float[] mCurrentPosition = new float[2];
    private boolean isAnimation;
    private ValueAnimator valueAnimator;
    private RelativeLayout.LayoutParams layoutParams;
    private double scaleRate = 1;
    private int offset = 30;

    public ScrollBallView(Context context) {
        super(context, null);
    }

    public ScrollBallView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollBallView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }


    private void init() {
        //3.构建画笔对象(Paint)
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);
        //4.执行绘制动作(绘制一个路径)
        paint.setStyle(Paint.Style.STROKE);//必须要设置
        paint.setStrokeWidth(2);

        ball = new ImageView(mContext);
        ball.setBackgroundResource(R.drawable.ball_shape);
        layoutParams = new RelativeLayout.LayoutParams(ballRadius * 2, ballRadius * 2);
        ball.setLayoutParams(layoutParams);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (heightMode == MeasureSpec.AT_MOST) {
            //边沿线和文字所占的长度：(xOffset + yOffset + textRect.width())
            heightSize = 600;
        }
        if (widthMode == MeasureSpec.AT_MOST) {
            widthSize = 600;
        }
        //保存测量结果
        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        Path path = new Path();//通过此对象绘制一个轨迹
        path.moveTo(0 + offset, 400 + offset);//起始点
        path.lineTo(200 + offset, 0 + offset);//终止点
        path.lineTo(400 + offset, 400 + offset);
//        path.moveTo(0 + 30, 400 + 30);//起始点
//        path.lineTo(200 + 30, 0 + 30);//终止点
//        path.lineTo(400 + 30, 400 + 30);
        path.close();//形成闭环
        canvas.drawPath(path, paint);

    }
}

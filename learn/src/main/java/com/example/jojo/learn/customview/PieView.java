package com.example.jojo.learn.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JoJo on 2018/8/6.
 * wechat:18510829974
 * description:
 */

public class PieView extends View {
    private Context mContext;
    private Paint mPaint;
    //圆弧半径
    private float radius = 200;
    private int strokeWidth = 80;

    private int startAngle = 0;
    //每块占比的绘制的颜色
    private List<Integer> mColorList = new ArrayList<>();
    //圆弧占比的集合
    private List<Float> mRateList = new ArrayList<>();

    public PieView(Context context) {
        this(context, null);
    }

    public PieView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PieView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();

        initData();
    }

    private void initData() {
        float[] rate = {30f, 40f, 15f, 15f};
        int[] colors = {Color.RED, Color.BLUE, Color.YELLOW, Color.GRAY};
        for (int i = 0; i < rate.length; i++) {
            mRateList.add(rate[i] / 100);
            mColorList.add(colors[i]);
        }
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(strokeWidth);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (heightMode == MeasureSpec.AT_MOST) {
            heightSize = (int) (radius * 2);
        }
        if (widthMode == MeasureSpec.AT_MOST) {

            widthSize = (int) (radius * 2);
        }
        //保存测量结果
        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        RectF rectF = new RectF(0 + strokeWidth / 2, 0 + strokeWidth / 2, 2 * radius - strokeWidth / 2, 2 * radius - strokeWidth / 2);
        mPaint.setColor(mColorList.get(0));
        canvas.drawArc(rectF, 0, 90, false, mPaint);
        mPaint.setColor(mColorList.get(1));
        canvas.drawArc(rectF, 90, 90, false, mPaint);
        mPaint.setColor(mColorList.get(2));
        canvas.drawArc(rectF, 180, 90, false, mPaint);
        mPaint.setColor(mColorList.get(3));
        canvas.drawArc(rectF, 270, 90, false, mPaint);
//        for (int i = 0; i < mRateList.size(); i++) {
//            mPaint.setColor(mColorList.get(i));
//            Log.e("TAG", "startAngle=" + startAngle + "--sweepAngle=" + (mRateList.get(i) * 360));
//            canvas.drawArc(rectF, startAngle, (int) (mRateList.get(i) * 360), false, mPaint);
//            startAngle = startAngle + (int) (mRateList.get(i) * 360);
//        }
    }

    public void updateDate(List<Float> rateList, List<Integer> colorList) {
        this.mRateList = rateList;
        this.mColorList = colorList;
        invalidate();
    }
}

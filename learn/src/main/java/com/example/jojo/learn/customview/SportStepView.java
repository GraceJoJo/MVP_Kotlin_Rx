package com.example.jojo.learn.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.example.jojo.learn.R;

/**
 * Created by JoJo on 2018/7/31.
 * wechat:18510829974
 * description: 仿钉钉运动步数
 */

public class SportStepView extends View {


    //内圆环颜色
    private int innerRoundColor;
    //外圆环颜色
    private int outerRoundColor;
    //绘制背景圆环的画笔
    private Paint mPaint;
    //绘制外面进度的圆环的画笔
    private Paint mProgressPaint;
    //绘制外面进度的圆环的画笔
    private Paint mTextPaint;
    //背景圆弧的绘制的宽度
    private int mRoundWidth = 40;
    //进度圆环的宽度
    private float mProgressRoundWidth = 60;
    private int mTextSize = 40;//单位 sp

    //圆环最大进度
    private int mMaxProgress = 100;
    //圆环当前进度
    private int mCurrentProgress = 0;

    public SportStepView(Context context) {
        this(context, null);
    }

    public SportStepView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SportStepView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SportStepView);
        innerRoundColor = typedArray.getColor(R.styleable.SportStepView_innerRoundColor, Color.GRAY);
        outerRoundColor = typedArray.getColor(R.styleable.SportStepView_outerRoundColor, Color.GRAY);

        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获取宽的模式
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        //获取宽的尺寸
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        //对wrap_content这种模式进行处理
        if (heightMode == MeasureSpec.AT_MOST) {
            heightSize = widthSize;
        }
        //以宽度为标准保存丈量结果
        setMeasuredDimension(widthSize, heightSize);
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(mRoundWidth);

        mProgressPaint = new Paint();
        mProgressPaint.setAntiAlias(true);// 抗锯齿效果
        mProgressPaint.setStyle(Paint.Style.STROKE);
        mProgressPaint.setColor(Color.YELLOW);
        mProgressPaint.setStrokeCap(Paint.Cap.ROUND);// 圆形笔头
        mProgressPaint.setStrokeWidth(mProgressRoundWidth);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);// 抗锯齿效果
        mTextPaint.setStyle(Paint.Style.STROKE);
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setTextSize(sp2px(mTextSize));


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        /**
         * 如果背景圆环和进度圆环宽度不一致，都以较大的宽度为准绘制。避免出现两者显示不居中的问题
         */
        //绘制背景圆环，设置画笔的Style为Paint.Style.STROKE，则绘制出来的为圆环，否则绘制出来的为圆
//        canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2, mPaint);
        if (mRoundWidth < mProgressRoundWidth) {
            canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2 - mProgressRoundWidth / 2, mPaint);
        } else {
            //正常情况下
            canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2 - mRoundWidth / 2, mPaint);
        }

        if (mRoundWidth < mProgressRoundWidth) {
            // //正常情况下，绘制进度圆环
            RectF oval = new RectF(0 + mProgressRoundWidth / 2, 0 + mProgressRoundWidth / 2, getWidth() - mProgressRoundWidth / 2, getWidth() - mProgressRoundWidth / 2);
            //        RectF oval = new RectF(0 , 0, getWidth(), getWidth());
            //画圆弧 useCenter:是否显示圆内的横线 下面的绘制0,360的圆弧，也可以实现绘制圆环的效果
            canvas.drawArc(oval, 0 + 90, mCurrentProgress * 1f / mMaxProgress * 360, false, mProgressPaint);
        } else {
            //绘制进度圆环
            RectF oval = new RectF(0 + mRoundWidth / 2, 0 + mRoundWidth / 2, getWidth() - mRoundWidth / 2, getWidth() - mRoundWidth / 2);
            //画圆弧 useCenter:是否显示圆内的横线 下面的绘制0,360的圆弧，也可以实现绘制圆环的效果
            canvas.drawArc(oval, 0 + 90, mCurrentProgress * 1f / mMaxProgress * 360, false, mProgressPaint);
        }


        //绘制中间的文字
        Rect textRect = new Rect();
        //进度百分比
        int progressPercent = (int) (mCurrentProgress * 1f / mMaxProgress * 100);
        String mShowText = progressPercent + "%";
        mTextPaint.getTextBounds(mShowText, 0, mShowText.length(), textRect);
        canvas.drawText(mShowText, getWidth() / 2 - textRect.width() / 2, getHeight() / 2 + textRect.height() / 2, mTextPaint);
    }

    public void setCurrentProgress(int currentProgress) {
        this.mCurrentProgress = currentProgress;
    }

    public void setMaxProgress(int maxProgress) {
        this.mMaxProgress = maxProgress;
    }

    public int getMaxProgress() {
        return mMaxProgress;
    }

    public int getCurrentProgress() {
        return mCurrentProgress;
    }


    /**
     * 将sp转换成px
     *
     * @param sp
     * @return
     */
    private int sp2px(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
                getResources().getDisplayMetrics());
    }
}

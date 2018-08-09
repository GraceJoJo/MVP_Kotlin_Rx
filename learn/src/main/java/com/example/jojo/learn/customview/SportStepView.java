package com.example.jojo.learn.customview;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.example.jojo.learn.R;

/**
 * Created by JoJo on 2018/8/1.
 * wechat:18510829974
 * description: 仿钉钉运动步数效果
 */

public class SportStepView extends View {


    private Context mContext;
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
    private int mRoundWidth = 10;
    //进度圆环的宽度
    private float mProgressRoundWidth = 15;
    //中间步数文字的大小
    private int mTextSize = 40;//单位 sp
    //名次文字大小
    private int mRandTextSize = 15;
    //圆环最大进度
    private int mMaxStep = 10000;
    //圆环当前进度
    private float mCurrentStep = 0;
    //排名与步数文字直接的间隔
    private float textPadding = 80;


    public SportStepView(Context context) {
        this(context, null);
    }

    public SportStepView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SportStepView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SportStepView);
        innerRoundColor = typedArray.getColor(R.styleable.SportStepView_innerRoundColor, ContextCompat.getColor(mContext, R.color.color_e4e4e4));
        outerRoundColor = typedArray.getColor(R.styleable.SportStepView_outerRoundColor, ContextCompat.getColor(mContext, R.color.color_4fd8f5));

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
        mPaint.setColor(innerRoundColor);
        mPaint.setStrokeCap(Paint.Cap.ROUND);// 圆形笔头
        mPaint.setStrokeWidth(mRoundWidth);

        mProgressPaint = new Paint();
        mProgressPaint.setAntiAlias(true);// 抗锯齿效果
        mProgressPaint.setStyle(Paint.Style.STROKE);
        mProgressPaint.setColor(outerRoundColor);
        mProgressPaint.setStrokeCap(Paint.Cap.ROUND);// 圆形笔头
        mProgressPaint.setStrokeWidth(mProgressRoundWidth);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);// 抗锯齿效果
        mTextPaint.setStyle(Paint.Style.STROKE);
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setColor(ContextCompat.getColor(mContext, R.color.color_56a9ff));
        mTextPaint.setTextSize(sp2px(mTextSize));


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /**
         *  处理渐变色
         */
        //默认的渐变颜色数组:
//        int[] mGradientColorArray = new int[]{ContextCompat.getColor(mContext, R.color.color_5e9fff),  ContextCompat.getColor(mContext, R.color.color_4fd8f5), ContextCompat.getColor(mContext, R.color.color_5e9fff)};
        int[] mGradientColorArray = new int[]{ContextCompat.getColor(mContext, R.color.color_4fd8f5), ContextCompat.getColor(mContext, R.color.color_4fd8f5), ContextCompat.getColor(mContext, R.color.color_5e9fff), ContextCompat.getColor(mContext, R.color.color_4fd8f5)};
        int count = mGradientColorArray.length;
        int[] colors = new int[count];
        System.arraycopy(mGradientColorArray, 0, colors, 0, count);
        float[] positions = new float[count];
        float v = (360f / 270);
        positions[0] = 0.0f;
        positions[1] = 0.33f * v;
        positions[2] = 0.67f * v;
        positions[3] = 1.0f;
        SweepGradient shader = new SweepGradient(getWidth() / 2 - mRoundWidth / 2, getWidth() / 2 - mRoundWidth / 2, mGradientColorArray, positions);
        mProgressPaint.setShader(shader);


        if (mRoundWidth < mProgressRoundWidth) {
            RectF oval = new RectF(0 + mProgressRoundWidth / 2, 0 + mProgressRoundWidth / 2, getWidth() - mProgressRoundWidth / 2, getWidth() - mProgressRoundWidth / 2);
            //绘制背景圆环
            canvas.drawArc(oval, 135, 270, false, mPaint);

            //绘制进度圆环，绘制的角度最大不超过270°
            if (mCurrentStep * 1f / mMaxStep <= 1) {
                canvas.drawArc(oval, 0 + 135, mCurrentStep / mMaxStep * 270, false, mProgressPaint);
            } else {
                canvas.drawArc(oval, 0 + 135, 270, false, mProgressPaint);
            }
        } else {
            RectF oval = new RectF(0 + mRoundWidth / 2, 0 + mRoundWidth / 2, getWidth() - mRoundWidth / 2, getWidth() - mRoundWidth / 2);
            //绘制背景圆环
            canvas.drawArc(oval, 135, 270, false, mPaint);
            //绘制进度圆环
            if (mCurrentStep * 1f / mMaxStep <= 1) {
                canvas.drawArc(oval, 0 + 135, mCurrentStep / mMaxStep * 270, false, mProgressPaint);
            } else {
                canvas.drawArc(oval, 0 + 135, 270, false, mProgressPaint);
            }
        }

        //绘制中间的步数的文字
        Rect textRect = new Rect();
        String mShowText = (int) mCurrentStep + "";
        mTextPaint.setTextSize(

                sp2px(mTextSize));
        mTextPaint.getTextBounds(mShowText, 0, mShowText.length(), textRect);
        canvas.drawText(mShowText, getWidth() / 2 - textRect.width() / 2, getHeight() / 2 + textRect.height() / 2, mTextPaint);

        //绘制排名的文字，在步数文字的上方
        String mRandText = "第4名";
        mTextPaint.setTextSize(

                sp2px(mRandTextSize));
        mTextPaint.getTextBounds(mRandText, 0, mRandText.length(), textRect);
        canvas.drawText(mRandText, getWidth() / 2 - textRect.width() / 2, getHeight() / 2 + textRect.height() / 2 - (textRect.height() + textPadding), mTextPaint);


    }

    public void setCurrentStep(float currentStep) {
        this.mCurrentStep = currentStep;
        //强制重绘，postInvalidate()可以在主线程也可以在分线程中执行
        postInvalidate();
    }

    public void setMaxProgress(int maxStep) {
        this.mMaxStep = maxStep;
    }

    public int getMaxtep() {
        return mMaxStep;
    }

    public float getCurrentStep() {
        return mCurrentStep;
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

    //速度,值越大，变化速度越快
    private float rate = 128;

    /**
     * 开始动态计步
     *
     * @param currentStep
     */
    public void startCountStep(final float currentStep) {
        //方法一：开一个分线程，动态改变进度的值，不断绘制达到进度变化的效果
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                setCurrentStep(0);
//                float changeProgress = currentStep;
//                for (float i = 0; i < changeProgress; i++) {
//                    setCurrentStep(getCurrentStep() + rate);
//                    SystemClock.sleep(20);
////                  invalidate();//invalidate()必须在主线程中执行，此处不能使用
////                  postInvalidate();//强制重绘，postInvalidate()可以在主线程也可以在分线程中执行
//                    changeProgress = changeProgress - rate;
//                }
//                //由于上面的循环结束时，可能计算后最终无法到达mCurrentProgress的值，所以在循环结束后，将mCurrentProgress重新设置
//                setCurrentStep(currentStep);
//            }
//        }).start();

        /**
         * 方法二
         */
        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0, currentStep);
        valueAnimator.setDuration(1600);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentStep = (float) animation.getAnimatedValue();
                setCurrentStep((int) currentStep);
            }
        });
        valueAnimator.start();
    }
}

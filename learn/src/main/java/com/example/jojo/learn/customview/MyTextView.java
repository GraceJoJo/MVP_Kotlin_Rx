package com.example.jojo.learn.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import com.example.jojo.learn.R;

import java.util.ArrayList;

/**
 * Created by JoJo on 2018/7/27.
 * wechat:18510829974
 * description:自定义Textview
 */

public class MyTextView extends View {
    //文字内容
    private String mText = "this is text";
    //文字大小
    private int mTextSize;
    //文字颜色
    private int mTextColor;
    //绘制的范围
    private Rect mTextBound;
    //绘制文字的画笔
    private Paint mPaint;
    private int mScreenWidth;
    private int mScreenHeight;
    private int baseLineY;
    private float ascent;
    private float descent;
    private float top;
    private float bottom;
    private int baseLineX;
    private Rect mMaxRect;
    private Rect mTextBoundOther;

    public MyTextView(Context context) {
        this(context, null);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MyTextViewApprence, defStyleAttr, 0);
        mText = typedArray.getString(R.styleable.MyTextViewApprence_text);
        mTextColor = typedArray.getColor(R.styleable.MyTextViewApprence_textColor, Color.BLACK);
        mTextSize = (int) typedArray.getDimension(R.styleable.MyTextViewApprence_textSize, 15);
        showMode = typedArray.getInt(R.styleable.MyTextViewApprence_showMode, 0);
        typedArray.recycle();

        //屏幕信息
        DisplayMetrics dm = getResources().getDisplayMetrics();
        mScreenHeight = dm.heightPixels;
        mScreenWidth = dm.widthPixels;

        init();
    }

    private void init() {
        //基线
//        baseLineY = getHeight() / 2 + mTextBound.height() / 2;
        baseLineY = mTextSize + 200;
        baseLineX = 0 + 200;

        //初始化画笔
        mPaint = new Paint();
        mPaint.setColor(mTextColor);
        mPaint.setTextSize(mTextSize);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(1);

        //获取绘制的宽高
        mTextBound = new Rect();
        mPaint.getTextBounds(mText, 0, mText.length(), mTextBound);

        mTextBoundOther = new Rect();
        mText = "Hello World !.....Hello World !.....Hello World !.....Hello World !.....Hello World !.....Hello World !.....Hello World !.....I Love U ~";
        mPaint.getTextBounds(mText, 0, mText.length(), mTextBoundOther);

        mTextBound.top = baseLineY + mTextBound.top;
        mTextBound.bottom = baseLineY + mTextBound.bottom;
        mTextBound.left = baseLineX + mTextBound.left;
        mTextBound.right = baseLineX + mTextBound.right;
        //获取文字所占区域最小矩形
        Log.e("TAG", mTextBound.toShortString());

        //计算各线在位置
        Paint.FontMetrics fm = mPaint.getFontMetrics();


        ascent = baseLineY + fm.ascent;//当前绘制顶线
        descent = baseLineY + fm.descent;//当前绘制底线
        top = baseLineY + fm.top;//可绘制最顶线
        bottom = baseLineY + fm.bottom;//可绘制最低线

        //字符串所占的高度和宽度
        int width = (int) mPaint.measureText(mText);
        int height = (int) (bottom - top);
        mMaxRect = new Rect(baseLineX, (int) (baseLineY + fm.top), (baseLineX + width), (int) (baseLineY + fm.bottom));

    }

    private ArrayList<String> mTextList = new ArrayList<>();
    private float lineNum;//文字最终所占的行数
    private float spLineNum;
    private boolean isOneLine;


    //换行展示的对齐方式
    private int showMode;

    /**
     * 测量
     * 父控件不强加任何约束给子控件，它可以为它逍遥的任何大小
     * public static final int UNSPECIFIED = 0 << MODE_SHIFT; //0
     * 父控件给子控件一个精确的值-match_parent
     * public static final int EXACTLY     = 1 << MODE_SHIFT; //1073741824
     * 父控件给子控件竟可能最大的值-wrap_content
     * public static final int AT_MOST     = 2 << MODE_SHIFT; //-2147483648
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获取宽的模式
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        //获取宽的尺寸
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        Log.e("TAG", "widthMode=" + widthMode + "  heightMode=" + heightMode + "  widthSize=" + widthSize + "  heightSize=" + heightSize);

        //(1)实现文字自动换行显示
        //文字的宽度
        int mTextWidth = mTextBoundOther.width();
        if (mTextList.size() == 0) {
            //将文本分段
            int padding = getPaddingLeft() + getPaddingRight();
            int specMaxWidth = widthSize - padding;//可显示文本的最大宽度
            //最大宽度大于文字所占宽度，则一行就能显示完全
            if (specMaxWidth >= mTextWidth) {
                lineNum = 1;
                mTextList.add(mText);
                isOneLine = true;
            } else {
                //超过一行，需切割，分行显示
                isOneLine = false;
                spLineNum = mTextWidth * 1.0f / specMaxWidth;

                //如果有小数的话就进1
                if ((spLineNum + "").contains(".")) {
                    lineNum = (float) (spLineNum + 0.5);
                } else {
                    lineNum = spLineNum;
                }

                //每行展示的文字的长度
                int lineLength = (int) (mText.length() / spLineNum);
                for (int i = 0; i < lineNum; i++) {
                    String lineStr;
                    //判断是否可以一行展示
                    if (mText.length() < lineLength) {
                        lineStr = mText.substring(0, mText.length());
                    } else {
                        lineStr = mText.substring(0, lineLength);
                    }

                    mTextList.add(lineStr);
                    //内容切割完，记录切割后的字符串，重新赋值给mText
                    if (!TextUtils.isEmpty(mText)) {
                        if (mText.length() < lineLength) {
                            mText = mText.substring(0, mText.length());
                        } else {
                            mText = mText.substring(lineLength, mText.length());
                        }
                    } else {
                        break;
                    }
                }

            }
        }


        //(2)下面对wrap_content这种模式进行处理
        int width;
        int height;
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            //如果是wrap_content,我们需要得到控件需要多大的尺寸
            //首先丈量文本的宽度
            float textWidth;
            if (mTextList.size() > 1) {
                textWidth = widthSize;
            } else {
                textWidth = mTextBoundOther.width();
            }
            //控件的宽度就是文本的宽度加上两边的内边距。内边距就是padding的值，在构造方法执行完被赋值
            width = (int) (getPaddingLeft() + textWidth + getPaddingRight());
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            //如果是wrap_content,我们需要得到控件需要多大的尺寸
            //首先丈量文本的宽度

//            float textHeight = mTextBoundOther.height();
            float textHeight = mTextBoundOther.height() * mTextList.size();
            //控件的宽度就是文本的宽度加上两边的内边距。内边距就是padding的值，在构造方法执行完被赋值
            height = (int) (getPaddingTop() + textHeight + getPaddingBottom());
        }
        //保存丈量结果
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        //绘制字符串所占的矩形区域
//        mPaint.setColor(Color.GREEN);
//        canvas.drawRect(mMaxRect, mPaint);
//
//        //绘制最小矩形
//        mPaint.setColor(Color.RED);
//        canvas.drawRect(mTextBound, mPaint);
//
////        canvas.drawText(mText, 0, mTextSize, mPaint);
//        //绘制文字-绘制的起点是：绘制文字所在矩形的左下角顶点
//        mPaint.setColor(Color.WHITE);
//        canvas.drawText(mText, baseLineX, baseLineY, mPaint);
//
//        //绘制基线
//        mPaint.setColor(Color.RED);
//        canvas.drawLine(0, baseLineY, mScreenWidth, baseLineY, mPaint);
//
//        mPaint.setColor(Color.YELLOW);
//        canvas.drawLine(0, top, mScreenWidth, top, mPaint);
//        mPaint.setColor(Color.BLUE);
//        canvas.drawLine(0, ascent, mScreenWidth, ascent, mPaint);
//        mPaint.setColor(Color.BLACK);
//        canvas.drawLine(0, descent, mScreenWidth, descent, mPaint);
//        mPaint.setColor(Color.WHITE);
//        canvas.drawLine(0, bottom, mScreenWidth, bottom, mPaint);

        //绘制Hello World !
//        canvas.drawText(mText, getWidth() / 2 - mTextBoundOther.width() / 2, getHeight() / 2 + mTextBoundOther.height() / 2, mPaint);

        //分行绘制文字
        for (int i = 0; i < mTextList.size(); i++) {
            mPaint.getTextBounds(mTextList.get(i), 0, mTextList.get(i).length(), mTextBoundOther);
            //换行左对齐展示
            if (showMode == 0) {
                canvas.drawText(mTextList.get(i), 0 + getPaddingLeft(), (getPaddingTop() + mTextBoundOther.height() * (i + 1)), mPaint);
            } else if (showMode == 1) {
                //换行居中展示
                canvas.drawText(mTextList.get(i), (getWidth() / 2 - mTextBoundOther.width() / 2) + getPaddingLeft(), (getPaddingTop() + mTextBoundOther.height() * (i + 1)), mPaint);
            }

            Log.v("TAG", "在X:" + (getWidth() / 2 - mTextBoundOther.width() / 2) + "  Y:" + (getPaddingTop() + (mTextBoundOther.height() * i)) + "  绘制：" + mTextList.get(i));
            Log.i("TAG", "getWidth() :" + getWidth() + ", mBound.width():" + mTextBoundOther.width() + ",getHeight:" + getHeight() + ",mBound.height() *i:" + mTextBoundOther.height() * i);
        }
    }
}

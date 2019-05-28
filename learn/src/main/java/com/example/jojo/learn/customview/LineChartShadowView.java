package com.example.jojo.learn.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.example.jojo.learn.R;
import com.example.jojo.learn.utils.DP2PX;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by JoJo on 2018/8/3.
 * wechat:18510829974
 * description: 曲线图/折线图
 */

public class LineChartShadowView extends View {
    private Context mContext;
    //绘制坐标轴的画笔
    private Paint mAxisPaint;
    //绘制曲线的画笔
    private Paint mPaint;
    //绘制X轴上方的画笔
    private Paint mXAxisLinePaint;
    private Paint mPaintText;
    //折线下的阴影的画笔
    private Paint baseShadow;
    //向上的曲线图的绘制起点(px)
    private int startX;
    private int startY;
    //向下的曲线图的绘制起点(px)
    private int downStartX;
    private int downStartY;
    //上方Y轴每单位刻度所占的像素值
    private float YAxisUpUnitValue;
    //下方Y轴每单位刻度所占的像素值
    private float YAxisDownUnitValue;
    //根据具体传入的数据，在坐标轴上绘制点
    private Point[] mPoints;
    //传入的数据，决定绘制的纵坐标值
    private List<Integer> mDatas = new ArrayList<>();
    //Y轴刻度集合
    private List<Integer> mYAxisList = new ArrayList<>();
    //X轴刻度集合
    private List<String> mXAxisList = new ArrayList<>();
    //X轴的绘制距离
    private int mXAxisMaxValue;
    //Y轴的绘制距离
    private int mYAxisMaxValue;
    //Y轴刻度间距(px)
    private int yAxisSpace = 40;
    //X轴刻度间距(px)
    private int xAxisSpace = 120;
    //Y轴刻度线宽度
    private int mKeduWidth = 20;
    private int keduTextSize = 11;//sp
    //刻度值距离坐标的padding距离
    private int textPadinng = 10;
    //X轴刻度文本距离X轴的边距
    private int yAxisTextPadding = 60;
    //Y轴递增的实际值
    private int yIncreaseValue;
    //true：绘制曲线 false：折线
    private boolean isCurve = true;
    private Rect mYMaxTextRect;
    private Rect mXMaxTextRect;
    //最大的数据值所占的文字矩形区域
    private Rect mMaxDataValueTextRect;
    private int mMaxTextHeight;
    private int mMaxTextWidth;
    private Path baseLinePath;
    private Paint mOutCirclePaint;
    private Paint mInnerCirclePaint;
    //内圆小圆点半径
    private int innerRadius = 10;
    //外圆小圆点半径
    private int outRadius = 16;
    //坐标上的点和点上文字之间的距离
    private int dotTextPading = 10;

    public LineChartShadowView(Context context) {
        this(context, null);
    }

    public LineChartShadowView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineChartShadowView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initData();
        initView();

    }

    int widthResult = 0;
    int heightResult = 0;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (heightMode == MeasureSpec.AT_MOST) {
            heightResult = (mYAxisList.size() - 1) * yAxisSpace + mMaxTextHeight * 2 + textPadinng * 2 + yAxisTextPadding + mMaxDataValueTextRect.height();
        } else if (heightMode == MeasureSpec.EXACTLY) {
            heightResult = Math.max(getContentHeight(), heightSize);
        }
        if (widthMode == MeasureSpec.AT_MOST) {
            widthResult = startX + (mDatas.size() - 1) * xAxisSpace + mMaxTextWidth;
        } else if (heightMode == MeasureSpec.EXACTLY) {
            widthResult = Math.max(getContentWidth(), widthSize);
        }
        //保存测量结果
        setMeasuredDimension(widthResult, heightResult);
    }

    /**
     * 获取控件内容总的宽度
     *
     * @return
     */
    private int getContentHeight() {
        return (mYAxisList.size() - 1) * yAxisSpace + mMaxTextHeight * 2 + textPadinng * 2 + yAxisTextPadding + mMaxDataValueTextRect.height();
    }

    /**
     * 获取控件内容总的宽度
     *
     * @return
     */
    private int getContentWidth() {
        return startX + (mDatas.size() - 1) * xAxisSpace + mMaxTextWidth;
    }

    private void initView() {
        //初始化画笔
        initPaint();

        //折线下的阴影的画笔
        baseShadow = new Paint();
        baseShadow.setAntiAlias(true);
        baseShadow.setColor((Color.WHITE & 0x40FFFFFF) | 0x10000000);
        baseShadow.setStyle(Paint.Style.FILL);


        mYMaxTextRect = new Rect();
        mXMaxTextRect = new Rect();
        mMaxDataValueTextRect = new Rect();//最大的数据值所占的文字矩形区域
        mPaintText.getTextBounds(Integer.toString(mYAxisList.get(mYAxisList.size() - 1)), 0, Integer.toString(mYAxisList.get(mYAxisList.size() - 1)).length(), mYMaxTextRect);
        mPaintText.getTextBounds(mXAxisList.get(mXAxisList.size() - 1), 0, mXAxisList.get(mXAxisList.size() - 1).length(), mXMaxTextRect);
        mPaintText.getTextBounds(Collections.max(mDatas) + "段", 0, (Collections.max(mDatas) + "段").length(), mMaxDataValueTextRect);
        //绘制的刻度文字的最大值所占的宽高
        mMaxTextWidth = mYMaxTextRect.width() > mXMaxTextRect.width() ? mYMaxTextRect.width() : mXMaxTextRect.width();
        mMaxTextHeight = mYMaxTextRect.height() > mXMaxTextRect.height() ? mYMaxTextRect.height() : mXMaxTextRect.height();


        //指定绘制的起始位置
        startX = mMaxTextWidth + textPadinng + mKeduWidth;
        //坐标原点Y的位置（+1的原因：X轴画笔的宽度为2 ; +DP2PX.dip2px(mContext, 5)原因：为刻度文字所占的超出的高度 ）——>解决曲线画到最大刻度值时，显示高度不够，曲线显示扁扁的问题
        startY = yAxisSpace * (mYAxisList.size() - 1) + mMaxTextHeight;

        if (mYAxisList.size() >= 2) {
            yIncreaseValue = mYAxisList.get(1) - mYAxisList.get(0);
        }
        //X轴绘制距离
        mXAxisMaxValue = (mDatas.size() - 1) * xAxisSpace;
        //Y轴绘制距离
        mYAxisMaxValue = (mYAxisList.size() - 1) * yAxisSpace;

        //坐标起始点Y轴高度=(startY+mKeduWidth)  下方文字所占高度= DP2PX.dip2px(mContext, keduTextSize)
        int viewHeight = startY + 2 * mKeduWidth + DP2PX.dip2px(mContext, keduTextSize);
        //viewHeight=121
        Log.e("TAG", "viewHeight=" + viewHeight);

        baseLinePath = new Path();


    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        //绘制曲线的画笔
        mPaint = new Paint();
        mPaint.setColor(ContextCompat.getColor(mContext, R.color.color_e05864));
        mPaint.setStrokeWidth(2);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);

        //绘制数据外圆的点的画笔
        mOutCirclePaint = new Paint();
        mOutCirclePaint.setColor(Color.WHITE);
        mOutCirclePaint.setStrokeWidth(2);
        mOutCirclePaint.setAntiAlias(true);
        mOutCirclePaint.setStyle(Paint.Style.FILL);

        //绘制数据内圆的点的画笔
        mInnerCirclePaint = new Paint();
        mInnerCirclePaint.setColor(ContextCompat.getColor(mContext, R.color.color_e05864));
        mInnerCirclePaint.setStrokeWidth(2);
        mInnerCirclePaint.setAntiAlias(true);
        mInnerCirclePaint.setStyle(Paint.Style.FILL);

        //绘制X,Y轴坐标的画笔
        mAxisPaint = new Paint();
        mAxisPaint.setColor(ContextCompat.getColor(mContext, R.color.color_274782));
        mAxisPaint.setStrokeWidth(2);
        mAxisPaint.setAntiAlias(true);
        mAxisPaint.setStyle(Paint.Style.STROKE);
        //绘制坐标轴上方的横线的画笔
        mXAxisLinePaint = new Paint();
        mXAxisLinePaint.setColor(ContextCompat.getColor(mContext, R.color.color_274782));
        mXAxisLinePaint.setStrokeWidth(1);
        mXAxisLinePaint.setAntiAlias(true);
        mXAxisLinePaint.setStyle(Paint.Style.STROKE);

        //绘制刻度值文字的画笔
        mPaintText = new Paint();
        mPaintText.setTextSize(sp2px(keduTextSize));
        mPaintText.setColor(ContextCompat.getColor(mContext, R.color.color_999999));
        mPaintText.setAntiAlias(true);
        mPaintText.setStrokeWidth(1);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPoints = initPoint(canvas);

//        for (int i = 0; i < mYAxisList.size(); i++) {
//            //Y轴方向递增的高度
//            int yAxisHeight = startY - yAxisSpace * i;
//            //绘制X轴和上方横线
//            canvas.drawLine(startX - mKeduWidth, yAxisHeight, startX + (mDatas.size() - 1) * xAxisSpace, yAxisHeight, mXAxisLinePaint);
//            //绘制左边Y轴刻度线
////                canvas.drawLine(startX, yAxisHeight, startX - mKeduWidth, yAxisHeight, mAxisPaint);
//            //绘制文字时,Y轴方向递增的高度
//            int yTextHeight = startY - yAxisSpace * i;
//            //绘制Y轴刻度旁边的刻度文字值,10为刻度线与文字的间距
//            mPaintText.setTextAlign(Paint.Align.RIGHT);
//            canvas.drawText(mYAxisList.get(i) + "", (startX - mKeduWidth) - textPadinng, yTextHeight, mPaintText);
//        }
//        //绘制Y轴
//        canvas.drawLine(startX, startY, startX, startY - mYAxisMaxValue, mAxisPaint);

        //绘制X轴下面显示的文字
        for (int i = 0; i < mXAxisList.size(); i++) {
            int xTextWidth = startX + xAxisSpace * i - mKeduWidth;
            //设置从起点位置的左边对齐绘制文字
            mPaintText.setTextAlign(Paint.Align.LEFT);
            Rect rect = new Rect();
            mPaintText.getTextBounds(mXAxisList.get(i), 0, mXAxisList.get(i).length(), rect);
            canvas.drawText(mXAxisList.get(i), startX - rect.width() / 2 + xAxisSpace * i, startY + rect.height() + textPadinng + yAxisTextPadding, mPaintText);
        }
        //连接所有的数据点,画曲线

        if (isCurve) {
            //画曲线
            drawScrollLine(canvas);
        } else {
            //画折线
            drawLine(canvas);
        }

        //绘制坐标上的点及值
        drawPoint(canvas);
    }


    /**
     * 根据传入的数据，确定绘制的点
     *
     * @param canvas
     * @return
     */
    private Point[] initPoint(Canvas canvas) {
        Point[] points = new Point[mDatas.size()];
        for (int i = 0; i < mDatas.size(); i++) {
            Integer ybean = mDatas.get(i);
            int drawHeight = (int) (startY * 1.0 - (ybean * yAxisSpace * 1.0 / yIncreaseValue));
            int startx = startX + xAxisSpace * i;
            points[i] = new Point(startx, drawHeight);
        }
        Log.e("TAG", "startX=" + startX + "---startY=" + startY);
        return points;
    }


    /**
     * 绘制曲线-曲线图
     *
     * @param canvas
     */
    private void drawScrollLine(Canvas canvas) {
        Point startp;
        Point endp;
        baseLinePath.reset();
        baseLinePath.moveTo(mPoints[0].x, mPoints[0].y);
        for (int i = 0; i < mPoints.length - 1; i++) {
            startp = mPoints[i];
            endp = mPoints[i + 1];
            int wt = (startp.x + endp.x) / 2;
            Point p3 = new Point();
            Point p4 = new Point();
            p3.y = startp.y;
            p3.x = wt;
            p4.y = endp.y;
            p4.x = wt;
//            Path path = new Path();
//            path.moveTo(startp.x, startp.y);
//            path.cubicTo(p3.x, p3.y, p4.x, p4.y, endp.x, endp.y);
//            canvas.drawPath(path, mPaint);
            baseLinePath.cubicTo(p3.x, p3.y, p4.x, p4.y, endp.x, endp.y);
        }
        canvas.drawPath(baseLinePath, mPaint);
        //绘制曲线下面的阴影
        drawArea(canvas, mPoints, baseLinePath);
    }

    /**
     * 绘制坐标上的各个数据点和数据值
     *
     * @param canvas
     */
    private void drawPoint(Canvas canvas) {
        // 需禁用硬件加速 使用绘制带阴影的圆的方法处理：https://www.jianshu.com/p/5c558f43ce2e
//        setLayerType(LAYER_TYPE_SOFTWARE, null);
//        mOutCirclePaint.setMaskFilter(new BlurMaskFilter(outRadius, BlurMaskFilter.Blur.SOLID));
        Bitmap dotBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_dot);
        for (int i = 0; i < mPoints.length; i++) {
//            canvas.drawCircle(mPoints[i].x, mPoints[i].y, outRadius, mOutCirclePaint);
//            canvas.drawCircle(mPoints[i].x, mPoints[i].y, innerRadius, mInnerCirclePaint);
            canvas.drawBitmap(dotBitmap, mPoints[i].x - dotBitmap.getHeight() / 2, mPoints[i].y - dotBitmap.getHeight() / 2, mOutCirclePaint);


            Rect rectText = new Rect();
            mPaintText.getTextBounds(mDatas.get(i) + "段", 0, (mDatas.get(i) + "段").length(), rectText);
            //绘制点上的值
            canvas.drawText(mDatas.get(i) + "段", mPoints[i].x - dotBitmap.getHeight() / 2,
                    mPoints[i].y - dotBitmap.getHeight() / 2 - rectText.height() / 2, mPaintText);

        }
    }

    /**
     * 阴影层叠
     *
     * @param canvas
     */

    private void drawArea(Canvas canvas, Point[] points, Path path) {
//        String[] colors = {"#f898a0", "#f9adb4", "#fffefe"};
        String[] colors = {"#ff0000", "#80ff0000", "#80fffefe"};
//        float[] floats = {0.2f, 0.60f, 0.85f};//渐变比例
//        float[] floats = {0.2f, 0.50f, 0.85f};//渐变比例
        float[] floats = {0.2f, 0.50f, 0.75f};//调整渐变比例
        LinearGradient mShader = new LinearGradient(0, 0, 0, getMeasuredHeight(), new int[]{Color.parseColor(colors[0]), Color.parseColor(colors[1]), Color.parseColor(colors[2])}, floats, Shader.TileMode.REPEAT);
        baseShadow.setShader(mShader);
        path.lineTo(points[points.length - 1].x, heightResult);
        path.lineTo(points[0].x, widthResult);
        path.close();
        canvas.drawPath(path, baseShadow);
    }


    /**
     * 绘制直线-折线图
     *
     * @param canvas
     */

    private void drawLine(Canvas canvas) {
        Point startp;
        Point endp;
        for (int i = 0; i < mPoints.length - 1; i++) {
            startp = mPoints[i];
            endp = mPoints[i + 1];
            canvas.drawLine(startp.x, startp.y, endp.x, endp.y, mPaint);
        }
    }

    private void initData() {
        //外界传入的数据，即为绘制曲线的每个点
        mDatas.add(0);
        mDatas.add(10);
        mDatas.add(5);
        mDatas.add(20);
        mDatas.add(15);

        int[] mYAxisData = new int[]{0, 10, 20, 30, 40};
        for (int i = 0; i < mYAxisData.length; i++) {
            mYAxisList.add(mYAxisData[i]);
        }

        //X轴数据
        mXAxisList.add("01月");
        mXAxisList.add("02月");
        mXAxisList.add("03月");
        mXAxisList.add("04月");
        mXAxisList.add("05月");
    }

    /**
     * 传入数据，重新绘制图表
     *
     * @param datas
     * @param yAxisData
     */
    public void updateData(List<Integer> datas, List<String> xAxisData, List<Integer> yAxisData) {
        this.mDatas = datas;
        this.mXAxisList = xAxisData;
        this.mYAxisList = yAxisData;
        initView();
        postInvalidate();
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

package com.example.jojo.learn;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;

import com.example.jojo.learn.customview.MyTextView;
import com.example.jojo.learn.customview.SportStepView;

public class MainActivity extends AppCompatActivity {

    private MyTextView mytextview;
    private SportStepView sportStepView;
    private int mCurrentProgress;
    private int rate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        mytextview = findViewById(R.id.mytextview);
        final SportStepView sportStepView = findViewById(R.id.sportstepview);
        mCurrentProgress = 80;
        //速度,值越大，变化速度越快
        rate = 2;
        //开一个分线程，动态改变进度的值，不断绘制达到进度变化的效果
        new Thread(new Runnable() {
            @Override
            public void run() {
                sportStepView.setCurrentProgress(0);

                for (int i = 0; i < mCurrentProgress / rate; i++) {
                    sportStepView.setCurrentProgress(sportStepView.getCurrentProgress() + rate);
                    SystemClock.sleep(20);
//                pb_progress.invalidate();//invalidate()必须在主线程中执行，此处不能使用
                    sportStepView.postInvalidate();//强制重绘，postInvalidate()可以在主线程也可以在分线程中执行
                }
            }
        }).start();

    }

    //    public void textLayoutLeft(View v) {
//        mytextview.reLayoutText(0);
//    }
//    public void textLayoutCenter(View v) {
//        mytextview.reLayoutText(1);
//    }
}

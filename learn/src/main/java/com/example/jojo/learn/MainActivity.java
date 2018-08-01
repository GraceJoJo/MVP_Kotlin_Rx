package com.example.jojo.learn;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.jojo.learn.customview.MyTextView;
import com.example.jojo.learn.customview.SportStepView;

public class MainActivity extends AppCompatActivity {

    private MyTextView mytextview;
    private float mCurrentStep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        mytextview = findViewById(R.id.mytextview);
        final SportStepView sportStepView = findViewById(R.id.sportstepview);
        //当前实际的步数
        mCurrentStep = 9709;
        sportStepView.startCountStep(mCurrentStep);

    }

    //    public void textLayoutLeft(View v) {
//        mytextview.reLayoutText(0);
//    }
//    public void textLayoutCenter(View v) {
//        mytextview.reLayoutText(1);
//    }
}

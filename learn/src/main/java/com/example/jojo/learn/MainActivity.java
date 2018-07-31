package com.example.jojo.learn;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.jojo.learn.customview.MyTextView;

public class MainActivity extends AppCompatActivity {

    private MyTextView mytextview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mytextview = findViewById(R.id.mytextview);
    }
    public void textLayoutLeft(View v) {
        mytextview.reLayoutText(0);
    }
    public void textLayoutCenter(View v) {
        mytextview.reLayoutText(1);
    }
}

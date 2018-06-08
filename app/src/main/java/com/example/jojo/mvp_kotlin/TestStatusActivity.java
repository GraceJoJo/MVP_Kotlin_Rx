package com.example.jojo.mvp_kotlin;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by JoJo on 2018/6/1.
 * wechat:18510829974
 * description: [封装一个BaseActivity并实现沉浸式状态栏](https://blog.csdn.net/qq_37842258/article/details/77479414)
 *
 */

public class TestStatusActivity extends BaseSatusActivity {
    private Button btn;

    @Override
    int setLayout() {
        return R.layout.act_test_status;
    }

    @Override
    protected void initView() {
//        setColor(MainActivity.this,Color.BLACK); // 改变状态栏的颜色
//        setTranslucent(TestStatusActivity.this); // 改变状态栏变成透明
        setTranslucentStatus(true);
        btn = bindView(R.id.btn);
//        setColor(this, Color.RED);
    }

    @Override
    protected void initData() {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TestStatusActivity.this, "按钮被触发", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

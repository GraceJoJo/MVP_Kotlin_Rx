package com.example.jojo.mvp_kotlin;

import android.widget.Button;
import android.widget.Toast;

import com.example.jojo.mvp_kotlin.base.BaseActivity;
import com.example.jojo.mvp_kotlin.ioc.ContentView;
import com.example.jojo.mvp_kotlin.ioc.ViewById;

/**
 * Created by JoJo on 2018/4/19.
 * wechat:18510829974
 * description:
 */
@ContentView(R.layout.act_splash)
public class TestLambda extends BaseActivity {
    @ViewById(R.id.button)
    Button button;
    @Override
    public void initView() {
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(TestLambda.this,"this is lambda",Toast.LENGTH_SHORT).show();
//            }
//        });
        button.setOnClickListener(v->Toast.makeText(TestLambda.this,"this is lambda",Toast.LENGTH_SHORT).show());
    }
}

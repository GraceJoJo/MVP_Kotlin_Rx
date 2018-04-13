package com.example.jojo.mvp_kotlin.mvp.ui;

import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jojo.mvp_kotlin.R;
import com.example.jojo.mvp_kotlin.ioc.ContentView;
import com.example.jojo.mvp_kotlin.ioc.OnClickView;
import com.example.jojo.mvp_kotlin.ioc.ViewById;
import com.example.jojo.mvp_kotlin.mvp.base.ACT_Base;
import com.example.jojo.mvp_kotlin.mvp.contract.LoginContract;
import com.example.jojo.mvp_kotlin.mvp.model.LoginModel;
import com.example.jojo.mvp_kotlin.mvp.presenter.LoginPresenter;


/**
 * Created by JoJo on 2018/3/29.
 * wechat:18510829974
 * description:
 */
@ContentView(R.layout.act_splash)
public class TestActivity extends ACT_Base<LoginPresenter, LoginModel> implements LoginContract.View {
    @ViewById(R.id.textView)
    TextView textView;

    @Override
    public void loginSuccess() {
        Log.e("TAG", "loginSuccess()");
    }

    @Override
    public void showMsg(String msg) {
        Log.e("TAG", "showMsg---" + msg);
    }

    @OnClickView({R.id.button, R.id.textView})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.textView:
                Toast.makeText(TestActivity.this, "点击了textView", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button:
                Toast.makeText(TestActivity.this, "点击了button", Toast.LENGTH_SHORT).show();
                break;
        }

    }

    @Override
    protected void initView() {
        textView.setText("this is annotation textView");
        mPresenter.login("", "", "jojo");
    }
}

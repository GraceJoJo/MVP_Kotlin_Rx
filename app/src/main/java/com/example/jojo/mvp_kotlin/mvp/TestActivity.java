package com.example.jojo.mvp_kotlin.mvp;

import android.util.Log;

import com.example.jojo.mvp_kotlin.mvp.base.ACT_Base;
import com.example.jojo.mvp_kotlin.mvp.contract.LoginContract;
import com.example.jojo.mvp_kotlin.mvp.model.LoginModel;
import com.example.jojo.mvp_kotlin.mvp.presenter.LoginPresenter;


/**
 * Created by JoJo on 2018/3/29.
 * wechat:18510829974
 * description:
 */

public class TestActivity extends ACT_Base<LoginPresenter, LoginModel> implements LoginContract.View {

    @Override
    public void loginSuccess() {
        Log.e("TAG", "loginSuccess()");
    }

    @Override
    public void showMsg(String msg) {
        Log.e("TAG", "showMsg---" + msg);
    }

    @Override
    protected void initView() {
        mPresenter.login("", "", "jojo");
    }
}

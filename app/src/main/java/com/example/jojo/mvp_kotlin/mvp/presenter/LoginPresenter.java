package com.example.jojo.mvp_kotlin.mvp.presenter;


import com.example.jojo.mvp_kotlin.mvp.contract.LoginContract;

/**
 * Created by JoJo on 2018/3/29.
 * wechat:18510829974
 * description:
 */

public class LoginPresenter extends LoginContract.Presenter {

    @Override
    public void login(String username, String password, String passcode) {
        if (mModel.login(username, password, passcode)) {
            mView.loginSuccess();
            mView.showMsg("登录成功");
        } else {
            mView.showMsg("登录失败");
        }
    }
}

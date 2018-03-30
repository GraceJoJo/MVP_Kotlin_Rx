package com.example.jojo.mvp_kotlin.mvp.contract;


import com.example.jojo.mvp_kotlin.mvp.base.BaseModel;
import com.example.jojo.mvp_kotlin.mvp.base.BasePresenter;
import com.example.jojo.mvp_kotlin.mvp.base.BaseView;

/**
 * Created by JoJo on 2018/3/29.
 * wechat:18510829974
 * description:
 */

public interface LoginContract {
    interface View extends BaseView {
        void loginSuccess();

        void showMsg(String msg);
    }

    interface Model extends BaseModel {
        boolean login(String username, String password, String passcode);
    }

    abstract class Presenter extends BasePresenter<View, Model> {

        public abstract void login(String username, String password, String passcode);
    }
}

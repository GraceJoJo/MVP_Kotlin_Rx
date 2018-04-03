package com.example.jojo.mvp_kotlin.mvp.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.jojo.mvp_kotlin.ioc.ViewBind;
import com.example.jojo.mvp_kotlin.mvp.uitls.TUtil;


/**
 * Created by JoJo on 2018/3/30.
 * wechat:18510829974
 * description:
 */

public abstract class ACT_Base<P extends BasePresenter, M extends BaseModel> extends AppCompatActivity {
    public P mPresenter;
    public M mModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        ViewBind.inJect(this);
        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);
        if (this instanceof BaseView) {
            mPresenter.setVM(this, mModel);
        }
        initView();
    }

    protected abstract int getContentViewId();

    protected abstract void initView();
}

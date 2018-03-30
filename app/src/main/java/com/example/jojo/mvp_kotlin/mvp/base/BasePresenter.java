package com.example.jojo.mvp_kotlin.mvp.base;

/**
 * Created by JoJo on 2018/3/29.
 * wechat:18510829974
 * description:
 */

public abstract class BasePresenter<V, M> {
    public M mModel;
    public V mView;

    public void setVM(V v, M m) {
        this.mView = v;
        this.mModel = m;
    }
}

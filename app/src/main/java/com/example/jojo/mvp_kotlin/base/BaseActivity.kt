package com.example.jojo.mvp_kotlin.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * Created by JoJo on 2018/4/13.
 * wechat:18510829974
 * description:
 */
abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getContentViewId())
        initView()
    }
    abstract fun getContentViewId(): Int
    abstract fun initView()
}
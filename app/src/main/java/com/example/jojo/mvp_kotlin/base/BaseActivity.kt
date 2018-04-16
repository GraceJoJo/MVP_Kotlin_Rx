package com.example.jojo.mvp_kotlin.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.jojo.mvp_kotlin.ioc.InjectUtils

/**
 * Created by JoJo on 2018/4/13.
 * wechat:18510829974
 * description:
 */
abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        InjectUtils.inJect(this)
        initView()
    }
    abstract fun initView()
}
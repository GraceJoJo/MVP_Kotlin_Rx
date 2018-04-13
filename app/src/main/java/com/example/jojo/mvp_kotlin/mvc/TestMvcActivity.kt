package com.example.jojo.mvp_kotlin.mvc

import android.content.Context
import android.text.TextUtils
import android.widget.Toast
import com.example.jojo.mvp_kotlin.R
import com.example.jojo.mvp_kotlin.base.BaseActivity
import kotlinx.android.synthetic.main.act_test_mvc.*

/**
 * Created by JoJo on 2018/4/13.
 * wechat:18510829974
 * description:
 */
class TestMvcActivity : BaseActivity() {
    var context:Context ?= null
    override fun getContentViewId(): Int {
        return R.layout.act_test_mvc
    }

    override fun initView() {
        context = this
        btn_login.setOnClickListener({
            v ->
            if (!TextUtils.isEmpty(et_phone.text.toString())||!TextUtils.isEmpty(et_pw.text.toString())){
                Toast.makeText(context,"账号不能为空",Toast.LENGTH_SHORT)
                return@setOnClickListener
            }

        })
    }
}
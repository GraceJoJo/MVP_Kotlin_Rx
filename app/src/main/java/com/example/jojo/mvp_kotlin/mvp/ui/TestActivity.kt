package com.example.jojo.mvp_kotlin.mvp.ui

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.jojo.mvp_kotlin.R
import com.example.jojo.mvp_kotlin.TestLambda
import com.example.jojo.mvp_kotlin.ioc.ContentView
import com.example.jojo.mvp_kotlin.ioc.OnClickView
import com.example.jojo.mvp_kotlin.ioc.ViewById
import com.example.jojo.mvp_kotlin.mvp.base.ACT_Base
import com.example.jojo.mvp_kotlin.mvp.contract.LoginContract
import com.example.jojo.mvp_kotlin.mvp.model.LoginModel
import com.example.jojo.mvp_kotlin.mvp.presenter.LoginPresenter


/**
 * Created by JoJo on 2018/3/29.
 * wechat:18510829974
 * description: 测试泛型+MVP
 */
@ContentView(R.layout.act_splash)
class TestActivity : ACT_Base<LoginPresenter, LoginModel>(), LoginContract.View {
    @ViewById(R.id.textView)
    internal var textView: TextView? = null

    override fun loginSuccess() {
        Log.e("TAG", "loginSuccess()")
    }

    override fun showMsg(msg: String) {
        Log.e("TAG", "showMsg---" + msg)
    }

    @OnClickView(R.id.button, R.id.textView)
    fun onClick(view: View) {
        when (view.id) {
            R.id.textView -> Toast.makeText(this@TestActivity, "点击了textView", Toast.LENGTH_SHORT).show()
            R.id.button -> {
                var intent = Intent()
                intent.setClass(this@TestActivity,TestLambda::class.java)
                startActivity(intent)
            }
        }

    }

    override fun initView() {
        textView!!.text = "this is annotation textView"
        textView!!.setOnClickListener { v -> Toast.makeText(applicationContext, "Lambda", Toast.LENGTH_LONG).show() }
        mPresenter.login("", "", "jojo")
    }
}

package com.example.jojo.mvp_kotlin.mvc

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.jojo.mvp_kotlin.R
import com.example.jojo.mvp_kotlin.base.BaseActivity
import com.example.jojo.mvp_kotlin.ioc.ContentView
import com.example.jojo.mvp_kotlin.ioc.OnClickView
import com.example.jojo.mvp_kotlin.mvp.ui.TestActivity
import kotlinx.android.synthetic.main.act_test_mvc.*

/**
 * Created by JoJo on 2018/4/13.
 * wechat:18510829974
 * description:测试MVC
 */
@ContentView(R.layout.act_test_mvc)
class TestMvcActivity : BaseActivity() {
    var context: Context? = null

    override fun initView() {
        context = this
        btn_login.setOnClickListener({ v ->
            if (TextUtils.isEmpty(et_phone.text.toString()) || TextUtils.isEmpty(et_pw.text.toString())) {
                Log.e("TAG", "账号或密码不能为空")
                Toast.makeText(applicationContext, "账号不能为空", Toast.LENGTH_SHORT).show()
            }

        })
    }

    @OnClickView(R.id.tv)
    fun onClick(v: View) {
        Toast.makeText(this, "点我干嘛", Toast.LENGTH_SHORT).show()
        startActivity(Intent()?.apply {
            setClass(this@TestMvcActivity, TestActivity::class.java)
        })
    }
}
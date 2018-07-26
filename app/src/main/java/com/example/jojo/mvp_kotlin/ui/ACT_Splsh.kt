package com.example.jojo.mvp_kotlin.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.example.jojo.mvp_kotlin.R
import com.example.jojo.mvp_kotlin.utils.ModelUtils
import kotlinx.android.synthetic.main.act_splash.*
import java.lang.Exception

/**
 * Created by JoJo on 2018/3/28.
 * wechat:18510829974
 * description:https://www.sojson.com/open/api/weather/json.shtml?city=北京
 * Collection-Android-master
 * KotlinMvp-master
 */
class ACT_Splsh : AppCompatActivity(), ModelUtils.OnResultListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_splash)
        var modelUtil = ModelUtils()
        modelUtil.getWeather("北京", this)
    }

    override fun onFailedListener(msg: Exception) {
        Toast.makeText(this, "接口请求失败", Toast.LENGTH_LONG)
    }

    override fun onSuccessListener(result: String) {
        textView.text = result
        Log.e("TAG","单元测试："+result)
    }
}
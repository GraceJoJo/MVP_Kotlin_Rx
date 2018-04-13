package com.example.jojo.mvp_kotlin.utils

import com.zhy.http.okhttp.OkHttpUtils
import com.zhy.http.okhttp.callback.StringCallback
import okhttp3.Call
import java.lang.Exception

/**
 * Created by JoJo on 2018/3/28.
 * wechat:18510829974
 * description:
 */
class ModelUtils {
    fun getWeather(city: String?, listener: OnResultListener) {
        var url = "https://www.sojson.com/open/api/weather/json.shtml?city=" + city
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(object : StringCallback() {
                    override fun onResponse(response: String?, id: Int) {
                        listener.onSuccessListener(response!!)
                    }

                    override fun onError(call: Call?, e: Exception?, id: Int) {
                        listener.onFailedListener(e!!)
                    }
                })
    }

    fun login(phone: String, pw: String) {
        var url = "https://www.sojson.com/open/api/weather/json.shtml?city=" + "北京"
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(object : StringCallback() {
                    override fun onResponse(response: String?, id: Int) {
                        listener!!.onSuccessListener(response!!)
                    }

                    override fun onError(call: Call?, e: Exception?, id: Int) {
                        listener!!.onFailedListener(e!!)
                    }
                })
    }

    var listener: OnResultListener? = null

    interface OnResultListener {
        fun onSuccessListener(result: String)
        fun onFailedListener(msg: Exception)
    }
}
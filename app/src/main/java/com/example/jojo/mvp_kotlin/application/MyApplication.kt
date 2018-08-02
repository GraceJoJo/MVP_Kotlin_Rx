package com.example.jojo.mvp_kotlin.application

import android.app.Application
import android.content.Context
import com.zhy.http.okhttp.OkHttpUtils
import com.zhy.http.okhttp.log.LoggerInterceptor
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import kotlin.properties.Delegates

/**
 * Created by JoJo on 2018/3/28.
 * wechat:18510829974
 * description:
 */
class MyApplication : Application() {
    companion object {

        private val TAG = "MyApplication"

        var context: Context by Delegates.notNull()

    }
    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        var okhttpClient = OkHttpClient().newBuilder()
                .addInterceptor(LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .build()
        OkHttpUtils.initClient(okhttpClient)
    }


}
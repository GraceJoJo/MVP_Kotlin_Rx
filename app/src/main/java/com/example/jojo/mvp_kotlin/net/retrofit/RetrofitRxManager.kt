package com.example.jojo.mvp_kotlin.net.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit

/**
 * Created by JoJo on 2018/4/20.
 * wechat:18510829974
 * description: Rxjava+Retrofit 联网管理类
 */
//object声明的类，为单例模式
class RetrofitRxManager {
    //超时时间
    val DEFAULT_TIMEOUT = 60L
    private var retrofit: Retrofit? = null

    fun getRetrofit(): Retrofit? {
        if (retrofit==null){
            synchronized(RetrofitRxManager::class.java){
                var okhttpClient  = OkHttpClient.Builder()
//                        .addInterceptor()
            }

        }
        return retrofit
    }
}
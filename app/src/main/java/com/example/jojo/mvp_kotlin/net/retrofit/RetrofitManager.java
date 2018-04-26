package com.example.jojo.mvp_kotlin.net.retrofit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;

/**
 * Created by JoJo on 2018/4/23.
 * wechat:18510829974
 * description:
 */

public class RetrofitManager {
    private Retrofit retrofit;
    public Retrofit getRetrofit(){
        if(retrofit==null) {
            synchronized (RetrofitManager.this){
              OkHttpClient mClient = new OkHttpClient().newBuilder()
                      .addInterceptor(getHeaderIntercepter())
                      .build();
            }
        }
        return retrofit;
    }

    private Interceptor getHeaderIntercepter() {
        return chain ->{
                Request request = chain.request();
                Request requestBuilder = request.newBuilder()
                        .method(request.method(), request.body())
                        .build();
            return chain.proceed(requestBuilder);
        };
    }
}

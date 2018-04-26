package com.example.jojo.mvp_kotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.TextView
import com.example.jojo.mvp_kotlin.net.retrofit.RxSchedulers
import com.zongxueguan.naochanle_android.retrofitrx.RetrofitRxManager
import io.reactivex.observers.DisposableObserver

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tv = findViewById<TextView>(R.id.tv);
        RetrofitRxManager.getRequestService().getWeather("北京")
                .compose(RxSchedulers.io_main())
                .subscribeWith(object : DisposableObserver<Any>() {
                    override fun onNext(t: Any) {
                        tv.text = t.toString()
                        Log.e("TAG", "onNext" + t)
                    }

                    override fun onError(e: Throwable) {
//                        onErrorExpected a string but was BEGIN_OBJECT at line 1 column 2 path $
                        Log.e("TAG", "onError" + e.message)
                    }

                    override fun onComplete() {
                        Log.e("TAG", "onComplete")
                    }
                })
    }
}

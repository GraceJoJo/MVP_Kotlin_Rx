package com.example.jojo.mvp_kotlin.ui

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.jojo.mvp_kotlin.R
import com.example.jojo.mvp_kotlin.net.retrofit.RxSchedulers
import com.zongxueguan.naochanle_android.retrofitrx.RetrofitRxManager
import io.reactivex.observers.DisposableObserver
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        multipleStatusView.setOnRetryClickListener({
            requestData()
        })

        requestData()

    }

    /**
     * //延迟3s请求数据，模拟loading
     */
    private fun requestData() {
        multipleStatusView.showLoading()
        Handler().postDelayed({
            RetrofitRxManager.getRequestService(this).getWeather("北京")
                    .compose(RxSchedulers.io_main())
                    .subscribeWith(object : DisposableObserver<Any>() {
                        override fun onNext(t: Any) {
                            tv.text = t.toString()
                            Log.e("TAG", "onNext" + t)
                            multipleStatusView.showContent()
                        }

                        override fun onError(e: Throwable) {
                            //                        onErrorExpected a string but was BEGIN_OBJECT at line 1 column 2 path $
                            Log.e("TAG", "onError" + e.message)
                            multipleStatusView.showError()
                        }

                        override fun onComplete() {
                            Log.e("TAG", "onComplete")
                        }
                    })
        }, 3000)
    }
}

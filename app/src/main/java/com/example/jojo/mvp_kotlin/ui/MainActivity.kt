package com.example.jojo.mvp_kotlin.ui

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.jojo.mvp_kotlin.R
import com.example.jojo.mvp_kotlin.bean.WeatherEntity
import com.example.jojo.mvp_kotlin.net.retrofit.RxSchedulers
import com.zongxueguan.naochanle_android.retrofitrx.RetrofitRxManager
import io.reactivex.observers.DisposableObserver
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_view.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //点击重试
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
                    .subscribeWith(object : DisposableObserver<WeatherEntity>() {
                        override fun onNext(bean: WeatherEntity) {
                            tv_city.text = "城市：" + bean.city
                            tv_quality.text = "空气质量：" + bean.data.quality
                            tv_wendu.text = "今日气温：" + bean.data.wendu
                            tv_pm.text = "PM值：" + bean.data.pm25 + "至" + bean.data.pm10
                            tv_notice.text = "温馨提醒：" + bean.data.ganmao
                            Log.e("TAG", "onNext" + bean)
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
        }, 1500)
    }
}

package com.example.jojo.mvp_kotlin.ui;

import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.jojo.mvp_kotlin.R;
import com.example.jojo.mvp_kotlin.base.BaseActivity;
import com.example.jojo.mvp_kotlin.ioc.ContentView;
import com.example.jojo.mvp_kotlin.ioc.ViewById;
import com.example.jojo.mvp_kotlin.net.retrofit.RxSchedulers;
import com.zongxueguan.naochanle_android.retrofitrx.RetrofitRxManager;

import io.reactivex.observers.DisposableObserver;

/**
 * Created by JoJo on 2018/4/19.
 * wechat:18510829974
 * description:
 */
@ContentView(R.layout.act_splash)
public class TestLambda extends BaseActivity {
    @ViewById(R.id.button)
    Button button;
    @ViewById(R.id.textView)
    TextView textView;

    @Override
    public void initView() {
        button.setOnClickListener(v -> {
                    RetrofitRxManager.INSTANCE.getRequestService(this).getWeather("北京")
                            .compose(RxSchedulers.io_main())
                            .subscribeWith(new DisposableObserver<Object>() {
                                @Override
                                public void onNext(Object result) {
                                    textView.setText(result.toString());
                                    Log.e("TAG", "result=" + result.toString());
                                }

                                @Override
                                public void onError(Throwable e) {

                                    Log.e("TAG", "onError=" + e.getMessage());
                                }

                                @Override
                                public void onComplete() {
                                    Log.e("TAG", "onComplete");
                                }
                            });
                }
        );
    }
}

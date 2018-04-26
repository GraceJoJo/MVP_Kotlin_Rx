package com.example.jojo.mvp_kotlin.mvc;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by JoJo on 2018/4/25.
 * wechat:18510829974
 * description:
 */

public class LoginModelImpl implements ILoginModel2 {
    @Override
    public void login(String phone, String pw) {
        OkHttpUtils
                .get()
                .url("https://www.sojson.com/open/api/weather/json.shtml?city=北京")
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(Response response, int id) throws Exception {
                        return null;
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(Object response, int id) {

                    }
                });
    }
}

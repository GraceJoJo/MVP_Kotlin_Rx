package com.example.jojo.mvp_kotlin.mvvm;

import android.databinding.DataBindingUtil;

import com.example.jojo.mvp_kotlin.R;
import com.example.jojo.mvp_kotlin.base.BaseActivity;
import com.example.jojo.mvp_kotlin.databinding.ActTestDatabindingBinding;
import com.example.jojo.mvp_kotlin.mvvm.model.User;

/**
 * Created by JoJo on 2018/4/16.
 * wechat:18510829974
 * description:
 */

public class TestDataBinding extends BaseActivity {
    @Override
    public void initView() {
        ActTestDatabindingBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.act_test_databinding);
        User user = new User();
        user.setFirstName("这是Java代码编写测试DataBinding");
        user.setLastName("呵呵呵");
        user.setPhone("1938207320384");
        viewDataBinding.setUser(user);
    }
}

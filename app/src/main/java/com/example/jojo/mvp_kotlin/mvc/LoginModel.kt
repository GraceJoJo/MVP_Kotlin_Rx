package com.example.jojo.mvp_kotlin.mvc

import com.example.jojo.mvp_kotlin.utils.ModelUtils

/**
 * Created by JoJo on 2018/4/13.
 * wechat:18510829974
 * description:
 */
class LoginModel : ILoginModel{
    override fun login(phone: String, pw: String) {
        ModelUtils().login(phone,pw)
    }
}
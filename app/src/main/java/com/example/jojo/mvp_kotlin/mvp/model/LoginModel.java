package com.example.jojo.mvp_kotlin.mvp.model;


import com.example.jojo.mvp_kotlin.mvp.contract.LoginContract;

/**
 * Created by JoJo on 2018/3/29.
 * wechat:18510829974
 * description:
 */

public class LoginModel implements LoginContract.Model {
    @Override
    public boolean login(String username, String password, String passcode) {
        if (passcode.equals("jojo")) {
            return true;
        }
        return false;
    }
}

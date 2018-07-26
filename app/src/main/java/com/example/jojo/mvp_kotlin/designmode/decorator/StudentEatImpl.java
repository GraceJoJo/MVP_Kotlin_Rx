package com.example.jojo.mvp_kotlin.designmode.decorator;

import android.util.Log;

/**
 * Created by JoJo on 2018/7/25.
 * wechat:18510829974
 * description:
 */

public class StudentEatImpl extends PersonEat {
    @Override
    public void eat() {
        super.eat();
        Log.e("TAG", "学生吃饭");
    }
}

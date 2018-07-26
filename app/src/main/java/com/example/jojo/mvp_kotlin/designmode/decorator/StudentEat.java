package com.example.jojo.mvp_kotlin.designmode.decorator;

import android.util.Log;

/**
 * Created by JoJo on 2018/7/25.
 * wechat:18510829974
 * description:
 */

public class StudentEat implements Eat{

    private  PersonEat presonEat;

    public StudentEat(PersonEat personEat) {
        this.presonEat = personEat;
    }

    @Override
    public void eat() {
        presonEat.eat();
        Log.e("TAG", "学生去食堂吃饭");
    }
}

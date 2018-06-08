package com.example.jojo.mvp_kotlin.designmode.builder;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JoJo on 2018/6/7.
 * wechat:18510829974
 * description:步骤4： 定义具体产品类（Product）：电脑
 */

public class Computer {
    //电脑组件的集合
    private List<String> parts = new ArrayList<String>();

    //用于将组件组装到电脑里
    public void add(String part) {
        parts.add(part);
    }

    /**
     * 展示组装完毕的电脑
     */
    public void showComputer() {
        for (int i = 0; i < parts.size(); i++) {
            Log.e("TAG", "组件" + parts.get(i) + "装好了");
        }
        Log.e("TAG", "电脑组装完成，请验收");
    }
}

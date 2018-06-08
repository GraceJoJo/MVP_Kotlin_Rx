package com.example.jojo.mvp_kotlin.designmode.adapter;

import android.util.Log;

/**
 * Created by JoJo on 2018/6/7.
 * wechat:18510829974
 * description:步骤2： 创建源类（Adaptee）
 */

//步骤2： 创建源类（原有的插头 220v）
public class Adaptee {
    //原有插头只能输出220V
    public void output_220V(){
        Log.e("TAG", "插头-输出220V");
    }
}

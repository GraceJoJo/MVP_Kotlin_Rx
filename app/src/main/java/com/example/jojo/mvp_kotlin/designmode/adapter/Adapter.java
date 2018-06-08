package com.example.jojo.mvp_kotlin.designmode.adapter;

import android.util.Log;

/**
 * Created by JoJo on 2018/6/7.
 * wechat:18510829974
 * description:步骤3： 创建适配器类（Adapter）
 */

// 适配器Adapter继承自Adaptee，同时又实现了目标(Target)接口。
public class Adapter extends Adaptee implements Target {
    private Adaptee adaptee;

    public Adapter(Adaptee adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void convert_110V() {
        //类的适配器模式
//        this.output_220V();

        //对象适配器——增加构造器  Adapter与Adaptee是委派关系，这决定了适配器模式是对象的。
        adaptee.output_220V();
        Log.e("TAG", "转换头——将220v转换成110V");
    }
    //期待的插头要求调用Convert_110v()，但原有插头没有
    //因此适配器补充上这个方法名
    //但实际上Convert_110v()只是调用原有插头的Output_220v()方法的内容
    //所以适配器只是将Output_220v()作了一层封装，封装成Target可以调用的Convert_110v()而已
}

package com.example.jojo.mvp_kotlin.designmode.adapter;

/**
 * Created by JoJo on 2018/6/8.
 * wechat:18510829974
 * description:步骤4：定义具体使用目标类，
 * 并通过Adapter类调用所需要的方法从而实现目标（不需要通过原有插头）
 */

public class AdapterPattern {
    public AdapterPattern(){
        /**
         * 1、类的适配器模式
         */
//        Adapter adapter = new Adapter();
//        adapter.convert_110V();
        /**
         * 2、对象的适配器模式
         */
        Adapter adapter = new Adapter(new Adaptee());
        adapter.convert_110V();
    }
}

package com.example.jojo.mvp_kotlin.designmode.builder;

/**
 * Created by JoJo on 2018/6/7.
 * wechat:18510829974
 * description:步骤2： 电脑城老板委派任务给装机人员（Director）
 */

public class Director {
    public void constrct(Builder builder){
        builder.buildCPU();
        builder.buildMainBoard();
        builder.buildHD();
    }
}

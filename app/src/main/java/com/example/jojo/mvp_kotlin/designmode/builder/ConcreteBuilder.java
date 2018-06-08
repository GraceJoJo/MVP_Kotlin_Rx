package com.example.jojo.mvp_kotlin.designmode.builder;

/**
 * Created by JoJo on 2018/6/7.
 * wechat:18510829974
 * description:步骤3： 创建具体的建造者（ConcreteBuilder）:装机人员
 */

public class ConcreteBuilder extends Builder {
    //创建产品实例
    Computer computer = new Computer();
    @Override
    public void buildCPU() {
        computer.add("CPU");
    }

    @Override
    public void buildMainBoard() {
        computer.add("主板");
    }

    @Override
    public void buildHD() {
        computer.add("硬盘");
    }

    @Override
    public Computer getComputer() {

        return computer;
    }
}

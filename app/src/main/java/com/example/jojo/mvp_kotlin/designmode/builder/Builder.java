package com.example.jojo.mvp_kotlin.designmode.builder;

/**
 * Created by JoJo on 2018/6/7.
 * wechat:18510829974
 * description: 步骤1： 定义组装的过程（Builder）：组装电脑的过程
 */

/**
 * 一、定义：建造者模式：将一个复杂对象的构建与它的表示分离，使得同样的构建过程可以创建不同的表示
 *
 * 二、作用：
 * 1.用户只需要给出指定复杂对象的类型和内容；
 * 2.建造者模式负责按顺序创建复杂对象（把内部的建造过程和细节隐藏起来)
 *
 * 三、 解决的问题
 * 1.方便用户创建复杂的对象（不需要知道实现过程）
 * 2.代码复用性 & 封装性（将对象构建过程和细节进行封装 & 复用）
 * 四、 模式讲解：
 1. 指挥者（Director）直接和客户（Client）进行需求沟通；
 2. 沟通后指挥者将客户创建产品的需求划分为各个部件的建造请求（Builder）；
 3. 将各个部件的建造请求委派到具体的建造者（ConcreteBuilder）；
 4. 各个具体建造者负责进行产品部件的构建；
 5. 最终构建成具体产品（Product）。
 */
public abstract class Builder {
    /**
     *   声明为抽象方法，具体由子类实现
     */
    //第一步：装CPU
    public abstract void buildCPU();
    //第二步：装主板
    public abstract void buildMainBoard();
    //第三步：装硬盘
    public abstract void buildHD();

    //获取组装好的电脑
    public abstract Computer getComputer();
}

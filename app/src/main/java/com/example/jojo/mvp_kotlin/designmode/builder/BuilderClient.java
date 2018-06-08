package com.example.jojo.mvp_kotlin.designmode.builder;

/**
 * Created by JoJo on 2018/6/7.
 * wechat:18510829974
 * description:步骤5： 客户端调用-小成到电脑城找老板买电脑
 */

public class BuilderClient {
    public BuilderClient(){
        //逛了很久终于发现一家合适的电脑店
        //找到该店的老板和装机人员
        Director director = new Director();
        Builder builder = new ConcreteBuilder();
        //沟通需求后，老板叫装机人员去装电脑
        director.constrct(builder);

        //装完后，组装人员搬来组装好的电脑
        Computer computer = builder.getComputer();

        //组装人员展示组装完成电脑
        computer.showComputer();
    }
}

/**
 * 建造者模式的： 优点
 1、易于解耦
    将产品本身与产品创建过程进行解耦，可以使用相同的创建过程来得到不同的产品。也就说细节依赖抽象。
 2、易于精确控制对象的创建
    复杂产品的创建步骤分解在不同的方法中，使得创建过程更加清晰
 3、易于拓展
   增加新的具体建造者无需修改原有类库的代码，易于拓展，符合“开闭原则“。
   每一个具体建造者都相对独立，而与其他的具体建造者无关，因此可以很方便地替换具体建造者或增加新的具体建造者，用户使用不同的具体建造者即可得到不同的产品对象。

   缺点：
 1、建造者模式所创建的产品一般具有较多的共同点，其组成部分相似；如果产品之间的差异性很大，则不适合使用建造者模式，因此其使用范围受到一定的限制。
 2、如果产品的内部变化复杂，可能会导致需要定义很多具体建造者类来实现这种变化，导致系统变得很庞大。
 */
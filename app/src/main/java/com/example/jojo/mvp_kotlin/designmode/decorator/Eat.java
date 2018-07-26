package com.example.jojo.mvp_kotlin.designmode.decorator;

/**
 * Created by JoJo on 2018/7/25.
 * wechat:18510829974
 * description:Decorator Pattern，又叫装饰者模式。
 * 装饰模式是在不必改变原类文件和使用继承的情况下，动态地扩展一个对象的功能。它是通过创建一个包装对象，也就是装饰来包裹真实的对象。
 */

public interface Eat {
    //吃饭（行为）
    void eat();
}

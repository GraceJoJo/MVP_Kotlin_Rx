package com.example.jojo.mvp_kotlin.designmode.decorator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by JoJo on 2018/7/25.
 * wechat:18510829974
 * description:装饰模式是在不必改变原类文件和使用继承的情况下，动态地扩展一个对象的功能。它是通过创建一个包装对象，也就是装饰来包裹真实的对象。
 * 动态地将责任附加到对象上。若要扩展功能，装饰者提供了比继承更加有弹性的替代方案。
 */

public class TestDecoratorActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //1.一般情况，通过new对象，调用方法，通过继承的方式拓展
        StudentEatImpl studentEat1 = new StudentEatImpl();
        studentEat1.eat();
        //2.装饰设计模式般情况都是把类对象作为构造参数传递，通过创建一个包装对象
        PersonEat personEat = new PersonEat();
        //学生吃饭（都是属于人吃饭，只是在人吃饭的基础上有拓展）
        StudentEat studentEat = new StudentEat(personEat);
        studentEat.eat();
    }
}

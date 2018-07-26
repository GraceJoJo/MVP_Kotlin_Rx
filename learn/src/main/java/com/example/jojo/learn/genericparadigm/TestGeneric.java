package com.example.jojo.learn.genericparadigm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.jojo.learn.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by JoJo on 2018/7/25.
 * wechat:18510829974
 * description:[集合中泛型正确的擦除](https://blog.csdn.net/lyandyhk/article/details/51174616)
 */

public class TestGeneric extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * 《1》数组：编译器对代码信息了解的足够多，一些地方允许编译通过
         */
        Fruit[] fruits = new Apple[10];
        fruits[0] = new Apple();
        /**
         *以下运行不通过
         *报错：Caused by: java.lang.ArrayStoreException: com.example.jojo.learn.genericparadigm.Orange cannot be stored in an array of type com.example.jojo.learn.genericparadigm.Apple[]
         *你无法将一个Orange放到一个Apple数组中，你也无法将一个父类对象放到一个子类对象数组中
         */
//        fruits[1] = new Orange();
//        fruits[2] = new Fruit();


        /**
         *《2》集合：编译器对代码的了解不够多，相对于数组来说有些地方编译不允许通过
         */
        //编译不通过：不能把一个涉及Apple的泛型赋给一个涉及Fruit的泛型。在泛型确定之后，不同的泛型的集合之间是不能转换的，即使一个泛型能上转型成另一个泛型
//        List<Fruit> fruitList = new ArrayList<Apple>();

        /**
         * 《3》<? extends Fruit>  ：的作用就是为了让泛型在符合继承的条件下正确准确的擦除
         */
        //该句允许：在擦除时将泛型确定为了Apple，编译通过也是因为Apple符合 extends Fruit1的规则
//        可是，你无法向里面add对象，编译期无法在你加东西地时候确定你加的到底是什么类型，他无法准确擦除泛型，所以编译失败
//        但是有一点，你要从里面拿东西，那是可以的，因为无论如何，它至少能保证拿出来的是Fruit类型
        List<? extends Fruit> fruitsList = new ArrayList<Apple>();
    }

    public class GennericRead {
        //(1)方法为泛型
        <T> T read(List<T> list) {
            return list.get(0);
        }

        List<Apple> apples = Arrays.asList(new Apple());
        List<Fruit> fruits = Arrays.asList(new Fruit());

        void fun1() {
            Apple a = read(apples);
            Fruit f = read(fruits);
            //方法级别上的泛型，接收时确定泛型为Apple1，返回时也返回Apple1，只是f作为Fruit1类型
            //接受，所以是成立的
            f = read(apples);
        }




        //(2)类中带泛型
        class Reader1<T> {
            T read(List<T> list) {
                return list.get(0);
            }
        }

        void fun2() {
            Reader1<Fruit> fruitReader = new Reader1<>();
            Fruit fruit = fruitReader.read(fruits);
            //这句编译不通过，因为 Reader<Fruit> fruitReader = new Reader<>();已经确定了泛型T是Fruit。这里使用的时候，又传入了apple。List<Apple>不能转换为List<Fruit>
//            Fruit fruit1 = fruitReader.read(apples);
        }




        //(3)泛型正确的擦除
        class Reader2<T> {
            T read(List<? extends T> list) {
                return list.get(0);
            }
        }

        void fun3() {
            Reader2<Fruit> reader2 = new Reader2<>();
            reader2.read(fruits);
            //这句编译通过是因为虽然在创建对象时确定了泛型T为Fruit，但是在方法中参数list使用的泛型
            //并不是T，而是<? extends T>,又因为传入的是apples，对应的泛型为Apple，且<Apple extends Fruit>
            //符合规范，最后返回值自动向上转型成T，也就是Fruit输出出去
            reader2.read(apples);
        }

        // 类中<? extends T>的参数不会因为类中的泛型T被确定而确定，只有使用的时候才会确定，在执行完最后
//        该方法最后一句时，Reader2类被擦除泛型成
        class Reader3 {
            Fruit read(List<Apple> list) {
                return list.get(0);
            }
        }
    }
}

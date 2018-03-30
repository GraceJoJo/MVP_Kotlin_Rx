package com.example.jojo.mvp_kotlin;

/**
 * Created by JoJo on 2018/3/29.
 * wechat:18510829974
 * description: java单元测试用例
 */

public class JavaRunTest {
    @org.junit.Test
    public void print(){
        //java测试类，只能用控制台输出
        System.out.print("Hello World!!!");
//
        // Log.e("TAG", "wwwww哈哈啊www"); 报错：这个实际是因为你在java的Unit test中引用了Android的代码，即android.util.log.Log。所以对于测试Android代码，需要在androidTest中
        /**
         * java.lang.RuntimeException: Method e in android.util.Log not mocked. See http://g.co/androidstudio/not-mocked for details.

         at android.util.Log.e(Log.java).......
         */
    }
}

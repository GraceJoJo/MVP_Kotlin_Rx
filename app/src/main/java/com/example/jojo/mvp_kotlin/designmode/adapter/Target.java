package com.example.jojo.mvp_kotlin.designmode.adapter;

/**
 * Created by JoJo on 2018/6/7.
 * wechat:18510829974
 * description:步骤1： 创建Target接口；
 */
/**
  实例讲解——适配器模式中的类的适配器模式
 接下来我用一个实例来对类的适配器模式进行更深一步的介绍。
 a. 实例概况

 背景：小成买了一个进口的电视机
 冲突：进口电视机要求电压（110V）与国内插头标准输出电压（220V）不兼容
 解决方案：设置一个适配器将插头输出的220V转变成110V
 */
//步骤1： 创建Target接口（期待得到的插头）：能输出110V（将220V转换成110V）
public interface Target {
    /**
     * 将220V转换输出110V（原有插头（Adaptee）没有的）
     */
    void convert_110V();

}
/**

 作者：Carson_Ho
 链接：https://www.jianshu.com/p/9d0575311214
 來源：简书
 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 */

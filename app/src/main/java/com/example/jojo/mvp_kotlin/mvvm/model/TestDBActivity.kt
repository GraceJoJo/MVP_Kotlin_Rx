package com.example.jojo.mvp_kotlin.mvvm.model

import android.databinding.DataBindingUtil

import com.example.jojo.mvp_kotlin.R
import com.example.jojo.mvp_kotlin.base.BaseActivity
import com.example.jojo.mvp_kotlin.databinding.ActTestDatabindingBinding

/**
 * Created by JoJo on 2018/4/16.
 * wechat:18510829974
 * description: 测试DataBinding的基本使用
 * 编译时会报错：
 * Error:(7, 36) Unresolved reference: databinding
 * Error:(17, 62) Unresolved reference: ActTestDatabindingBinding
 * Error:Execution failed for task ':app:compileDebugKotlin'.
 *> Compilation error. See log for more details
 * 解决：是Kotlin和DataBinding冲突  Unresolved reference: databinding 参考：https://blog.csdn.net/victor_fang/article/details/54668326
 */

class TestDBActivity : BaseActivity() {
    override fun initView() {
        val viewDataBinding = DataBindingUtil.setContentView<ActTestDatabindingBinding>(this, R.layout.act_test_databinding)
        //        // 初始化数据
        val user = User()
        user.firstName = "Victor-这是Kotlin编写测试DataBinding"
        user.lastName = "JOJO"
        user.phone = "13333333333"
        user.isShowPhone = true
        // 绑定数据
        viewDataBinding.user = user
    }
}

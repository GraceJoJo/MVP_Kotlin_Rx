package com.example.jojo.mvp_kotlin.mvvm.model

import android.databinding.DataBindingUtil
import android.view.View
import android.widget.Toast
import com.example.jojo.mvp_kotlin.R
import com.example.jojo.mvp_kotlin.base.BaseActivity
import com.example.jojo.mvp_kotlin.databinding.ActTestDatabindingBinding
import com.example.jojo.mvp_kotlin.ioc.OnClickView

/**
 * Created by JoJo on 2018/4/16.
 * wechat:18510829974
 * description: Kotlin编写测试DataBinding
 * 编译时会报错：
 * Error:(7, 36) Unresolved reference: databinding
 * Error:(17, 62) Unresolved reference: ActTestDatabindingBinding
 * Error:Execution failed for task ':app:compileDebugKotlin'.
 *> Compilation error. See log for more details
 * 解决：是Kotlin和DataBinding冲突  Unresolved reference: databinding 参考：https://blog.csdn.net/ethanco/article/details/54693140
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

        //数据模型变了，view就随之改变
        user.lastName = "这是lastName的修改内容"
    }

    //???不生效，猜测原因应该是找不到R.id.tv_last_name这个id了
    @OnClickView(R.id.tv_last_name)
    fun onClick(view: View) {
        when (view.id) {
            R.id.tv_last_name -> Toast.makeText(this@TestDBActivity, "LastName-注解-别点我!!!", Toast.LENGTH_SHORT).show()
        }
        Toast.makeText(this@TestDBActivity, "FirstName-xml-onClick别点我!!!", Toast.LENGTH_SHORT).show()
    }
}

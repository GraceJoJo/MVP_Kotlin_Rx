package com.example.jojo.mvp_kotlin.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * Created by JoJo on 2018/7/25.
 * wechat:18510829974
 * description:
 */
abstract class BaseCompatActivity : AppCompatActivity() {
    //屏幕宽度
    protected var mScreenWidth = 0
    //屏幕高度
    protected var mScreenHeight = 0
    //屏幕密度
    protected var mScreenDensity = 0.0f
    protected var mContext = null

    //Activity切换动画
    enum class TransitionMode{
        LEFT,RIGHT,TOP,BOTTOM,SCALE,FADE,ZOOM
    }

    abstract val overridePendingTransitionMode: TransitionMode

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val extras = intent.extras
        getBundleExtras(extras!!)
        if (toggleOverPendingTransition()){
            when(overridePendingTransitionMode){

            }
        }
    }

    abstract fun toggleOverPendingTransition(): Boolean

    abstract fun getBundleExtras(extras: Bundle)
}
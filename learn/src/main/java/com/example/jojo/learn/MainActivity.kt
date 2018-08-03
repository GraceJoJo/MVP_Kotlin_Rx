package com.example.jojo.learn

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //        mytextview = findViewById(R.id.mytextview);
        //当前实际的步数
        var mCurrentStep: Float = 9709f
        sportstepview.startCountStep(mCurrentStep)

        var datas = listOf<Int>(40, 80, 90, 50, 187)
        var xList = listOf<String>("1月份", "2月份", "3月份", "4月份", "5月份")

        //根据数据的最大值生成上下对应的Y轴坐标范围
        var ylist = mutableListOf<Int>()
        var maxYAxis: Int? = Collections.max(datas)
        if (maxYAxis!! % 2 == 0) {
            maxYAxis = maxYAxis + 2
        } else {
            maxYAxis = maxYAxis + 1
        }
        var keduSpace = (maxYAxis / datas.size) + 1
        for (i in 0..datas.size) {
            ylist.add(0 + keduSpace * i)
        }
        barchartview.updateValueData(datas, xList, ylist)

        //ViewCompat.animate(barchartview).scaleX(0.5f).scaleY(0.5f).setDuration(0).start()
    }

    //    public void textLayoutLeft(View v) {
    //        mytextview.reLayoutText(0);
    //    }
    //    public void textLayoutCenter(View v) {
    //        mytextview.reLayoutText(1);
    //    }
}

package com.example.jojo.mvp_kotlin;

import android.content.Context;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

/**
 * 单元测试参考网址：https://www.cnblogs.com/soaringEveryday/p/5461970.html
 * Created by JoJo on 2018/3/29.
 * wechat:18510829974
 * description:测试单个Activity或者多个Activities的测试用例方法基类，比如ActivityInstrumentationTestCase2
 */

public class TestActivity extends ActivityInstrumentationTestCase2<MainActivity> {

    private Context ctx;

    public TestActivity() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        ctx = getActivity().getApplicationContext();
        Log.e("TAG", "TestActivity");
        testStart();
    }

    public void testStart() {
        Intent intent = new Intent(ctx, ACT_Splsh.class);
        ctx.startActivity(intent);
    }
}
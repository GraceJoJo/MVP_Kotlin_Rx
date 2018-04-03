package com.example.jojo.mvp_kotlin;

import android.util.Log;

import com.example.jojo.mvp_kotlin.unit_test.Caculator;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by JoJo on 2018/3/29.
 * wechat:18510829974
 * description:
 */
public class CaculatorTest {

    private Caculator mCaculator;

    @Before
    public void setUp() throws Exception {
        mCaculator = new Caculator();
    }

    @org.junit.Test
    public void sum() throws Exception {
        //可以在logcat中查看
        Log.e("TAG","CaculatorTest---Hello　Android");
        assertEquals(mCaculator.sum(2, 4), 6.0);
    }


    @Test
    public void substract() throws Exception {
    }

    @Test
    public void divide() throws Exception {
    }

    @Test
    public void multiply() throws Exception {
    }

}
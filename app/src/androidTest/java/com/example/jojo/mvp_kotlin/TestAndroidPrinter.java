package com.example.jojo.mvp_kotlin;

import android.test.InstrumentationTestCase;
import android.util.Log;

/**
 * Created by JoJo on 2018/3/29.
 * wechat:18510829974
 * description:
 * 1.在androidTest下新建一个java类，并且继承自InstrumentationTestCase
 * 2.编写一个public void的方法，但是必须要是方法名以test打头，比如testPublishSubject，并不需要@Test注解
 */

public class TestAndroidPrinter extends InstrumentationTestCase {
    private static final String TAG = "TAG";

    public void testPublishSubject() {
        Log.d(TAG, "Hello TestAndroidPrinter completed");
    }
}

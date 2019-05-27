package com.example.jojo.learn.room

import android.arch.persistence.room.Room
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View

import com.example.jojo.learn.R

/**
 * author : JOJO
 * e-mail : 18510829974@163.com
 * date   : 2019/1/4 5:39 PM
 * desc   : 数据库 Room 测试
 */
class TestRoomActivity : AppCompatActivity() {
    private var mUserDao: UserDao? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test_room_activity)

        //得到AppDatabase 对象
        val db = Room.databaseBuilder(applicationContext,
                AppDatabase::class.java, "roomDemo-database")
                //下面注释表示允许主线程进行数据库操作，但是不推荐这样做。
                //他可能造成主线程lock以及anr
                //所以我们的操作都是在新线程完成的
                // .allowMainThreadQueries()
                //                .addMigrations(AppDatabase.MIGRATION_1_2)
                .build()
        //得到userDao对象
        mUserDao = db.userDao()
    }

    fun insert(view: View) {
        //        ToastUtils.Companion.makeShortToast("插入");
        //        //防止UI线程阻塞以及ANR,所有的数据库操作，推荐开启新的线程访问。
        //        new Thread(new Runnable() {
        //            @Override
        //            public void run() {
        //                User user = new User(1, "t" + System.currentTimeMillis() / 1000, "allen", 18);
        //                mUserDao.insert(user);
        //                ToastUtils.Companion.makeShortToast("插入成功");
        //            }
        //        });


    }

    fun queryItem(view: View) {
        //        User user = mUserDao.findByUid(1);
        //        ToastUtils.Companion.makeShortToast(user.getLastName());

    }

    fun queryAll(view: View) {

    }

    fun deleteItem(view: View) {

    }

    fun deleteAll(view: View) {

    }
}

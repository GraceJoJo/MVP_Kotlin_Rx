package com.example.jojo.learn.room

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update

/**
 * author : JOJO
 * e-mail : 18510829974@163.com
 * date   : 2019/1/4 5:55 PM
 * desc   :
 */
//注解配置sql语句
@Dao
interface UserDao {
    //所有的CURD根据primary key进行匹配
    //------------------------query------------------------
    //简单sql语句，查询user表所有的column
    @get:Query("SELECT * FROM user")
    val all: List<User>

    //根据条件查询，方法参数和注解的sql语句参数一一对应
    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<User>

    //同上
    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " + "last_name LIKE :last LIMIT 1")
    fun findByName(first: String, last: String): User

    //同上
    @Query("SELECT * FROM user WHERE uid = :uid")
    fun findByUid(uid: Int): User

    //-----------------------insert----------------------

    // OnConflictStrategy.REPLACE表示如果已经有数据，那么就覆盖掉
    //数据的判断通过主键进行匹配，也就是uid，非整个user对象
    //返回Long数据表示，插入条目的主键值（uid）
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User): Long?

    //返回List<Long>数据表示被插入数据的主键uid列表
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg users: User): List<Long>

    //返回List<Long>数据表示被插入数据的主键uid列表
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<User>): List<Long>

    //---------------------update------------------------
    //更新已有数据，根据主键（uid）匹配，而非整个user对象
    //返回类型int代表更新的条目数目，而非主键uid的值。
    //表示更新了多少条目
    @Update
    fun update(user: User): Int

    //同上
    @Update
    fun updateAll(vararg user: User): Int

    //同上
    @Update
    fun updateAll(user: List<User>): Int

    //-------------------delete-------------------
    //删除user数据，数据的匹配通过主键uid实现。
    //返回int数据表示删除了多少条。非主键uid值。
    @Delete
    fun delete(user: User): Int

    //同上
    @Delete
    fun deleteAll(users: List<User>): Int

    //同上
    @Delete
    fun deleteAll(vararg users: User): Int
}

//作者：Allen___
//        链接：https://www.jianshu.com/p/72c8efc3ad87
//        來源：简书
//        简书著作权归作者所有，任何形式的转载都请联系作者获得授权并注明出处。

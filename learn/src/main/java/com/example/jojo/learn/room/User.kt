package com.example.jojo.learn.room

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey

/**
 * author : JOJO
 * e-mail : 18510829974@163.com
 * date   : 2019/1/4 5:50 PM
 * desc   :
 */
//entity声明定义，并且指定了映射数据表明
@Entity(tableName = "user")
class User {
    //设置主键，并且定义自增增
    //Setter、Getter方法是需要添加的，为了支持room工作
    @PrimaryKey(autoGenerate = true)
    //字段映射具体的数据表字段名
    @ColumnInfo(name = "uid")
    var uid: Int = 0
    //字段映射具体的数据表字段名
    @ColumnInfo(name = "first_name")
    var firstName: String? = null
    //字段映射具体的数据表字段名
    @ColumnInfo(name = "last_name")
    var lastName: String? = null
    @ColumnInfo(name = "age")
    var age: Int = 0

    @Ignore
    constructor(uid: Int, firstName: String, lastName: String, age: Int) {
        this.uid = uid
        this.firstName = firstName
        this.lastName = lastName
        this.age = age
    }

    //必须指定一个构造方法，room框架需要。并且只能指定一个
    //，如果有其他构造方法，则其他的构造方法必须添加@Ignore注解
    constructor() {}

    //其他构造方法要添加@Ignore注解
    @Ignore
    constructor(uid: Int) {
        this.uid = uid
    }
}
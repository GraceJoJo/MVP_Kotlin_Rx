package com.example.jojo.mvp_kotlin.mvvm.model

/**
 * Created by JoJo on 2018/4/16.
 * wechat:18510829974
 * description:
 */

data class User(var firstName: String, var lastName: String?, var phone: String, var isShowPhone: Boolean) {
    constructor() : this("", "", "", false) {
    }
//    /**
//     * 获取全名
//     * @param firstName
//     * @param lastName
//     * @return
//     */
//    fun getFullName(firstName: String, lastName: String): String {
//        val sb = StringBuilder()
//        sb.append("全名：")
//        if (!TextUtils.isEmpty(firstName)) {
//            sb.append(firstName)
//        }
//        if (!TextUtils.isEmpty(lastName)) {
//            sb.append("." + lastName)
//        }
//        return sb.toString()
//    }
}

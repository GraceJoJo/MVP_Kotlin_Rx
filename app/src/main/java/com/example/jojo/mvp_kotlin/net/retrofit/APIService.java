package com.example.jojo.mvp_kotlin.net.retrofit;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by JoJo on 2018/4/24.
 * wechat:18510829974
 * description:
 */

public interface APIService {
    //获取北京的天气信息
//    "https://www.sojson.com/open/api/weather/json.shtml?city=" + "北京"
    @GET("weather/json.shtml")
    Observable<Object> getWeather(@Query("city")String city);
    /**
     * Get请求情形一: https://api-t.zmbai.com/v1/user/111111111
     */
    @GET("user/{user_id}")
    Observable<String>  getUser(@Path("user_id")String user_id );

    /**
     * Get请求情形一: https://api-t.zmbai.com/v1/sysmsg?user_id=1645
     */
    @GET("sysmsg")
    Observable<String> getMessage(@Query("user_id")String user_id);

    /**
     * Get请求情形二: https://api-t.zmbai.com/v1/selfdesbyid/2
     */
    @GET("selfdesbyid/{teacher_id}")
    Observable<String> getTeacherSelfDes(@Path("teacher_id") String teacher_id);

    /**
     * Get请求情形三：多个参数
     */
    @GET("selfdesbyid/{teacher_id}")
    Observable<Object> getTeacherSelfDesOther(@Path("teacher_id")String teacher_id, @QueryMap Map<String, String> queryParams);


    @FormUrlEncoded //使用@Field时记得添加@FormUrlEncoded
    @POST("attentions")
    void doAttentionOne(@Field("topic_id")String topic_id, @Field("type")String type, @Field("user_id")String user_id);

    /**
     * post请求情形二：map形式
     */
    @FormUrlEncoded
    @POST("attentions")
    void doAttentionByMap(@FieldMap Map<String, String> paramsMap );
    /**
     * 文件上传
     */
    @POST("upload/")
    Observable<Object> uploadFile(@Body RequestBody requestBody);

    @FormUrlEncoded
    @PUT("collections/{voiceId}")
    void cancelCollect(@Path("voiceId") String voiceId, @Field("end_time") String end_time);
}

package com.zongxueguan.naochanle_android.retrofitrx

import android.util.Log
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import okio.Buffer
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Created by JoJo on 2018/1/15.
 * wechat:18510829974
 * description:
 */
object RetrofitRxManager {
    private val DEFAULT_TIMEOUT = 60L
    private var retrofit: Retrofit? = null
    //请求头信息
    private val HEADER_CONNECTION = "keep-alive"
    private val BASE_URL = "https://www.sojson.com/open/api/"
    fun getRetrofit(): Retrofit? {
        if (retrofit == null) {
            synchronized(RetrofitRxManager::class.java) {
                if (retrofit == null) {
                    var mClient = OkHttpClient.Builder()
//                            .cache()//添加缓存
                            //添加公告查询参数
//                            .addInterceptor(CommonQueryParamsInterceptor ())
                            //处理多个Baseurl的拦截器
//                            .addInterceptor(MutiBaseUrlInterceptor())
                            .addInterceptor(getHeaderInterceptor())
                            .addInterceptor(LoggingInterceptor())//添加请求拦截(可以在此处打印请求信息和响应信息)
                            .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                            .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                            .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                            .build()
                    retrofit = Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .client(mClient)
                            .build()
                }
            }
        }
        return retrofit
    }

    /**
     * 获取Api接口
     */
    fun getRequestService(): ApiService {
        return getRetrofit()!!.create(ApiService::class.java)
    }

    /**
     * 设置公共查询参数
     */
    class CommonQueryParamsInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain?): Response {
            val request = chain!!.request()
            val url = request.url().newBuilder()
                    .addQueryParameter("zxg", "mingbai")
                    .addQueryParameter("zxg", "hehe")
                    .build()
            return chain!!.proceed(request.newBuilder().url(url).build())
        }
    }

    /**
     * header
     */
    class HeaderInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain?): Response {
            val request = chain!!.request()
            val requestBuilder = request.newBuilder()
                    .addHeader("api_key", "mingjiazongxueguan")
//                    .addHeader("authorization", UserConstants.AUTHORIZATION)
                    .method(request.method(), request.body())
                    .build()
            return chain!!.proceed(requestBuilder)
        }
    }

    fun getHeaderInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain!!.request()
            val requestBuilder = request.newBuilder()
                    .addHeader("Connection", HEADER_CONNECTION)
                    .addHeader("api_key", "mingjiazongxueguan")
//                    .addHeader("authorization", UserConstants.AUTHORIZATION)// TOKEN
                    .method(request.method(), request.body())
                    .build()
            chain!!.proceed(requestBuilder)
        }
    }

    /**
     * 设置缓存
     */
    class CacheInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain?): Response {
            val request = chain!!.request()
            request.newBuilder().cacheControl(CacheControl.FORCE_CACHE)
                    .build()
            val response = chain!!.proceed(request)
            val maxAge = 0
            // 有网络时 设置缓存超时时间0个小时 ,意思就是不读取缓存数据,只对get有用,post没有缓冲
            response.newBuilder()
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .removeHeader("Retrofit")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                    .build()
            // 无网络时，设置超时为4周  只对get有用,post没有缓冲
            val maxStale = 60 * 60 * 24 * 28
            response.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .removeHeader("nyn")
                    .build()
            return response
        }

    }

    /**
     * 自定义log日志打印:http://blog.csdn.net/csdn_lqr/article/details/61420753
     */
    class LoggingInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain?): Response {
            //这个chain里面包含了request和response，所以你要什么都可以从这里拿
            val request = chain!!.request()
            val t1 = System.nanoTime()//请求发起的时间
            val method = request.method()
            val jsonObject = JSONObject()
            if ("POST" == method || "PUT" == method) {
                if (request.body() is FormBody) {
                    val body = request.body() as FormBody?//as?安全转型，当转型不成功的时候，它会返回 null
                    if (body != null) {
                        for (i in 0 until body!!.size()) {
                            try {
                                jsonObject.put(body!!.name(i), body!!.encodedValue(i))
                            } catch (e: JSONException) {
                                e.printStackTrace()
                            }

                        }
                    }
                    Log.e("request", String.format("发送请求 %s on %s  %nRequestParams:%s%nMethod:%s",
                            request.url(), chain!!.connection(), jsonObject.toString(), request.method()))
                } else {
                    val buffer = Buffer()
                    val requestBody = request.body()
                    if (requestBody != null) {
                        request.body()!!.writeTo(buffer)
                        val body = buffer.readUtf8()
                        Log.e("request", String.format("发送请求 %s on %s  %nRequestParams:%s%nMethod:%s",
                                request.url(), chain!!.connection(), body, request.method()))
                    }
                }
            } else {
                Log.e("request", String.format("发送请求 %s on %s%nMethod:%s",
                        request.url(), chain!!.connection(), request.method()))
            }
            val response = chain!!.proceed(request)
            val t2 = System.nanoTime()//收到响应的时间
            val responseBody = response.peekBody((1024 * 1024).toLong())
            Log.e("request",
                    String.format("Retrofit接收响应: %s %n返回json:【%s】 %n耗时：%.1fms %nCode:%s",
                            response.request().url(),
                            responseBody.string(),
                            (t2 - t1) / 1e6, response.code()
                    ))
            return response
        }
    }

    /**
     * 该拦截器用于记录应用中的网络请求的信息
     */
    fun getHttpLogingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message ->
            //包含所有的请求信息
            //如果收到响应是json才打印
            if ("{" == message || "[" == message) {
                Log.d("TAG", "收到响应: " + message)
            }
            Log.d("TAG", "message=" + message)
        })
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    private val BASE_URL_OTHER = "http://wthrcdn.etouch.cn/"

    class MutiBaseUrlInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain?): Response {
            //获取request
            val request = chain!!.request()
            //从request中获取原有的HttpUrl实例oldHttpUrl
            val oldHttpUrl = request.url()
            //获取request的创建者builder
            val builder = request.newBuilder()
            //从request中获取headers，通过给定的键url_name
            val headerValues = request.headers("url_name")
            if (headerValues != null && headerValues.size > 0) {
                //如果有这个header，先将配置的header删除，因此header仅用作app和okhttp之间使用
                builder.removeHeader("url_name")
                //匹配获得新的BaseUrl
                val headerValue = headerValues[0]
                var newBaseUrl: HttpUrl? = null
                if ("other" == headerValue) {
                    newBaseUrl = HttpUrl.parse(BASE_URL_OTHER)
                    //                } else if ("other".equals(headerValue)) {
                    //                    newBaseUrl = HttpUrl.parse(BASE_URL_PAY);
                } else {
                    newBaseUrl = oldHttpUrl
                }
                //在oldHttpUrl的基础上重建新的HttpUrl，修改需要修改的url部分
                val newFullUrl = oldHttpUrl
                        .newBuilder()
                        .scheme("http")//更换网络协议,根据实际情况更换成https或者http
                        .host(newBaseUrl!!.host())//更换主机名
                        .port(newBaseUrl!!.port())//更换端口
                        .removePathSegment(0)//移除第一个参数v1
                        .build()
                //重建这个request，通过builder.url(newFullUrl).build()；
                // 然后返回一个response至此结束修改
                Log.e("Url", "intercept: " + newFullUrl.toString())
                return chain!!.proceed(builder.url(newFullUrl).build())
            }
            return chain!!.proceed(request)
        }
    }

    /**
     * 上传文件
     *
     * @param mImagePath
     * @return
     */
    fun getUploadFileRequestBody(imagePath: String): RequestBody {
        val file = File(imagePath)
        val requestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("file", file.name, RequestBody.create(MediaType.parse("multipart/form-data"), file))
                .build()
        return requestBody

    }
}
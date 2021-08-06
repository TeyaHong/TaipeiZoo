package com.demo.taipeizoo.data.api

import com.demo.taipeizoo.BuildConfig
import com.demo.taipeizoo.TaipeiZooApplication
import com.demo.taipeizoo.utils.NetworkUtils
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

/**
 * NetworkManager
 *
 * Created by TeyaHong on 2021/8/4
 */
object NetworkManager {
    private val retrofit by lazy {
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://data.taipei/api/v1/dataset/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val okHttpClient by lazy {
        val client = OkHttpClient.Builder()
        // 在Debug模式下設置日誌攔截器
        if (BuildConfig.DEBUG) {
            val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            client.addNetworkInterceptor(logger)
        }
        // 緩存設置
        val cacheFile =
            File(
                TaipeiZooApplication.instance.externalCacheDir,
                "cache_retrofit"
            )
        val cache = Cache(cacheFile, 1024 * 1024 * 10)
        client.cache(cache)
            .addInterceptor(OfflineInterceptor())
            .addNetworkInterceptor(OnlineInterceptor())
        client.build()
    }

    /**
     * 有網路的時候
     */
    class OnlineInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()
            val response = chain.proceed(request)
            val onlineCacheTime = 0 // 在線的時候的緩存過期時間，如果想要不緩存，直接時間設置為0
            return response.newBuilder()
                .header("Cache-Control", "public, max-age=$onlineCacheTime")
                .removeHeader("Pragma")
                .build()
        }
    }

    /**
     * 沒有網路的時候
     */
    class OfflineInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()
            if (!NetworkUtils.isConnected()) {
                // 從緩存取數據
                val newRequest = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build()
                val maxTime = 60 * 60 * 24
                val response = chain.proceed(newRequest)
                return response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, only-if-cached, max-stale=$maxTime")
                    .build()
            }
            return chain.proceed(request)
        }
    }

    fun <T> create(service: Class<T>): T {
        return retrofit.create(service)
    }
}
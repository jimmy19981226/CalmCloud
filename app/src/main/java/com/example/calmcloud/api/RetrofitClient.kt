package com.example.calmcloud.api

import com.example.calmcloud.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    val isChatConfigured: Boolean
        get() = BuildConfig.CHAT_BASE_URL.isNotBlank()

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        // Never put wellness entries or chat messages in request-body logs.
        level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BASIC
                else HttpLoggingInterceptor.Level.NONE
        redactHeader("Authorization")
    }

    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .build()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BACKEND_BASE_URL.trimEnd('/') + "/")
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val openAiRetrofit by lazy {
        check(isChatConfigured) { "Configure CALMCLOUD_CHAT_BASE_URL with your chat proxy URL" }
        Retrofit.Builder()
            .baseUrl(BuildConfig.CHAT_BASE_URL.trimEnd('/') + "/")
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    val openAiApi: ApiService by lazy {
        openAiRetrofit.create(ApiService::class.java)
    }
}

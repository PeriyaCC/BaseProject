package com.cc.baseproject.network

import android.content.Context
import com.cc.baseproject.BuildConfig
import com.cc.baseproject.common.BaseApp
import com.cc.baseproject.data.remote.PostService
import com.cc.baseproject.network.adapter.NullToEmptyStringAdapter
import com.sevenpeakssoftware.periya.network.jsonadapter.StringToBooleanAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit


object NetworkModule {


    fun makeApiService(): PostService = Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com/")
        .client(okHttpClient(BaseApp.getAppContext()).build())
        .addConverterFactory(MoshiConverterFactory.create(moshiFactory()))
        .build()
        .create(PostService::class.java)


    fun moshiFactory(): Moshi {
        return Moshi.Builder()
            .add(StringToBooleanAdapter())
            .add(NullToEmptyStringAdapter())
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    private fun okHttpClient(applicationContext: BaseApp) =
        okHttpBuilder(applicationContext)


    private fun okHttpBuilder(context: Context) = OkHttpClient.Builder()
        .addInterceptor(makeLoggingInterceptor())
        .connectTimeout(120, TimeUnit.SECONDS)
        .readTimeout(120, TimeUnit.SECONDS)


    private fun makeLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG)
            HttpLoggingInterceptor.Level.BODY
        else
            HttpLoggingInterceptor.Level.NONE
    }

}
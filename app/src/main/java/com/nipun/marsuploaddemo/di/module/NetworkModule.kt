package com.nipun.marsuploaddemo.di.module

import android.app.Application
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.nipun.marsuploaddemo.data.ApiService
import com.nipun.marsuploaddemo.utils.StringConverter
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/*
* Created by Nipun Kumar Rajput on 15-04-2020.
* Copyright (c) 2020 Nipun. All rights reserved.
*/
@Module
class NetworkModule {
    val BASE_URL = "https://api.imgbb.com/"

    @Provides
    @Singleton
    fun provideGson(): Gson {
        val gb = GsonBuilder()
        gb.setLenient()
        gb.registerTypeAdapter(String::class.java, StringConverter())
        return gb.create()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(application: Application): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val cacheDir = File(application.cacheDir, UUID.randomUUID().toString())
        // 10 MiB cache
        val cache = Cache(cacheDir, 10 * 1024 * 1024)

        return OkHttpClient.Builder()
            .cache(cache)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(gson: Gson, okHttpClient: OkHttpClient): ApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build().create(ApiService::class.java)
    }
}
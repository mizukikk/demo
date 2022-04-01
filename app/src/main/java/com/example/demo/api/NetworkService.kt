package com.example.demo.api

import com.example.demo.api.service.GithubService
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NetworkService(interceptor: Interceptor?) {
    companion object {
        private const val BASE_URL = "https://api.github.com"
        private const val WRITE_TIME_OUT = 30L
        private const val READ_TIME_OUT = 30L
        private const val CONNECT_TIME_OUT = 30L
    }

    val githubService: GithubService

    init {
        val clientBuilder = OkHttpClient.Builder()
            .writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
            .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
        if (interceptor != null) {
            clientBuilder.addInterceptor(interceptor)
        }

        val client = clientBuilder.build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        githubService = retrofit.create(GithubService::class.java)
    }
}
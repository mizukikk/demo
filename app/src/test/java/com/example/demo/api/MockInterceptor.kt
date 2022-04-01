package com.example.demo.api

import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

data class MockResponse(
    val status: Int = 0,
    val response: String = ""
)

class MockInterceptor : Interceptor {
    private var listener: MockInterceptorListener? = null

    fun setListener(listener: MockInterceptorListener) {
        this.listener = listener
    }

    interface MockInterceptorListener {
        fun setApiResponse(url: String): MockResponse
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request().url.toString()
        val response = listener?.setApiResponse(url)
        if (response != null) {
            return chain.proceed(chain.request())
                .newBuilder()
                .code(response.status)
                .body(
                    response.response.toResponseBody("text/json".toMediaTypeOrNull())
                )
                .addHeader("content-type", "application/json")
                .build()
        } else {
            val origin = chain.request()
            val request = origin
                .newBuilder()
                .method(origin.method, origin.body)
                .build()
            return chain.proceed(request)
        }
    }
}
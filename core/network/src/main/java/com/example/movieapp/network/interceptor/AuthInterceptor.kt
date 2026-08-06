package com.example.movieapp.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val bearerToken: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val requestBuilder = originalRequest.newBuilder()
            .addHeader("Authorization", "Bearer $bearerToken")
            .addHeader("accept", "application/json")

        return chain.proceed(requestBuilder.build())
    }
}
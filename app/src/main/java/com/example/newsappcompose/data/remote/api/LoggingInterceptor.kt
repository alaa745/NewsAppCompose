package com.example.newsappcompose.data.remote.api

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor

class LoggingInterceptor : Interceptor {
    private val logger = HttpLoggingInterceptor.Logger { message ->
        // Log your message here
        // Example: Log.d("OkHttp", message)
    }

    private val loggingInterceptor = HttpLoggingInterceptor(logger).apply {
        level = HttpLoggingInterceptor.Level.BODY // Change the log level as needed
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        // Log the request
        val request = chain.request()
         Log.d("OkHttp", "Request: ${request.url}")
//
        // Proceed with the request
        val response = chain.proceed(request)

        // Log the response
         Log.d("OkHttp", "Response: ${response.isSuccessful}")

        // Return the response
        return response
    }
}

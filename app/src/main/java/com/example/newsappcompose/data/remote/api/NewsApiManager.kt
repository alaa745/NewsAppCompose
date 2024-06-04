package com.example.newsappcompose.data.remote.api

import com.example.newsappcompose.data.remote.dto.GetNewsResponse
import com.example.newsappcompose.domain.Constants.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiManager {
    @GET("latest")
    suspend fun getNews(
        @Query("apikey") apiKey: String = API_KEY,
        @Query("category") category: String?,
        @Query("domain") domain: String?,
        @Query("page") page: String?,
        @Query("q") searchText: String?
    ): GetNewsResponse
}
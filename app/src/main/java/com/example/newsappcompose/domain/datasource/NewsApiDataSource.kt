package com.example.newsappcompose.domain.datasource

import androidx.paging.PagingData
import com.example.newsappcompose.data.remote.dto.GetNewsResponse
import com.example.newsappcompose.domain.model.Result
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface NewsApiDataSource {
    suspend fun getNews(page: String?, category: String?, searchText: String? = null , domain: String?): GetNewsResponse
   suspend fun getTopNews(page: String?, category: List<String>?, searchText: String? = null , domain: List<String>?): Response<GetNewsResponse>

}
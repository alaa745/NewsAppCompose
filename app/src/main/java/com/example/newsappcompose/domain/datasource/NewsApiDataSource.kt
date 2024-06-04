package com.example.newsappcompose.domain.datasource

import androidx.paging.PagingData
import com.example.newsappcompose.data.remote.dto.GetNewsResponse
import com.example.newsappcompose.domain.model.Result
import kotlinx.coroutines.flow.Flow

interface NewsApiDataSource {
    suspend fun getNews(page: String?, category: String?, searchText: String? = null , domain: String?): GetNewsResponse
}
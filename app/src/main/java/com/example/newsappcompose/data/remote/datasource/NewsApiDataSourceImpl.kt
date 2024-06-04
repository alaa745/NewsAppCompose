package com.example.newsappcompose.data.remote.datasource

import androidx.paging.PagingData
import com.example.newsappcompose.data.remote.api.NewsApiManager
import com.example.newsappcompose.data.remote.dto.GetNewsResponse
import com.example.newsappcompose.domain.datasource.NewsApiDataSource
import com.example.newsappcompose.domain.model.Result
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import javax.inject.Inject

class NewsApiDataSourceImpl @Inject constructor(private val newsApiManager: NewsApiManager): NewsApiDataSource {
    override suspend fun getNews(page: String?, category: String? , searchText: String? , domain: String?): GetNewsResponse {
        try {
            return newsApiManager.getNews(category = category , page = page , domain = domain , searchText = searchText)
        } catch (e: HttpException){
            println("data source error")
            throw e
        }
    }
}
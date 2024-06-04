package com.example.newsappcompose.data.remote.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.newsappcompose.data.remote.NewsPagingSource
import com.example.newsappcompose.domain.datasource.NewsApiDataSource
import com.example.newsappcompose.domain.model.Result
import com.example.newsappcompose.domain.repository.NewsApiRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import javax.inject.Inject

class NewsApiRepositoryImpl @Inject constructor(private val newsApiDataSource: NewsApiDataSource): NewsApiRepository {
    override fun getNews(page: String?, category: List<String>? , searchText: String? , domain: List<String>?): Flow<PagingData<Result>> {
        try {
            return Pager(
                config = PagingConfig(pageSize = 10),
                pagingSourceFactory = {
                    NewsPagingSource(
                        newsApiDataSource = newsApiDataSource,
                        category = category?.joinToString(separator = ","),
                        domain = domain?.joinToString(","),
                        searchText = searchText
                    )
                }
            ).flow
        } catch (e: HttpException){
            println("repo error")
            throw e
        }
    }
}
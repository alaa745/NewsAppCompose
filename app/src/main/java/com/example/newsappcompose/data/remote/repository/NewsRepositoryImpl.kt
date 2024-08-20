package com.example.newsappcompose.data.remote.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.newsappcompose.data.local.NewsDao
import com.example.newsappcompose.data.remote.NewsPagingSource
import com.example.newsappcompose.data.remote.dto.GetNewsResponse
import com.example.newsappcompose.domain.datasource.NewsApiDataSource
import com.example.newsappcompose.domain.model.Result
import com.example.newsappcompose.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(private val newsApiDataSource: NewsApiDataSource , private val newsDao: NewsDao): NewsRepository {
    override fun getNews(
        page: String?,
        category: List<String>?,
        searchText: String?,
        domain: List<String>?
    ): Flow<PagingData<Result>> {
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
        } catch (e: HttpException) {
            println("repo error")
            throw e
        }
    }

    override suspend fun getTopNews(
        page: String?,
        category: List<String>?,
        searchText: String?,
        domain: List<String>?
    ): Flow<GetNewsResponse> = flow {
        val response = newsApiDataSource.getTopNews(
            page = page,
            category = category,
            domain = domain,
            searchText = searchText
        )
        if (response.isSuccessful) {
            response.body()?.let {
                emit(it)
            } ?: throw Exception("Response body is null")
        } else {
            throw HttpException(response)
        }
    }

    override suspend fun insertArticles(result: Result) {
        try {
            newsDao.insertResult(result)
        }catch (e: Exception){
            throw e
        }
    }

    override suspend fun getArticleById(id: String): Result {
        return newsDao.getArticleById(id)
    }

    override suspend fun deleteArticleById(id: String) {
        return newsDao.deleteResultById(id)
    }

    override suspend fun checkArticleExists(id: String): Boolean {
        return newsDao.isArticleExists(id)
    }

    override suspend fun getAllArticles(): Flow<List<Result>> {
        return newsDao.getAllArticles()
    }
}
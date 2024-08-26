package com.example.newsappcompose.domain.repository
import androidx.paging.PagingData
import com.example.newsappcompose.data.remote.dto.GetNewsResponse
import com.example.newsappcompose.domain.model.Result
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getNews(page: String?, category: List<String>?, searchText: String? = null , domain: List<String>?): Flow<PagingData<Result>>
    suspend fun getTopNews(page: String?, category: List<String>?, searchText: String? = null , domain: List<String>?): Flow<GetNewsResponse>

    suspend fun insertArticles(result: Result)

    suspend fun getArticleById(id: String): Result

    suspend fun deleteArticleById(id: String)

    suspend fun checkArticleExists(id: String): Boolean

    suspend fun getAllSavedArticles(): Flow<List<Result>>
}
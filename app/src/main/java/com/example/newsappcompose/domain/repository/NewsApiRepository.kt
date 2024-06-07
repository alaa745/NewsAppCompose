package com.example.newsappcompose.domain.repository
import androidx.paging.PagingData
import com.example.newsappcompose.data.remote.dto.GetNewsResponse
import com.example.newsappcompose.domain.model.Result
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface NewsApiRepository {
    fun getNews(page: String?, category: List<String>?, searchText: String? = null , domain: List<String>?): Flow<PagingData<Result>>
    suspend fun getTopNews(page: String?, category: List<String>?, searchText: String? = null , domain: List<String>?): Flow<GetNewsResponse>

}
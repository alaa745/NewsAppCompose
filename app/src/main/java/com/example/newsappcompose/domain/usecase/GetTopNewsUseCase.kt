package com.example.newsappcompose.domain.usecase

import com.example.newsappcompose.data.remote.dto.GetNewsResponse
import com.example.newsappcompose.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import javax.inject.Inject

class GetTopNewsUseCase @Inject constructor(private val newsRepository: NewsRepository) {
    suspend operator fun invoke(page: String?, category: List<String>?, searchText: String? = null, domain: List<String>?): Flow<GetNewsResponse> {
        try {
            return newsRepository.getTopNews(page = page , category = category , domain = domain , searchText = searchText)
        } catch (e: HttpException){
            println("usecase error")

            throw e
        }
    }
}
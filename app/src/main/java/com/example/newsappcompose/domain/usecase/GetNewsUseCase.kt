package com.example.newsappcompose.domain.usecase

import androidx.paging.PagingData
import com.example.newsappcompose.domain.model.Result
import com.example.newsappcompose.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(private val newsRepository: NewsRepository) {
    operator fun invoke(page: String?, category: List<String>? , searchText: String? = null , domain: List<String>?): Flow<PagingData<Result>>{
        try {

            return newsRepository.getNews(page = page , category = category , domain = domain , searchText = searchText)

        } catch (e: HttpException){
            println("usecase error")
            throw e
        }
    }
}
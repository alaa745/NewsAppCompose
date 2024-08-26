package com.example.newsappcompose.domain.usecase

import com.example.newsappcompose.domain.model.Result
import com.example.newsappcompose.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSavedArticlesUseCase @Inject constructor(private val repository: NewsRepository) {
    suspend operator fun invoke(): Flow<List<Result>> {
        try {
            return repository.getAllSavedArticles()
        } catch (e:Exception){
            println("usecase error")
            throw e
        }
    }
}
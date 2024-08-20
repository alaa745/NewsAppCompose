package com.example.newsappcompose.presentation.newsDetailsScreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsappcompose.domain.model.Result
import com.example.newsappcompose.domain.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class NewsDetailsScreenViewModel @Inject constructor(private val repository: NewsRepository): ViewModel() {
    private val _isSaved = MutableLiveData(false)
    val isSaved: LiveData<Boolean?> = _isSaved

    private val _isExist = MutableLiveData(false)
    val isExist: LiveData<Boolean?> = _isExist
    fun insertArticle(result: Result){
        try {
            viewModelScope.launch {
                repository.insertArticles(result)
                _isSaved.value = true
            }
        } catch (e: Exception){
            _isSaved.value = false
            Log.d("Room Error" , e.message ?: "")
        }
    }

    fun checkArticleExistsForFirstTime(result: Result): Boolean{
        var exist = false
        try {
//            println("id $id")
            viewModelScope.launch {
                exist =  withContext(Dispatchers.IO){
                    repository.checkArticleExists(result.article_id)
                }
                _isExist.value = exist
                println("exist after ${_isExist.value}")

            }

            return exist
        } catch (e: Exception){
            _isExist.value = false
            Log.d("Room Error" , e.message ?: "")
        }
        return exist
    }
     fun insertOrDeleteArticle(result: Result): Boolean{
        var exist = false
        try {
//            println("id $id")
            viewModelScope.launch {
                exist =  withContext(Dispatchers.IO){
                    repository.checkArticleExists(result.article_id)
                }
                _isExist.value = exist
                if (exist)
                    deleteArticle(result.article_id)
                else
                    insertArticle(result)
                println("exist after ${_isExist.value}")

            }

            return exist
        } catch (e: Exception){
            _isExist.value = false
            Log.d("Room Error" , e.message ?: "")
        }
        return exist
    }

    fun deleteArticle(id: String){
        try {
            viewModelScope.launch {
                withContext(Dispatchers.IO){
                    repository.deleteArticleById(id)
                }
                _isExist.value = false
                _isSaved.value = false
            }
        } catch (e: Exception){
            _isSaved.value = false
            Log.d("Room Error" , e.message ?: "")
        }
    }
}
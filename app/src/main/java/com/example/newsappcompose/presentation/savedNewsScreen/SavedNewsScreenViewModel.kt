package com.example.newsappcompose.presentation.savedNewsScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsappcompose.domain.model.Result
import com.example.newsappcompose.domain.usecase.GetSavedArticlesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedNewsScreenViewModel @Inject constructor(private val getSavedArticlesUseCase: GetSavedArticlesUseCase):
    ViewModel() {
    private var _isLoading = MutableStateFlow(true)
    var isLoading: StateFlow<Boolean> = _isLoading

    private val _isSearching = MutableStateFlow(false)
    val isSearching: StateFlow<Boolean> = _isSearching
    private val _isError = MutableStateFlow(false)
    val isError: StateFlow<Boolean> = _isError
    private val _error = MutableStateFlow("")
    val error: StateFlow<String> = _error
    private val _savedArticles = MutableStateFlow<List<Result>?>(null)
    val savedArticles: StateFlow<List<Result>?> = _savedArticles
    init {
        fetchSavedArticles()
    }
    private fun fetchSavedArticles(){
        viewModelScope.launch {
            try {
                getSavedArticlesUseCase.invoke().collect{ articels ->
                    _savedArticles.value = articels
                    _isLoading.value = false
                }
            } catch (e: Exception) {
                _isLoading.value = false
                _isError.value = true
                _error.value = e.message ?: "Unknown error"
            }
        }
    }

    fun getSavedArticles(): Flow<List<Result>> = flow {
        try {
            val data = getSavedArticlesUseCase.invoke().catch {
                println("errorr")
            }
            _isLoading.value = false
            emitAll(data)
        } catch (e: Exception){
            println("view model error")
            _isLoading.value = false
            _isError.value = true
            _error.value = e.message!!
        }
    }
}
package com.example.newsappcompose.presentation.homeNewsScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.newsappcompose.domain.model.Result
import com.example.newsappcompose.domain.usecase.GetNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(private val getNewsUseCase: GetNewsUseCase) : ViewModel() {
    private var _isLoading = MutableStateFlow(true)
    var isLoading: StateFlow<Boolean> = _isLoading

    private val _isSearching = MutableStateFlow(false)
    val isSearching: StateFlow<Boolean> = _isSearching
    private val _isError = MutableStateFlow(false)
    val isError: StateFlow<Boolean> = _isError
    private val _error = MutableStateFlow("")
    val error: StateFlow<String> = _error
    fun getNews(domain: List<String>? = null , searchText: String? = null , category: List<String>? = null , page: String? = null): Flow<PagingData<Result>> = flow {
//        _isLoading.value = true
//        _isSearching.value = isSearching
        println("searching model $searchText")
        try {
            val data = getNewsUseCase.invoke(page = page , category = if (category?.contains("") == true || category?.isNullOrEmpty() == true) null else category , searchText = if (searchText?.trim().isNullOrEmpty()) null else searchText, domain = domain)
                .cachedIn(viewModelScope)
                .catch {
                    println("errorr")
                }
            _isLoading.value = false
//            _isSearching.value = false
            emitAll(data)
        } catch (e: HttpException){
            println("view model error")
            _isLoading.value = false
            _isError.value = true
            _error.value = e.message!!
        }
    }
}
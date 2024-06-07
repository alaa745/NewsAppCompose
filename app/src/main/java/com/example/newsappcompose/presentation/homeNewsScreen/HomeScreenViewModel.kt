package com.example.newsappcompose.presentation.homeNewsScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.newsappcompose.data.remote.dto.GetNewsResponse
import com.example.newsappcompose.domain.model.Result
import com.example.newsappcompose.domain.usecase.GetNewsUseCase
import com.example.newsappcompose.domain.usecase.GetTopNewsUseCase
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
class HomeScreenViewModel @Inject constructor(private val getNewsUseCase: GetNewsUseCase ,private val getTopNewsUseCase: GetTopNewsUseCase) : ViewModel() {
    private var _isLoading = MutableStateFlow(true)
    var isLoading: StateFlow<Boolean> = _isLoading

    private val _isSearching = MutableStateFlow(false)
    val isSearching: StateFlow<Boolean> = _isSearching
    private val _isError = MutableStateFlow(false)
    val isError: StateFlow<Boolean> = _isError
    private val _error = MutableStateFlow("")
    val error: StateFlow<String> = _error

    private var _isTopLoading = MutableStateFlow(true)
    var isTopLoading: StateFlow<Boolean> = _isTopLoading

    private val _isTopSearching = MutableStateFlow(false)
    val isTopSearching: StateFlow<Boolean> = _isTopSearching
    private val _isTopError = MutableStateFlow(false)
    val isTopError: StateFlow<Boolean> = _isTopError
    private val _topError = MutableStateFlow("")
    val topError: StateFlow<String> = _topError
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

    fun getTopNews(domain: List<String>? = null , searchText: String? = null , category: List<String>? = null , page: String? = null): Flow<GetNewsResponse> = flow {
//        _isLoading.value = true
//        _isSearching.value = isSearching
        println("searching model $searchText")
        try {
            val data = getTopNewsUseCase.invoke(page = page , category = if (category?.contains("") == true || category?.isNullOrEmpty() == true) null else category , searchText = if (searchText?.trim().isNullOrEmpty()) null else searchText, domain = domain)
                .catch {
                    println("errorr")
                }
            _isTopLoading.value = false
            emitAll(data)
//            _isSearching.value = false
//            emitAll(data)
        } catch (e: HttpException) {
            _isTopError.value = true
            _topError.value = e.message ?: "Unknown error"
        } catch (e: Exception) {
            _isTopError.value = true
            _topError.value = e.message ?: "Unknown error"
        } finally {
            _isTopLoading.value = false
        }
    }
}
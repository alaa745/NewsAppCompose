package com.example.newsappcompose.data.remote

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.newsappcompose.domain.datasource.NewsApiDataSource
import com.example.newsappcompose.domain.model.Result
import java.io.IOException

class NewsPagingSource(private val newsApiDataSource: NewsApiDataSource , private val domain: String? , private val searchText: String? , private val category: String?): PagingSource<String , Result>() {
    override fun getRefreshKey(state: PagingState<String, Result>): String? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey ?: anchorPage?.nextKey
        }
    }

    private var totalNewsCount = 0
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun load(params: LoadParams<String>): LoadResult<String, Result> {
        print("load fun")
        val page = params.key
        return try {
            val newsResponse = newsApiDataSource.getNews(page = page , category = category , domain = domain , searchText = searchText)
            totalNewsCount += newsResponse.results.size
            val articles = newsResponse.results
            LoadResult.Page(
                data = articles,
                nextKey = if (totalNewsCount == newsResponse.totalResults) null else newsResponse.nextPage,
                prevKey = null
            )
        }  catch (exception: IOException) {
            // Handle network errors
            LoadResult.Error(exception)
        } catch (exception: retrofit2.HttpException) {
            // Handle API errors
            println("paging error")

            LoadResult.Error(exception)
//            throw exception
        }
    }

}
package com.example.newsappcompose.presentation.savedNewsScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.newsappcompose.R
import com.example.newsappcompose.destination.Destination
import com.example.newsappcompose.domain.model.Result
import com.example.newsappcompose.presentation.components.NewsCard
import com.example.newsappcompose.presentation.components.SearchBar
import com.example.newsappcompose.presentation.homeNewsScreen.BottomNavigationBar
import com.example.newsappcompose.presentation.homeNewsScreen.ErrorView
import com.google.gson.Gson
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SavedNewsScreen(viewModel: SavedNewsScreenViewModel = hiltViewModel() , navHostController: NavHostController){
    val isLoading by viewModel.isLoading.collectAsState()
    val isError by viewModel.isError.collectAsState()
    val newsFlow = viewModel.getSavedArticles()
    val savedNews by viewModel.savedArticles.collectAsState()

    val error by viewModel.error.collectAsState()
    var searchText by remember {
        mutableStateOf("")
    }
    val filteredNews by remember {
        derivedStateOf{
            if (searchText.isBlank()) savedNews else savedNews?.filter { result ->
                result.title!!.contains(searchText , ignoreCase = true) || result.description!!.contains(searchText , ignoreCase = true)
            }
        }
    }
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp)
                .systemBarsPadding(),
            ) {
            Text(
                text = "Bookmark",
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold
            )
            Box(modifier = Modifier.padding(top = 20.dp))
            SearchBar(
                onValueChange = {
                    searchText = it
                },
                value = searchText ?: "",
            ){
//                isSearch = true
//                viewModel.getNews(searchText = searchTextForApi)
//                searchTextForApi = ""h
//                filterNews(filteredText = searchText , filteredNews = filteredNews?.toMutableList() , newsList = savedNews)
                println("Search ${searchText}")
                searchText = ""

            }
            when {
                isLoading ->{
                    println("loadinggggg")
                }
                isError -> {
                    println("errrorrrr")
                    ErrorView(error = error)
                }
                else ->{
                    LazyColumn(
//                        contentPadding = innerPadding,
                        modifier = Modifier
//                            .padding(start = 10.dp, end = 10.dp)
                            .padding(top = 10.dp)
                            .systemBarsPadding(),

                        ) {
                        filteredNews.let { articles ->
                            if (articles != null) {
                                items(articles.size) { index ->
                                    val article = articles[index]
                                    NewsCard(
                                        result = article,
                                        placeHolderImage = R.drawable.small_placeholder,
                                        onClick = { desc ->
                                            val articleJson = Gson().toJson(article)
                                            val encodedCountriesJson = URLEncoder.encode(articleJson, StandardCharsets.UTF_8.toString())
                                            navHostController.navigate("${Destination.NewsDetailsScreen.route}/$encodedCountriesJson")
                        //                                        println("Desc $desc")
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

}